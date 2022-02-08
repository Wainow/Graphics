package com.company;

public class Pixel {
    public int x;
    public int y;
    public Color color;

    public Pixel(int x, int y, Color color) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = new Color();
    }
}
