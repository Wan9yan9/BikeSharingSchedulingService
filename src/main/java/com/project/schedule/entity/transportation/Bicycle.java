package com.project.schedule.entity.transportation;

import com.project.schedule.entity.place.DropPlace;

public class Bicycle extends Vehicle {
    // 状态
    public int state;

    // 距离
    public int distance;

    // 目标场地
    public DropPlace targetPlace;

    public Bicycle(String name,int state){
        this.name = name;
        this.state = 0;
        this.velocity = 1;
    }

    public void changeState(int state){
        this.state = state;
    }

    public void changeDistance(){
        this.distance -= velocity;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public DropPlace getTargetPlace() {
        return targetPlace;
    }

    public void setTargetPlace(DropPlace targetPlace) {
        this.targetPlace = targetPlace;
    }
}
