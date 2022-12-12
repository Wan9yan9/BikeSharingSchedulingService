package com.project.schedule.entity.transportation;

import com.project.schedule.algo.PathAlgo;
import com.project.schedule.entity.place.DropPlace;
import com.project.schedule.entity.map.Coord;
import com.project.schedule.entity.map.Map;
import com.project.schedule.entity.map.MapInfo;
import com.project.schedule.entity.map.Node;
import com.project.schedule.service.intf.ISubscriber;

import java.util.List;

import static com.project.schedule.entity.transportation.OperatingTruck.putTruck;

public class Truck extends Vehicle implements ISubscriber<DropPlace> {

    public Coord truckLocation;
    public int capacity;
    public int distance;
    public DropPlace fromPlace;
    public DropPlace targetPlace;
    public List<Bicycle> bicycleList;

    public Truck(String name,int x,int y){
        this.name = name;
        this.velocity = 3;
        this.capacity = 20;
        this.truckLocation = new Coord(x,y);
    }

    @Override
    public void on(DropPlace dropPlace, Object msg) {
        System.out.println(this.getTargetPlace().getPlaceName()+"请求调度");
        Map map = (Map) msg;
        // 计算货车从出发点->取货点->目的地的距离
        Node start = new Node(this.getTruckLocation().x,this.getTruckLocation().y);
        Node from = new Node(this.getFromPlace().getPlaceLocation().x,this.getFromPlace().getPlaceLocation().y);
        Node end = new Node(this.getTargetPlace().getPlaceLocation().x,this.getTargetPlace().getPlaceLocation().y);
        MapInfo startToFrom = new MapInfo(map.getMaps(),map.getWidth(),map.getHeight(),start,from);
        MapInfo fromToEnd = new MapInfo(map.getMaps(),map.getWidth(),map.getHeight(),from,end);
        PathAlgo pathAlgo = new PathAlgo();
        int pathLength = pathAlgo.start(startToFrom) + pathAlgo.start(fromToEnd);
        this.setDistance(pathLength);
        putTruck(this);
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

    public Coord getTruckLocation() {
        return truckLocation;
    }

    public void setTruckLocation(Coord truckLocation) {
        this.truckLocation = truckLocation;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Bicycle> getBicycleList() {
        return bicycleList;
    }

    public void setBicycleList(List<Bicycle> bicycleList) {
        this.bicycleList = bicycleList;
    }

    public DropPlace getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(DropPlace fromPlace) {
        this.fromPlace = fromPlace;
    }
}
