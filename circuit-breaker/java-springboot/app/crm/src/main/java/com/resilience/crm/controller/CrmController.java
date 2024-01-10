package com.resilience.crm.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/crm")
public class CrmController {

    private RestTemplate restTemplate;

    @GetMapping("/circuitbreaker")
    @CircuitBreaker(name = "crm", fallbackMethod = "ciruitBreakerFallback")
    public String getClientCircuitBreaker(){
        restTemplate = new RestTemplate();

        // Faz a requisição ao serviço cliente para obter a lista de clientes
        List<Map<String, Object>> clients = restTemplate.getForObject("http://localhost:8081/client", List.class);

        // Adiciona uma mensagem específica do serviço CRM a cada cliente e concatena as mensagens
        String crmMessage = clients.stream()
                .map(client -> "Proposta enviada ao cliente " + client.get("nome") + " no email " + client.get("email"))
                .collect(Collectors.joining("\n"));

        return crmMessage;
    }

    int count = 1;

    @GetMapping("/retry")
    @Retry(name = "crm")
    public String getClientRetry(){

        System.out.println("Foram " + count++ + " tentativas de requisições ao serviço de clientes");

        restTemplate = new RestTemplate();

        // Faz a requisição ao serviço cliente para obter a lista de clientes
        List<Map<String, Object>> clients = restTemplate.getForObject("http://localhost:8081/client", List.class);

        // Adiciona uma mensagem específica do serviço CRM a cada cliente e concatena as mensagens
        String crmMessage = clients.stream()
                .map(client -> "Proposta enviado ao cliente " + client.get("nome") + " no email " + client.get("email"))
                .collect(Collectors.joining("\n"));

        return crmMessage;
    }

    @GetMapping("/ratelimiter")
    @RateLimiter(name = "crm", fallbackMethod = "reteLimiterBreakerFallback")
    public String getClientRateLimiter(){

        restTemplate = new RestTemplate();

        // Faz a requisição ao serviço cliente para obter a lista de clientes
        List<Map<String, Object>> clients = restTemplate.getForObject("http://localhost:8081/client", List.class);

        // Adiciona uma mensagem específica do serviço CRM a cada cliente e concatena as mensagens
        String crmMessage = clients.stream()
                .map(client -> "Proposta enviado ao cliente " + client.get("nome") + " no email " + client.get("email"))
                .collect(Collectors.joining("\n"));

        return crmMessage;
    }

    public String ciruitBreakerFallback(Exception e){
        return "Fallback: Serviço de clientes está fora do ar";
    }

    public String reteLimiterBreakerFallback(Exception e){
        return "Fallback: Estourou a quantidade de chamadas";
    }
}
