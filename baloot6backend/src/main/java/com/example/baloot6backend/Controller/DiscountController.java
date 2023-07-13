package com.example.baloot6backend.Controller;

import com.example.baloot6backend.Model.BuyListItem;
import com.example.baloot6backend.Model.Discount;
import com.example.baloot6backend.Model.User;
import com.example.baloot6backend.Repository.DiscountRepository;
import com.example.baloot6backend.Repository.UserRepository;
import com.example.baloot6backend.Service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/")
public class DiscountController {

    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DiscountService discountService;

    @GetMapping("discounts")
    public List<Discount> getDiscounts(){
        return discountRepository.findAll();
    }

    @PostMapping("discount")
    public String applyDiscount(@RequestBody String discount){
        StringTokenizer tokenizer = new StringTokenizer(discount, "=");
        String discountCode = tokenizer.nextToken();

        float cartPrice = 0;

        List<User> users = userRepository.findAll();

        for (User userObj : users){
            if (userObj.isLoginStatus()){
                List<BuyListItem> buyListItems = userObj.getBuyList().getBuyListItems();
                for (BuyListItem buyListItemObj : buyListItems){
                    cartPrice += buyListItemObj.getQuantity() * buyListItemObj.getCommodity().getPrice();
                }
                if (!discountService.checkIfDiscountExists(userObj.getUsername(), discountCode)){
                    return "wrong discount alert";
                }
                Discount discountObj = discountRepository.findByDiscountCode(discountCode);
                cartPrice = cartPrice * ( 1 - (discountObj.getDiscount() / 100));
            }
        }
        return String.valueOf(cartPrice);
    }
}
