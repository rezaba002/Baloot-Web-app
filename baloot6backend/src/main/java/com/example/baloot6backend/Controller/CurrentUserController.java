package com.example.baloot6backend.Controller;

import com.example.baloot6backend.Model.User;
import com.example.baloot6backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/")
public class CurrentUserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("currentUser")
    public User getCurrentUser(){
        System.out.println("i'm there");
        List<User> users = userRepository.findAll();
        for (User userObj : users){
            if (userObj.isLoginStatus()){
                System.out.println("i'm here");
                return userObj;
            }
        }
        return null;
    }
}
