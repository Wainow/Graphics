package com.company;

import java.util.ArrayList;

public class SimpleObj {
    private ArrayList<Coord> coords;

    private ArrayList<Polygon> polygons;

    public SimpleObj(){
        coords=new ArrayList<Coord>();
        polygons=new ArrayList<Polygon>();
    }

    public ArrayList<Coord> getCoords() {
        return coords;
    }

    public void addCoord(Coord coord) {
        coords.add(coord);
    }

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

    public void addPolygon(Polygon polygon) {
        polygons.add(polygon);
    }
}
