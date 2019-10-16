package com.company.domain;

import com.company.domain.command.*;
import com.company.domain.event.ProductDataReceived;
import com.company.domain.event.ProductDataRequested;
import com.company.domain.event.ProductFamilyEvent;
import com.company.domain.event.ProductSelected;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ProductFamilySelectionTest {

    //TODO : tester attributs events
    @Test
    public void selectProduct_returnsProductSelected() {
        ProductFamilySelection productFamilySelection = new ProductFamilySelection();
        ProductId productId = new ProductId("id");
        SelectProductCommand selectProductCommand = new SelectProductCommand(productId, new ProductName("name"), new ProductPicture("picture"));

        List<ProductFamilyEvent> events = productFamilySelection.selectProduct(selectProductCommand);
        assertEquals(ProductSelected.class,events.get(1).getClass());
        assertEquals(ProductDataReceived.class,events.get(0).getClass());
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
        assertEquals(ProductDataRequested.class,events.get(0).getClass());
        assertEquals(ProductSelected.class,events.get(1).getClass());
    }

    @Test
    public void unselectProduct_returnsProductUnSelected() {
        ProductFamilySelection productFamilySelection = new ProductFamilySelection();
        ProductId productId = new ProductId("id");
        SelectProductCommand selectProductCommand = createSelectProductCommand("id");
        productFamilySelection.selectProduct(selectProductCommand);
        ProductUnselected productUnselectedCommand = new ProductUnselected(productId);


        assertTrue(productFamilySelection.unselectProduct(productUnselectedCommand).isPresent());

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

        assertTrue(productFamilySelection.confirmFamilySelection(new ConfirmFamilySelection()).isPresent());

    }

    @Test
    public void confirmFamilySelection_returnsNothing_whenNoProductSelected() {
        ProductFamilySelection productFamilySelection = new ProductFamilySelection();

        assertTrue(productFamilySelection.confirmFamilySelection(new ConfirmFamilySelection()).isEmpty());

    }


}
