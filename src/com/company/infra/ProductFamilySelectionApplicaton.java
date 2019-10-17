package com.company.infra;

import com.company.domain.ProductFamilySelection;
import com.company.domain.command.SelectProductCommand;
import com.company.domain.event.ProductFamilyEvent;

import java.util.List;
import java.util.stream.Collectors;

public class ProductFamilySelectionApplicaton {


    private ProductFamilySelection productFamilySelection;
    private EventPublisher<FamilyIdEvent> eventPublisher;


    public ProductFamilySelectionApplicaton(ProductFamilySelection productFamilySelection, EventPublisher<FamilyIdEvent> eventPublisher) {

        this.productFamilySelection = productFamilySelection;
        this.eventPublisher = eventPublisher;
    }

    public void selectProduct(String idFamille, SelectProductCommand selectProductCommand) {
        List<ProductFamilyEvent> events = this.productFamilySelection.selectProduct(selectProductCommand);
        eventPublisher.publishEvents(events.stream()
                .map(event -> new FamilyIdEvent(idFamille, event)).collect(Collectors.toList()));

    }
}
