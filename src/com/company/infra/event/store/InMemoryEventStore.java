package com.company.infra.event.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryEventStore<K, V> implements EventStore<K, V> {
    Map<K, List<V>> events = new HashMap<>();

    @Override
    public void addEvent(K id, V event) throws NumeroDeSequenceInvalide {
        List<V> events = getEvents(id);
        events.add(event);
        this.events.put(id, events);
    }

    @Override
    public void addEvents(K id, List<V> events) throws NumeroDeSequenceInvalide {
        for(var event : events ) {
            this.addEvent(id, event);
        }
    }

    @Override
    public List<V> getEvents() {
        return this.events.values().stream().flatMap(List::stream).collect(Collectors.toList());
    }

    @Override
    public List<V> getEvents(K id) {
        List<V> events = this.events.get(id);
        return events == null ? new ArrayList<>() : events;
    }
}
