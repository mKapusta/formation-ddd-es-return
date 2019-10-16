package com.company.domain.event;

import com.company.domain.ProductId;
import com.company.domain.command.ProductName;
import com.company.domain.command.ProductPicture;

public class ProductDataReceived implements ProductFamilyEvent {


    public final ProductId productId;
    public final ProductName productName;
    public final ProductPicture productPicture;

    public ProductDataReceived(ProductId productId, ProductName productName, ProductPicture productPicture) {

        this.productId = productId;
        this.productName = productName;
        this.productPicture = productPicture;
    }
}
