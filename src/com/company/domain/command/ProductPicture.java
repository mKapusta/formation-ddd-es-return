package com.company.domain.command;

import java.util.Objects;

public class ProductPicture {

    public final String picture;


    public ProductPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPicture that = (ProductPicture) o;
        return Objects.equals(picture, that.picture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(picture);
    }
}
