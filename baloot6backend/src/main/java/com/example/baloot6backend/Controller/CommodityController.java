package com.example.baloot6backend.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.example.baloot6backend.Model.Commodity;
import com.example.baloot6backend.Model.CommodityDTO;
import com.example.baloot6backend.Model.Provider;
import com.example.baloot6backend.Service.CommodityService;
import com.example.baloot6backend.Service.ProviderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/")
public class CommodityController {
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private ProviderService providerService;
    @GetMapping("commodities")
    public List<CommodityDTO> getCommodities(){
        return commodityService.getCommodities();
    }
    @PostMapping("commodities")
    public List<CommodityDTO> getProviderCommodities(@RequestBody String providerId) {
        StringTokenizer tokenizer = new StringTokenizer(providerId, "=");
        long id = Long.parseLong(tokenizer.nextToken());
        List<CommodityDTO> commodities = commodityService.getCommodityByProviderId(id);
        System.out.println("CommodityController: " + commodities);
        return commodities;
    }
    @GetMapping("commodities/{id}")
    public CommodityDTO getCommodity(@PathVariable("id") Long commodityId){
        System.out.println(commodityId);
        CommodityDTO commodityDTO = commodityService.getCommodity(commodityId);
        return commodityDTO;
    }
    //suggested commodities
    @PostMapping("suggested")
    public List<CommodityDTO> sendSuggestedCommodities(@RequestBody String Id){
        StringTokenizer tokenizer = new StringTokenizer(Id,"=");
        long commodityId = Long.parseLong(tokenizer.nextToken());
        List<CommodityDTO> commodities = commodityService.getCommodities();
        CommodityDTO commodityDTOObj = new CommodityDTO();
        List<CommodityDTO> tempCommodities = new ArrayList<>();

        for (CommodityDTO commodityDTO : commodities){
            if (commodityDTO.getId() == commodityId){
                commodityDTOObj = commodityDTO;
            }
            else {
                tempCommodities.add(commodityDTO);
            }
        }
        System.out.println(commodityDTOObj);
        System.out.println(tempCommodities);
        for (CommodityDTO commodityDTO : tempCommodities){
            if (commodityDTO.getCategories().containsAll(commodityDTOObj.getCategories())){
                commodityDTO.setRating(commodityDTO.getRating() + 11);
            }
        }
        for (int i = 0 ; i < tempCommodities.size() - 1 ; i++) {
            for (int j = i+1 ; j < tempCommodities.size() ; j++) {
                if (tempCommodities.get(i).getRating() < tempCommodities.get(j).getRating()) {
                    CommodityDTO tempCommodity = tempCommodities.get(j);
                    tempCommodities.set(j, tempCommodities.get(i));
                    tempCommodities.set(i, tempCommodity);
                }
            }
        }
        for (CommodityDTO commodityDTO1 : tempCommodities){
            if (commodityDTO1.getCategories().containsAll(commodityDTOObj.getCategories())){
                commodityDTO1.setRating(commodityDTO1.getRating() - 11);
            }
        }
        ArrayList<CommodityDTO> suggestedCommodities = new ArrayList<>();

        for (int i=0 ; i<4 ; i++){
            suggestedCommodities.add(tempCommodities.get(i));
        }
        return suggestedCommodities;
    }
    //searched commodities
    @PostMapping("searchCommodity")
    public List<CommodityDTO> sendSearchedCommodities(@RequestBody Map<String, Object> jsonObject){
        String searchValue = (String) jsonObject.get("searchValue");
        String searchType = (String) jsonObject.get("searchType");

        List<CommodityDTO> commodityDTOS = commodityService.getCommodities();
        List<CommodityDTO> commodityDTOList = new ArrayList<>();

        for (CommodityDTO commodityDTOObj : commodityDTOS){
            if (searchType.equalsIgnoreCase("name")){
                if (commodityDTOObj.getName().contains(searchValue)){
                    commodityDTOList.add(commodityDTOObj);
                }
            }
            else if (searchType.equalsIgnoreCase("category")){
                for (String str : commodityDTOObj.getCategories()){
                    if (str.contains(searchValue)){
                        commodityDTOList.add(commodityDTOObj);
                    }
                }
            }
            else if (searchType.equalsIgnoreCase("provider")){
                List<Provider> providers = providerService.getAllProviders();
                for (Provider providerObj : providers){
                    if (providerObj.getName().contains(searchValue)){
                        if (commodityDTOObj.getProviderId() == providerObj.getId()){
                            commodityDTOList.add(commodityDTOObj);
                        }
                    }
                }
            }
        }
        return commodityDTOList;
    }
    //sorted commodities by price
    @PostMapping ("sortPriceCommodity")
    public List<CommodityDTO> sendPriceSortedCommodities(@RequestBody String jsonString) throws JsonProcessingException, UnsupportedEncodingException {
        String decodedStr = URLDecoder.decode(jsonString, "UTF-8");
        String newDecodedStr = decodedStr.substring(15);
        String resultStr = newDecodedStr.substring(0, newDecodedStr.length() - 1);
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Map<String, Object>> jsonArray = objectMapper.readValue(resultStr, new TypeReference<ArrayList<Map<String, Object>>>() {});
        List<CommodityDTO> commodityDTOS = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            CommodityDTO commodityDTOObj = new CommodityDTO();
            commodityDTOObj.setId((int) jsonArray.get(i).get("id"));
            commodityDTOObj.setName((String) jsonArray.get(i).get("name"));
            commodityDTOObj.setProviderId((int) jsonArray.get(i).get("providerId"));
            commodityDTOObj.setPrice((int) jsonArray.get(i).get("price"));
            commodityDTOObj.setCategories((ArrayList<String>) jsonArray.get(i).get("categories"));
//            commodityDTOObj.setRating((Float) jsonArray.get(i).get("rating"));
            commodityDTOObj.setInStock((int) jsonArray.get(i).get("inStock"));
            commodityDTOObj.setImage((String) jsonArray.get(i).get("image"));
            commodityDTOObj.setNumberOfVoters((int) jsonArray.get(i).get("numberOfVoters"));
            commodityDTOS.add(commodityDTOObj);
        }
        for (int i = 0; i < commodityDTOS.size(); i++) {
            for (int j = i + 1; j < commodityDTOS.size(); j++) {
                if (commodityDTOS.get(i).getPrice() < (commodityDTOS.get(j).getPrice())) {
                    CommodityDTO tempCommodityDTO = commodityDTOS.get(j);
                    commodityDTOS.set(j, commodityDTOS.get(i));
                    commodityDTOS.set(i, tempCommodityDTO);
                }
            }
        }
        return commodityDTOS;
    }
    //sorted commodities by name
    @PostMapping ("sortNameCommodity")
    public List<CommodityDTO> sendNameSortedCommodities(@RequestBody String jsonString) throws JsonProcessingException, UnsupportedEncodingException {
        String decodedStr = URLDecoder.decode(jsonString, "UTF-8");
        String newDecodedStr = decodedStr.substring(15);
        String resultStr = newDecodedStr.substring(0, newDecodedStr.length() - 1);
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Map<String, Object>> jsonArray = objectMapper.readValue(resultStr, new TypeReference<ArrayList<Map<String, Object>>>() {});
        List<CommodityDTO> commodityDTOS = new ArrayList<>();
        for (int i = 0 ; i < jsonArray.size() ; i++) {
            CommodityDTO commodityDTOObj = new CommodityDTO();
            commodityDTOObj.setId((int) jsonArray.get(i).get("id"));
            commodityDTOObj.setName((String) jsonArray.get(i).get("name"));
            commodityDTOObj.setProviderId((int) jsonArray.get(i).get("providerId"));
            commodityDTOObj.setPrice((int) jsonArray.get(i).get("price"));
            commodityDTOObj.setCategories((ArrayList<String>) jsonArray.get(i).get("categories"));
//            commodityDTOObj.setRating((Float) jsonArray.get(i).get("rating"));
            commodityDTOObj.setInStock((int) jsonArray.get(i).get("inStock"));
            commodityDTOObj.setImage((String) jsonArray.get(i).get("image"));
            commodityDTOObj.setNumberOfVoters((int) jsonArray.get(i).get("numberOfVoters"));
            commodityDTOS.add(commodityDTOObj);
        }
        for (int i = 0 ; i < commodityDTOS.size() ; i++) {
            for (int j = i+1 ; j < commodityDTOS.size() ; j++) {
                if (commodityDTOS.get(i).getName().compareTo(commodityDTOS.get(j).getName()) > 0) {
                    CommodityDTO tempCommodityDTO = commodityDTOS.get(j);
                    commodityDTOS.set(j, commodityDTOS.get(i));
                    commodityDTOS.set(i, tempCommodityDTO);
                }
            }
        }
        return commodityDTOS;
    }
}
