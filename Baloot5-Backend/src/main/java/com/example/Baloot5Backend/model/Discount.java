package com.example.Baloot5Backend.model;

public class Discount {
    private String discountCode;
    private float discount;

    public String getDiscountCode() {return discountCode;}
    public void setDiscountCode(String discountCode) {this.discountCode = discountCode;}

    public float getDiscount() {return discount;}
    public void setDiscount(float discount) {this.discount = discount;}

    @Override
    public String toString() {
        return "Discount{" +
                "discountCode='" + discountCode + '\'' +
                ", discount=" + discount +
                '}';
    }
}
