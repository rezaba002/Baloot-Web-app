package com.example.Baloot5Backend.controller;

import com.example.Baloot5Backend.model.Provider;
import com.example.Baloot5Backend.model.Store;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.StringTokenizer;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/")
public class ProviderController {
    Store store = Store.getInstance();

    @GetMapping("providers")
    public List<Provider> getProviders() {
        return store.getProviders();
    }

    @PostMapping("provider")
    public Provider sendProvider(@RequestBody String providerId) {
        StringTokenizer tokenizer = new StringTokenizer(providerId,"=");
        String id = tokenizer.nextToken();
        for (Provider providerObj : store.getProviders()) {
            if (providerObj.getId() == Integer.valueOf(id)) {
                return providerObj;
            }
        }
        return null;
    }
}
