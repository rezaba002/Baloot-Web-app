package org.example.classes;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class ApiHandler {
    public static void downloadData() throws IOException, InterruptedException {
        final String Users_API_URL = "http://5.253.25.110:5000/api/users";
        final String Providers_API_URL = "http://5.253.25.110:5000/api/providers";
        final String Commodities_API_URL = "http://5.253.25.110:5000/api/commodities";
        final String Comments_API_URL = "http://5.253.25.110:5000/api/comments";
        final String Discount_API_URL = "http://5.253.25.110:5000/api/discount";

        Store storeObj = Store.getInstance();
        Gson gsonObj = new Gson();
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest requestUsers = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/Json")
                .uri(URI.create(Users_API_URL))
                .build();
        storeObj.addUser(client, requestUsers, gsonObj);
        HttpRequest requestProviders = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/Json")
                .uri(URI.create(Providers_API_URL))
                .build();
        storeObj.addProvider(client, requestProviders, gsonObj);
        HttpRequest requestCommodities = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/Json")
                .uri(URI.create(Commodities_API_URL))
                .build();
        storeObj.addCommodity(client, requestCommodities, gsonObj);
        HttpRequest requestComments = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/Json")
                .uri(URI.create(Comments_API_URL))
                .build();
        storeObj.addComment(client, requestComments, gsonObj);
        HttpRequest requestDiscounts = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/Json")
                .uri(URI.create(Discount_API_URL))
                .build();
        storeObj.addDiscount(client, requestDiscounts, gsonObj);
    }
}
