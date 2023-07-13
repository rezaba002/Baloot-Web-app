package com.example.baloot6backend.Controller;

import com.example.baloot6backend.Model.*;
import com.example.baloot6backend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/")
public class BuyListController {
    @Autowired
    private BuyListItemRepository buyListItemRepository;
    @Autowired
    private BuyListRepository buyListRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private CommodityCategoryRepository commodityCategoryRepository;

    @GetMapping("cart")
    public List<CommodityDTO> getBuyListItems(){
        System.out.println("cart1");
        List<User> users = userRepository.findAll();
        for (User userObj : users){
            if (userObj.    isLoginStatus()){
                List<BuyListItem> buyListItems = buyListItemRepository.findByUsername(userObj.getUsername());
                List<CommodityDTO> commodityDTOS = new ArrayList<>();
                for (BuyListItem buyListItemObj : buyListItems) {
                    ArrayList<String> categoriesDTO = new ArrayList<>();
                    List<CommodityCategory> results = commodityCategoryRepository.findByCommodityId(buyListItemObj.getCommodity().getId());

                    for (CommodityCategory result : results){
                        String categoryName = result.getCategory().getName();
                        categoriesDTO.add(categoryName);
                    }
                    CommodityDTO commodityDTOObj = new CommodityDTO(Math.toIntExact(buyListItemObj.getCommodity().getId()),
                                                buyListItemObj.getCommodity().getName(), buyListItemObj.getCommodity().getProviderId(),
                                                buyListItemObj.getCommodity().getPrice(), categoriesDTO, buyListItemObj.getCommodity().getRating(),
                                                buyListItemObj.getCommodity().getInStock(), buyListItemObj.getCommodity().getImage(),
                                                buyListItemObj.getCommodity().getNumberOfVoters(), buyListItemObj.getQuantity());
                    commodityDTOS.add(commodityDTOObj);
                }
                System.out.println("commodityDTOS: XXXXXXXXXXXXXXXXXXXXXXX");
                System.out.println(commodityDTOS);
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                return commodityDTOS;
            }
        }
        return null;
    }
    @PostMapping("cart")
    @Transactional
    public String addToBuyList(@RequestBody Map<String, Object> stringObjectMap) {
        String username = (String) stringObjectMap.get("username");
        long commodityId = (Integer) stringObjectMap.get("commodityId");
        String buyType = (String) stringObjectMap.get("buyType");
        System.out.println("cart2");
        List<User> users = userRepository.findAll();
        for (User userObj : users){
            if (userObj.getUsername().equalsIgnoreCase(username)){
                if (userObj.isLoginStatus()) {
                    BuyList buyListObj = buyListRepository.findByUsername(userObj.getUsername());
                    List<BuyListItem> buyListItemsObj1 = buyListObj.getBuyListItems();
                    Commodity commodityObj = commodityRepository.findByCommodityId(commodityId);
                    for (BuyListItem buyListItemObj : buyListItemsObj1){
                        if (buyListItemObj.getCommodity().getId() == commodityObj.getId() && buyListItemObj.getBuylist() != null) {
                            if (buyListItemObj.getQuantity() == 1 && buyType.equalsIgnoreCase("decrease")) {
                                buyListObj.getBuyListItems().remove(buyListItemObj);
                                buyListRepository.save(buyListObj);
                                return "success";
                            }
                            else if (buyType.equalsIgnoreCase("decrease")) {
                                buyListItemObj.setQuantity(buyListItemObj.getQuantity() - 1);
                                buyListObj.setBuyListItems(buyListItemsObj1);
                                buyListRepository.save(buyListObj);
                                return "success";
                            }
                            else if (buyType.equalsIgnoreCase("increase")) {
                                buyListItemObj.setQuantity(buyListItemObj.getQuantity() + 1);
                                buyListObj.setBuyListItems(buyListItemsObj1);
                                buyListRepository.save(buyListObj);
                                return "success";
                            }
                        }
                    }
                    List<BuyListItem> buyListItemsObj2 = userObj.getBuyList().getBuyListItems();
                    for (BuyListItem buyListItemObj : buyListItemsObj2){
                        if (buyListItemObj.getCommodity().getId() == commodityObj.getId() && buyListItemObj.getBuylist() != null) {
                            if (buyListItemObj.getQuantity() == 1 && buyType.equalsIgnoreCase("decrease")) {
                                userObj.getBuyList().getBuyListItems().remove(buyListItemObj);
                                userRepository.save(userObj);
                                return "success";
                            }
                            else if (buyType.equalsIgnoreCase("decrease")) {
                                buyListItemObj.setQuantity(buyListItemObj.getQuantity() - 1);
                                userObj.getBuyList().setBuyListItems(buyListItemsObj2);
                                userRepository.save(userObj);
                                return "success";
                            }
                            else if (buyType.equalsIgnoreCase("increase")) {
                                buyListItemObj.setQuantity(buyListItemObj.getQuantity() + 1);
                                userObj.getBuyList().setBuyListItems(buyListItemsObj2);
                                userRepository.save(userObj);
                                return "success";
                            }
                        }
                    }
                    List<BuyListItem> buyListItemsObj3 = buyListItemRepository.findByUsername(username);
                    for (BuyListItem buyListItemObj : buyListItemsObj3){
                        if (buyListItemObj.getCommodity().getId() == commodityObj.getId() && buyListItemObj.getBuylist() != null) {
                            if (buyListItemObj.getQuantity() == 1 && buyType.equalsIgnoreCase("decrease")) {
                                buyListItemRepository.delete(buyListItemObj);
                                return "success";
                            }
                            else if (buyType.equalsIgnoreCase("decrease")) {
                                buyListItemObj.setQuantity(buyListItemObj.getQuantity() - 1);
                                buyListItemRepository.save(buyListItemObj);
                                return "success";
                            }
                            else if (buyType.equalsIgnoreCase("increase")) {
                                buyListItemObj.setQuantity(buyListItemObj.getQuantity() + 1);
                                buyListItemRepository.save(buyListItemObj);
                                return "success";
                            }
                        }
                    }
                    BuyListItem bylsItem = new BuyListItem();
                    bylsItem.setUsername(username);
                    bylsItem.setCommodity(commodityObj);
                    bylsItem.setQuantity(1);
                    userObj.getBuyList().getBuyListItems().add(bylsItem);
                    BuyList buyListObj2 = buyListRepository.findByUsername(userObj.getUsername());
                    buyListObj2.getBuyListItems().add(bylsItem);
                    bylsItem.setBuylist(buyListObj2);
                    buyListRepository.save(buyListObj2);
                    userRepository.save(userObj);
                    buyListItemRepository.save(bylsItem);
                }
            }
        }
        // we should discuss that how we are gonna handle quantity
        // if it's going to be like before so
        // create a method in userService
        // add or update the status of buylistItem considering the username
        return "success";
    }
}
