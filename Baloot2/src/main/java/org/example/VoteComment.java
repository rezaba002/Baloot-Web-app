package org.example;

public class VoteComment {
    private String username;
    private int commentId;
    private int vote;


    public String getUsername() {return username;}
    public int getCommentId() {return commentId;}
    public int getVote() {return vote;}


    public void setUsername(String username) {this.username = username;}
    public void setCommentId(int commentId) {this.commentId = commentId;}
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