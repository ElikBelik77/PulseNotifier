package com.example.pulsenotifier;

public interface Observable<T> {
    void add(Observer<T> observer);
}
