package com.company.infra;

import com.company.domain.ProductFamilySelection;
import com.company.domain.ProductId;
import com.company.domain.command.ProductName;
import com.company.domain.command.ProductPicture;
import com.company.domain.command.SelectProductCommand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {

    private EventStore<FamilyIdEvent> buildEventStore() {
        return new EventStore<FamilyIdEvent>() {

            List<FamilyIdEvent> events = new ArrayList<>();

            @Override
            public void addEvent(FamilyIdEvent event) {
                events.add(event);
            }

            @Override
            public void addEvents(List<FamilyIdEvent> events) {
                this.events.addAll(events);
            }

            @Override
            public List<FamilyIdEvent> getEvents() {
                return events;
            }
        };
    }

    @Test
    public void updateProjection_whenSendCommand() {
        //given
        ProductFamilySelection productFamilySelection = new ProductFamilySelection();
        EventStore<FamilyIdEvent> eventStore = buildEventStore();
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
