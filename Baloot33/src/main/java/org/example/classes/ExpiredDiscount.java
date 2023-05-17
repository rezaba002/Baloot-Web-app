package org.example.classes;

public class ExpiredDiscount {
    private String username;
    private String discountCode;

    public String getUsername() {return username;}
    public String getDiscountCode() {return discountCode;}

    public void setUsername(String username) {this.username = username;}
    public void setDiscountCode(String discountCode) {this.discountCode = discountCode;}

    @Override
    public String toString() {
        return "ExpiredDiscount{" +
                "username='" + username + '\'' +
                ", discountCode='" + discountCode + '\'' +
                '}';
    }
}
