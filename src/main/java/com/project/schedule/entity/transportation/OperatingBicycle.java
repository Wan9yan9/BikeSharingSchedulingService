package com.project.schedule.entity.transportation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.project.schedule.main.Main.PERIOD_TIME;

public class OperatingBicycle {
    // 运营中自行车集合
    List<Bicycle> bicycles  = new ArrayList<>();

    /**
     * 自行车进入运营
     *
     * @param bicycle 自行车
     */
    public void putBicycle(Bicycle bicycle){
        bicycles.add(bicycle);
        bicycleCalculate();
    }

    /**
     * 自行车运行计算
     */
    private void bicycleCalculate() {
        Iterator<Bicycle> iterator = bicycles.iterator();
        while(iterator.hasNext()) {
            Bicycle bicycle = iterator.next();
            bicycle.setDistance(bicycle.getDistance() - PERIOD_TIME * bicycle.getVelocity());
            if (bicycle.getDistance() == 0) {
//                System.out.println("--------"+bicycle.getName()+"运行完成，已抵达"+bicycle.getTargetPlace().getPlaceName()+"--------");
                bicycle.getTargetPlace().putIn(bicycle);
                bicycle.setState(0);
                iterator.remove();
            }
        }
    }

    /**
     * 获取运营中自行车数量
     *
     * @return 运营中自行车数量
     */
    public int getBicyclesSize(){
        return bicycles.size();
    }


}
