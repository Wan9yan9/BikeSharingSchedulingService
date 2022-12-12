package com.project.schedule.entity.transportation;

public class Vehicle {
    // 交通工具名称
    public String name;

    // 交通工具速率
    public int velocity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
}
