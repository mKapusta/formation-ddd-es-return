package com.company.infra;

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
        eventHandlers.forEach(handler -> handler.handleEvent(event));
        eventStore.addEvent(event);
    }


    public void publishEvents(List<T> events) {
        events.forEach(this::publishEvent);
    }
}
