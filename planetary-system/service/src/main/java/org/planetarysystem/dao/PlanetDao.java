package org.planetarysystem.dao;


import org.planetarysystem.domain.Planet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlanetDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlanetDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Planet> retrieveAll() {
        List<Planet> planets = jdbcTemplate.query("SELECT * FROM PLANETS", new BeanPropertyRowMapper<Planet>(Planet.class));
        LOGGER.debug(String.format("Returning %s planets", planets.size()));
        return planets;
    }
}
