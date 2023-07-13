//package com.example.baloot6backend.Model;
//
//import jakarta.persistence.*;
//
//import java.util.List;
//
//@Entity
//public class CurrentUser {
//    @Id
//    private String username;
//
//    public CurrentUser() {
//    }
//
//    public CurrentUser(String username, String password, String email, String birthDate, String address, int credit, BuyList buyList, List<PurchasedList> purchasedLists) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.birthDate = birthDate;
//        this.address = address;
//        this.credit = credit;
//        this.buyList = buyList;
//        this.purchasedLists = purchasedLists;
//    }
//
//    @OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "fk_user")
//    private User user;
//
//    public BuyList getBuyList() {return buyList;}
//
//    public void setBuyList(BuyList buyList) {this.buyList = buyList;}
//    public String getUsername() {return username;}
//    public void setUsername(String username) {this.username = username;}
//
//    public String getPassword() {return password;}
//    public void setPassword(String password) {this.password = password;}
//
//    public String getEmail() {return email;}
//    public void setEmail(String email) {this.email = email;}
//
//    public String getBirthDate() {return birthDate;}
//    public void setBirthDate(String birthDate) {this.birthDate = birthDate;}
//
//    public String getAddress() {return address;}
//    public void setAddress(String address) {this.address = address;}
//
//    public int getCredit() {return credit;}
//    public void setCredit(int credit) {this.credit = credit;}
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", email='" + email + '\'' +
//                ", birthDate='" + birthDate + '\'' +
//                ", address='" + address + '\'' +
//                ", credit=" + credit +
//                '}';
//    }
//
//}
