package com.company.infra;

import com.company.domain.ProductFamilySelection;
import com.company.domain.command.SelectProductCommand;
import com.company.domain.event.ProductFamilyEvent;
import com.company.infra.event.publisher.EventPublisher;
import com.company.infra.event.store.EventStore;
import com.company.infra.event.store.NumeroDeSequenceInvalide;

import java.util.List;

public class ProductFamilySelectionApplicaton {


    private EventStore<String, ProductFamilyEvent> eventStore;
    private EventPublisher<String, ProductFamilyEvent> eventPublisher;


    public ProductFamilySelectionApplicaton(EventStore<String, ProductFamilyEvent> eventStore, EventPublisher<String, ProductFamilyEvent> eventPublisher) {
        this.eventStore = eventStore;
        this.eventPublisher = eventPublisher;
    }

    public void selectProduct(String idFamille, SelectProductCommand selectProductCommand) throws NumeroDeSequenceInvalide {
        List<ProductFamilyEvent> productFamilyEvents = eventStore.getEvents(idFamille);
        var productFamilySelection = new ProductFamilySelection(productFamilyEvents);

        List<ProductFamilyEvent> events = productFamilySelection.selectProduct(selectProductCommand);

        eventPublisher.publishEvents(idFamille, events, productFamilyEvents.size());
    }
}
