package com.company.infra.event.publisher;

import com.company.infra.event.handler.EventHandler;
import com.company.infra.event.store.EventStore;

import java.util.List;

public class EventPublisher<T> {

    private EventStore<T> eventStore;
    private List<EventHandler<T>> eventHandlers;

    public EventPublisher(EventStore<T> eventStore, EventHandler<T> eventHandler) {
        this.eventStore = eventStore;
        this.eventHandlers = List.of(eventHandler);
    }


    public EventPublisher(EventStore<T> eventStore, List<EventHandler<T>> eventHandlers) {
        this.eventStore = eventStore;
        this.eventHandlers = eventHandlers;
    }

    public void publishEvent(T event) {
        eventStore.addEvent(event);
        eventHandlers.forEach(handler -> handler.handleEvent(event));
    }


    public void publishEvents(List<T> events) {
        events.forEach(this::publishEvent);
    }
}
