package com.resilience.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client")
public class ClientController {

    @GetMapping
    public List<Map<String, String>> client(){

        List<Map<String, String>> clients = new ArrayList<>();

        Map<String, String> client1 = new HashMap<>();
        client1.put("nome", "João");
        client1.put("email", "joao@email.com");
        client1.put("telefone", "123456789");

        Map<String, String> client2 = new HashMap<>();
        client2.put("nome", "Maria");
        client2.put("email", "maria@email.com");
        client2.put("telefone", "987654321");

        clients.add(client1);
        clients.add(client2);

        return clients;
    }
}

