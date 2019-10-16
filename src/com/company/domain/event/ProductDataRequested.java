package com.company.domain.event;

import com.company.domain.ProductId;

public class ProductDataRequested implements ProductFamilyEvent {

    public final ProductId productId;

    public ProductDataRequested(ProductId productId) {
        this.productId = productId;
    }
}
