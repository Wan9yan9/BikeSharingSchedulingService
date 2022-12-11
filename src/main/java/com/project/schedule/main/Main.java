package com.project.schedule.main;

import com.project.schedule.algo.PathAlgo;
import com.project.schedule.entity.*;
import com.project.schedule.service.ScheduleService;
import com.project.schedule.service.TruckScheduleService;

import java.util.*;

public class Main {
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
        Node start = new Node(0, 0);
        Node end = new Node(0, 0);
        MapInfo mapInfo = new MapInfo(maps, 8, 8, start, end);
        PathAlgo pathAlgo = new PathAlgo();
        int pathLength = pathAlgo.start(mapInfo);
        System.out.println(pathLength);

        DropPlace subwayStationOne = new DropPlace(0, 0, 30, "地铁站一");
        DropPlace subwayStationTwo = new DropPlace(3, 2, 40, "地铁站二");
        DropPlace subwayStationThree = new DropPlace(0, 0, 30, "地铁站三");
        DropPlaceCollection dropPlaces = new DropPlaceCollection();
//        List<DropPlace> dropPlaces = new ArrayList<>();
        dropPlaces.add(subwayStationOne);
        dropPlaces.add(subwayStationTwo);
        dropPlaces.add(subwayStationThree);

        Truck truckOne = new Truck("货车一");
        Truck truckTwo = new Truck("货车二");
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

                Node startNode = new Node(startPlace.placeLocation.x, startPlace.placeLocation.y);
                Node endNode = new Node(targetPlace.placeLocation.x, targetPlace.placeLocation.y);
                PathAlgo pathAlgo = new PathAlgo();
                MapInfo mapInfo = new MapInfo(maps, 8, 8, startNode, endNode);
                int pathLength = pathAlgo.start(mapInfo);
                Bicycle bicycle = startPlace.takeOut();
                bicycle.setDistance(pathLength);
                operatingBicycle.putBicycle(bicycle);


                ScheduleService scheduleService = new TruckScheduleService();
                scheduleService.schedule(dropPlaces, trucks);
            }
        };
        timer.schedule(timerTask, 0, 1000);
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
