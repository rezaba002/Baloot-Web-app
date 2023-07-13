package com.example.baloot6backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Discount {
    @Id
    private String discountCode;
    private float discount;
    @JsonIgnore
    @OneToMany(targetEntity = UserDiscount.class, mappedBy = "discount", cascade = CascadeType.ALL)
    private List<UserDiscount> userDiscounts;

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "discountCode='" + discountCode + '\'' +
                ", discount=" + discount +
                ", userDiscounts=" + userDiscounts +
                '}';
    }
}
