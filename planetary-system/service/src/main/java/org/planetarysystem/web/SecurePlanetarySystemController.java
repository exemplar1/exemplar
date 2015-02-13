package org.planetarysystem.web;

import org.planetarysystem.api.PlanetarySystemService;
import org.planetarysystem.contract.Planet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
@RequestMapping("/secure/planetary.system/")
public class SecurePlanetarySystemController {

    @Autowired
    private PlanetarySystemService planetarySystemService;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/planet/create", method = RequestMethod.POST)
    public ResponseEntity<Planet> create(@RequestBody Planet planet) {
        Planet created = planetarySystemService.insertOrUpdate(planet);
        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/planet/update", method = RequestMethod.POST)
    public ResponseEntity<org.planetarysystem.contract.Planet> update(@RequestBody Planet planet) {
        Planet updated = planetarySystemService.insertOrUpdate(planet);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/planet/delete/{planetId}", method = RequestMethod.GET)
    public ResponseEntity<org.planetarysystem.contract.Planet> deleteById(@PathVariable Long planetId) {
        Planet deleted = planetarySystemService.delete(planetId);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/planet/delete", method = RequestMethod.POST)
    public ResponseEntity<org.planetarysystem.contract.Planet> delete(@RequestBody Planet planet) {
        Planet deleted = planetarySystemService.delete(planet);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
}
