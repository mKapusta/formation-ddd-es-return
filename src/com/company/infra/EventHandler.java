package com.company.infra;

public interface EventHandler<T> {

    void handleEvent(T event);
}
