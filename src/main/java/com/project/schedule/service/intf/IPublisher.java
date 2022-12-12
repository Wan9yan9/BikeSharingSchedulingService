package com.project.schedule.service.intf;

public interface IPublisher<T> {

    /**
     * 添加订阅者
     * @param subscriber 订阅者
     */
    void emit(T subscriber);

    /**
     * 触发消息
     * @param msg 消息体
     */
    void on(Object msg);

    /**
     * 移除订阅者
     * @param subscriber 订阅者
     */
    void remove(T subscriber);

}
