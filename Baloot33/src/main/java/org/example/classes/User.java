package org.example.classes;

public class User {
    private String username;
    private String password;
    private String email;
    private String birthDate;
    private String address;
    private int credit;

    public String getUsername() { return username; }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public String getBirthDate() {
        return birthDate;
    }
    public String getAddress() {
        return address;
    }
    public int getCredit() {
        return credit;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    public void setAddress(String address) {
        this.address = address;
    }
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
