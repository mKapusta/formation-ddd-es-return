package com.company.infra.event.handler;

import com.company.domain.event.*;
import com.company.infra.FamilyIdEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class DashboardEventHandler implements EventHandler<FamilyIdEvent> {

    //Peut être dans une classe specifique
    private Map<String, Integer> productFamiliesRepository = new HashMap<>();
    private Map<Class, Consumer<FamilyIdEvent>> eventClassMapping = Map.of(ProductSelected.class, (familyIdEvent) -> apply(familyIdEvent.familyId, (ProductSelected) familyIdEvent.event),
            com.company.domain.event.ProductUnselected.class, (familyIdEvent) -> apply(familyIdEvent.familyId,(com.company.domain.event.ProductUnselected) familyIdEvent.event),
            com.company.domain.event.ProductDataRequested.class, (familyIdEvent) -> apply(familyIdEvent.familyId,(com.company.domain.event.ProductDataRequested) familyIdEvent.event),
            com.company.domain.event.ProductDataReceived.class, (familyIdEvent) -> apply(familyIdEvent.familyId,(com.company.domain.event.ProductDataReceived) familyIdEvent.event));

    private void apply(String id, ProductDataReceived event) {

    }

    private void apply(String id, ProductDataRequested event) {

    }


    public DashboardEventHandler() {

    }

    public DashboardEventHandler(Map<String, Integer> projections) {
        productFamiliesRepository = projections;
    }

    public Map<String, Integer> getFamilies() {
        return productFamiliesRepository;
    }


    public void apply(String id, ProductSelected productSelected) {
        productFamiliesRepository.put(id, productFamiliesRepository.get(id) == null ? 1 : productFamiliesRepository.get(id) + 1);
    }

    public void apply(String id, ProductUnselected productUnSelected) {
        Integer familySize = productFamiliesRepository.get(id);
        if (familySize != null) {
            productFamiliesRepository.put(id, familySize - 1);
        }
    }

    public void apply(String id, ProductFamilyDefined productFamilyDefined) {
        productFamiliesRepository.remove(id);
    }

    @Override
    public void handleEvent(FamilyIdEvent familyIdEvent) {
        eventClassMapping.get(familyIdEvent.event.getClass()).accept(familyIdEvent);
    }
}
