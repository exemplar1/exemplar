package org.planetarysystem.service;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.planetarysystem.api.PlanetarySystemService;
import org.planetarysystem.contract.Planet;
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

    private Mapper mapper = new DozerBeanMapper();

    @Override
    public Optional<Planet> retrievePlanet(Long id) {
        Planet response = null;
        org.planetarysystem.domain.Planet planet = planetDao.retrieveById(id);

        if(planet != null) {
            LOGGER.info("Retrieved planet " + planet);
            response = mapper.map(planet, org.planetarysystem.contract.Planet.class);
        }
        else {
            LOGGER.info(String.format("Planet with id %s not found", id));
        }
        return Optional.fromNullable(response);
    }

    @Override
    public ImmutableList<Planet> retrieveAll() {
        List<org.planetarysystem.domain.Planet> planets = planetDao.retrieveAll();

        List<Planet> result = Lists.newArrayList();
        if(!planets.isEmpty()) {
            for(org.planetarysystem.domain.Planet planet : planets) {
                result.add(
                        mapper.map(planet, org.planetarysystem.contract.Planet.class)
                );
            }
        }

        LOGGER.info(String.format("Returning %s planets", planets.size()));
        return ImmutableList.copyOf(result);
    }

    @Override
    public Planet insertOrUpdate(Planet incoming) {
        org.planetarysystem.domain.Planet planet = mapper.map(incoming, org.planetarysystem.domain.Planet.class);
        org.planetarysystem.domain.Planet updated = planetDao.insertOrUpdate(planet);
        Planet result = mapper.map(updated, org.planetarysystem.contract.Planet.class);
        LOGGER.info(String.format("Inserted/Updated planet %s", planet));
        return result;
    }

    @Override
    public Planet delete(Long id) {
        org.planetarysystem.domain.Planet deleted = planetDao.delete(id);
        Planet result = mapper.map(deleted, org.planetarysystem.contract.Planet.class);
        LOGGER.info(String.format("Deleted planet %s", deleted));
        return result;
    }

    @Override
    public Planet delete(Planet incoming) {
        org.planetarysystem.domain.Planet planet = mapper.map(incoming, org.planetarysystem.domain.Planet.class);
        org.planetarysystem.domain.Planet deleted = planetDao.delete(planet);
        Planet result = mapper.map(deleted, org.planetarysystem.contract.Planet.class);
        LOGGER.info(String.format("Deleted planet %s", deleted));
        return result;
    }
}
