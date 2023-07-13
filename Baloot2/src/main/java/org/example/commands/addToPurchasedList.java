package org.example.commands;

import org.example.BuyList;
import org.example.Commodity;
import org.example.Store;
import org.example.User;
import org.example.exceptions.NotEnoughCredit400Exp;

import java.util.StringTokenizer;

public class addToPurchasedList implements Command {
    public String handle(Store store, String secondParam, String thirdParam, String fourthParam) throws Exception {
        StringTokenizer tokenizer = new StringTokenizer(secondParam,"=");
        String attribute = tokenizer.nextToken();
        String username = tokenizer.nextToken();

        int usercredit = 0;

        for (User userObj : store.getUsers()){
            if (userObj.getUsername().equalsIgnoreCase(username)){
                usercredit = userObj.getCredit();
            }
        }

        int priceSum = 0;

        for (BuyList buyListObj : store.getBuyLists()){
            if (buyListObj.getUsername().equalsIgnoreCase(username)){
                for (Commodity commodityObj : store.getCommodities()){
                    if (buyListObj.getCommodityId() == commodityObj.getId()){
                        priceSum += commodityObj.getPrice();
                    }
                }
            }
        }

        int newCredit = usercredit - priceSum;

        if (usercredit < priceSum)
            throw new NotEnoughCredit400Exp();

        else {
            //add buyList to purchasedList
            for (BuyList buyListObj : store.getBuyLists()){
                if (buyListObj.getUsername().equalsIgnoreCase(username))
                    store.addToPurchasedList(username, buyListObj.getCommodityId());
            }
            //empty buyList
            for (int i=store.getBuyLists().size()-1 ; i>=0 ; i--){
                if (store.getBuyLists().get(i).getUsername().equalsIgnoreCase(username))
                    store.removeFromBuyList(username, store.getBuyLists().get(i).getCommodityId());
            }
            //update user's credit
            for (int i=0 ; i<store.getUsers().size() ; i++){
                if (store.getUsers().get(i).getUsername().equalsIgnoreCase(username)){
                    store.getUsers().get(i).setCredit(newCredit);
                    break;
                }
            }
        }
        return "users/" + username;
    }
}