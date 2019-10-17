package com.company.infra.event.publisher;

import com.company.infra.event.handler.EventHandler;
import com.company.infra.event.store.EventStore;
import com.company.infra.event.store.NumeroDeSequenceInvalide;

import java.util.List;

public class EventPublisher<K, V> {

    private EventStore<K, V> eventStore;
    private List<EventHandler<K, V>> eventHandlers;

    public EventPublisher(EventStore<K, V> eventStore, EventHandler<K, V> eventHandler) {
        this.eventStore = eventStore;
        this.eventHandlers = List.of(eventHandler);
    }


    public EventPublisher(EventStore<K, V> eventStore, List<EventHandler<K, V>> eventHandlers) {
        this.eventStore = eventStore;
        this.eventHandlers = eventHandlers;
    }

    public void publishEvent(K key, V event, int sequenceNumber) throws NumeroDeSequenceInvalide {
        eventStore.addEvent(key, event, sequenceNumber);
        eventHandlers.forEach(handler -> handler.handleEvent(key, event));
    }


    public void publishEvents(K key, List<V> events, int sequenceNumber) throws NumeroDeSequenceInvalide {
        int currentSequenceNumber = sequenceNumber;
        for(V event : events){
            this.publishEvent(key, event, currentSequenceNumber);
            currentSequenceNumber++;
        }
    }
}
