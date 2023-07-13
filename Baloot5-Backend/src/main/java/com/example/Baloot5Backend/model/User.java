package com.example.Baloot5Backend.model;

public class User {
    private String username;
    private String password;
    private String email;
    private String birthDate;
    private String address;
    private int credit;

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getBirthDate() {return birthDate;}
    public void setBirthDate(String birthDate) {this.birthDate = birthDate;}

    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

    public int getCredit() {return credit;}
    public void setCredit(int credit) {this.credit = credit;}

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
