package org.example;

import java.util.ArrayList;

public class Commodity {
    private int id;
    private String name;
    private int providerId;
    private int price;
    private ArrayList<String> categories;
    private float rating;
    private int inStock;
    private int numberOfVoters;

    public Commodity(int id, String name, int providerId, int price, ArrayList<String> categories, float rating, int inStock){
        this.id = id;
        this.name = name;
        this.providerId = providerId;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.inStock = inStock;
    }

    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setProviderId(int providerId) {this.providerId = providerId;}
    public void setPrice(int price) {this.price = price;}
    public void setCategories(ArrayList<String> categories) {this.categories = categories;}
    public void setRating(float rating) {this.rating = rating;}
    public void setInStock(int inStock) {this.inStock = inStock;}
    public void setNumberOfVoters(int numberOfVoters) {this.numberOfVoters = numberOfVoters;}

    public int getId() {return id;}
    public String getName() {return name;}
    public int getProviderId() {return providerId;}
    public int getPrice() {return price;}
    public ArrayList<String> getCategories() {return categories;}
    public float getRating() {return rating;}
    public int getInStock() {return inStock;}
    public int getNumberOfVoters() {return numberOfVoters;}

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
                ", numberOfVoters=" + numberOfVoters +
                '}';
    }
}