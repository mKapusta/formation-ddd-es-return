package com.company.domain.command;

import com.company.domain.ProductId;

public class ProductUnselected {

    public final ProductId productId;

    public ProductUnselected(ProductId productId) {
        this.productId = productId;
    }
}
