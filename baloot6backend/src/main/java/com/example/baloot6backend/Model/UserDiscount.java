package com.example.baloot6backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Entity
@IdClass(UserDiscountId.class)
public class UserDiscount {

    @Id
    @JsonIgnore
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;
    @Id
    @JsonIgnore
    @ManyToOne(targetEntity = Discount.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "discount_code", referencedColumnName = "discountCode")
    private Discount discount;

    public UserDiscount(User user, Discount discount) {
        this.user = user;
        this.discount = discount;
    }
    public UserDiscount(){

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "UserDiscount{" +
//                "user=" + user +
//                ", discount=" + discount +
                '}';
    }
}
