package com.example.baloot6backend.Model;

import java.io.Serializable;
import java.util.Objects;

public class CommodityCategoryId implements Serializable {
    private Long commodity;
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getCommodity() {
        return commodity;
    }

    public void setCommodity(Long commodity) {
        this.commodity = commodity;
    }

    @Override
    public String toString() {
        return "CommodityCategoryId{" +
                "commodity=" + commodity +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommodityCategoryId that = (CommodityCategoryId) o;
        return Objects.equals(commodity, that.commodity) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commodity, category);
    }
}
