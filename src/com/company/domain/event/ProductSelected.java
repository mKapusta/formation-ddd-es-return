package com.company.domain.event;

import com.company.domain.ProductId;

public class ProductSelected implements ProductFamilyEvent {
    public final ProductId productId;


    public ProductSelected(ProductId productId) {
        this.productId = productId;
    }
}
