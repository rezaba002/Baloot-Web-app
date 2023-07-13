package org.example.classes;

public class Discount {
    private String discountCode;
    private float discount;

    public Discount (String discountCode, float discount) {
        this.discountCode = discountCode;
        this.discount = discount;
    }

    public String getDiscountCode() {return discountCode;}
    public float getDiscount() {return discount;}

    public void setDiscountCode(String discountCode) {this.discountCode = discountCode;}
    public void setDiscount(float discount) {this.discount = discount;}

    @Override
    public String toString() {
        return "Discount{" +
                "discountCode='" + discountCode + '\'' +
                ", discount=" + discount +
                '}';
    }
}
