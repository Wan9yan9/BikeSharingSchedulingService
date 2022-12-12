package com.project.schedule.main;

import com.project.schedule.algo.PathAlgo;
import com.project.schedule.entity.map.Map;
import com.project.schedule.entity.map.MapInfo;
import com.project.schedule.entity.map.Node;
import com.project.schedule.entity.place.DropPlace;
import com.project.schedule.entity.place.DropPlaceCollection;
import com.project.schedule.entity.transportation.Bicycle;
import com.project.schedule.entity.transportation.OperatingBicycle;
import com.project.schedule.entity.transportation.OperatingTruck;
import com.project.schedule.entity.transportation.Truck;
import com.project.schedule.service.ScheduleService;
import com.project.schedule.service.TruckScheduleService;

import java.util.*;

public class Main {
    // 单位间隔时间
    public static final int PERIOD_TIME = 1;

    private static Integer time = 0;

    public static void main(String[] args) {
        // 构造地图
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
        int wight = 8;
        int height = 8;
        Map map = new Map();
        map.setMaps(maps);
        map.setWidth(wight);
        map.setHeight(height);

        // 构造三个投放点
        DropPlace subwayStationOne = new DropPlace(0, 0, 30, "地铁站一");
        DropPlace subwayStationTwo = new DropPlace(3, 2, 40, "地铁站二");
        DropPlace subwayStationThree = new DropPlace(7, 7, 30, "地铁站三");
        DropPlaceCollection dropPlaces = new DropPlaceCollection();
        dropPlaces.add(subwayStationOne);
        dropPlaces.add(subwayStationTwo);
        dropPlaces.add(subwayStationThree);

        // 构造两辆调度车
        Truck truckOne = new Truck("货车一",0,0);
        Truck truckTwo = new Truck("货车二",0,0);
        List<Truck> trucks = new ArrayList<>();
        trucks.add(truckOne);
        trucks.add(truckTwo);
        OperatingBicycle operatingBicycle = new OperatingBicycle();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(time == 0){
                    System.out.print(secondToTime(time)+" ");
                    dropPlaces.forEach(dropPlace -> {
                        System.out.print(dropPlace.placeName + "现有自行车" + dropPlace.bicycleList.size() + "辆,");
                    });
                    System.out.println("路上车"+operatingBicycle.getBicyclesSize() + "辆。");
                }else{
                    // 随机取投放点车辆运营至随机投放点
                    Random rand = new Random();
                    int startIndex = rand.nextInt(dropPlaces.size());
                    int targetIndex = afterNextInt(startIndex, dropPlaces.size());
                    DropPlace startPlace = dropPlaces.get(startIndex);
                    DropPlace targetPlace = dropPlaces.get(targetIndex);

                    Node startNode = new Node(startPlace.getPlaceLocation().x, startPlace.getPlaceLocation().y);
                    Node endNode = new Node(targetPlace.getPlaceLocation().x, targetPlace.getPlaceLocation().y);
                    PathAlgo pathAlgo = new PathAlgo();
                    MapInfo mapInfo = new MapInfo(map, startNode, endNode);
                    int pathLength = pathAlgo.start(mapInfo);
                    Bicycle bicycle = startPlace.takeOut();
                    bicycle.setDistance(pathLength);
                    bicycle.setTargetPlace(targetPlace);
                    operatingBicycle.putBicycle(bicycle);

                    // 信息记录
                    System.out.print(secondToTime(time)+" ");
                    dropPlaces.forEach(dropPlace -> {
                        System.out.print(dropPlace.placeName + "现有自行车" + dropPlace.bicycleList.size() + "辆,");
                    });
                    System.out.println("路上车"+operatingBicycle.getBicyclesSize() + "辆。");

                    // 执行调度
                    ScheduleService scheduleService = new TruckScheduleService();
                    scheduleService.schedule(dropPlaces, trucks, map);
                    OperatingTruck.truckCalculate();
                    if(time >= 200){
                        timer.cancel();
                    }
                }
                time++;
            }
        };
        timer.schedule(timerTask, 0, PERIOD_TIME * 1000);
    }

    private static int afterNextInt(int nextInt, int scope) {
        Random rand = new Random();
        int ret = rand.nextInt(scope);
        while (ret == nextInt) {
            ret = rand.nextInt(scope);
        }
        return ret;
    }

    private static String secondToTime(int time) {
        StringBuilder timeStr = new StringBuilder();
        int h = time / 3600;
        int m = (time % 3600) / 60;
        int s = time % 60;
        return timeStr.append(unitFormat(h)).append(":").append(unitFormat(m)).append(":").append(unitFormat(s)).toString();
    }

    private static String unitFormat(int i) {
        StringBuilder retStr = new StringBuilder();
        if (i >= 0 && i < 10){
            retStr.append("0").append(i);
        } else {
            retStr.append(i);
        }
        return retStr.toString();
    }



}
