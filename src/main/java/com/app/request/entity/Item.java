package com.app.request.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemId;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private long itemQuantity;

    @Column(nullable = false)
    private double itemPrice;

    @Column(nullable = false)
    private String itemUnit;

    @Column(nullable = false)
    private double totalItemPriceForUserAsPerQuantity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Media> medias;

    public Item() {
    }

    public Item(String itemName, int itemQuantity, double itemPrice, String itemUnit, double totalItemPriceForUserAsPerQuantity, List<Media> medias) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.itemUnit = itemUnit;
        this.totalItemPriceForUserAsPerQuantity = totalItemPriceForUserAsPerQuantity;
        this.medias = medias;
    }

    public long getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public long getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(long itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public double getTotalItemPriceForUserAsPerQuantity() {
        return totalItemPriceForUserAsPerQuantity;
    }

    public void setTotalItemPriceForUserAsPerQuantity(double totalItemPriceForUserAsPerQuantity) {
        this.totalItemPriceForUserAsPerQuantity = totalItemPriceForUserAsPerQuantity;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }
}
