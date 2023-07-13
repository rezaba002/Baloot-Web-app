package com.example.baloot6backend.Service;

import com.example.baloot6backend.Model.Discount;
import com.example.baloot6backend.Model.User;
import com.example.baloot6backend.Model.UserDiscount;
import com.example.baloot6backend.Repository.DiscountRepository;
import com.example.baloot6backend.Repository.UserDiscountRepository;
import com.example.baloot6backend.Repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountService {
    ArrayList<Discount> discounts = new ArrayList<Discount>();

    public ArrayList<Discount> getDiscounts() {return discounts;}

    public void setDiscounts(ArrayList<Discount> discounts) {this.discounts = discounts;}
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDiscountRepository userDiscountRepository;
    public void addDiscount(HttpClient client, HttpRequest request, Gson gson) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type discountsList = new TypeToken<ArrayList<Discount>>() {}.getType();
        discounts = gson.fromJson(response.body(), discountsList);
        List<Discount> availableDiscounts = discountRepository.findAll();
        if (availableDiscounts.isEmpty()) {
            discountRepository.saveAll(discounts);
            List<User> users = userRepository.findAll();
            for (User userObj : users) {
                for (Discount discountObj : discounts) {
                    UserDiscount userDiscountObj = new UserDiscount(userObj, discountObj);
                    userDiscountRepository.save(userDiscountObj);
                }
            }
        }
    }
    public boolean checkIfDiscountExists(String username, String discountCode){
        List<UserDiscount> userDiscounts = userDiscountRepository.findByUserUsername(username);
        for (UserDiscount userDiscountObj : userDiscounts){
            if (userDiscountObj.getDiscount().getDiscountCode().equalsIgnoreCase(discountCode)){
                return true;
            }
        }
        return false;
    }

}
