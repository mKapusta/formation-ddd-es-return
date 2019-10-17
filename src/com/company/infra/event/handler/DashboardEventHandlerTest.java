package com.company.infra.event.handler;

import com.company.domain.ProductId;
import com.company.domain.event.ProductFamilyDefined;
import com.company.domain.event.ProductSelected;
import com.company.domain.event.ProductUnselected;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DashboardEventHandlerTest {

    @Test
    public void startsWithEmptyDashboard() {
        DashboardEventHandler dashboardProjection = new DashboardEventHandler();

        assertEquals(Map.of(), dashboardProjection.getFamilies());
    }

    @Test
    public void shouldGetOneProductInFamily() {
        DashboardEventHandler dashboardProjection = new DashboardEventHandler();
        ProductId productId = new ProductId("A");
        dashboardProjection.apply("familleA", new ProductSelected(productId));

        assertEquals(1, dashboardProjection.getFamilies().get("familleA"));
    }

    @Test
    public void shouldGetTwoProductInOnefamily() {
        DashboardEventHandler dashboardProjection = new DashboardEventHandler();
        ProductId productId = new ProductId("A");
        dashboardProjection.apply("familleA", new ProductSelected(productId));
        dashboardProjection.apply("familleA", new ProductSelected(new ProductId("B")));

        assertEquals(2, dashboardProjection.getFamilies().get("familleA"));
    }

    @Test
    public void shouldGetTwoProductInTwofamilies() {
        DashboardEventHandler dashboardProjection = new DashboardEventHandler();
        ProductId productId = new ProductId("A");
        dashboardProjection.apply("familleA", new ProductSelected(productId));
        dashboardProjection.apply("familleB", new ProductSelected(new ProductId("B")));

        assertEquals(1, dashboardProjection.getFamilies().get("familleA"));
        assertEquals(1, dashboardProjection.getFamilies().get("familleB"));
    }

    @Test
    public void shouldRemoveProductFromOneFamily(){
        HashMap families = new HashMap();
        families.put("familleA", 5);
        DashboardEventHandler dashboardProjection = new DashboardEventHandler(families);
        ProductId productId = new ProductId("A");
        dashboardProjection.apply("familleA", new ProductUnselected(productId));

        assertEquals(4, dashboardProjection.getFamilies().get("familleA"));
    }

    @Test
    public void shouldRemoveProductFromOneFamilyAmongTwo(){
        HashMap families = new HashMap();
        families.put("familleA", 5);
        families.put("familleB", 3);
        DashboardEventHandler dashboardProjection = new DashboardEventHandler(families);
        ProductId productId = new ProductId("A");
        dashboardProjection.apply("familleA", new ProductUnselected(productId));

        assertEquals(4, dashboardProjection.getFamilies().get("familleA"));
        assertEquals(3, dashboardProjection.getFamilies().get("familleB"));
    }

    @Test
    public void shouldRemoveProjectionWhenProductsFamilyDefined(){
        HashMap families = new HashMap();
        families.put("familleA", 5);
        families.put("familleB", 3);
        DashboardEventHandler dashboardProjection = new DashboardEventHandler(families);
        ProductId productId = new ProductId("A");
        dashboardProjection.apply("familleA", new ProductFamilyDefined(Set.of(productId)));

        assertNull(dashboardProjection.getFamilies().get("familleA"));
        assertEquals(3, dashboardProjection.getFamilies().get("familleB"));
    }
}
