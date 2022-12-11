package com.project.schedule.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.project.schedule.main.Main.PERIOD_TIME;

public class OperatingBicycle {
    List<Bicycle> bicycles  = new ArrayList<>();
    public void putBicycle(Bicycle bicycle){
        bicycles.add(bicycle);
        bicycleCalculate();
    }

    private void bicycleCalculate() {
        Iterator<Bicycle> iterator = bicycles.iterator();
        while(iterator.hasNext()) {
            Bicycle bicycle = iterator.next();
            bicycle.setDistance(bicycle.getDistance() - PERIOD_TIME * bicycle.getVelocity());
            if (bicycle.getDistance() == 0) {
                System.out.println(bicycle.getName()+"运行完成，已抵达"+bicycle.getTargetPlace().getPlaceName());
                bicycle.getTargetPlace().putIn(bicycle);
                iterator.remove();
            }
        }
    }
    public int getBicyclesSize(){
        return bicycles.size();
    }


}
