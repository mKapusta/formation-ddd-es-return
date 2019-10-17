package com.company.domain;

import com.company.domain.command.ConfirmFamilySelection;
import com.company.domain.command.ProductUnselected;
import com.company.domain.command.ReceiveProductData;
import com.company.domain.command.SelectProductCommand;
import com.company.domain.event.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductFamilySelection {


    private final DecisionProjection decisionProjection;

    public ProductFamilySelection(List<ProductFamilyEvent> events) {
        this.decisionProjection = new DecisionProjection(events);
    }

    public ProductFamilySelection() {
        this.decisionProjection = new DecisionProjection();
    }



    public List<ProductFamilyEvent> selectProduct(SelectProductCommand command) {
        if (decisionProjection.selectProductContainsProduct(command.productId)) {
            return List.of();
        }
        List<ProductFamilyEvent> events = new ArrayList<>();
        ProductSelected event = new ProductSelected(command.productId);
        decisionProjection.apply(event);
        events.add(event);
        Product product = new Product(command.productId, command.productName, command.productPicture);
        if (!product.isFull()) {
            ProductDataRequested productDataRequested = new ProductDataRequested(product.productId);
            events.add(productDataRequested);
            decisionProjection.apply(productDataRequested);
        }else {
            ProductDataReceived productDataReceived = new ProductDataReceived(product.productId, product.productName.get(), product.productPicture.get());
            events.add(productDataReceived);
            decisionProjection.apply(productDataReceived);
        }

        return events;
    }

    public Optional<com.company.domain.event.ProductUnselected> unselectProduct(ProductUnselected productUnselectedCommand) {
        if (!decisionProjection.selectProductContainsProduct(productUnselectedCommand.productId)) {
            return Optional.empty();
        }
        com.company.domain.event.ProductUnselected event = new com.company.domain.event.ProductUnselected(productUnselectedCommand.productId);
        decisionProjection.apply(event);
        return Optional.of(event);
    }

    public Optional<ProductFamilyDefined> confirmFamilySelection(ConfirmFamilySelection confirmFamilySelectionCommand) {
        if (decisionProjection.isEmpty() || !decisionProjection.allProductsFull()) {
            return Optional.empty();
        }
        ProductFamilyDefined event = new ProductFamilyDefined(decisionProjection.getProductsId());
        decisionProjection.apply(event);
        return Optional.of(event);
    }


    public Optional<ProductDataReceived> receiveData(ReceiveProductData receiveProductData) {
        ProductDataReceived productDataReceived = new ProductDataReceived(receiveProductData.productId, receiveProductData.productName, receiveProductData.productPicture);
        decisionProjection.apply(productDataReceived);
        return Optional.of(productDataReceived);
    }
}
