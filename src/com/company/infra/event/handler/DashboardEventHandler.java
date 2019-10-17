package com.company.infra.event.handler;

import com.company.domain.event.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class DashboardEventHandler implements EventHandler<String, ProductFamilyEvent> {

    //Peut être dans une classe specifique
    private Map<Class, BiConsumer<String, ProductFamilyEvent>> eventClassMapping = Map.of(ProductSelected.class, (familyId,event) -> apply(familyId, (ProductSelected) event),
            com.company.domain.event.ProductUnselected.class, (familyId,event)  -> apply(familyId,(com.company.domain.event.ProductUnselected) event),
            com.company.domain.event.ProductDataRequested.class, (familyId,event)  -> apply(familyId,(com.company.domain.event.ProductDataRequested) event),
            com.company.domain.event.ProductDataReceived.class, (familyId,event)  -> apply(familyId,(com.company.domain.event.ProductDataReceived) event));
    private Map<String, Integer> productFamiliesRepository = new HashMap<>();

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
    public void handleEvent(String idFamille, ProductFamilyEvent productFamilyEvent) {
        eventClassMapping.get(productFamilyEvent.getClass()).accept(idFamille, productFamilyEvent);
    }
}
