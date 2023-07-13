package com.example.baloot6backend.Controller;

import com.example.baloot6backend.Model.Provider;
import com.example.baloot6backend.Repository.ProviderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.StringTokenizer;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/")
public class ProviderController {
    @Autowired
    private ProviderRepository providerRepository;
    @GetMapping("provider/{id}")
    public Provider getProvider(@PathVariable("id") Long providerId){
        System.out.println("provideram");
        Provider providerObj = providerRepository.getReferenceById(providerId);
        System.out.println("hanooz provideram");
        System.out.println(providerObj);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return providerObj;
    }
}
