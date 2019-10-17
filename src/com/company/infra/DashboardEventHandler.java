package com.company.infra;

import com.company.domain.event.ProductFamilyDefined;
import com.company.domain.event.ProductSelected;
import com.company.domain.event.ProductUnselected;

import java.util.HashMap;
import java.util.Map;

public class DashboardEventHandler {

    //Peut être dans une classe specifique
    private Map<String, Integer> productFamiliesRepository = new HashMap<>();

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
}
