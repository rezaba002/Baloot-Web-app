package com.example.Baloot5Backend.model;

import java.util.ArrayList;

public class Commodity {
    private int id;
    private String name;
    private int providerId;
    private int price;
    private ArrayList<String> categories;
    private float rating;
    private int inStock;
    private String image;
    private int numberOfVoters;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getProviderId() {return providerId;}
    public void setProviderId(int providerId) {this.providerId = providerId;}

    public int getPrice() {return price;}
    public void setPrice(int price) {this.price = price;}

    public ArrayList<String> getCategories() {return categories;}
    public void setCategories(ArrayList<String> categories) {this.categories = categories;}

    public float getRating() {return rating;}
    public void setRating(float rating) {this.rating = rating;}

    public int getInStock() {return inStock;}
    public void setInStock(int inStock) {this.inStock = inStock;}

    public String getImage() {return image;}
    public void setImage(String image) {this.image = image;}

    public int getNumberOfVoters() {return numberOfVoters;}
    public void setNumberOfVoters(int numberOfVoters) {this.numberOfVoters = numberOfVoters;}

    @Override
    public String toString() {
        return "Commodity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", providerId=" + providerId +
                ", price=" + price +
                ", categories=" + categories +
                ", rating=" + rating +
                ", inStock=" + inStock +
                ", image='" + image + '\'' +
                ", numberOfVoters=" + numberOfVoters +
                '}';
    }
}
