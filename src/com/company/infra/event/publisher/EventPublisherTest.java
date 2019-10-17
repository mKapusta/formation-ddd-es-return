package com.company.infra.event.publisher;

import com.company.domain.ProductId;
import com.company.domain.event.ProductFamilyEvent;
import com.company.domain.event.ProductSelected;
import com.company.domain.event.ProductUnselected;
import com.company.infra.event.handler.EventHandler;
import com.company.infra.event.store.EventStore;
import com.company.infra.event.store.InMemoryEventStore;
import com.company.infra.event.store.NumeroDeSequenceInvalide;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventPublisherTest {


    @Test
    public void storeEvent_whenPublished() throws NumeroDeSequenceInvalide {
        EventStore<String, ProductFamilyEvent> eventStore = new InMemoryEventStore<>();
        ProductSelected event = new ProductSelected(new ProductId("A"));
        EventPublisher<String, ProductFamilyEvent> eventPublisher = new EventPublisher<>(eventStore, new MockEventHandler());
        eventPublisher.publishEvent("", event, 0);
        assertEquals(event, eventStore.getEvents().get(0));
    }

    @Test
    public void storeMultipleEvents_whenMultipleEvents() throws NumeroDeSequenceInvalide {
        EventStore<String, ProductFamilyEvent> eventStore = new InMemoryEventStore<>();
        EventPublisher<String, ProductFamilyEvent> eventPublisher = new EventPublisher<>(eventStore, new MockEventHandler());
        ProductSelected eventSelect = new ProductSelected(new ProductId("A"));
        ProductUnselected eventUnselect = new ProductUnselected(new ProductId("A"));

        eventPublisher.publishEvents("", List.of(eventSelect, eventUnselect), 0);

        assertEquals(eventSelect, eventStore.getEvents().get(0));
        assertEquals(eventUnselect, eventStore.getEvents().get(1));
    }

    @Test
    public void callHandler_whenPublishEvent() throws NumeroDeSequenceInvalide {
        EventStore<String, ProductFamilyEvent> eventStore = new InMemoryEventStore<>();
        MockEventHandler eventHandler = new MockEventHandler();
        EventPublisher<String, ProductFamilyEvent> eventPublisher = new EventPublisher<>(eventStore, eventHandler);

        ProductSelected eventSelect = new ProductSelected(new ProductId("A"));
        eventPublisher.publishEvent("", eventSelect, 0);

        assertTrue(eventHandler.called);
        assertEquals(1, eventHandler.callCount);

    }

    //TODO : CREER TEST POUR EXCEPTION

    @Test
    public void callHandler_whenPublishMultipleEvents() throws NumeroDeSequenceInvalide {
        EventStore<String, ProductFamilyEvent> eventStore = new InMemoryEventStore<>();
        MockEventHandler eventHandler = new MockEventHandler();
        EventPublisher<String, ProductFamilyEvent> eventPublisher = new EventPublisher<>(eventStore, eventHandler);
        ProductSelected eventSelect = new ProductSelected(new ProductId("A"));
        ProductUnselected eventUnselect = new ProductUnselected(new ProductId("A"));

        eventPublisher.publishEvents("A", List.of(eventSelect, eventUnselect), 0);

        assertTrue(eventHandler.called);
        assertEquals(2, eventHandler.callCount);

    }

    @Test
    public void callHandlers_whenPublishEvent() throws NumeroDeSequenceInvalide {
        EventStore<String, ProductFamilyEvent> eventStore = new InMemoryEventStore<>();
        MockEventHandler firstEventHandler = new MockEventHandler();
        MockEventHandler secondEventHandler = new MockEventHandler();
        EventPublisher<String, ProductFamilyEvent> eventPublisher = new EventPublisher<>(eventStore, List.of(firstEventHandler, secondEventHandler));

        ProductSelected eventSelect = new ProductSelected(new ProductId("A"));
        eventPublisher.publishEvent("", eventSelect, 0);


        assertTrue(firstEventHandler.called);
        assertEquals(1, firstEventHandler.callCount);
        assertTrue(secondEventHandler.called);
        assertEquals(1, secondEventHandler.callCount);
    }

    private class MockEventHandler implements EventHandler<String, ProductFamilyEvent> {
        public boolean called;
        public int callCount;

        @Override
        public void handleEvent(String key, ProductFamilyEvent event) {
            called = true;
            callCount++;
        }
    }
}
