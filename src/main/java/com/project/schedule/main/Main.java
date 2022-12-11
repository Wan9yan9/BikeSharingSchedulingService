package com.project.schedule.main;

import com.project.schedule.algo.PathAlgo;
import com.project.schedule.entity.*;
import com.project.schedule.service.ScheduleService;
import com.project.schedule.service.TruckScheduleService;

import java.util.*;

public class Main {
    public static final int PERIOD_TIME = 1;
    public static void main(String[] args) {
        int[][] maps = {
                {0, 0, 0, 0, 1, 1, 1, 1},
                {0, 1, 1, 0, 1, 1, 1, 1},
                {0, 1, 1, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 1, 0},
                {1, 1, 1, 1, 1, 0, 1, 0},
                {1, 1, 1, 1, 1, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 0},
        };

        DropPlace subwayStationOne = new DropPlace(0, 0, 30, "地铁站一");
        DropPlace subwayStationTwo = new DropPlace(3, 2, 40, "地铁站二");
        DropPlace subwayStationThree = new DropPlace(7, 7, 30, "地铁站三");
        DropPlaceCollection dropPlaces = new DropPlaceCollection();
        dropPlaces.add(subwayStationOne);
        dropPlaces.add(subwayStationTwo);
        dropPlaces.add(subwayStationThree);

        Truck truckOne = new Truck("货车一",0,0);
        Truck truckTwo = new Truck("货车二",0,0);
        List<Truck> trucks = new ArrayList<>();
        trucks.add(truckOne);
        trucks.add(truckTwo);
        Integer time = 0;
        OperatingBicycle operatingBicycle = new OperatingBicycle();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Random rand = new Random();
                int startIndex = rand.nextInt(dropPlaces.size());
                int targetIndex = afterNextInt(startIndex, dropPlaces.size());
                DropPlace startPlace = dropPlaces.get(startIndex);
                DropPlace targetPlace = dropPlaces.get(targetIndex);

                Node startNode = new Node(startPlace.getPlaceLocation().x, startPlace.getPlaceLocation().y);
                Node endNode = new Node(targetPlace.getPlaceLocation().x, targetPlace.getPlaceLocation().y);
                PathAlgo pathAlgo = new PathAlgo();
                MapInfo mapInfo = new MapInfo(maps, 8, 8, startNode, endNode);
                int pathLength = pathAlgo.start(mapInfo);
                Bicycle bicycle = startPlace.takeOut();
                bicycle.setDistance(pathLength);
                bicycle.setTargetPlace(targetPlace);
                operatingBicycle.putBicycle(bicycle);

                dropPlaces.forEach(dropPlace -> {
                    System.out.print(dropPlace.placeName + "现有自行车" + dropPlace.bicycleList.size() + "辆,");
                });
                System.out.println("路上车"+operatingBicycle.getBicyclesSize() + "辆。");


                ScheduleService scheduleService = new TruckScheduleService();
                scheduleService.schedule(dropPlaces, trucks);
                OperatingTruck.truckCalculate();
            }
        };
        timer.schedule(timerTask, 0, PERIOD_TIME * 1000);
    }

    public static int afterNextInt(int nextInt, int scope) {
        Random rand = new Random();
        int ret = rand.nextInt(scope);
        while (ret == nextInt) {
            ret = rand.nextInt(scope);
        }
        return ret;
    }
}
