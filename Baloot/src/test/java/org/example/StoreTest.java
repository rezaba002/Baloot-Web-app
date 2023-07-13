package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;


import java.util.*;

class StoreTest {
    Store store = new Store();
    Gson gson = new Gson();

    @Test
    void userNameIncludesIllegalChar() {
        //testUser = "{\"username\": \"reza\", \"password\": \"5634\", \"email\": \"reza@gmail.com\", \"birthDate\": \"2002-04-23\", \"address\": \"address1\", \"credit\": 1500}";
        assertFalse(store.usernameValidation("re%za"));
    }
    @Test
    void userNameIncludeslegalChar() {
        assertTrue(store.usernameValidation("reza"));
    }
    @Test
    void providerNameIncludesIllegalChar() {
        String jSon = "{\"id\": 1, \"name\": \"provi&der1\", \"registryDate\": \"2023-09-15\"}";
        Provider provider = gson.fromJson(jSon, Provider.class);
        assertFalse(store.providerValidation(provider));
    }
    @Test
    void providerNameIncludesIllegalCharTrueCase() {
        String jSon = "{\"id\": 1, \"name\": \"provider1\", \"registryDate\": \"2023-09-15\"}";
        Provider provider = gson.fromJson(jSon, Provider.class);
        assertTrue(store.providerValidation(provider));
    }
    @Test
    void providerIdBeingUnic() {
        String jSon = "{\"id\": 1, \"name\": \"provider1\", \"registryDate\": \"2023-09-15\"}";
        Provider provider1 = gson.fromJson(jSon, Provider.class);
        assertTrue(store.providerValidation(provider1));
        store.addProvider(jSon,gson);
        String jSon2 = "{\"id\": 1, \"name\": \"provider2\", \"registryDate\": \"2023-09-20\"}";
        Provider provider2 = gson.fromJson(jSon2, Provider.class);
        assertFalse(store.providerValidation(provider2));
        store.addProvider(jSon2,gson);
    }
    @Test
    void providerIdBeingUnicTrueCase() {
        String jSon = "{\"id\": 1, \"name\": \"provider1\", \"registryDate\": \"2023-09-15\"}";
        Provider provider1 = gson.fromJson(jSon, Provider.class);
        assertTrue(store.providerValidation(provider1));
        store.addProvider(jSon,gson);
        String jSon2 = "{\"id\": 2, \"name\": \"provider2\", \"registryDate\": \"2023-09-20\"}";
        Provider provider2 = gson.fromJson(jSon2, Provider.class);
        assertTrue(store.providerValidation(provider2));
        store.addProvider(jSon2,gson);
    }
    @Test
    void commodityDosentExistFalseTest(){
        String jSonprovider = "{\"id\": 1, \"name\": \"provider1\", \"registryDate\": \"2023-09-15\"}";
        Provider provider1 = gson.fromJson(jSonprovider, Provider.class);
        store.addProvider(jSonprovider,gson);
        String jSon2provider = "{\"id\": 2, \"name\": \"provider2\", \"registryDate\": \"2023-09-20\"}";
        Provider provider2 = gson.fromJson(jSon2provider, Provider.class);
        store.addProvider(jSon2provider,gson);

        String jSon = "{\"id\": 1, \"name\": \"Headphone\", \"providerId\": 1, \"price\": 35000, \"categories\":[\"Technology\", \"Phone\"], \"rating\": 8.8, \"inStock\": 50}";
        Commodity commodity1 = gson.fromJson(jSon, Commodity.class);
        store.addCommodity(jSon,gson);
        String jSon2 = "{\"id\": 2, \"name\": \"phone\", \"providerId\": 1, \"price\": 32000, \"categories\":[\"Phone\"], \"rating\": 8.9, \"inStock\": 50}";
        Commodity commodity2 = gson.fromJson(jSon2, Commodity.class);
        store.addCommodity(jSon2,gson);
        String jSon3 = "{\"id\": 1, \"name\": \"Head\", \"providerId\": 2, \"price\": 35000, \"categories\":[\"Technology\", \"Phone\"], \"rating\": 8, \"inStock\": 5}";
        Commodity commodity3 = gson.fromJson(jSon3, Commodity.class);
        store.addCommodity(jSon3,gson);
        assertFalse(store.commodityDoesntExists(commodity3.getId()));

    }
    @Test
    void commodityDosentExistTrueTest(){
        String jSonprovider = "{\"id\": 1, \"name\": \"provider1\", \"registryDate\": \"2023-09-15\"}";
        Provider provider1 = gson.fromJson(jSonprovider, Provider.class);
        store.addProvider(jSonprovider,gson);
        String jSon2provider = "{\"id\": 2, \"name\": \"provider2\", \"registryDate\": \"2023-09-20\"}";
        Provider provider2 = gson.fromJson(jSon2provider, Provider.class);
        store.addProvider(jSon2provider,gson);

        String jSon = "{\"id\": 1, \"name\": \"Headphone\", \"providerId\": 1, \"price\": 35000, \"categories\":[\"Technology\", \"Phone\"], \"rating\": 8.8, \"inStock\": 50}";
        Commodity commodity1 = gson.fromJson(jSon, Commodity.class);
        store.addCommodity(jSon,gson);
        String jSon2 = "{\"id\": 2, \"name\": \"phone\", \"providerId\": 1, \"price\": 32000, \"categories\":[\"Phone\"], \"rating\": 8.9, \"inStock\": 50}";
        Commodity commodity2 = gson.fromJson(jSon2, Commodity.class);
        assertTrue(store.commodityDoesntExists(commodity2.getId()));
        store.addCommodity(jSon2,gson);
    }
    @Test
    void isCommodityAvailableFalseCase(){
        String jSonprovider = "{\"id\": 1, \"name\": \"provider1\", \"registryDate\": \"2023-09-15\"}";
        Provider provider1 = gson.fromJson(jSonprovider, Provider.class);
        store.addProvider(jSonprovider,gson);
        String jSon2provider = "{\"id\": 2, \"name\": \"provider2\", \"registryDate\": \"2023-09-20\"}";
        Provider provider2 = gson.fromJson(jSon2provider, Provider.class);
        store.addProvider(jSon2provider,gson);

        String jSon = "{\"id\": 1, \"name\": \"Headphone\", \"providerId\": 1, \"price\": 35000, \"categories\":[\"Technology\", \"Phone\"], \"rating\": 8.8, \"inStock\": 50}";
        Commodity commodity1 = gson.fromJson(jSon, Commodity.class);
        store.addCommodity(jSon,gson);
        String jSon2 = "{\"id\": 2, \"name\": \"phone\", \"providerId\": 1, \"price\": 32000, \"categories\":[\"Phone\"], \"rating\": 8.9, \"inStock\": 0}";
        Commodity commodity2 = gson.fromJson(jSon2, Commodity.class);
        store.addCommodity(jSon2,gson);
        assertFalse(store.isCommodityAvailable(commodity2.getId()));
    }
    @Test
    void isCommodityAvailableTrueCase(){
        String jSonprovider = "{\"id\": 1, \"name\": \"provider1\", \"registryDate\": \"2023-09-15\"}";
        Provider provider1 = gson.fromJson(jSonprovider, Provider.class);
        store.addProvider(jSonprovider,gson);
        String jSon2provider = "{\"id\": 2, \"name\": \"provider2\", \"registryDate\": \"2023-09-20\"}";
        Provider provider2 = gson.fromJson(jSon2provider, Provider.class);
        store.addProvider(jSon2provider,gson);

        String jSon = "{\"id\": 1, \"name\": \"Headphone\", \"providerId\": 1, \"price\": 35000, \"categories\":[\"Technology\", \"Phone\"], \"rating\": 8.8, \"inStock\": 40}";
        Commodity commodity1 = gson.fromJson(jSon, Commodity.class);
        store.addCommodity(jSon,gson);
        String jSon2 = "{\"id\": 2, \"name\": \"phone\", \"providerId\": 1, \"price\": 32000, \"categories\":[\"Phone\"], \"rating\": 8.9, \"inStock\": 30}";
        Commodity commodity2 = gson.fromJson(jSon2, Commodity.class);
        store.addCommodity(jSon2,gson);
        assertTrue(store.isCommodityAvailable(commodity2.getId()));
    }
    @Test
    void userExistanceTrueCase(){
        String jSon = "{\"username\":\"reza\", \"password\":\"5634\", \"email\":\"reza@gmail.com\", \"birthDate\":\"2002-04-23\", \"address\":\"address1\", \"credit\": 1500}";
        User user1 = gson.fromJson(jSon, User.class);
        String jSon2 = "{\"username\":\"reza\", \"password\":\"5634\", \"email\":\"reza@gmail.com\", \"birthDate\":\"2002-04-23\", \"address\":\"address1\", \"credit\": 1500}";
        User user2 = gson.fromJson(jSon2, User.class);
        store.addUser(jSon,gson);
        store.addUser(jSon2,gson);
        assertTrue(store.userExists(user1.getUsername()));
    }
    @Test
    void commodityAvailableInBuyListTrueCase(){
        String jSonuser = "{\"username\":\"reza\", \"password\":\"5634\", \"email\":\"reza@gmail.com\", \"birthDate\":\"2002-04-23\", \"address\":\"address1\", \"credit\": 1500}";
        User user1 = gson.fromJson(jSonuser, User.class);
        store.addUser(jSonuser,gson);

        String jSonprovider = "{\"id\": 1, \"name\": \"provider1\", \"registryDate\": \"2023-09-15\"}";
        Provider provider1 = gson.fromJson(jSonprovider, Provider.class);
        store.addProvider(jSonprovider,gson);
        String jSon2provider = "{\"id\": 2, \"name\": \"provider2\", \"registryDate\": \"2023-09-20\"}";
        Provider provider2 = gson.fromJson(jSon2provider, Provider.class);
        store.addProvider(jSon2provider,gson);

        String jSon = "{\"id\": 1, \"name\": \"Headphone\", \"providerId\": 1, \"price\": 35000, \"categories\":[\"Technology\", \"Phone\"], \"rating\": 8.8, \"inStock\": 50}";
        Commodity commodity1 = gson.fromJson(jSon, Commodity.class);
        store.addCommodity(jSon,gson);
        String jSon2 = "{\"id\": 2, \"name\": \"phone\", \"providerId\": 1, \"price\": 32000, \"categories\":[\"Phone\"], \"rating\": 8.9, \"inStock\": 0}";
        Commodity commodity2 = gson.fromJson(jSon2, Commodity.class);
        store.addCommodity(jSon2,gson);

        String jSonbuylist = "{\"username\":\"reza\", \"commodityId\": 1}";
        BuyList buyList = gson.fromJson(jSonbuylist, BuyList.class);
        store.addToBuyList(jSonbuylist,gson);
        assertTrue(store.isCommodityAvailableInBuyList(buyList));

    }
    @Test
    void addUserWorksCorrectly(){
        String jSon = "{\"username\":\"user1\",\"password\":\"1234\",\"email\":\"user@gmail.com\",\"birthDate\":\"1977-09-15\",\"address\":\"address1\",\"credit\":1500}";
        User testUser = gson.fromJson(jSon, User.class);
        store.addUser(jSon,gson);
        String jSon2 = "{\"username\":\"user2\",\"password\":\"5634\",\"email\":\"reza@gmail.com\",\"birthDate\":\"1977-04-15\",\"address\":\"address2\",\"credit\":1400}";
        User testUser2 = gson.fromJson(jSon2, User.class);
        store.addUser(jSon2,gson);

        boolean valid=true;
        for (User usr : store.getUsers()){
            valid = true;
            if (!usr.getUsername().contains(testUser2.getUsername())){
                valid = false;
                continue;
            }
            if (!usr.getPassword().contains(testUser2.getPassword())){
                valid = false;
                continue;
            }
            if (!usr.getEmail().contains(testUser2.getEmail())){
                valid = false;
                continue;
            }
            if (!usr.getBirthDate().contains(testUser2.getBirthDate())){
                valid = false;
                continue;
            }
            if (!usr.getAddress().contains(testUser2.getAddress())){
                valid = false;
                continue;
            }
            if (usr.getCredit() != (testUser2.getCredit())){
                valid = false;
                continue;
            }
        }
        assertTrue(valid);
    }
    @Test
    void addProviderWorksCorrectly(){
        String jSon = "{\"id\": 1, \"name\": \"provider1\", \"registryDate\": \"2023-09-15\"}";
        Provider testProvider = gson.fromJson(jSon, Provider.class);
        store.addProvider(jSon,gson);
        String jSon2 = "{\"id\": 2, \"name\": \"provider2\", \"registryDate\": \"2023-09-19\"}";
        Provider testProvider2 = gson.fromJson(jSon2, Provider.class);
        store.addProvider(jSon2,gson);

        boolean valid=true;
        for (Provider prv : store.getProviders()){
            valid=true;
            if (prv.getId() != (testProvider2.getId())){
                valid = false;
                continue;
            }
            if (!prv.getName().contains(testProvider2.getName())){
                valid = false;
                continue;
            }
            if (!prv.getRegistryDate().contains(testProvider2.getRegistryDate())){
                valid = false;
                continue;
            }
        }
        assertTrue(valid);
    }
    @Test
    void addCommodityWorksCorrectly(){
        String jSonprovider = "{\"id\": 1, \"name\": \"provider1\", \"registryDate\": \"2023-09-15\"}";
        Provider provider1 = gson.fromJson(jSonprovider, Provider.class);
        store.addProvider(jSonprovider,gson);
        String jSon2provider = "{\"id\": 2, \"name\": \"provider2\", \"registryDate\": \"2023-09-20\"}";
        Provider provider2 = gson.fromJson(jSon2provider, Provider.class);
        store.addProvider(jSon2provider,gson);

        String jSon = "{\"id\": 1, \"name\": \"Headphone\", \"providerId\": 1, \"price\": 35000, \"categories\":[\"Technology\", \"Phone\"], \"rating\": 8.8, \"inStock\": 40}";
        Commodity testCommodity1 = gson.fromJson(jSon, Commodity.class);
        store.addCommodity(jSon,gson);
        String jSon2 = "{\"id\": 2, \"name\": \"phone\", \"providerId\": 1, \"price\": 32000, \"categories\":[\"Phone\"], \"rating\": 8.9, \"inStock\": 30}";
        Commodity testCommodity2 = gson.fromJson(jSon2, Commodity.class);
        store.addCommodity(jSon2,gson);

        boolean valid=true;
        for (Commodity cmd : store.getCommodities()){
            valid = true;
            for (int i=0; i<testCommodity2.getCategories().size();i++){
                if (!cmd.getCategories().get(i).contains(testCommodity2.getCategories().get(i))){
                    valid = false;
                    break;
                }
            }
            if(!valid){
                continue;
            }
            if (cmd.getInStock() != testCommodity2.getInStock()){
                valid = false;
                continue;
            }
            if (cmd.getId() != (testCommodity2.getId())){
                valid = false;
                continue;
            }
            if (!cmd.getName().contains(testCommodity2.getName())){
                valid = false;
                continue;
            }
            if (cmd.getRating() != (testCommodity2.getRating())){
                valid = false;
                continue;
            }
            if (cmd.getPrice() != testCommodity2.getPrice()){
                valid = false;
                continue;
            }
            if (cmd.getProviderId() != testCommodity2.getProviderId()){
                valid = false;
            }
        }
        assertTrue(valid);
    }
    @Test
    void rateCommodityWorksCorrectly(){
        String jSon = "{\"username\":\"user1\",\"password\":\"1234\",\"email\":\"user@gmail.com\",\"birthDate\":\"1977-09-15\",\"address\":\"address1\",\"credit\":1500}";
        User testUser = gson.fromJson(jSon, User.class);
        store.addUser(jSon,gson);

        String jSon2 = "{\"id\": 1, \"name\": \"provider1\", \"registryDate\": \"2023-09-15\"}";
        Provider testProvider = gson.fromJson(jSon2, Provider.class);
        store.addProvider(jSon2,gson);

        String jSon3 = "{\"id\": 1, \"name\": \"Headphone\", \"providerId\": 1, \"price\": 35000, \"categories\":[\"Technology\", \"Phone\"], \"rating\": 8.8, \"inStock\": 40}";
        Commodity testCommodity = gson.fromJson(jSon3, Commodity.class);
        store.addCommodity(jSon3,gson);

        String jSon4 = "{\"username\": \"user1\", \"commodityId\": 1, \"score\": 7}";
        RateCommodity testRate = gson.fromJson(jSon4, RateCommodity.class);
        store.rateCommodity(jSon4,gson);

        float newRating = (testCommodity.getRating() + testRate.getScore())/2;

        boolean valid = false;
        for (Commodity cdm : store.getCommodities()) {
            for (RateCommodity rcm : store.getRateCommodities()) {
                if(cdm.getId() == rcm.getCommodityId()) {
                    if (newRating == cdm.getRating()) {
                        valid = true;
                    }
                }
            }
        }
        assertTrue(valid);
    }
    @Test
    void addToBuyListWorksCorrectly(){
        String jSon = "{\"username\":\"user1\",\"password\":\"1234\",\"email\":\"user@gmail.com\",\"birthDate\":\"1977-09-15\",\"address\":\"address1\",\"credit\":1500}";
        User testUser = gson.fromJson(jSon, User.class);
        store.addUser(jSon,gson);

        String jSon2 = "{\"id\": 1, \"name\": \"provider1\", \"registryDate\": \"2023-09-15\"}";
        Provider testProvider = gson.fromJson(jSon2, Provider.class);
        store.addProvider(jSon2,gson);

        String jSon3 = "{\"id\": 1, \"name\": \"Headphone\", \"providerId\": 1, \"price\": 35000, \"categories\":[\"Technology\", \"Phone\"], \"rating\": 8.8, \"inStock\": 40}";
        Commodity testCommodity = gson.fromJson(jSon3, Commodity.class);
        store.addCommodity(jSon3,gson);

        String jSon4 = "{\"username\": \"user1\", \"commodityId\": 1}";
        BuyList testBuyList = gson.fromJson(jSon4, BuyList.class);
        store.addToBuyList(jSon4,gson);

        boolean valid=true;
        for (BuyList byls : store.getBuyLists()) {
            valid = true;
            if (!byls.getUsername().contains(testBuyList.getUsername())) {
                valid = false;
                continue;
            }
            if (byls.getCommodityId() != testBuyList.getCommodityId()) {
                valid = false;
                continue;
            }
        }
        assertTrue(valid);
    }
    @Test
    void removeFromBuyListWorksCorrectly(){
        String jSon = "{\"username\": \"user1\", \"commodityId\": 1}";
        BuyList testBuyList = gson.fromJson(jSon, BuyList.class);
        store.addToBuyList(jSon,gson);

        for (BuyList byls : store.getBuyLists()){
            if (byls.getCommodityId() == testBuyList.getCommodityId() && byls.getUsername().contains(testBuyList.getUsername())){
                store.removeFromBuyList(jSon,gson);
            }
        }

        boolean valid=true;
        for (BuyList byls : store.getBuyLists()){
            if (byls.getCommodityId() == testBuyList.getCommodityId() && byls.getUsername().contains(testBuyList.getUsername())){
                valid = false;
                break;
            }
        }
        assertTrue(valid);
    }
}