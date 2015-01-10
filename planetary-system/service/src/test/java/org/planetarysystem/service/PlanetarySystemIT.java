package org.planetarysystem.service;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.planetarysystem.PlanetarySystemApplication;
import org.planetarysystem.contract.Planets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {PlanetarySystemApplication.class})
@WebAppConfiguration
@IntegrationTest(value = {"server.port:0"})
@DirtiesContext
public class PlanetarySystemIT {

    @Value("${local.server.port}")
    private int port;

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testWebAgentLoginService() {
        String url = String.format("http://localhost:%s/planetary.system/service/retrieveAllPlanets", port);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Planets> response = restTemplate.exchange(url, HttpMethod.POST, request, Planets.class);
        System.out.println(response.getBody());
    }
}