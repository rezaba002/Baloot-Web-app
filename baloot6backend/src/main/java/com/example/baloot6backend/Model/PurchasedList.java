package com.example.baloot6backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "purchasedlists")
public class PurchasedList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @JsonIgnore
    @OneToMany(mappedBy = "purchasedList")
    private List<PurchasedListItem> purchasedListItems;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_purchasedlist")
    private User user;

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public List<PurchasedListItem> getPurchasedListItems() {return purchasedListItems;}

    public void setPurchasedListItems(List<PurchasedListItem> purchasedListItems) {this.purchasedListItems = purchasedListItems;}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PurchasedList{" +
                "id=" + id +
                ", username='" + username + '\'' +
//                ", purchasedListItems=" + purchasedListItems +
                ", user=" + user +
                '}';
    }

}
