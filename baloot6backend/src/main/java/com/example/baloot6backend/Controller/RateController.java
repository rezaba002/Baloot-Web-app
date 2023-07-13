package com.example.baloot6backend.Controller;

import com.example.baloot6backend.Model.Category;
import com.example.baloot6backend.Model.Commodity;
import com.example.baloot6backend.Model.CommodityDTO;
import com.example.baloot6backend.Model.User;
import com.example.baloot6backend.Repository.CategoryRepository;
import com.example.baloot6backend.Repository.CommodityRepository;
import com.example.baloot6backend.Repository.UserRepository;
import com.example.baloot6backend.Service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("")
public class RateController {

    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("rate")
    public CommodityDTO RateControl(@RequestBody Map<String, Object> jsonObject){
        long commodityId = (Integer) jsonObject.get("commodityId");
        String scoreString = (String) jsonObject.get("score");

        List<User> users = userRepository.findAll();
        for (User user : users){
            if (user.isLoginStatus()){
                String username = user.getUsername();
                float score = Float.parseFloat(scoreString);
                commodityService.rateCommodity(score, commodityId, username);
                System.out.println("RateController successfully updated the rate");
                CommodityDTO commodityDTOObj = commodityService.getCommodity(commodityId);
                System.out.println("updated commodity fetched");
                return commodityDTOObj;
            }
        }
        // creating a method in commodity service
        // it gets the score & commodityId as a value = done

        // set the new rate of commodityObj
        // save commodityObj using .save(commodityObj) = done
        // for returning, we should discuss it later.
        // I think we should only return the new rate, and set the new state in front
        return null;
    }
}
