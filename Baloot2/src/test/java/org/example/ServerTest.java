package org.example;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {
    Store store = new Store();
    @BeforeClass
    public static void startServer() throws IOException, InterruptedException {
        Server.run();
    }
    @Test
    void searchPriceTest() throws IOException {
        int responseStatus = Jsoup.connect("http://localhost:5000/commodities/search/1000/450000").execute().statusCode();
        assertEquals(200, responseStatus);
        HttpResponse<String> response = Unirest.get("http://localhost:5000/commodities/search/3000000/250000").asString();
        //responseStatus = Jsoup.connect("http://localhost:5000/commodities/search/3000000/250000").execute().statusCode();
        assertEquals(400, response.getStatus());
    }
    @Test
    void rateCommodityTest() throws IOException {
        int responseStatus = Jsoup.connect("http://localhost:5000/rateCommodity/amir/2/5").execute().statusCode();
        assertEquals(200, responseStatus);
        HttpResponse response = Unirest.get("http://localhost:5000/rateCommodity/amir/2/15").asString();
        assertEquals(400, response.getStatus());
    }
    @Test
    void getBuyListTest() throws Exception {
        int responseStatus = Jsoup.connect("http://localhost:5000/users/hamid/getBuyList").execute().statusCode();
        assertEquals(200,responseStatus);
        HttpResponse response = Unirest.get("http://localhost:5000/users/ahmad/getBuyList").asString();
        assertEquals(400, response.getStatus());

                // if the user page works correctly, it will contain the buylist.
        responseStatus = Jsoup.connect("http://localhost:5000/users/hamid").execute().statusCode();
        assertEquals(200, responseStatus);
        response = Unirest.get("http://localhost:5000/users/ahmad").asString();
        assertEquals(400, response.getStatus());
    }
}