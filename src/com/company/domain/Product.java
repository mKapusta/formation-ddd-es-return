package com.company.domain;

import com.company.domain.command.ProductName;
import com.company.domain.command.ProductPicture;

import java.util.Objects;
import java.util.Optional;

public class Product {

    public final ProductId productId;
    public final Optional<ProductName> productName;
    public final Optional<ProductPicture> productPicture;

    public Product(ProductId productId, Optional<ProductName> productName, Optional<ProductPicture> productPicture) {
        this.productId = productId;
        this.productName = productName;
        this.productPicture = productPicture;
    }

    public Product(ProductId productId) {
        this.productId = productId;
        this.productName = Optional.empty();
        this.productPicture = Optional.empty();
    }

    public boolean isFull() {
        return productName.isPresent() && productPicture.isPresent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
