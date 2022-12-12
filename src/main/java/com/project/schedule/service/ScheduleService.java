package com.project.schedule.service;

import com.project.schedule.entity.place.DropPlaceCollection;
import com.project.schedule.entity.map.Map;
import com.project.schedule.entity.transportation.Vehicle;

import java.util.List;

public interface ScheduleService<T extends Vehicle> {

    /**
     * 搬运调度
     * @param dropPlaces 场所集合
     * @param vehicles 搬运车集合
     * @param map 地图
     */
    void schedule(DropPlaceCollection dropPlaces, List<T> vehicles, Map map);
}
