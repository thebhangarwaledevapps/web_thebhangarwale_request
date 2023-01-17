package com.app.request.entity;

public class BhangarTypeAndPrice {

    private Long id;
    private String bhangarType;
    private String bhangarUnit;
    private Double bhangarPrice;

    public BhangarTypeAndPrice() {
    }

    public BhangarTypeAndPrice(Long id, String bhangarType, String bhangarUnit, Double bhangarPrice) {
        this.id = id;
        this.bhangarType = bhangarType;
        this.bhangarUnit = bhangarUnit;
        this.bhangarPrice = bhangarPrice;
    }

    public Long getId() {
        return id;
    }

    public String getBhangarType() {
        return bhangarType;
    }

    public String getBhangarUnit() {
        return bhangarUnit;
    }

    public Double getBhangarPrice() {
        return bhangarPrice;
    }

}
