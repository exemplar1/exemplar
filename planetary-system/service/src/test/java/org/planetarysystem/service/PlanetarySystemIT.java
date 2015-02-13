package org.planetarysystem.service;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.planetarysystem.PlanetarySystemApplication;
import org.planetarysystem.contract.Planet;
import org.planetarysystem.contract.Planets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {PlanetarySystemApplication.class})
@WebAppConfiguration
@IntegrationTest(value = {"server.port:0"})
@DirtiesContext
public class PlanetarySystemIT {

    private static final Long EXISTING_ID = 8l;
    private static final Long NON_EXISTING_ID = 10l;

    @Value("${local.server.port}")
    private int port;

    private RestTemplate restTemplate = new RestTemplate();
    private RestTemplate secureRestTemplate = new TestRestTemplate("ironman", "triathlete");

    @Test
    public void testRetrieval() {
        Planet planet = retrieveById(EXISTING_ID);
        assertNotNull(planet);
        assertEquals(BigInteger.valueOf(EXISTING_ID), planet.getId());
    }

    @Test
    public void testRetrievalWithNullResponse() {
        Planet planet = retrieveById(NON_EXISTING_ID);
        assertNull(planet);
    }

    @Test
    public void testRetrieveAllPlanets() {
        Planets planets = retrieveAll();
        assertNotNull(planets);
        assertTrue(planets.getPlanets().size() >= 8);
    }

    @Test
    public void testCreate() {
        Planet planet = new Planet().withName("Pluto")
                .withRadius(BigDecimal.valueOf(1.184))
                .withCircumference(BigDecimal.valueOf(7.232))
                .withArea(BigDecimal.valueOf(16600000))
                .withVolume(new BigDecimal("6390000000"))
                .withMass(BigDecimal.valueOf(1.30900).multiply(BigDecimal.TEN.pow(22)));

        Planet created = create(planet);
        assertNotNull(created);
        assertEquals(BigInteger.valueOf(9), created.getId());

        Planets allPlanets = retrieveAll();
        assertNotNull(allPlanets);
        assertEquals(9, allPlanets.getPlanets().size());
    }

    @Test
    public void testUpdate() {
        Planet planet = retrieveById(EXISTING_ID);
        assertNotNull(planet);
        assertEquals(BigDecimal.valueOf(2462200, 2), planet.getRadius());

        BigDecimal radius = BigDecimal.valueOf(24630);
        planet.setRadius(radius);
        Planet updated = update(planet);
        assertEquals(radius, updated.getRadius());
    }

    @Test
    public void testDelete() {
        Long elysiumId = 10l;

        Planet elysium = new Planet().withName("Elysium")
                .withRadius(BigDecimal.valueOf(1.184))
                .withCircumference(BigDecimal.valueOf(7.232))
                .withArea(BigDecimal.valueOf(16600000))
                .withVolume(new BigDecimal("6390000000"))
                .withMass(BigDecimal.valueOf(1.30900).multiply(BigDecimal.TEN.pow(22)));

        Planet created = create(elysium);
        assertNotNull(created);
        assertEquals(BigInteger.valueOf(elysiumId), created.getId());

        Planet deleted = deleteById(elysiumId);
        assertNotNull(deleted);
        assertEquals(BigInteger.valueOf(elysiumId), deleted.getId());

        assertNull(retrieveById(elysiumId));
    }

    private Planet retrieveById(Long id) {
        String url = String.format("http://localhost:%s/planetary.system/planet/%s", port, id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Planet> response = restTemplate.exchange(url, HttpMethod.GET, request, Planet.class);
        return response.getBody();
    }

    private Planets retrieveAll() {
        String url = String.format("http://localhost:%s/planetary.system/planet/all", port);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Planets> response = restTemplate.exchange(url, HttpMethod.POST, request, Planets.class);
        return response.getBody();
    }

    private Planet create(Planet planet) {
        String createUrl = String.format("http://localhost:%s/secure/planetary.system/planet/create", port);
        return exchange(createUrl, planet);
    }

    private Planet update(Planet planet) {
        String updateUrl = String.format("http://localhost:%s/secure/planetary.system/planet/update", port);
        return exchange(updateUrl, planet);
    }

    private Planet deleteById(Long id) {
        String url = String.format("http://localhost:%s/secure/planetary.system/planet/delete/%s", port, id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Planet> response = secureRestTemplate.exchange(url, HttpMethod.GET, request, Planet.class);
        return response.getBody();
    }

    private Planet exchange(String url, Planet planet) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON));

        HttpEntity<Planet> request = new HttpEntity<>(planet, headers);
        ResponseEntity<Planet> response = secureRestTemplate.exchange(url, HttpMethod.POST, request, Planet.class);
        return response.getBody();
    }
}