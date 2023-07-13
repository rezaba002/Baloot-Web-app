package org.example;

public class User {
    private String password;
    private String username;
    private String email;
    private String birthDate;
    private String address;
    private int credit;
    public User (){
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.address = address;
        this.credit = credit;
    }
    public String getUsername() { return username; }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public String getBirthDate() {
        return birthDate;
    }
    public int getCredit() {
        return credit;
    }
    public String getAddress() {
        return address;
    }
    public String getEmail() {
        return email;
    }
    public void setAddress(String newAddress) {
        this.address = address;
    }
    public void setBirthDate(String newBirthDate) {
        this.birthDate = birthDate;
    }
    public void setCredit(int newCredit) {
        this.credit = credit;
    }
    public void setEmail(String newEmail) {
        this.email = email;
    }
    public void setPassword(String newPassword) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", address='" + address + '\'' +
                ", credit=" + credit +
                '}';
    }
}