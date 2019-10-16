package com.company.domain;

import com.company.domain.event.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class DecisionProjection {

    private Set<Product> selectProduct = new HashSet<>();
    private Map<Class, Consumer<ProductFamilyEvent>> eventClassMapping = Map.of(ProductSelected.class, event -> apply((ProductSelected) event),
            com.company.domain.event.ProductUnselected.class, event -> apply((com.company.domain.event.ProductUnselected) event));


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
    }

    public boolean isEmpty() {
        return selectProduct.isEmpty();
    }
}