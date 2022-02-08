package com.company;

public class Picture {
    public int W;
    public int H;

    public final Color[][] colorArray;

    public Picture(int w, int h) {
        W = w;
        H = h;
        colorArray = new Color[W][H];
        initArray();
    }

    public Picture(int w, int h, Color color) {
        W = w;
        H = h;
        colorArray = new Color[W][H];
        initArray(color);
    }

    private void initArray() { initArray(null); }

    private void initArray(Color color) {
        if(color == null) color = new Color();
        for(int i = 0; i < W; i++) {
            for(int j = 0; j < H; j++) {
                colorArray[i][j] = color;
            }
        }
    }
}
