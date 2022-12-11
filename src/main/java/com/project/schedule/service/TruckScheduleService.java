package com.project.schedule.service;

import com.project.schedule.entity.*;

import java.util.List;

import static com.project.schedule.entity.OperatingTruck.putTruck;

public class TruckScheduleService implements ScheduleService<Truck> {
    private static final int SCHEDULE_THRESHOLD_VALUE = 10;
    @Override
    public void schedule(DropPlaceCollection dropPlaces, List<Truck> trucks) {


        dropPlaces.forEach(item->{
            if(isNeedGet(item) && item.state == 0){
                dropPlaces.forEach(otherItem->{
                    if(!otherItem.equals(item)){
                        if(isCanGive(otherItem)){
                            trucks.forEach(truckItem->{
                                if(truckItem.getDistance() == 0 && item.getState() == 0){
                                    int giveNum = otherItem.getBicycleList().size() - otherItem.getInitialQuantity();
                                    if(giveNum > 0){
                                        truckItem.setFromPlace(otherItem);
                                        truckItem.setTargetPlace(item);
                                        truckItem.setBicycleList(otherItem.takeOut(giveNum));
                                        item.setState(1);
                                        item.emit(truckItem);
                                        item.on(item.getPlaceName()+"请求调度");
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
