package org.example.classes;

public class RateCommodity {
    private String username;
    private int commodityId;
    private int score;

    public String getUsername() {return username;}
    public int getCommodityId() {return commodityId;}
    public int getScore() {return score;}

    public void setUsername(String username) {this.username = username;}
    public void setCommodityId(int commodityId) {this.commodityId = commodityId;}
    public void setScore(int score) {this.score = score;}

    @Override
    public String toString() {
        return "RateCommodity{" +
                "username='" + username + '\'' +
                ", commodityId=" + commodityId +
                ", score=" + score +
                '}';
    }
}
