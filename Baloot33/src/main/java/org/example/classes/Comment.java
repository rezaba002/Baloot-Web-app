package org.example.classes;

public class Comment {
    private int commentId;
    private String userEmail;
    private int commodityId;
    private String text;
    private String date;
    private int like;
    private int dislike;

    public int getCommentId() {return commentId;}
    public String getUserEmail() {return userEmail;}
    public int getCommodityId() {return commodityId;}
    public String getText() {return text;}
    public String getDate() {return date;}
    public int getLike() {return like;}
    public int getDislike() {return dislike;}

    public void setCommentId(int commentId) {this.commentId = commentId;}
    public void setUserEmail(String userEmail) {this.userEmail = userEmail;}
    public void setCommodityId(int commodityId) {this.commodityId = commodityId;}
    public void setText(String text) {this.text = text;}
    public void setDate(String date) {this.date = date;}
    public void setLike(int like) {this.like = like;}
    public void setDislike(int dislike) {this.dislike = dislike;}

    @Override
    public String toString() {
        return "Comment{" +
                "userEmail='" + userEmail + '\'' +
                ", commodityId=" + commodityId +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
