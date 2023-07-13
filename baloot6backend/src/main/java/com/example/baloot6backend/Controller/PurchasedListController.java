package com.example.baloot6backend.Controller;

import com.example.baloot6backend.Model.*;
import com.example.baloot6backend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/")
public class PurchasedListController {
    @Autowired
    private PurchasedListRepository purchasedListRepository;
    @Autowired
    private PurchasedListItemRepository purchasedListItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDiscountRepository userDiscountRepository;
    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private BuyListItemRepository buyListItemRepository;
    @Autowired
    private BuyListRepository buyListRepository;
    @Autowired
    private CommodityCategoryRepository commodityCategoryRepository;
    @GetMapping("history")
    public List<CommodityDTO> getPurchasedListItems() {
        System.out.println("history1");
        List<User> users = userRepository.findAll();
        for (User userObj : users){
            if (userObj.isLoginStatus()){
                List<PurchasedListItem> purchasedListItems = purchasedListItemRepository.findByUsername(userObj.getUsername());
                System.out.println("Items successfully fetched");
                System.out.println(purchasedListItems);
                List<CommodityDTO> commodityDTOS = new ArrayList<>();
                for (PurchasedListItem purchasedListItemObj : purchasedListItems) {
                    ArrayList<String> categoriesDTO = new ArrayList<>();
                    List<CommodityCategory> results = commodityCategoryRepository.findByCommodityId(purchasedListItemObj.getCommodity().getId());

                    for (CommodityCategory result : results){
                        String categoryName = result.getCategory().getName();
                        categoriesDTO.add(categoryName);
                    }
                    CommodityDTO commodityDTOObj = new CommodityDTO(Math.toIntExact(purchasedListItemObj.getCommodity().getId()),
                            purchasedListItemObj.getCommodity().getName(), purchasedListItemObj.getCommodity().getProviderId(),
                            purchasedListItemObj.getCommodity().getPrice(), categoriesDTO, purchasedListItemObj.getCommodity().getRating(),
                            purchasedListItemObj.getCommodity().getInStock(), purchasedListItemObj.getCommodity().getImage(),
                            purchasedListItemObj.getCommodity().getNumberOfVoters(), purchasedListItemObj.getQuantity());
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
//        List<User> users = userRepository.findAll();
//        for (User userObj : users){
//            if (userObj.isLoginStatus()){
//                return userObj.getPurchasedLists();
//            }
//        }
//        return null;

    @PostMapping("history")
    public String addToPurchasedList(@RequestBody Map<String, Object> stringObjectMap) {
        String discountCode = (String) stringObjectMap.get("discountCode");
        int priceSum = (int) stringObjectMap.get("priceSum");
        System.out.println("history2");
        List<User> users = userRepository.findAll();
        for (User userObj : users) {
            if (userObj.isLoginStatus()) {
                //possible error handling
                if (userObj.getCredit() < priceSum)
                    return "not enough credit";
                List<BuyListItem> buyListItemsObj = buyListItemRepository.findByUsername(userObj.getUsername());
                for (BuyListItem buyListItemObj : buyListItemsObj) {
                    if (buyListItemObj.getCommodity().getInStock() < buyListItemObj.getQuantity())
                        return "inStock alert";
                }

                //add this new purchasedlist to others before.
                PurchasedList purchasedList = new PurchasedList();
                purchasedList.setUsername(userObj.getUsername());
                purchasedList.setUser(userObj);
                System.out.println("empty purchasedList initialized");
                purchasedListRepository.save(purchasedList);
                System.out.println("empty purchasedList saved");
                List<PurchasedListItem> purchasedListItems = new ArrayList<PurchasedListItem>();

                for (BuyListItem buyListItemObj : buyListItemsObj) {
                    PurchasedListItem purchasedListItem = new PurchasedListItem();

                    //setting purchasedItem username
                    purchasedListItem.setUsername(buyListItemObj.getUsername());

                    //setting purchasedItem commodity
                    Commodity commodityObj = buyListItemObj.getCommodity();
                    purchasedListItem.setCommodity(commodityObj);

                    //updating instock value of item
                    Commodity commodityDataBase = commodityRepository.findByCommodityId(commodityObj.getId());
                    commodityDataBase.setInStock(commodityDataBase.getInStock() - buyListItemObj.getQuantity());
                    commodityRepository.save(commodityDataBase);

                    //setting purchasedItem quantity
                    purchasedListItem.setQuantity(buyListItemObj.getQuantity());
                    purchasedListItem.setPurchasedList(purchasedList);

                    //saving new purchasedItem into database
                    purchasedListItems.add(purchasedListItem);
                    purchasedListItemRepository.save(purchasedListItem);
                    System.out.println("new item purchased");
                }

                //saving new purchasedList into database
                purchasedList.setPurchasedListItems(purchasedListItems);
                purchasedListRepository.save(purchasedList);
                System.out.println("New PurchasedList successfully saved!");

                //making buylist empty
                System.out.println(buyListItemsObj);
                buyListItemRepository.deleteAll(buyListItemsObj);
                System.out.println("BuyListItem table is clean now");

                //updating buylist
                BuyList buyListObj = buyListRepository.findByUsername(userObj.getUsername());
                buyListObj.getBuyListItems().removeAll(buyListItemsObj);
                buyListRepository.save(buyListObj);
                System.out.println("BuyList table is successfully updated!");

                //updating credit
                userObj.setCredit(userObj.getCredit()-priceSum);
                userRepository.save(userObj);
                System.out.println("User credit is successfully updated!");

                //expiring the used discount code
                if (discountCode != null){
                    List<UserDiscount> userDiscounts = userDiscountRepository.findByDiscountDiscountCode(discountCode);
                    for(UserDiscount userDiscountObj : userDiscounts){
                        if (userObj.getUsername().equalsIgnoreCase(userDiscountObj.getUser().getUsername())){
                            userDiscountRepository.delete(userDiscountObj);
                            System.out.println("DiscountCode successfully got expired!");
                        }
                    }
                }
            }
        }
        return "success";
    }
}
