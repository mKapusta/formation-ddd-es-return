package com.company.domain.event;

import com.company.domain.ProductId;

public class ProductUnselected implements ProductFamilyEvent {

    public final ProductId productId;

    public ProductUnselected(ProductId productId) {
        this.productId = productId;
    }
}
