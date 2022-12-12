package com.project.schedule.service;

import com.project.schedule.entity.map.Map;
import com.project.schedule.entity.place.DropPlace;
import com.project.schedule.entity.place.DropPlaceCollection;
import com.project.schedule.entity.transportation.Truck;

import java.util.List;

public class TruckScheduleService implements ScheduleService<Truck> {
    /**
     * 阈值
     */
    private static final int SCHEDULE_THRESHOLD_VALUE = 10;

    @Override
    public void schedule(DropPlaceCollection dropPlaces, List<Truck> trucks, Map map) {
        dropPlaces.forEach(item->{
            // 判断是否有投放点需要被投放
            if(isNeedGet(item) && item.state == 0){
                dropPlaces.forEach(otherItem->{
                    if(!otherItem.equals(item)){
                        // 判断其余投放点是否可取出资源
                        if(isCanGive(otherItem)){
                            trucks.forEach(truckItem->{
                                // 判断是否有调度车可投入使用
                                if(truckItem.getDistance() == 0 && item.getState() == 0){
                                    int giveNum = otherItem.getBicycleList().size() - otherItem.getInitialQuantity();
                                    if(giveNum > truckItem.getCapacity()){
                                        giveNum = truckItem.getCapacity();
                                    }
                                    if(giveNum > 0){
                                        // 执行调度
                                        truckItem.setFromPlace(otherItem);
                                        truckItem.setTargetPlace(item);
                                        truckItem.setBicycleList(otherItem.takeOut(giveNum));
                                        item.setState(1);
                                        item.emit(truckItem);
                                        item.on(map);
                                        item.remove(truckItem);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }
    private boolean isNeedGet(DropPlace dropPlace){
        return dropPlace.bicycleList.size() <= dropPlace.initialQuantity - SCHEDULE_THRESHOLD_VALUE;
    }

    private boolean isCanGive(DropPlace dropPlace){
        return dropPlace.bicycleList.size() > dropPlace.initialQuantity;
    }
}
