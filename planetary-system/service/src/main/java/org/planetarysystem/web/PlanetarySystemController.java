package org.planetarysystem.web;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.planetarysystem.contract.Planets;
import org.planetarysystem.domain.Planet;
import org.planetarysystem.service.PlanetarySystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

@RestController
@ResponseBody
@RequestMapping("/planetary.system/service")
public class PlanetarySystemController extends AbstractController {

    @Autowired
    private PlanetarySystemService planetarySystemService;
    private Mapper mapper = new DozerBeanMapper();

    @RequestMapping(value = "/retrieveAllPlanets", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Planets> findAll(@RequestHeader(value = "Content-Type", required = false) String contentType) {
        ImmutableList<Planet> planets = planetarySystemService.retrieveAll();

        List<org.planetarysystem.contract.Planet> result = Lists.newArrayList();
        if (!planets.isEmpty()) {
            for (Planet planet : planets) {
                result.add(
                        mapper.map(planet, org.planetarysystem.contract.Planet.class)
                );
            }
        }

        Planets response = new Planets(result);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(getMediaType(contentType));

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    private MediaType getMediaType(String contentType) {
        MediaType result = MediaType.APPLICATION_XML;
        if (!isNullOrEmpty(contentType)) {
            try {
                result = MediaType.valueOf(contentType);
            } catch (Exception e) {
                logger.warn("User provided an incorrect content type: " + contentType);
            }
        }
        return result;
    }
}
