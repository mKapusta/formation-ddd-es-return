package com.company.domain;

import com.company.domain.event.*;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class DecisionProjection {

    private Set<Product> selectProduct = new HashSet<>();
    private Map<Class, Consumer<ProductFamilyEvent>> eventClassMapping =
            Map.of(ProductSelected.class, event -> apply((ProductSelected) event),
            com.company.domain.event.ProductUnselected.class, event -> apply((com.company.domain.event.ProductUnselected) event),
            com.company.domain.event.ProductDataRequested.class, event -> apply((com.company.domain.event.ProductDataRequested) event),
            com.company.domain.event.ProductDataReceived.class, event -> apply((com.company.domain.event.ProductDataReceived) event));


    public DecisionProjection(List<ProductFamilyEvent> events){
        events.forEach(event -> eventClassMapping.get(event.getClass()).accept(event));
    }

    public DecisionProjection() {

    }

    public boolean selectProductContainsProduct(ProductId productId){
        return selectProduct.contains(new Product(productId));
    }

    public void apply(ProductSelected productSelected) {
        selectProduct.add(new Product(productSelected.productId));
    }

    public void apply(com.company.domain.event.ProductUnselected productUnselected) {
        selectProduct.remove(new Product(productUnselected.productId));
    }

    public void apply(ProductFamilyDefined productFamilyDefined) {
    }

    public void apply(ProductDataRequested productDataRequested) {
    }

    public void apply(ProductDataReceived productDataReceived) {
        Product fullProduct = new Product(productDataReceived.productId, Optional.of(productDataReceived.productName), Optional.of(productDataReceived.productPicture));
        selectProduct.remove(fullProduct);
        selectProduct.add(fullProduct);
    }

    public boolean isEmpty() {
        return selectProduct.isEmpty();
    }

    public boolean allProductsFull(){
        return selectProduct.stream().anyMatch(Product::isFull);
    }

    public Set<ProductId> getProductsId() {
        return selectProduct.stream().map(product -> product.productId).collect(Collectors.toSet());
    }


}
