package com.company.infra;

import com.company.domain.event.ProductFamilyEvent;

public class FamilyIdEvent {

    public String familyId;
    public ProductFamilyEvent event;

    public FamilyIdEvent(String idFamille, ProductFamilyEvent event) {
        this.familyId = idFamille;
        this.event = event;
    }
}
