package com.example.baloot6backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "purchasedListItems")
public class PurchasedListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    
    @ManyToOne(targetEntity = Commodity.class)
    @JoinColumn(name = "fk_commodity")
    private Commodity commodity;
    private int quantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_purchasedlist")
    private PurchasedList purchasedList;

    public PurchasedList getPurchasedList() {
        return purchasedList;
    }
    public void setPurchasedList(PurchasedList purchasedList) {
        this.purchasedList = purchasedList;
    }

    @Override
    public String toString() {
        return "PurchasedListItem{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", commodity=" + commodity +
                ", quantity=" + quantity +
                ", purchasedList=" + purchasedList +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
