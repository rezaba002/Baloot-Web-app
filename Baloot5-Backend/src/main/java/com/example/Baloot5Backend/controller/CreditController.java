package com.example.Baloot5Backend.controller;

import com.example.Baloot5Backend.model.Store;
import com.example.Baloot5Backend.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.StringTokenizer;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/")
public class CreditController {
    @PostMapping("credit")
    public int addCredit(@RequestBody String creditAmount) {
        System.out.println(creditAmount);
        StringTokenizer tokenizer = new StringTokenizer(creditAmount,"=");
        int creditNumber = Integer.valueOf(tokenizer.nextToken());
        Store store = Store.getInstance();
        String username = store.getCurrentUser().getUsername();
        for (User userObj : store.getUsers()) {
            if (username.equalsIgnoreCase(userObj.getUsername())) {
                userObj.setCredit(userObj.getCredit() + creditNumber);
                store.getCurrentUser().setCredit(userObj.getCredit());
                return userObj.getCredit();
            }
        }
        return 0;
    }
}
