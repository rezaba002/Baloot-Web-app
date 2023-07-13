package com.example.baloot6backend.Controller;

import com.example.baloot6backend.Model.User;
import com.example.baloot6backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.StringTokenizer;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/")
public class CreditController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("credit")
    public int addCredit (@RequestBody String creditAmount){
        System.out.println(creditAmount);
        StringTokenizer tokenizer = new StringTokenizer(creditAmount,"=");
        int creditNumber = Integer.parseInt(tokenizer.nextToken());
        List<User> users = userRepository.findAll();
        for (User userObj: users){
            if (userObj.isLoginStatus()){
                userObj.setCredit(userObj.getCredit() + creditNumber);
                userRepository.save(userObj);
                return userObj.getCredit();
            }
        }
        return 0;
    }
}
