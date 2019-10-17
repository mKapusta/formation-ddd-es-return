package com.company.infra.event.handler;

public interface EventHandler<T> {

    void handleEvent(T event);
}
