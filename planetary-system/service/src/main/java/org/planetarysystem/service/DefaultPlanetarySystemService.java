package org.planetarysystem.service;

import com.google.common.collect.ImmutableList;
import org.planetarysystem.domain.Planet;
import org.planetarysystem.dao.PlanetDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultPlanetarySystemService implements PlanetarySystemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPlanetarySystemService.class);

    @Autowired
    private PlanetDao planetDao;

    @Override
    public ImmutableList<Planet> retrieveAll() {
        List<Planet> planets = planetDao.retrieveAll();
        LOGGER.info(String.format("Returning %s planets", planets.size()));
        return ImmutableList.copyOf(planets);
    }
}
