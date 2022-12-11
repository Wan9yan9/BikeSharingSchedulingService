package com.project.schedule.entity;

public class Place {
    public String placeName;
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
