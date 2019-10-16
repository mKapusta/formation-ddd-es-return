package com.company.domain.command;

import com.company.domain.ProductId;

public class ReceiveProductData {

    public final ProductId productId;
    public final ProductName productName;
    public final ProductPicture productPicture;

    public ReceiveProductData(ProductId productId, ProductName productName, ProductPicture productPicture) {
        this.productId = productId;
        this.productName = productName;
        this.productPicture = productPicture;
    }

}
