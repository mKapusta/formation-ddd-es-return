package com.company.infra.event.store;

import java.util.List;

public interface EventStore<K, V> {

    void addEvent(K id, V event) throws NumeroDeSequenceInvalide;
    void addEvents(K id, List<V> events) throws NumeroDeSequenceInvalide;
    List<V> getEvents();
    List<V> getEvents(K id);
}
