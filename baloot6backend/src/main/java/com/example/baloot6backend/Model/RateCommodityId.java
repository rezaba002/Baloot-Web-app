package com.example.baloot6backend.Model;


import java.io.Serializable;
import java.util.Objects;

public class RateCommodityId implements Serializable {
    private Long commodity;
    private String user;

    public Long getCommodity() {
        return commodity;
    }

    public void setCommodity(Long commodity) {
        this.commodity = commodity;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RateCommodityId that = (RateCommodityId) o;
        return Objects.equals(commodity, that.commodity) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commodity, user);
    }
}
