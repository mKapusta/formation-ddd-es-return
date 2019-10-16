package com.company.domain.event;

import com.company.domain.ProductId;

import java.util.Set;

public class ProductFamilyDefined implements ProductFamilyEvent{
    public final Set<ProductId> productsId;

    public ProductFamilyDefined(Set<ProductId> productsId) {
        this.productsId = productsId;
    }
}
