package com.infnet.transactionapi.domain.domainModels;

public class SellerDomain {
    private Long id;
    private String name;

    public SellerDomain() {}

    public SellerDomain(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
