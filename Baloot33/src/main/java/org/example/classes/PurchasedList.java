package org.example.classes;

public class PurchasedList {
    private String username;
    private int commodityId;

    public String getUsername() {return username;}
    public int getCommodityId() {return commodityId;}

    public void setUsername(String username) {this.username = username;}
    public void setCommodityId(int commodityId) {this.commodityId = commodityId;}

    @Override
    public String toString() {
        return "BuyList{" +
                "username='" + username + '\'' +
                ", commodityId='" + commodityId + '\'' +
                '}';
    }
}
