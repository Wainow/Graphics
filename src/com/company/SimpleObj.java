package com.company;

import java.util.ArrayList;

// класс obj-объекта
public class SimpleObj {

    // массив координат вершин
    private ArrayList<Coord> coords;

    // массив полигонов
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

    public void setCoords(ArrayList<Coord> coords){ this.coords=coords;}

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

    public void addPolygon(Polygon polygon) {
        polygons.add(polygon);
    }
}
