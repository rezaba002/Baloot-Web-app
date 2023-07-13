package com.example.Baloot5Backend.controller;

import com.example.Baloot5Backend.model.CurrentUser;
import com.example.Baloot5Backend.model.Store;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/")
public class CurrentUserController {
    Store store = Store.getInstance();

    @GetMapping("currentUser")
    public CurrentUser getCurrentUser() {
        return store.getCurrentUser();
    }
}
