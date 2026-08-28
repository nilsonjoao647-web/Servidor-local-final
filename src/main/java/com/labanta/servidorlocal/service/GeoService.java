package com.labanta.servidorlocal.service;


import com.labanta.servidorlocal.dto.GeoLocationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeoService {
    private final RestTemplate restTemplate;

    public GeoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GeoLocationResponse localizarIp(String ip) {

        String url = "https://ipapi.co/" + ip + "/json/";

        return restTemplate.getForObject(
                url,
                GeoLocationResponse.class
        );
    }
}
