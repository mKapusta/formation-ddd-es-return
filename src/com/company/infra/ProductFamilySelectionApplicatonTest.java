package com.company.infra;

import com.company.domain.ProductFamilySelection;
import com.company.domain.ProductId;
import com.company.domain.command.ProductName;
import com.company.domain.command.ProductPicture;
import com.company.domain.command.SelectProductCommand;
import com.company.infra.event.handler.DashboardEventHandler;
import com.company.infra.event.publisher.EventPublisher;
import com.company.infra.event.store.EventStore;
import com.company.infra.event.store.InMemoryEventStore;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductFamilySelectionApplicatonTest {

    @Test
    public void updateProjection_whenSendCommand() {
        //given
        ProductFamilySelection productFamilySelection = new ProductFamilySelection();
        EventStore<FamilyIdEvent> eventStore = new InMemoryEventStore<>();
        DashboardEventHandler eventHandler = new DashboardEventHandler();
        EventPublisher<FamilyIdEvent> eventPublisher = new EventPublisher<FamilyIdEvent>(eventStore, eventHandler);
        ProductFamilySelectionApplicaton productFamilySelectionApplicaton =
                new ProductFamilySelectionApplicaton(productFamilySelection, eventPublisher);
        SelectProductCommand selectProductCommand =
                new SelectProductCommand(new ProductId("A"), Optional.of(new ProductName("name")), Optional.of(new ProductPicture("picture")));
        //when
        productFamilySelectionApplicaton.selectProduct("familleA", selectProductCommand);
        //then
        assertEquals(1, eventHandler.getFamilies().get("familleA"));

    }
}
