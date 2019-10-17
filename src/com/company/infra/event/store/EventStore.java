package com.company.infra.event.store;

import java.util.List;

public interface EventStore<K, V> {

    void addEvent(K id, V event, int sequenceNumber) throws NumeroDeSequenceInvalide;
    void addEvents(K id, List<V> events, int sequenceNumber) throws NumeroDeSequenceInvalide;
    List<V> getEvents();
    List<V> getEvents(K id);
}
