package com.example.Baloot5Backend.model;

public class Comment {
    private int commentId;
    private String userEmail;
    private int commodityId;
    private String text;
    private String date;
    private int like;
    private int dislike;

    public int getCommentId() {return commentId;}
    public void setCommentId(int commentId) {this.commentId = commentId;}

    public String getUserEmail() {return userEmail;}
    public void setUserEmail(String userEmail) {this.userEmail = userEmail;}

    public int getCommodityId() {return commodityId;}
    public void setCommodityId(int commodityId) {this.commodityId = commodityId;}

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}

    public int getLike() {return like;}
    public void setLike(int like) {this.like = like;}

    public int getDislike() {return dislike;}
    public void setDislike(int dislike) {this.dislike = dislike;}

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", userEmail='" + userEmail + '\'' +
                ", commodityId=" + commodityId +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                ", like=" + like +
                ", dislike=" + dislike +
                '}';
    }
}
