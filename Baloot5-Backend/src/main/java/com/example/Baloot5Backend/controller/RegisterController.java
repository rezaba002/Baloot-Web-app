package com.example.Baloot5Backend.controller;

import com.example.Baloot5Backend.model.User;
import com.example.Baloot5Backend.model.Store;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/")
public class RegisterController {
    Store store = Store.getInstance();

    @PostMapping("register")
    public String RegisterControl(@RequestBody User user) {
        String username = user.getUsername();
        String email = user.getEmail();

        if (!store.checkIfUsernameIsValid(username))
            return "username is invalid";
        else if (store.checkIfUsernameAlreadyExists(username))
            return "username is already taken";
        else if (store.checkIfEmailAlreadyExists(email))
            return "email is already taken";
        else {
            store.getUsers().add(user);
            return "registration successful";
        }
    }
}
