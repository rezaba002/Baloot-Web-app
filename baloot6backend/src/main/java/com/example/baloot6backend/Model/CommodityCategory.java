package com.example.baloot6backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@IdClass(CommodityCategoryId.class)
@Table (name = "commodityCategories")
public class CommodityCategory {
    @Id
    @JsonIgnore
    @ManyToOne(targetEntity = Commodity.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "commodity_id", referencedColumnName = "id")
    private Commodity commodity;
    @Id
    @JsonIgnore
    @ManyToOne(targetEntity = Category.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_name", referencedColumnName = "name")
    private Category category;

    public CommodityCategory(Commodity commodity, Category category) {
        this.commodity = commodity;
        this.category = category;
    }

    public CommodityCategory() {
    }

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "CommodityCategory{" +
                "commodity=" + commodity +
                ", category=" + category +
                '}';
    }
}
