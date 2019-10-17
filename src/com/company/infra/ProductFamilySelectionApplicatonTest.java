package com.company.infra;

import com.company.domain.ProductFamilySelection;
import com.company.domain.ProductId;
import com.company.domain.command.ProductName;
import com.company.domain.command.ProductPicture;
import com.company.domain.command.SelectProductCommand;
import com.company.domain.event.ProductFamilyEvent;
import com.company.infra.event.handler.DashboardEventHandler;
import com.company.infra.event.publisher.EventPublisher;
import com.company.infra.event.store.EventStore;
import com.company.infra.event.store.InMemoryEventStore;
import com.company.infra.event.store.NumeroDeSequenceInvalide;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductFamilySelectionApplicatonTest {

    @Test
    public void updateProjection_whenSendCommand() throws NumeroDeSequenceInvalide {
        //given
        ProductFamilySelection productFamilySelection = new ProductFamilySelection();
        EventStore<String, ProductFamilyEvent> eventStore = new InMemoryEventStore<>();
        DashboardEventHandler eventHandler = new DashboardEventHandler();
        EventPublisher<String, ProductFamilyEvent> eventPublisher = new EventPublisher<String, ProductFamilyEvent>(eventStore, eventHandler);
        ProductFamilySelectionApplicaton productFamilySelectionApplicaton =
                new ProductFamilySelectionApplicaton(eventStore, eventPublisher);
        SelectProductCommand selectProductCommand =
                new SelectProductCommand(new ProductId("A"), Optional.of(new ProductName("name")), Optional.of(new ProductPicture("picture")));
        //when
        productFamilySelectionApplicaton.selectProduct("familleA", selectProductCommand);
        //then
        assertEquals(1, eventHandler.getFamilies().get("familleA"));

    }
}
