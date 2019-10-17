package com.company.infra.event.store;

import java.util.List;

public interface EventStore<T> {

    void addEvent(T event);
    void addEvents(List<T> events);
    List<T> getEvents();
}
