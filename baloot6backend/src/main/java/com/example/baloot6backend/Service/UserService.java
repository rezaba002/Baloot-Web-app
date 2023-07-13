package com.example.baloot6backend.Service;

import com.example.baloot6backend.Model.*;
import com.example.baloot6backend.Repository.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    ArrayList<User> users = new ArrayList<User>();
    public ArrayList<User> getUsers() {return users;}
    public void setUsers(ArrayList<User> users) {this.users = users;}

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BuyListRepository buyListRepository;
    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private BuyListItemRepository buyListItemRepository;
    @Transactional
    public void addUser(HttpClient client, HttpRequest request, Gson gson) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type usersList = new TypeToken<ArrayList<User>>() {}.getType();
        users = gson.fromJson(response.body(), usersList);
        List<User> availableUsers = userRepository.findAll();
        if (availableUsers.isEmpty()) {
            for (User userObj : users) {

                BuyList buyListObj = new BuyList();
                buyListObj.setUsername(userObj.getUsername());
                userObj.setBuyList(buyListObj);

                ArrayList<PurchasedList> purchasedLists = new ArrayList<>();
                PurchasedList purchasedListObj = new PurchasedList();
                purchasedLists.add(purchasedListObj);
                purchasedListObj.setUsername(userObj.getUsername());
                userObj.setPurchasedLists(purchasedLists);

                userRepository.save(userObj);
            }
        }
    }
//    public void addCurrentUser(User user){
//        CurrentUser currentUser = new CurrentUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getBirthDate(),
//                                                  user.getAddress(), user.getCredit(), user.getBuyList(), user.getPurchasedLists() );
//
//        currentUserRepository.save(currentUser);
//    public void loginUser(String username){
//        User userObj = userRepository.findByUsername(username);
//        userObj.setLoginStatus(true);
//        userRepository.save(userObj);
//    }
    public void logoutUser(String username){
        User userObj = userRepository.findByUsername(username);
        userObj.setLoginStatus(false);
        userRepository.save(userObj);
    }
    public void addNewUser(User user){
        userRepository.save(user);
    }

}
