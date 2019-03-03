package com.example.presence.integration;

import com.example.presence.model.Access;
import com.example.presence.model.FingerprintScan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CaptureIntegrationTests {

    @Test
    public void loginTest() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:8080/scan/read";
        URI uri = new URI(baseUrl);

        final Date now = new Date();
        final String fingerprintHash = "4d8276c6732e92fd37fe6a3f9f58284a";
        FingerprintScan scan = new FingerprintScan(fingerprintHash, now, Access.LOGIN);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, scan, String.class);

        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void logoutTest() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:8080/scan/read";
        URI uri = new URI(baseUrl);

        final Date now = new Date();
        final String fingerprintHash = "4d8276c6732e92fd37fe6a3f9f58284a";
        FingerprintScan scan = new FingerprintScan(fingerprintHash, now, Access.LOGOUT);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, scan, String.class);

        assertEquals(200, result.getStatusCodeValue());
    }

}
