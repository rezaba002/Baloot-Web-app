package com.example.Baloot5Backend.controller;

import com.example.Baloot5Backend.model.CurrentUser;
import com.example.Baloot5Backend.model.Store;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/")
public class LoginController {
    Store store = Store.getInstance();

    @PostMapping("login")
    public Boolean loginControl(@RequestBody CurrentUser currentUser) {
        String username = currentUser.getUsername();
        String password = currentUser.getPassword();

        if (store.checkIfLoginCredentialsIsValid(username, password)) {
            store.setFullCurrentUser(username);
            return true;
        }
        return false;
    }
}
