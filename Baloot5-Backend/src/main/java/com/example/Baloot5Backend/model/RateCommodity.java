package com.example.Baloot5Backend.model;

public class RateCommodity {
    private String username;
    private int commodityId;
    private float score;

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public int getCommodityId() {return commodityId;}
    public void setCommodityId(int commodityId) {this.commodityId = commodityId;}

    public float getScore() {return score;}
    public void setScore(float score) {this.score = score;}

    @Override
    public String toString() {
        return "RateCommodity{" +
                "username='" + username + '\'' +
                ", commodityId=" + commodityId +
                ", score=" + score +
                '}';
    }
}
