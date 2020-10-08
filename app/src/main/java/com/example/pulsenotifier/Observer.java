package com.example.pulsenotifier;

public interface Observer<T> {
    void notify(T value, Observable<T> sender);
}
