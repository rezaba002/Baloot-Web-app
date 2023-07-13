package com.example.Baloot5Backend.controller;

import com.example.Baloot5Backend.model.Commodity;
import com.example.Baloot5Backend.model.Provider;
import com.example.Baloot5Backend.model.Store;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/")
public class CommodityController {
    Store store = Store.getInstance();

    @GetMapping("commodities")
    public List<Commodity> getCommodities() {
        return store.getCommodities();
    }

    @PostMapping("commodities")
    public List<Commodity> getProviderCommodities(@RequestBody String providerId) {
        StringTokenizer tokenizer = new StringTokenizer(providerId, "=");
        int id = Integer.valueOf(tokenizer.nextToken());
        ArrayList<Commodity> commodities = new ArrayList<Commodity>();
        for (Commodity commodityObj : store.getCommodities()) {
            if (commodityObj.getProviderId() == id) {
                commodities.add(commodityObj);
            }
        }
        return commodities;
    }

    @PostMapping("commodity")
    public Commodity sendCommodity(@RequestBody String commodityId) {
        StringTokenizer tokenizer = new StringTokenizer(commodityId,"=");
        int id = Integer.valueOf(tokenizer.nextToken());
        for (Commodity commodityObj : store.getCommodities()) {
            if (commodityObj.getId() == id) {
                return commodityObj;
            }
        }
        return null;
    }

    @PostMapping("suggested")
    public List<Commodity> sendSuggestedCommodities(@RequestBody String commodityId)    {
        StringTokenizer tokenizer = new StringTokenizer(commodityId,"=");
        int id = Integer.valueOf(tokenizer.nextToken());
        ArrayList<Commodity> tempCommodities = new ArrayList<Commodity>();
        Commodity thisCommodity = new Commodity();
        for (Commodity commodityObj : store.getCommodities()) {
            if (commodityObj.getId() == id)
                thisCommodity = commodityObj;
            else
                tempCommodities.add(commodityObj);
        }

        for (Commodity tcmd : tempCommodities) {
            if (tcmd.getCategories().containsAll(thisCommodity.getCategories())) {
                tcmd.setRating(tcmd.getRating() + 11);
            }
        }

        for (int i = 0 ; i < tempCommodities.size() - 1 ; i++) {
            for (int j = i+1 ; j < tempCommodities.size() ; j++) {
                if (tempCommodities.get(i).getRating() < tempCommodities.get(j).getRating()) {
                    Commodity tempCommodity = tempCommodities.get(j);
                    tempCommodities.set(j, tempCommodities.get(i));
                    tempCommodities.set(i, tempCommodity);
                }
            }
        }

        for (Commodity tcmd : tempCommodities) {
            if (tcmd.getCategories().containsAll(thisCommodity.getCategories())) {
                tcmd.setRating(tcmd.getRating() - 11);
            }
        }

        ArrayList<Commodity> suggestedCommodities = new ArrayList<Commodity>();

        for (int i = 0 ; i < 4 ; i++) {
            suggestedCommodities.add(tempCommodities.get(i));
        }

        return suggestedCommodities;
    }

    @PostMapping ("searchCommodity")
    public List<Commodity> sendSearchedCommodities(@RequestBody Map<String, Object> jsonObject) {
        String searchValue = (String) jsonObject.get("searchValue");
        String searchType = (String) jsonObject.get("searchType");
        ArrayList<Commodity> temp = new ArrayList<Commodity>();
        for (Commodity commodityObj : store.getCommodities()) {
            if (searchType.equalsIgnoreCase("name")) {
                if (commodityObj.getName().contains(searchValue)) {
                    temp.add(commodityObj);
                }
            }
            else if (searchType.equalsIgnoreCase("category")) {
                if (commodityObj.getCategories().contains(searchValue)) {
                    temp.add(commodityObj);
                }
            }
            else if (searchType.equalsIgnoreCase("provider")) {
                for (Provider providerObj : store.getProviders()) {
                    if (providerObj.getName().contains(searchValue)) {
                        if (commodityObj.getProviderId() == providerObj.getId()) {
                            temp.add(commodityObj);
                        }
                    }
                }
            }
        }
        return temp;
    }

    @PostMapping ("sortNameCommodity")
    public List<Commodity> sendNameSortedCommodities(@RequestBody String jsonString) throws JsonProcessingException, UnsupportedEncodingException {
        String decodedStr = URLDecoder.decode(jsonString, "UTF-8");
        String newDecodedStr = decodedStr.substring(15);
        String resultStr = newDecodedStr.substring(0, newDecodedStr.length() - 1);
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Map<String, Object>> jsonArray = objectMapper.readValue(resultStr, new TypeReference<ArrayList<Map<String, Object>>>() {});
        ArrayList<Commodity> commodities = new ArrayList<Commodity>();
        for (int i = 0 ; i < jsonArray.size() ; i++) {
            Commodity commodityObj = new Commodity();
            commodityObj.setId((int) jsonArray.get(i).get("id"));
            commodityObj.setName((String) jsonArray.get(i).get("name"));
            commodityObj.setProviderId((int) jsonArray.get(i).get("providerId"));
            commodityObj.setPrice((int) jsonArray.get(i).get("price"));
            commodityObj.setCategories((ArrayList<String>) jsonArray.get(i).get("categories"));
//            commodityObj.setRating((Float) jsonArray.get(i).get("rating"));
            commodityObj.setInStock((int) jsonArray.get(i).get("inStock"));
            commodityObj.setImage((String) jsonArray.get(i).get("image"));
            commodityObj.setNumberOfVoters((int) jsonArray.get(i).get("numberOfVoters"));
            commodities.add(commodityObj);
        }
        for (int i = 0 ; i < commodities.size() ; i++) {
            for (int j = i+1 ; j < commodities.size() ; j++) {
                if (commodities.get(i).getName().compareTo(commodities.get(j).getName()) > 0) {
                    Commodity tempCommodity = commodities.get(j);
                    commodities.set(j, commodities.get(i));
                    commodities.set(i, tempCommodity);
                }
            }
        }
        return commodities;
    }

    @PostMapping ("sortPriceCommodity")
    public List<Commodity> sendPriceSortedCommodities(@RequestBody String jsonString) throws JsonProcessingException, UnsupportedEncodingException {
        String decodedStr = URLDecoder.decode(jsonString, "UTF-8");
        String newDecodedStr = decodedStr.substring(15);
        String resultStr = newDecodedStr.substring(0, newDecodedStr.length() - 1);
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Map<String, Object>> jsonArray = objectMapper.readValue(resultStr, new TypeReference<ArrayList<Map<String, Object>>>() {});
        ArrayList<Commodity> commodities = new ArrayList<Commodity>();
        for (int i = 0; i < jsonArray.size(); i++) {
            Commodity commodityObj = new Commodity();
            commodityObj.setId((int) jsonArray.get(i).get("id"));
            commodityObj.setName((String) jsonArray.get(i).get("name"));
            commodityObj.setProviderId((int) jsonArray.get(i).get("providerId"));
            commodityObj.setPrice((int) jsonArray.get(i).get("price"));
            commodityObj.setCategories((ArrayList<String>) jsonArray.get(i).get("categories"));
//            commodityObj.setRating((Float) jsonArray.get(i).get("rating"));
            commodityObj.setInStock((int) jsonArray.get(i).get("inStock"));
            commodityObj.setImage((String) jsonArray.get(i).get("image"));
            commodityObj.setNumberOfVoters((int) jsonArray.get(i).get("numberOfVoters"));
            commodities.add(commodityObj);
        }
        for (int i = 0; i < commodities.size(); i++) {
            for (int j = i + 1; j < commodities.size(); j++) {
                if (commodities.get(i).getPrice() < (commodities.get(j).getPrice())) {
                    Commodity tempCommodity = commodities.get(j);
                    commodities.set(j, commodities.get(i));
                    commodities.set(i, tempCommodity);
                }
            }
        }
        return commodities;
    }
}
