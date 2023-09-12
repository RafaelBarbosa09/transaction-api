package com.infnet.transactionapi.domain.domainModels;

import java.util.List;
import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SellerDomain that = (SellerDomain) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
