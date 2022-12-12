package com.project.schedule.entity.place;

import com.project.schedule.entity.map.Coord;

public class Place {
    // 场地名称
    public String placeName;

    // 场地地点
    public Coord placeLocation;

    public Coord getPlaceLocation() {
        return placeLocation;
    }

    public void setPlaceLocation(Coord placeLocation) {
        this.placeLocation = placeLocation;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
}
