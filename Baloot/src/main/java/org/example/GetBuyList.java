package org.example;

public class GetBuyList {
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "getBuyList{" +
                "username='" + username + '\'' +
                '}';
    }
}