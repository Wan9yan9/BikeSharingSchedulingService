package com.project.schedule.service;

public interface IPublisher<T> {

    /**
     * 添加订阅者
     */
    void emit(T subscriber);

    /**
     * 触发消息
     */
    void on(String msg);

    /**
     * 移除订阅者
     */
    void remove(T subscriber);

}
