package com.example.Baloot5Backend.model;

public class BuyListItem {
    private String username;
    private Commodity commodity;
    private int quantity;





    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public Commodity getCommodity() {return commodity;}
    public void setCommodity(Commodity commodity) {this.commodity = commodity;}

    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    @Override
    public String toString() {
        return "BuyListItem{" +
                "username='" + username + '\'' +
                ", commodity=" + commodity +
                ", quantity=" + quantity +
                '}';
    }
}
