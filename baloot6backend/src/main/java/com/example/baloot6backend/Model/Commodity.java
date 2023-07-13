package com.example.baloot6backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import javax.sound.midi.Sequence;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "commodities")
public class Commodity {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int providerId;
    private int price;
    @JsonIgnore
    @OneToMany(targetEntity = CommodityCategory.class, mappedBy = "commodity", cascade = CascadeType.ALL)
    private List<CommodityCategory> commodityCategories;

    @OneToOne(mappedBy = "commodity")
    private BuyListItem buyListItem;


    @OneToMany(mappedBy = "commodity", targetEntity = PurchasedListItem.class, cascade = CascadeType.ALL)
    private List<PurchasedListItem> purchasedListItems = new ArrayList<>();
    @OneToMany(targetEntity = RateCommodity.class, mappedBy = "commodity")
    private List<RateCommodity> rateCommodities;
    private float rating;
    private int inStock;
    private String image;
    private int numberOfVoters;

    public Commodity() {}

    public Commodity(Long id, String name, int providerId, int price, float rating, int inStock, String image, int numberOfVoters) {
        this.id = id;
        this.name = name;
        this.providerId = providerId;
        this.price = price;
        this.rating = rating;
        this.inStock = inStock;
        this.image = image;
        this.numberOfVoters = numberOfVoters;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getProviderId() {return providerId;}
    public void setProviderId(int providerId) {this.providerId = providerId;}

    public int getPrice() {return price;}
    public void setPrice(int price) {this.price = price;}

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
//                ", commodityCategories=" + commodityCategories +
//                ", buyListItem=" + buyListItem +
//                ", purchasedListItems=" + purchasedListItems +
//                ", rateCommodities=" + rateCommodities +
                ", rating=" + rating +
                ", inStock=" + inStock +
                ", image='" + image + '\'' +
                ", numberOfVoters=" + numberOfVoters +
                '}';
    }

    public List<CommodityCategory> getCommodityCategories() {
        return commodityCategories;
    }

    public void setCommodityCategories(List<CommodityCategory> commodityCategories) {
        this.commodityCategories = commodityCategories;
    }

    public BuyListItem getBuyListItem() {
        return buyListItem;
    }

    public void setBuyListItem(BuyListItem buyListItem) {
        this.buyListItem = buyListItem;
    }

    public List<PurchasedListItem> getPurchasedListItems() {return purchasedListItems;}

    public void setPurchasedListItems(List<PurchasedListItem> purchasedListItems) {this.purchasedListItems = purchasedListItems;}

    public List<RateCommodity> getRateCommodities() {
        return rateCommodities;
    }

    public void setRateCommodities(List<RateCommodity> rateCommodities) {
        this.rateCommodities = rateCommodities;
    }
}
