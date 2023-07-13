package com.example.baloot6backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@IdClass(RateCommodityId.class)
public class RateCommodity {
    @Id
    @JsonIgnore
    @ManyToOne(targetEntity = Commodity.class)
    @JoinColumn(name = "commodity_id", referencedColumnName = "id")
    private Commodity commodity;

    @Id
    @JsonIgnore
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;
    private float score;

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "RateCommodity{" +
                "commodity=" + commodity +
                ", user=" + user +
                ", score=" + score +
                '}';
    }
}
