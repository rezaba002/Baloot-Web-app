package com.example.Baloot5Backend.controller;

import com.example.Baloot5Backend.model.Store;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/")
public class LogoutController {
    @GetMapping("logout")
    public String logoutControl(){
        Store store = Store.getInstance();
        if (store.checkIfCurrentUserIsLoggedIn())
            store.clearCurrentUser();
        return "success";
    }
}
