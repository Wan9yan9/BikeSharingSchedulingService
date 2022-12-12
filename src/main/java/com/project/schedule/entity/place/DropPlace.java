package com.project.schedule.entity.place;

import com.project.schedule.entity.map.Coord;
import com.project.schedule.entity.transportation.Bicycle;
import com.project.schedule.entity.transportation.Truck;
import com.project.schedule.service.intf.IPublisher;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DropPlace extends Place implements IPublisher<Truck> {

    // 场地自行车数量
    public Queue<Bicycle> bicycleList = new LinkedList<>();

    // 订阅者集合
    private ArrayList events = new ArrayList<Truck>();

    // 场地调度状态
    public int state = 0;

    // 自行车初始容量
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

    /**
     * 取出自行车
     *
     * @return 自行车
     */
    public Bicycle takeOut() {
        return bicycleList.poll();
    }

    /**
     * 批量取出自行车
     *
     * @param nums 数量
     * @return 自行车列表
     */
    public List<Bicycle> takeOut(int nums){
        List<Bicycle> ret = new ArrayList<>();
        for(int i=0;i<nums;i++){
            ret.add(bicycleList.poll());
        }
        return ret;
    }

    /**
     * 放回自行车
     *
     * @param bicycle 自行车
     */
    public void putIn(Bicycle bicycle) {
        bicycleList.add(bicycle);
    }

    /**
     * 批量放入自行车
     *
     * @param bicycles 自行车列表
     */
    public void putIn(List<Bicycle> bicycles) {
        bicycleList.addAll(bicycles);
    }

    @Override
    public void emit(Truck subscriber) {
        // 将每一个新的订阅者添加到集合中维护
        events.add(subscriber);
    }


    @Override
    public void on(Object msg) {
        // 将消息发送给集合中的每一个订阅者
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
