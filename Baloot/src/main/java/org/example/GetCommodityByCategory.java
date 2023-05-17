package org.example;

public class GetCommodityByCategory {
    private String category;

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "GetCommodityByCategory{" +
                "category='" + category + '\'' +
                '}';
    }
}