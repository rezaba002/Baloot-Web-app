package com.example.baloot6backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    private String name;

    @JsonIgnore
    @OneToMany(targetEntity = CommodityCategory.class, mappedBy = "category", cascade = CascadeType.ALL)
    private List<CommodityCategory> commodityCategories;

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }
}
