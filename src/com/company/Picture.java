package com.company;

public class Picture {
    private int w;
    private int h;

    private Color[][] colorArray;

    public Picture(int w, int h) {
        this.w = w;
        this.h = h;
        colorArray = new Color[w][h];
        initArray();
    }

    public Picture(int w, int h, Color color) {
        this.w = w;
        this.h = h;
        colorArray = new Color[w][h];
        initArray(color);
    }

    public void setW(int w){
        this.w=w;
    }

    public int getW(){
        return w;
    }

    public void setH(int h){
        this.h=h;
    }

    public int getH(){
        return h;
    }

    public void setColorArray(Color[][] colorArray){
        this.colorArray=colorArray;
    }

    public Color[][] getColorArray(){
        return colorArray;
    }

    private void initArray() { initArray(null); }

    private void initArray(Color color) {
        if(color == null) color = new Color();
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                colorArray[i][j] = color;
            }
        }
    }
}
