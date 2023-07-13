package com.example.baloot6backend.Model;

import java.io.Serializable;
import java.util.Objects;

public class UserDiscountId implements Serializable {
    private String user;
    private String discount;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "UserDiscountId{" +
                "user='" + user + '\'' +
                ", discount='" + discount + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDiscountId that = (UserDiscountId) o;
        return Objects.equals(user, that.user) && Objects.equals(discount, that.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, discount);
    }
}
