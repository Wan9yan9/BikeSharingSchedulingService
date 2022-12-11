package com.project.schedule.entity;

import java.util.ArrayList;
import java.util.List;

public class OperatingBicycle {
    List<Bicycle> bicycles  = new ArrayList<>();
    public void putBicycle(Bicycle bicycle){
        bicycles.add(bicycle);
        bicycleCalculate();
    }

    private void bicycleCalculate() {

    }


}
