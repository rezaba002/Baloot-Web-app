package com.example.baloot6backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Entity
@Table (name = "buyListItems")
public class BuyListItem {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    @JsonProperty("commodity")
    @OneToOne(targetEntity = Commodity.class)
    @JoinColumn(name = "fk_commodity")
    private Commodity commodity;
    private int quantity;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_buylist")
    private BuyList buylist;

    @Override
    public String toString() {
        return "BuyListItem{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", commodity=" + commodity +
                ", quantity=" + quantity +
//                ", buylist=" + buylist +
                '}';
    }

    public BuyList getBuylist() {
        return buylist;
    }

    public void setBuylist(BuyList buylist) {
        this.buylist = buylist;
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
