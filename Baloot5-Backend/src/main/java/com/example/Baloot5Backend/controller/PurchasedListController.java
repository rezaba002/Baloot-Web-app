package com.example.Baloot5Backend.controller;

import com.example.Baloot5Backend.model.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/")
public class PurchasedListController {
    Store store = Store.getInstance();

    @GetMapping("history")
    public List<BuyListItem> getPurchasedListItems() {
        ArrayList<BuyListItem> buyListItems = new ArrayList<BuyListItem>();
        for (BuyListItem buyListItemObj : store.getPurchasedLists()) {
            if (store.getCurrentUser().getUsername().equalsIgnoreCase(buyListItemObj.getUsername())) {
                buyListItems.add(buyListItemObj);
            }
        }
        return buyListItems;
    }

    @PostMapping("history")
    public String addToPurchasedList(@RequestBody String discount) {
        StringTokenizer tokenizer = new StringTokenizer(discount, "=");
        String discountCode = tokenizer.nextToken();

        int cartPrice = 0;
        String username = store.getCurrentUser().getUsername();
        ArrayList<BuyListItem> buyListItems = new ArrayList<BuyListItem>();

        for (BuyList buyListObj : store.getBuyLists()) {
            if (buyListObj.getUsername().equalsIgnoreCase(username)) {
                buyListItems.addAll(buyListObj.getBuyListItems());
            }
        }

        for (BuyListItem buyListItemObj : buyListItems) {
            if (buyListItemObj.getCommodity().getInStock() < buyListItemObj.getQuantity())
                return "inStock alert";
            cartPrice += buyListItemObj.getQuantity() * buyListItemObj.getCommodity().getPrice();
        }


        if (cartPrice > store.getCurrentUser().getCredit())
            return "not enough credit";

        for (User userObj : store.getUsers()) {
            if (username.equalsIgnoreCase(userObj.getUsername())) {
                userObj.setCredit(userObj.getCredit() - cartPrice);
                store.getCurrentUser().setCredit(userObj.getCredit());
            }
        }
        store.addExpiredDiscount(username, discountCode);
        System.out.println(discountCode);
        store.addToPurchasedList(buyListItems);

        return "success";
    }
}
