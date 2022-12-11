package com.project.schedule.service;

public interface ISubscriber<T> {
    void on(T object, String msg);
}

