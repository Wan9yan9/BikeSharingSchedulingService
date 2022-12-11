package com.project.schedule.entity;

import com.project.schedule.service.ISubscriber;

public class Truck extends Vehicle implements ISubscriber<DropPlace> {

    public Coord truckLocation;
    public int capacity;

    public Truck(String name){
        this.name = name;
        this.velocity = 3;
        this.capacity = 20;
    }

    @Override
    public void on(DropPlace dropPlace, String msg) {

    }
}
