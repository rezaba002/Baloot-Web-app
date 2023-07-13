package com.example.Baloot5Backend.controller;

import com.example.Baloot5Backend.model.BuyList;
import com.example.Baloot5Backend.model.BuyListItem;
import com.example.Baloot5Backend.model.Discount;
import com.example.Baloot5Backend.model.Store;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/")
public class DiscountController {
    Store store = Store.getInstance();

    @GetMapping("discounts")
    public List<Discount> getDiscounts(){
        System.out.println(store.getDiscounts());
        return store.getDiscounts();
    }

    @PostMapping("discount")
    public String applyDiscount(@RequestBody String discount){

        StringTokenizer tokenizer = new StringTokenizer(discount, "=");
        String discountCode = tokenizer.nextToken();

        String username = store.getCurrentUser().getUsername();

        ArrayList<BuyListItem> buyListItems = new ArrayList<BuyListItem>();

        float cartPrice = 0;

        for (BuyList buyListObj : store.getBuyLists()) {
            if (buyListObj.getUsername().equalsIgnoreCase(username)) {
                buyListItems.addAll(buyListObj.getBuyListItems());
            }
        }
        System.out.println(buyListItems);
        for (BuyListItem buyListItemObj : buyListItems) {
            cartPrice += buyListItemObj.getQuantity() * buyListItemObj.getCommodity().getPrice();
        }
        System.out.println(cartPrice);
        System.out.println(discountCode);
        if (!store.checkIfDiscountExists(discountCode))
            return "wrong discount alert";

        if (store.checkIfDiscountIsExpired(username, discountCode))
            return "wrong discount alert";

        for (Discount discountObj : store.getDiscounts()){
            if (discountObj.getDiscountCode().equalsIgnoreCase(discountCode)) {
                cartPrice = cartPrice * ( 1 - (discountObj.getDiscount() / 100));
                //store.addExpiredDiscount(username, discountObj.getDiscountCode());
            }
        }
        System.out.println(cartPrice);
        return String.valueOf(cartPrice);
    }
}
