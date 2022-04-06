package com.company;

public class Light {
    private Coord direction;
    private Color color;

    public Light(){
        this.direction=new Coord(0,0,1);
        this.color=new Color(255);
    }

    public Light(Coord direction, Color color){
        this.direction=direction;
        this.color=color;
    }

    public Coord getDirection() {
        return direction;
    }

    public void setDirection(Coord direction) {
        this.direction = direction;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
