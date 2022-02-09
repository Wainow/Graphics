package com.company;

import java.util.ArrayList;

public class Polygon {

    private ArrayList<Vertice> vertices;

    public Polygon(){
        vertices=new ArrayList<Vertice>();
    }

    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public void addVertice(Vertice vertice) {
        vertices.add(vertice);
    }
}
