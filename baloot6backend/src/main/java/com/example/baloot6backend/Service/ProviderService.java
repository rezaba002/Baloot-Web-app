package com.example.baloot6backend.Service;

import com.example.baloot6backend.Model.Provider;
import com.example.baloot6backend.Model.User;
import com.example.baloot6backend.Repository.ProviderRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProviderService {

    @Autowired
    private ProviderRepository providerRepository;
    public void addProvider(HttpClient client, HttpRequest request, Gson gson) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type providersList = new TypeToken<ArrayList<Provider>>() {}.getType();
        ArrayList<Provider> providers = new ArrayList<Provider>();

        System.out.println("providerService");
        providers = gson.fromJson(response.body(), providersList);
        System.out.println(providers);
        List<Provider> availableProviders = providerRepository.findAll();
        if (availableProviders.isEmpty())
            providerRepository.saveAll(providers);

    }
    public List<Provider> getAllProviders(){
        List<Provider> providers = providerRepository.findAll();
        return providers;
    }
}
