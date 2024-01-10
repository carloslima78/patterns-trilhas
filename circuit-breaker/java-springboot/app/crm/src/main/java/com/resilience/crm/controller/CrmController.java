package com.resilience.crm.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/crm")
public class CrmController {

    private RestTemplate restTemplate;

    @GetMapping
    @CircuitBreaker(name = "crm", fallbackMethod = "crmFallback")
    public List<Map<String, Object>> crm(){
        restTemplate = new RestTemplate();
        // Faz a requisição ao serviço cliente para obter a lista de clientes
        List<Map<String, Object>> clients = restTemplate.getForObject("http://localhost:8081/client", List.class);

        // Adiciona uma mensagem específica do serviço CRM
        String crmMessage = "Oferta especial de 10% de desconto em todos os produtos!";

        // Adiciona a mensagem à lista de clientes
        for (Map<String, Object> client : clients) {
            client.put("Mensagem", crmMessage);
        }

        return clients;
    }

    public String crmFallback(Exception e){
        return "Fallback para o serviço CRM";
    }
}
