package com.oga.cqrsexample.commonapi.events;

import lombok.Getter;

public abstract class BaseEvent<T> {
    @Getter private T id;

    protected BaseEvent(T id) {
        this.id = id;
    }
}
