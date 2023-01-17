package com.app.request.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer {

    @Id
    @Column(nullable = false)
    private String customerId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<RequestItem> requestItems;

    public Customer() {
    }

    public Customer(String customerId, List<RequestItem> requestItems) {
        this.customerId = customerId;
        this.requestItems = requestItems;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<RequestItem> getRequestItems() {
        return requestItems;
    }

    public void setRequestItems(List<RequestItem> requestItems) {
        this.requestItems = requestItems;
    }
}
