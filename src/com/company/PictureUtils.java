package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class PictureUtils {
    public static void savePicture(Picture picture, String filename) throws IOException {
        BufferedImage png = new BufferedImage(picture.getW(), picture.getH(), TYPE_INT_RGB);
        for(int i = 0; i < picture.getW(); i++) {
            for(int j = 0; j < picture.getH(); j++) {
                Color color = picture.getColorArray()[i][j];
                png.setRGB(i, j, new java.awt.Color(color.getR(), color.getG(), color.getB()).getRGB());
            }
        }
        ImageIO.write(png, "png", new File(filename));
    }

    public static void drawGradient(Picture picture) {
        for(int i = 0; i < picture.getW(); i++) {
            for(int j = 0; j < picture.getH(); j++) {
                int k = (i + j) % 256;
                picture.getColorArray()[i][j] = new Color(k,k,k);
            }
        }
    }

    public static void drawPixel(Picture picture, int x, int y, Color color) {
        picture.getColorArray()[x][y] = color;
    }

    public static void drawSimpleLine(Picture picture, int x0, int y0, int x1, int y1, Color color, double dt) {
        for(double t = 0.0; t < 1.0; t+= dt) {
            drawPixel(picture,
                    (int)(x0 * (1-t) + x1 * t),
                    (int)(y0 * (1-t) + y1 * t),
                    color
             );
        }
    }
    public static void drawSimpleLine2(Picture picture, int x0, int y0, int x1, int y1, Color color) {
        for(int x = x0; x < x1; x++) {
            double t = (x - x0) / (double)(x1 - x0);
            drawPixel(picture,
                    x,
                    (int) (y0 * (1-t) + y1 * t),
                    color
             );
        }
    }
    public static void drawSimpleLine3(Picture picture, int x0, int y0, int x1, int y1, Color color) {
        boolean steep = false;
        if (Math.abs(x0-x1)<Math.abs(y0-y1)) {
            int k=x0;
            x0=y0;
            y0=k;
            k=x1;
            x1=y1;
            y1=k;
            steep = true;
        }
        if (x0>x1) { // make it left-to-right
            int k=x0;
            x0=x1;
            x1=k;
            k=y0;
            y0=y1;
            y1=k;
        }
        for(int x = x0; x < x1; x++) {
            double t = (x - x0) / (double)(x1 - x0);
            int y=(int) (y0 * (1-t) + y1 * t);
            if (steep)
                drawPixel(picture,
                    y,
                    x,
                    color
            );
            else
            drawPixel(picture,
                    x,
                    y,
                    color
            );
        }
    }
    public static void drawLine(Picture picture, int x0, int y0, int x1, int y1, Color color) {
        boolean steep = false;
        if (Math.abs(x0-x1)<Math.abs(y0-y1)) {
            int k=x0;
            x0=y0;
            y0=k;
            k=x1;
            x1=y1;
            y1=k;
            steep = true;
        }
        if (x0>x1) { // make it left-to-right
            int k=x0;
            x0=x1;
            x1=k;
            k=y0;
            y0=y1;
            y1=k;
        }
        int dx = x1-x0;
        int dy = y1-y0;
        double derror = Math.abs(dy/(double)(dx));
        double error = 0;
        int y = y0;

        for(int x = x0; x < x1; x++) {
            if (steep)
                drawPixel(picture,
                        y,
                        x,
                        color
                );
            else
                drawPixel(picture,
                        x,
                        y,
                        color
                );
            error += derror;
            if (error>0.5) {
                y += (y1>y0?1:-1);
                error -= 1;
            }
        }
    }

    public static void drawStar(Picture picture, Color color) {
        for(int i = 0; i < 13; i++) {
            double alpha = 2 * Math.PI * i / 13;
            drawSimpleLine(
                    picture,
                    100, 100,
                    (int) (100 + 95 * Math.cos(alpha)),
                    (int) (100 + 95 * Math.sin(alpha)),
                    color,
                    0.1
            );
        }
    }
    public static void drawStar2(Picture picture, Color color) {
        for(int i = 0; i < 13; i++) {
            double alpha = 2 * Math.PI * i / 13;
            drawSimpleLine2(
                    picture,
                    100, 100,
                    (int) (100 + 95 * Math.cos(alpha)),
                    (int) (100 + 95 * Math.sin(alpha)),
                    color
            );
        }
    }
    public static void drawStar3(Picture picture, Color color) {
        for(int i = 0; i < 13; i++) {
            double alpha = 2 * Math.PI * i / 13;
            drawSimpleLine3(
                    picture,
                    100, 100,
                    (int) (100 + 95 * Math.cos(alpha)),
                    (int) (100 + 95 * Math.sin(alpha)),
                    color
            );
        }
    }
    public static void drawStar4(Picture picture, Color color) {
        for(int i = 0; i < 13; i++) {
            double alpha = 2 * Math.PI * i / 13;
            drawLine(
                    picture,
                    100, 100,
                    (int) (100 + 95 * Math.cos(alpha)),
                    (int) (100 + 95 * Math.sin(alpha)),
                    color
            );
        }
    }

    public static Picture rotatePicture(Picture picture){
        int w= picture.getW();
        int h= picture.getH();
        Picture ansPicture = new Picture(w, h);
        Color[][] colors = picture.getColorArray();
        for(int i=0; i< w; i++)
            for (int j=0; j< h; j++)
                drawPixel(ansPicture, i, j, colors[w-i-1][h-j-1]);
        return ansPicture;
    }
}
