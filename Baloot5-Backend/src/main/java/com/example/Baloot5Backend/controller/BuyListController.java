package com.example.Baloot5Backend.controller;

import com.example.Baloot5Backend.model.BuyList;
import com.example.Baloot5Backend.model.BuyListItem;
import com.example.Baloot5Backend.model.Store;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/")
public class BuyListController {
    Store store = Store.getInstance();

    @GetMapping("cart")
    public List<BuyListItem> getBuyListItems() {
        for (BuyList buyListObj : store.getBuyLists()) {
            if(store.getCurrentUser().getUsername() == null)
                break;
            if (store.getCurrentUser().getUsername().equalsIgnoreCase(buyListObj.getUsername())) {
                return buyListObj.getBuyListItems();
            }
        }

        return null;

    }

    @PostMapping("cart")
    public String addToBuyList(@RequestBody Map<String, Object> stringObjectMap) {
        String username = (String) stringObjectMap.get("username");
        int commodityId = (int) stringObjectMap.get("commodityId");
        String buyType = (String) stringObjectMap.get("buyType");

        if (!store.checkIfCommodityExistsInBuyList(username, commodityId)) {
            store.addToBuyList(username, commodityId);
        }
        else {
            store.updateBuyList(username, commodityId, buyType);
        }
        return "success";
    }
}
