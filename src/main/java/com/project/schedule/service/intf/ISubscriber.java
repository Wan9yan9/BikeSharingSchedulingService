package com.project.schedule.service.intf;

public interface ISubscriber<T> {
    /**
     * 触发订阅
     * @param object T
     * @param msg 消息体
     */
    void on(T object, Object msg);
}

