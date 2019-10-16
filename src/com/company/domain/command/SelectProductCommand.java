package com.company.domain.command;

import com.company.domain.ProductId;

import java.util.Optional;

public class SelectProductCommand {

    public final ProductId productId;
    public final Optional<ProductName> productName;
    public final Optional<ProductPicture> productPicture;


    public SelectProductCommand(ProductId productId, Optional<ProductName> productName, Optional<ProductPicture> productPicture) {
        this.productId = productId;
        this.productName = productName;
        this.productPicture = productPicture;
    }

}
