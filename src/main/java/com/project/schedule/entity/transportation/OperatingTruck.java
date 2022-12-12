package com.project.schedule.entity.transportation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.project.schedule.main.Main.PERIOD_TIME;

public class OperatingTruck {
    // 运营中货车集合
    private static List<Truck> trucks  = new ArrayList<>();

    /**
     * 进入运营
     *
     * @param truck 货车
     */
    public static void putTruck(Truck truck){
        trucks.add(truck);
    }

    /**
     * 货车搬运计算
     */
    public static void truckCalculate() {
        Iterator<Truck> iterator = trucks.iterator();
        while(iterator.hasNext()) {
            Truck truck = iterator.next();
            truck.setDistance(truck.getDistance() - PERIOD_TIME * truck.getVelocity());
            if (truck.getDistance() <= 0) {
                System.out.println(truck.getName()+"调度完成。从"+truck.getFromPlace().getPlaceName()+"向"+truck.getTargetPlace().getPlaceName()+"调度"+truck.getBicycleList().size()+"辆");
                truck.getTargetPlace().putIn(truck.getBicycleList());
                truck.getTargetPlace().setState(0);
                truck.setTruckLocation(truck.getTargetPlace().getPlaceLocation());
                truck.setDistance(0);
                iterator.remove();
            }
        }
    }
}
