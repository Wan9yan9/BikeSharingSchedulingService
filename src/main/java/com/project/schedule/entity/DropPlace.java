package com.project.schedule.entity;

import com.project.schedule.service.IPublisher;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DropPlace extends Place implements IPublisher<Truck> {

    public Queue<Bicycle> bicycleList = new LinkedList<>();
    // 订阅者集合
    private ArrayList events = new ArrayList<Truck>();

    public int state = 0;

    public int initialQuantity;

    public DropPlace(int x, int y, int bikeNums, String placeName) {
        Coord coord = new Coord(x, y);
        this.setPlaceLocation(coord);
        this.setPlaceName(placeName);
        this.initialQuantity = bikeNums;
        for(int i=0;i<bikeNums;i++){
            Bicycle bicycle = new Bicycle("共享单车"+i,0);
            bicycleList.add(bicycle);
        }
    }

    public Bicycle takeOut() {
        return bicycleList.poll();
    }
    public List<Bicycle> takeOut(int nums){
        List<Bicycle> ret = new ArrayList<>();
        for(int i=0;i<nums;i++){
            ret.add(bicycleList.poll());
        }
        return ret;
    }

    public void putIn(Bicycle bicycle) {
        bicycleList.add(bicycle);
    }

    public void putIn(List<Bicycle> bicycle) {
        bicycleList.addAll(bicycle);
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


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Queue<Bicycle> getBicycleList() {
        return bicycleList;
    }

    public void setBicycleList(Queue<Bicycle> bicycleList) {
        this.bicycleList = bicycleList;
    }

    public ArrayList getEvents() {
        return events;
    }

    public void setEvents(ArrayList events) {
        this.events = events;
    }

    public int getInitialQuantity() {
        return initialQuantity;
    }

    public void setInitialQuantity(int initialQuantity) {
        this.initialQuantity = initialQuantity;
    }
}
