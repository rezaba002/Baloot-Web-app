package com.example.baloot6backend.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class ApiHandler {
    @Autowired
    private UserService userService;
    @Autowired
    private ProviderService providerService;
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private DiscountService discountService;
    public void downloadData() throws IOException, InterruptedException {
        final String Users_API_URL = "http://5.253.25.110:5000/api/users";
        final String Providers_API_URL = "http://5.253.25.110:5000/api/v2/providers";
        final String Commodities_API_URL = "http://5.253.25.110:5000/api/v2/commodities";
        final String Comments_API_URL = "http://5.253.25.110:5000/api/comments";
        final String Discount_API_URL = "http://5.253.25.110:5000/api/discount";

        Gson gsonObj = new Gson();
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest requestUsers = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/Json")
                .uri(URI.create(Users_API_URL))
                .build();
        userService.addUser(client, requestUsers, gsonObj);

        HttpRequest requestProviders = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/Json")
                .uri(URI.create(Providers_API_URL))
                .build();
        providerService.addProvider(client, requestProviders, gsonObj);

        HttpRequest requestCommodities = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/Json")
                .uri(URI.create(Commodities_API_URL))
                .build();
        commodityService.addCommodity(client, requestCommodities, gsonObj);
//        HttpRequest requestComments = HttpRequest.newBuilder()
//                .GET()
//                .header("accept", "application/Json")
//                .uri(URI.create(Comments_API_URL))
//                .build();
//        storeObj.addComment(client, requestComments, gsonObj);

        HttpRequest requestDiscounts = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/Json")
                .uri(URI.create(Discount_API_URL))
                .build();
        discountService.addDiscount(client, requestDiscounts, gsonObj);
    }
}
