package com.company.domain.command;

import com.company.domain.ProductId;

public class SelectProductCommand {

    public final ProductId productId;
    public final ProductName productName;
    public final ProductPicture productPicture;


    public SelectProductCommand(ProductId productId, ProductName productName, ProductPicture productPicture) {
        this.productId = productId;
        this.productName = productName;
        this.productPicture = productPicture;
    }

    public boolean isFull() {
        return productName != null && productPicture != null;
    }

}
