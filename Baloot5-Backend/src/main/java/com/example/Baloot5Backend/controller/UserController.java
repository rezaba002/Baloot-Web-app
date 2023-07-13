package com.example.Baloot5Backend.controller;

import com.example.Baloot5Backend.model.User;
import com.example.Baloot5Backend.model.Store;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/")
public class UserController {
    Store store = Store.getInstance();

    @GetMapping("users")
    public List<User> getUsers() {
        return store.getUsers();
    }
}
