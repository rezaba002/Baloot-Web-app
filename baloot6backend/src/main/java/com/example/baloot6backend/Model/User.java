package com.example.baloot6backend.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String username;
    private String password;
    private String email;
    private String birthDate;
    private String address;
    private int credit;
    private boolean loginStatus = false;

    public boolean isLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    @JsonIgnore
    @OneToOne(targetEntity = BuyList.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_buylist")
    private BuyList buyList;
    @JsonIgnore
    @OneToMany(targetEntity = PurchasedList.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_purchasedlist")
    private List<PurchasedList> purchasedLists;

    @JsonIgnore
    @OneToMany(targetEntity = RateCommodity.class, mappedBy = "user")
    private List<RateCommodity> rateCommodity;
    @JsonIgnore
    @OneToMany(targetEntity = UserDiscount.class, mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserDiscount> userDiscounts;
    public BuyList getBuyList() {return buyList;}
    public void setBuyList(BuyList buyList) {this.buyList = buyList;}

    public List<PurchasedList> getPurchasedLists() {
        return purchasedLists;
    }

    public void setPurchasedLists(List<PurchasedList> purchasedLists) {
        this.purchasedLists = purchasedLists;
    }

    public List<RateCommodity> getRateCommodity() {
        return rateCommodity;
    }

    public void setRateCommodity(List<RateCommodity> rateCommodity) {
        this.rateCommodity = rateCommodity;
    }

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getBirthDate() {return birthDate;}
    public void setBirthDate(String birthDate) {this.birthDate = birthDate;}

    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

    public int getCredit() {return credit;}
    public void setCredit(int credit) {this.credit = credit;}

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", address='" + address + '\'' +
                ", credit=" + credit +
                ", loginStatus=" + loginStatus +
                ", buyList=" + buyList +
//                ", purchasedLists=" + purchasedLists +
//                ", rateCommodity=" + rateCommodity +
//                ", userDiscounts=" + userDiscounts +
                '}';
    }

}
