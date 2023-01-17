package com.app.request.entity;

import javax.persistence.*;

@Entity
public class RequestItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long requestItemId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Item item;

    public RequestItem() {
    }

    public RequestItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public long getRequestItemId() {
        return requestItemId;
    }
}
