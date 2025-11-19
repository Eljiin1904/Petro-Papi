package com.example.petropapi.models;

public class Place {
    private String name;
    private String place_id;
    private Geometry geometry;

    public String getName() {
        return name;
    }
    public String getPlace_id() {
        return place_id;
    }
    public Geometry getGeometry() {
        return geometry;
    }
}
