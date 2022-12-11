package com.project.schedule.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.project.schedule.main.Main.PERIOD_TIME;

public class OperatingTruck {
    private static List<Truck> trucks  = new ArrayList<>();

    public synchronized static void putTruck(Truck truck){
        trucks.add(truck);
        truckCalculate();
    }

    public synchronized static void truckCalculate() {
        Iterator<Truck> iterator = trucks.iterator();
        while(iterator.hasNext()) {
            Truck truck = iterator.next();
            truck.setDistance(truck.getDistance() - PERIOD_TIME * truck.getVelocity());
            if (truck.distance <= 0) {
                System.out.println(truck.getName()+"调度完成。向"+truck.getTargetPlace().getPlaceName()+"调度"+truck.getBicycleList().size()+"辆");
                truck.getTargetPlace().putIn(truck.getBicycleList());
                truck.getTargetPlace().setState(0);
                truck.setTruckLocation(truck.getTargetPlace().getPlaceLocation());
                iterator.remove();
            }
        }
    }
}
