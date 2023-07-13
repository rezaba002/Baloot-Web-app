package com.example.baloot6backend.Controller;

import com.example.baloot6backend.Model.User;
import com.example.baloot6backend.Repository.UserRepository;
import com.example.baloot6backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/")
public class RegisterController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("register")
    public String RegisterControl(@RequestBody User user){
        User userObj = new User();
        userObj.setUsername(user.getUsername());
        userObj.setPassword(user.getPassword());
        userObj.setEmail(user.getEmail());
        userObj.setAddress(user.getAddress());
        userObj.setBirthDate(user.getBirthDate());
        userObj.setCredit(user.getCredit());

        //check if username is valid
        for (char c : user.getUsername().toCharArray()) {
            if (!Character.isLetterOrDigit(c))
                return "please enter a valid username";
        }
        //check if username is already in database
        if (userRepository.findByUsername(user.getUsername()) == null){
            return "This username is already taken";
        }
        //check if email is already in database
        if (userRepository.findByEmail(user.getEmail()) == null){
            return "This email is already taken";
        }
        //adding user
        userService.addNewUser(userObj);
        return "Register was successful";
    }
}
