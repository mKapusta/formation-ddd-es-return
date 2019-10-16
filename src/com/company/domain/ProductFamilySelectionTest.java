package com.company.domain;

import com.company.domain.command.*;
import com.company.domain.command.ProductUnselected;
import com.company.domain.event.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ProductFamilySelectionTest {

    //TODO : tester attributs events
    @Test
    public void selectProduct_returnsProductSelected() {
        ProductFamilySelection productFamilySelection = new ProductFamilySelection();
        ProductId productId = new ProductId("id");
        ProductName productName = new ProductName("name");
        ProductPicture productPicture = new ProductPicture("picture");
        SelectProductCommand selectProductCommand = new SelectProductCommand(productId, productName, productPicture);

        List<ProductFamilyEvent> events = productFamilySelection.selectProduct(selectProductCommand);
        assertEquals(ProductSelected.class,events.get(0).getClass());
        assertEquals(productId,((ProductSelected) events.get(0)).productId);
        assertEquals(ProductDataReceived.class,events.get(1).getClass());
        assertEquals(productId,((ProductDataReceived) events.get(1)).productId);
        assertEquals(productName,((ProductDataReceived) events.get(1)).productName);
        assertEquals(productPicture,((ProductDataReceived) events.get(1)).productPicture);
    }


    private SelectProductCommand createSelectProductCommand(String id){
       return new SelectProductCommand(new ProductId(id), new ProductName("name"), new ProductPicture("picture"));
    }
    @Test
    public void selectProduct_returnsProductDataRequested_whenMissingData() {
        ProductFamilySelection productFamilySelection = new ProductFamilySelection();
        ProductId productId = new ProductId("id");
        //FIXME : PAS NULL
        SelectProductCommand selectProductCommand = new SelectProductCommand(productId, null, null);

        List<ProductFamilyEvent> events = productFamilySelection.selectProduct(selectProductCommand);
        assertEquals(ProductDataRequested.class,events.get(1).getClass());
        assertEquals(productId,((ProductDataRequested) events.get(1)).productId);
        assertEquals(ProductSelected.class,events.get(0).getClass());
        assertEquals(productId,((ProductSelected) events.get(0)).productId);
    }

    @Test
    public void unselectProduct_returnsProductUnSelected() {

        ProductId productId = new ProductId("id");
        ProductFamilySelection productFamilySelection = new ProductFamilySelection(List.of(new ProductSelected(productId)));
        ProductUnselected productUnselectedCommand = new ProductUnselected(productId);


        Optional<com.company.domain.event.ProductUnselected> productUnselected = productFamilySelection.unselectProduct(productUnselectedCommand);
        assertTrue(productUnselected.isPresent());
        assertEquals(productId,productUnselected.get().productId);


    }



    @Test
    public void unselectProduct_returnsNothingWhenDifferentProduct() {
        ProductId productIdA = new ProductId("A");
        ProductFamilySelection productFamilySelection = new ProductFamilySelection(List.of(new ProductSelected(productIdA)));

        ProductId productIdB = new ProductId("B");
        ProductUnselected productUnselectedCommand = new ProductUnselected(productIdB);


        assertTrue(productFamilySelection.unselectProduct(productUnselectedCommand).isEmpty());

    }


    @Test
    public void confirmFamilySelection_returnsProductFamilyDefined_whenProductSelected() {
        ProductFamilySelection productFamilySelection = new ProductFamilySelection();
        productFamilySelection.selectProduct(createSelectProductCommand("A"));

        Optional<ProductFamilyDefined> productFamilyDefined = productFamilySelection.confirmFamilySelection(new ConfirmFamilySelection());
        assertTrue(productFamilyDefined.isPresent());
        assertEquals(Set.of(new ProductId("A")),productFamilyDefined.get().productsId);

    }

    @Test
    public void confirmFamilySelection_returnsNothing_whenNoProductSelected() {
        ProductFamilySelection productFamilySelection = new ProductFamilySelection();

        assertTrue(productFamilySelection.confirmFamilySelection(new ConfirmFamilySelection()).isEmpty());

    }


    @Test
    public void receiveProductData_returnsProductDataReceived(){
        ProductId productId = new ProductId("A");
        ProductFamilySelection productFamilySelection = new ProductFamilySelection(List.of(new ProductSelected(productId), new ProductDataRequested(productId)));

        ProductName productName = new ProductName("name");
        ProductPicture productPicture = new ProductPicture("picture");
        Optional<ProductDataReceived> productDataReceived = productFamilySelection.receiveData(new ReceiveProductData(productId, productName, productPicture));
        assertTrue(productDataReceived.isPresent());
        assertEquals(productId, productDataReceived.get().productId);
        assertEquals(productName, productDataReceived.get().productName);
        assertEquals(productPicture, productDataReceived.get().productPicture);
    }

    @Test
    public void confirmFamilySelection_returnsNothing_whenNoFullProductSelected() {
        ProductFamilySelection productFamilySelection = new ProductFamilySelection(List.of(new ProductSelected(new ProductId("a"))));
        assertTrue(productFamilySelection.confirmFamilySelection(new ConfirmFamilySelection()).isEmpty());
    }

    @Test
    public void confirmFamilySelection_returnsProductsFamilyDefined_whenProductDataReceived() {
        ProductId productId = new ProductId("a");
        ProductFamilySelection productFamilySelection = new ProductFamilySelection(List.of(new ProductSelected(productId),
                new ProductDataRequested(productId), new ProductDataReceived(productId, new ProductName("name"), new ProductPicture("picture")) ));
        Optional<ProductFamilyDefined> productFamilyDefined = productFamilySelection.confirmFamilySelection(new ConfirmFamilySelection());
        assertTrue(productFamilyDefined.isPresent());
        assertEquals(Set.of(productId),productFamilyDefined.get().productsId);
    }


}
