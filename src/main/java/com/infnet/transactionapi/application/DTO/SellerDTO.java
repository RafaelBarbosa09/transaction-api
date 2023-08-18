package com.infnet.transactionapi.application.DTO;

public class SellerDTO {
    private String name;

    public SellerDTO() {}

    public SellerDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
