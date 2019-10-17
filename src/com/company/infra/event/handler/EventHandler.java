package com.company.infra.event.handler;

public interface EventHandler<K, V> {

    void handleEvent(K key, V event);
}
