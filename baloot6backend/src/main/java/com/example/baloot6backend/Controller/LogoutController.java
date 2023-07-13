package com.example.baloot6backend.Controller;

import com.example.baloot6backend.Model.User;
import com.example.baloot6backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/")
public class LogoutController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("logout")
    public String Logout(){
        List<User> users = userRepository.findAll();

        for (User userObj : users){
            if (userObj.isLoginStatus()) {
                userObj.setLoginStatus(false);
                userRepository.save(userObj);
                return "success";
            }
        }
        return null;
    }
}
