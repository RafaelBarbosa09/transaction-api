package com.infnet.transactionapi.domain.entities;

public class Seller {
    private String name;

    public Seller() {}

    public Seller(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
