package com.company;

public class Light {
    private Coord direction;
    private double brightness;

    public Light(){
        this.direction=new Coord(0,0,1);
        this.brightness=1;
    }

    public Light(Coord direction, double brightness){
        this.direction=direction;
        this.brightness=brightness;
    }

    public Coord getDirection() {
        return direction;
    }

    public void setDirection(Coord direction) {
        this.direction = direction;
    }

    public double getBrightness() {
        return brightness;
    }

    public void setBrightness(double brightness) {
        this.brightness = brightness;
    }
}
