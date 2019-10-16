package com.company.domain;

import com.company.domain.command.ConfirmFamilySelection;
import com.company.domain.command.ProductUnselected;
import com.company.domain.command.SelectProductCommand;
import com.company.domain.event.*;

import java.util.*;
import java.util.function.Consumer;

public class ProductFamilySelection {


    public Set<ProductId> selectProduct = new HashSet<>();
    private Map<Class, Consumer<ProductFamilyEvent>> eventClassMapping = Map.of(ProductSelected.class, event -> apply((ProductSelected) event),
            com.company.domain.event.ProductUnselected.class, event -> apply((com.company.domain.event.ProductUnselected) event));

    public ProductFamilySelection(List<ProductFamilyEvent> events) {
        events.forEach(event -> eventClassMapping.get(event.getClass()).accept(event));
    }

    public ProductFamilySelection() {

    }

    private void apply(ProductSelected productSelected) {
        selectProduct.add(productSelected.productId);
    }

    private void apply(com.company.domain.event.ProductUnselected productUnselected) {
        selectProduct.remove(productUnselected.productId);
    }

    private void apply(ProductFamilyDefined productFamilyDefined) {
    }

    private void apply(ProductDataRequested productDataRequested) {
    }

    private void apply(ProductDataReceived productDataReceived) {
    }

    public List<ProductFamilyEvent> selectProduct(SelectProductCommand command) {
        List<ProductFamilyEvent> events = new ArrayList<>();
        if (!command.isFull()) {
            ProductDataRequested productDataRequested = new ProductDataRequested(command.productId);
            events.add(productDataRequested);
            apply(productDataRequested);
        }else{
            ProductDataReceived productDataReceived = new ProductDataReceived(command.productId, command.productName, command.productPicture);
            events.add(productDataReceived);
            apply(productDataReceived);
        }

        if (selectProduct.contains(command.productId)) {
            return List.of();
        }
        ProductSelected event = new ProductSelected(command.productId);
        apply(event);
        events.add(event);
        return events;
    }

    public Optional<com.company.domain.event.ProductUnselected> unselectProduct(ProductUnselected productUnselectedCommand) {
        if (!selectProduct.contains(productUnselectedCommand.productId)) {
            return Optional.empty();
        }
        com.company.domain.event.ProductUnselected event = new com.company.domain.event.ProductUnselected(productUnselectedCommand.productId);
        apply(event);
        return Optional.of(event);
    }

    public Optional<ProductFamilyDefined> confirmFamilySelection(ConfirmFamilySelection confirmFamilySelectionCommand) {
        if (selectProduct.isEmpty()) {
            return Optional.empty();
        }
        ProductFamilyDefined event = new ProductFamilyDefined();
        apply(event);
        return Optional.of(event);
    }
}
