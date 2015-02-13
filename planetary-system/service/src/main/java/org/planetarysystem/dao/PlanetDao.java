package org.planetarysystem.dao;


import org.planetarysystem.domain.Planet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class PlanetDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlanetDao.class);

    private static final String SELECT_BY_ID = "SELECT * FROM PLANETS WHERE ID = %s";

    private static final String INSERT =
            "INSERT INTO PLANETS (NAME, RADIUS, CIRCUMFERENCE, AREA, VOLUME, MASS) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE =
            "UPDATE PLANETS SET RADIUS = ?, CIRCUMFERENCE = ?, AREA = ?, VOLUME = ?, MASS = ?";

    private static final String DELETE = "DELETE FROM PLANETS WHERE ID = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Planet retrieveById(Long id) {
        final BeanPropertyRowMapper<Planet> rowMapper = new BeanPropertyRowMapper<>(Planet.class);

        Planet planet = jdbcTemplate.query(String.format(SELECT_BY_ID, id),
                new ResultSetExtractor<Planet>() {
                    @Override
                    public Planet extractData(ResultSet rs) throws SQLException, DataAccessException {
                        return rs.next() ? rowMapper.mapRow(rs, 0) : null;
                    }
                });
        LOGGER.debug(String.format("Retrieved planet %s", planet));
        return planet;
    }

    public List<Planet> retrieveAll() {
        List<Planet> planets = jdbcTemplate.query("SELECT * FROM PLANETS", new BeanPropertyRowMapper<>(Planet.class));
        LOGGER.debug(String.format("Returning %s planets", planets.size()));
        return planets;
    }

    public Planet insertOrUpdate(final Planet planet) {
        if(planet.getId() == null) {
            KeyHolder holder = new GeneratedKeyHolder();

            jdbcTemplate.update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, planet.getName());
                    ps.setBigDecimal(2, planet.getRadius());
                    ps.setBigDecimal(3, planet.getCircumference());
                    ps.setBigDecimal(4, planet.getArea());
                    ps.setBigDecimal(5, planet.getVolume());
                    ps.setBigDecimal(6, planet.getMass());
                    return ps;
                }
            }, holder);

            Long newPlanetId = holder.getKey().longValue();
            planet.setId(newPlanetId);
            LOGGER.debug(String.format("Inserted planet %s", planet));
        }
        else {
            Planet existing = jdbcTemplate.queryForObject(String.format(SELECT_BY_ID, planet.getId()),
                    new BeanPropertyRowMapper<>(Planet.class));

            if(existing == null) {
                throw new IllegalStateException(String.format("Planet with Id %s not found", planet.getId()));
            }

            jdbcTemplate.update(UPDATE, planet.getRadius(),
                    planet.getCircumference(), planet.getArea(), planet.getVolume(), planet.getMass());
            LOGGER.debug(String.format("Updated planet %s", planet));
        }

        return planet;
    }

    public Planet delete(Planet planet) {
        if(planet == null) {
            throw new IllegalStateException("Planet cannot be null");
        }
        return delete(planet.getId());
    }

    public Planet delete(Long id) {
        if(id == null) {
            throw new IllegalStateException("Planet Id cannot be null");
        }

        Planet existing = jdbcTemplate.queryForObject(String.format(SELECT_BY_ID, id),
                new BeanPropertyRowMapper<>(Planet.class));
        jdbcTemplate.update(DELETE, id);
        return existing;
    }
}
