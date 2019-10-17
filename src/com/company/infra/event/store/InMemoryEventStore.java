package com.company.infra.event.store;

import java.util.ArrayList;
import java.util.List;

public class InMemoryEventStore<T> implements EventStore<T> {
    List<T> events = new ArrayList<>();

    @Override
    public void addEvent(T event) {
        events.add(event);
    }

    @Override
    public void addEvents(List<T> events) {
        this.events.addAll(events);
    }

    @Override
    public List<T> getEvents() {
        return events;
    }
}
