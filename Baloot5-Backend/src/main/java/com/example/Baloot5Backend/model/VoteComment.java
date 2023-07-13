package com.example.Baloot5Backend.model;

public class VoteComment {
    private String username;
    private int commentId;
    private int vote;

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public int getCommentId() {return commentId;}
    public void setCommentId(int commentId) {this.commentId = commentId;}

    public int getVote() {return vote;}
    public void setVote(int vote) {this.vote = vote;}

    @Override
    public String toString() {
        return "VoteComment{" +
                "username='" + username + '\'' +
                ", commentId=" + commentId +
                ", vote=" + vote +
                '}';
    }
}
