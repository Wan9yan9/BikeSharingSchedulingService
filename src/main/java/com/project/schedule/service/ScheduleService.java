package com.project.schedule.service;

import com.project.schedule.entity.DropPlaceCollection;
import com.project.schedule.entity.Vehicle;

import java.util.List;

public interface ScheduleService<T extends Vehicle> {
    void schedule(DropPlaceCollection dropPlaces, List<T> vehicles);
}
