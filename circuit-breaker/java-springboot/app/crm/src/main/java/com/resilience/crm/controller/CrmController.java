package com.resilience.crm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/crm")
public class CrmController {

    private RestTemplate restTemplate;

    @GetMapping
    public String crm(){
        restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:8081/client", String.class);
    }
}
