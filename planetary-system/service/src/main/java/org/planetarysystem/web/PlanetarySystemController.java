package org.planetarysystem.web;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.planetarysystem.api.PlanetarySystemService;
import org.planetarysystem.contract.Planet;
import org.planetarysystem.contract.Planets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
@RequestMapping("/planetary.system/")
public class PlanetarySystemController {

    @Autowired
    private PlanetarySystemService planetarySystemService;

    @RequestMapping(value = "/planet/{planetId}", method = RequestMethod.GET)
    public ResponseEntity<Planet> find(@PathVariable Long planetId) {
        Optional<Planet> planet = planetarySystemService.retrievePlanet(planetId);
        return new ResponseEntity<>(planet.orNull(), HttpStatus.OK);
    }

    @RequestMapping(value = "/planet/all", method = RequestMethod.POST)
    public ResponseEntity<Planets> findAll() {
        ImmutableList<Planet> planets = planetarySystemService.retrieveAll();
        Planets response = new Planets(planets);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
