package com.project.schedule.entity;

import com.project.schedule.service.IPublisher;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class DropPlace extends Place implements IPublisher<Truck> {

    public int bikeNums;

    public Queue<Bicycle> bicycleList = new LinkedList<>();
    // 订阅者集合
    private ArrayList events = new ArrayList<Truck>();

    private int state = 0;

    private int initialQuantity;

    public DropPlace(int x, int y, int bikeNums, String placeName) {
        Coord coord = new Coord(x, y);
        this.setPlaceLocation(coord);
        this.setPlaceName(placeName);
        this.initialQuantity = bikeNums;
        this.bikeNums = bikeNums;
        for(int i=0;i<bikeNums;i++){
            Bicycle bicycle = new Bicycle("共享单车"+i,0);
            bicycleList.add(bicycle);
        }
    }

    public Bicycle takeOut() {
        return bicycleList.poll();
    }

    public void putIn(Bicycle bicycle) {
        bicycleList.add(bicycle);
    }

    @Override
    public void emit(Truck subscriber) {
        // 将每一个新的订阅者添加到集合中维护
        events.add(subscriber);
    }


    @Override
    public void on(String msg) {
        // 触发订阅的原理就是遍历该集合，然后将消息发送给集合中的每一个订阅者
        for (int i = 0; i < events.size(); i++) {
            Truck subscriber = (Truck) events.get(i);
            subscriber.on(this, msg);
        }
    }

    @Override
    public void remove(Truck subscriber) {
        // 移除订阅者
        events.remove(subscriber);
    }


}
