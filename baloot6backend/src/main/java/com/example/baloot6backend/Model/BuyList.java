package com.example.baloot6backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "buylists")
public class BuyList {
    @Id
    private String username;

    @JsonIgnore
    @OneToMany(mappedBy = "buylist")
    private List<BuyListItem> buyListItems;

    @JsonIgnore
    @OneToOne(targetEntity = User.class, mappedBy = "buyList")
    private User user;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<BuyListItem> getBuyListItems() {
        return buyListItems;
    }

    public void setBuyListItems(List<BuyListItem> buyListItems) {
        this.buyListItems = buyListItems;
    }

    @Override
    public String toString() {
        return "BuyList{" +
                "username='" + username + '\'' +
                ", buyListItems=" + buyListItems +
//                ", user=" + user +
                '}';
    }
}
