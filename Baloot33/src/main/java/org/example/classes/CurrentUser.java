package org.example.classes;

public class CurrentUser {
    private String username;

    public String getUsername() { return username; }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "username='" + username + '\'' +
                '}';
    }
}
