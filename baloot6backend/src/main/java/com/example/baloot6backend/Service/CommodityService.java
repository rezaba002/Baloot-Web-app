package com.example.baloot6backend.Service;

import com.example.baloot6backend.Model.*;
import com.example.baloot6backend.Repository.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CommodityService {
    ArrayList<CommodityDTO> sampleCommodities = new ArrayList<CommodityDTO>();
    public ArrayList<CommodityDTO> getSampleCommodities() {return sampleCommodities;}
    public void setSampleCommodities(ArrayList<CommodityDTO> sampleCommodities) {this.sampleCommodities = sampleCommodities;}

    List<Commodity> commodities = new ArrayList<Commodity>();
    ArrayList<Category> categories = new ArrayList<Category>();

    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CommodityCategoryRepository commodityCategoryRepository;
    @Autowired
    private RateCommodityRepository rateCommodityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BuyListItemRepository buyListItemRepository;

    @Transactional
    public void addCommodity(HttpClient client, HttpRequest request, Gson gson) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type commoditiesList = new TypeToken<ArrayList<CommodityDTO>>() {}.getType();
        sampleCommodities = gson.fromJson(response.body(), commoditiesList);

        Set<String> uniqueCategories = new HashSet<>(); // Track unique category names
        List<Commodity> availableCommodities = commodityRepository.findAll();
        if(availableCommodities.isEmpty()) {
            for (CommodityDTO cmdsmple : sampleCommodities) {
                Commodity commodityObj = new Commodity(
                        (long) cmdsmple.getId(), cmdsmple.getName(), cmdsmple.getProviderId(),
                        cmdsmple.getPrice(), cmdsmple.getRating(), cmdsmple.getInStock(),
                        cmdsmple.getImage(), cmdsmple.getNumberOfVoters()
                );
                // inserting commodities
                commodityRepository.save(commodityObj);

                for (String categoryName : cmdsmple.getCategories()) {
                    if (!uniqueCategories.contains(categoryName)) {
                        Category categoryObj = new Category(categoryName);
                        // inserting categories
                        categoryRepository.save(categoryObj);
                        uniqueCategories.add(categoryName);
                        categories.add(categoryObj);

                        // inserting commodityCategories
                        CommodityCategory commodityCategoryObj = new CommodityCategory(commodityObj, categoryObj);
                        commodityCategoryRepository.save(commodityCategoryObj);
                    } else {
                        // Category already exists, retrieve it from the database
                        Category categoryObj = categoryRepository.findByName(categoryName);
                        // inserting commodityCategories
                        CommodityCategory commodityCategoryObj = new CommodityCategory(commodityObj, categoryObj);
                        commodityCategoryRepository.save(commodityCategoryObj);
                    }
                }
            }
        }
    }

    public List<CommodityDTO> getCommodities(){
        commodities = commodityRepository.findAll();
        List<CommodityDTO> commodityDTOS = new ArrayList<CommodityDTO>();
        for (Commodity commodityObj : commodities){
            ArrayList<String> categoriesDTO = new ArrayList<>();
            List<CommodityCategory> results = commodityCategoryRepository.findByCommodityId(commodityObj.getId());

            for (CommodityCategory result : results){
                String categoryName = result.getCategory().getName();
                categoriesDTO.add(categoryName);
            }
            CommodityDTO commodityDTOObj = new CommodityDTO(Math.toIntExact(commodityObj.getId()), commodityObj.getName(),
                                                             commodityObj.getProviderId(), commodityObj.getPrice(), categoriesDTO, commodityObj.getRating(),
                                                             commodityObj.getInStock(), commodityObj.getImage(), commodityObj.getNumberOfVoters(), 0);
            commodityDTOS.add(commodityDTOObj);
        }
        return commodityDTOS;
    }
    public List<CommodityDTO> getCommodityByProviderId(long providerId){
        List<Commodity> commoditiesList = commodityRepository.findByProviderId(providerId);
        List<CommodityDTO> commodityDTOS = new ArrayList<CommodityDTO>();
        for (Commodity commodityObj : commoditiesList){
            ArrayList<String> categoriesDTO = new ArrayList<>();
            List<CommodityCategory> results = commodityCategoryRepository.findByCommodityId(commodityObj.getId());

            for (CommodityCategory result : results){
                String categoryName = result.getCategory().getName();
                categoriesDTO.add(categoryName);
            }
            CommodityDTO commodityDTOObj = new CommodityDTO(Math.toIntExact(commodityObj.getId()), commodityObj.getName(),
                    commodityObj.getProviderId(), commodityObj.getPrice(), categoriesDTO, commodityObj.getRating(),
                    commodityObj.getInStock(), commodityObj.getImage(), commodityObj.getNumberOfVoters(), 0);
            commodityDTOS.add(commodityDTOObj);
        }
        return commodityDTOS;
    }
    public CommodityDTO getCommodity(Long commodityId) {
        Commodity commodityObj = commodityRepository.getReferenceById(commodityId);
        ArrayList<String> categoriesDTO = new ArrayList<>();

//            System.out.println(commodityCategoryRepository.findByCommodityId(commodityObj.getId()));
        List<CommodityCategory> results = commodityCategoryRepository.findByCommodityId(commodityObj.getId());

        for (CommodityCategory result : results){
            String categoryName = result.getCategory().getName();
            categoriesDTO.add(categoryName);
//                System.out.println(categoriesDTO);
        }
        CommodityDTO commodityDTOObj = new CommodityDTO(Math.toIntExact(commodityObj.getId()), commodityObj.getName(),
                commodityObj.getProviderId(), commodityObj.getPrice(), categoriesDTO, commodityObj.getRating(),
                commodityObj.getInStock(), commodityObj.getImage(), commodityObj.getNumberOfVoters(), 0);
//            System.out.println(commodityDTOObj);
        return commodityDTOObj;
    }

    @Transactional
    public void rateCommodity(float score, Long commodityId, String username){
        RateCommodity rateCommodityObj = new RateCommodity();
        User userObj = userRepository.findByUsername(username);
        Commodity commodityObj = commodityRepository.findByCommodityId(commodityId);
        System.out.println("Commodity for rate: " + commodityObj + "User who is rating: " + userObj.getUsername());
        rateCommodityObj.setUser(userObj);
        rateCommodityObj.setCommodity(commodityObj);
        rateCommodityObj.setScore(score);
        System.out.println("New RateCommodity: " + rateCommodityObj);
        List<RateCommodity> rateCommodities = rateCommodityRepository.findByCommodityId(commodityId);
        System.out.println("Founded rateCommodities: " + rateCommodities.size());

        //update rate if needed
        for (RateCommodity rateCommodity : rateCommodities){
            if (rateCommodity.getUser().getUsername().equalsIgnoreCase(username)){
//                rateCommodityRepository.delete(rateCommodity);
//                System.out.println("Old rateCommodity successfully deleted");
                System.out.println("Old commodity rating: " + commodityObj.getRating());
                if (commodityObj.getNumberOfVoters()>1) {
                    commodityObj.setRating((float) ((commodityObj.getRating() * commodityObj.getNumberOfVoters()) - rateCommodity.getScore()) / (commodityObj.getNumberOfVoters() - 1));
                    commodityObj.setRating((float) ((score + (commodityObj.getRating() * (commodityObj.getNumberOfVoters() - 1))) / (commodityObj.getNumberOfVoters())));
                }
                else if (commodityObj.getNumberOfVoters() == 1){
                    commodityObj.setRating((commodityObj.getRating() + score) / 2);
                }
                else if (commodityObj.getNumberOfVoters() == 0){
                    commodityObj.setRating(score);
                }

                System.out.println("New commodity rating: " + commodityObj.getRating());
                rateCommodity.setScore(score);
                rateCommodityRepository.save(rateCommodity);
                System.out.println("Existing rateCommodity successfully updated");
                commodityRepository.save(commodityObj);
                System.out.println("Commodity updated");
                return;
            }
        }
        //add new rate
        System.out.println("Old commodity rating: " + commodityObj.getRating());
        commodityObj.setRating((float) (score + (commodityObj.getRating() * commodityObj.getNumberOfVoters()))/(commodityObj.getNumberOfVoters()+1));
        System.out.println("New commodity rating: " + commodityObj.getRating());
        commodityObj.setNumberOfVoters(commodityObj.getNumberOfVoters()+1);
        rateCommodityRepository.save(rateCommodityObj);

    }
}
