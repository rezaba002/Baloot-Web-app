package com.example.Baloot5Backend.model;

import java.util.ArrayList;

public class BuyList {
    private String username;
    private ArrayList<BuyListItem> buyListItems;

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public ArrayList<BuyListItem> getBuyListItems() {return buyListItems;}
    public void setBuyListItems(ArrayList<BuyListItem> buyListItems) {this.buyListItems = buyListItems;}

    @Override
    public String toString() {
        return "BuyList{" +
                "username='" + username + '\'' +
                ", buyListItems=" + buyListItems +
                '}';
    }
}
