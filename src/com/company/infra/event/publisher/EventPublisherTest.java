package com.company.infra.event.publisher;

import com.company.domain.ProductId;
import com.company.domain.event.ProductFamilyEvent;
import com.company.domain.event.ProductSelected;
import com.company.domain.event.ProductUnselected;
import com.company.infra.event.handler.EventHandler;
import com.company.infra.event.store.EventStore;
import com.company.infra.event.store.InMemoryEventStore;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventPublisherTest {


    @Test
    public void storeEvent_whenPublished() {
        EventStore<ProductFamilyEvent> eventStore = new InMemoryEventStore<>();
        ProductSelected event = new ProductSelected(new ProductId("A"));
        EventPublisher<ProductFamilyEvent> eventPublisher = new EventPublisher<>(eventStore, new MockEventHandler());
        eventPublisher.publishEvent(event);
        assertEquals(event, eventStore.getEvents().get(0));
    }

    @Test
    public void storeMultipleEvents_whenMultipleEvents() {
        EventStore<ProductFamilyEvent> eventStore = new InMemoryEventStore();
        EventPublisher<ProductFamilyEvent> eventPublisher = new EventPublisher<>(eventStore, new MockEventHandler());
        ProductSelected eventSelect = new ProductSelected(new ProductId("A"));
        ProductUnselected eventUnselect = new ProductUnselected(new ProductId("A"));

        eventPublisher.publishEvents(List.of(eventSelect, eventUnselect));

        assertEquals(eventSelect, eventStore.getEvents().get(0));
        assertEquals(eventUnselect, eventStore.getEvents().get(1));
    }

    @Test
    public void callHandler_whenPublishEvent() {
        EventStore<ProductFamilyEvent> eventStore = new InMemoryEventStore();
        MockEventHandler eventHandler = new MockEventHandler();
        EventPublisher<ProductFamilyEvent> eventPublisher = new EventPublisher<>(eventStore, eventHandler);

        ProductSelected eventSelect = new ProductSelected(new ProductId("A"));
        eventPublisher.publishEvent(eventSelect);

        assertTrue(eventHandler.called);
        assertEquals(1, eventHandler.callCount);

    }

    @Test
    public void callHandler_whenPublishMultipleEvents() {
        EventStore<ProductFamilyEvent> eventStore = new InMemoryEventStore();
        MockEventHandler eventHandler = new MockEventHandler();
        EventPublisher<ProductFamilyEvent> eventPublisher = new EventPublisher<>(eventStore, eventHandler);
        ProductSelected eventSelect = new ProductSelected(new ProductId("A"));
        ProductUnselected eventUnselect = new ProductUnselected(new ProductId("A"));

        eventPublisher.publishEvents(List.of(eventSelect, eventUnselect));

        assertTrue(eventHandler.called);
        assertEquals(2, eventHandler.callCount);

    }

    @Test
    public void callHandlers_whenPublishEvent() {
        EventStore<ProductFamilyEvent> eventStore = new InMemoryEventStore();
        MockEventHandler firstEventHandler = new MockEventHandler();
        MockEventHandler secondEventHandler = new MockEventHandler();
        EventPublisher<ProductFamilyEvent> eventPublisher = new EventPublisher<>(eventStore, List.of(firstEventHandler, secondEventHandler));

        ProductSelected eventSelect = new ProductSelected(new ProductId("A"));
        eventPublisher.publishEvent(eventSelect);


        assertTrue(firstEventHandler.called);
        assertEquals(1, firstEventHandler.callCount);
        assertTrue(secondEventHandler.called);
        assertEquals(1, secondEventHandler.callCount);
    }

    private class MockEventHandler implements EventHandler<ProductFamilyEvent> {
        public boolean called;
        public int callCount;

        @Override
        public void handleEvent(ProductFamilyEvent event) {
            called = true;
            callCount++;
        }
    }
}
