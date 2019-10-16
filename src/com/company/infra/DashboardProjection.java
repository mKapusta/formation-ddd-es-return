package com.company.infra;

import com.company.domain.event.ProductFamilyDefined;
import com.company.domain.event.ProductSelected;
import com.company.domain.event.ProductUnselected;

import java.util.HashMap;
import java.util.Map;

public class DashboardProjection {

    private Map<String, Integer> productFamilies = new HashMap<>();

    public DashboardProjection() {

    }

    public DashboardProjection(Map<String, Integer> projections) {
        productFamilies = projections;
    }

    public Map<String, Integer> getFamilies() {
        return productFamilies;
    }


    public void apply(String id, ProductSelected productSelected) {
        productFamilies.put(id, productFamilies.get(id) == null ? 1 : productFamilies.get(id) + 1);
    }

    public void apply(String id, ProductUnselected productUnSelected) {
        Integer familySize = productFamilies.get(id);
        if (familySize != null) {
            productFamilies.put(id, familySize - 1);
        }
    }

    public void apply(String id, ProductFamilyDefined productFamilyDefined) {
        productFamilies.remove(id);
    }
}
