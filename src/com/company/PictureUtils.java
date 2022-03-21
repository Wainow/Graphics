package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

// методы для работы с картинкой
public class PictureUtils {
    // сохранение картинки в файл
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

    // заполнение картинки градиентом
    public static void drawGradient(Picture picture) {
        for(int i = 0; i < picture.getW(); i++) {
            for(int j = 0; j < picture.getH(); j++) {
                int k = (i + j) % 256;
                picture.getColorArray()[i][j] = new Color(k,k,k);
            }
        }
    }

    // отрисовка одного пикселя
    public static void drawPixel(Picture picture, int x, int y, Color color) {
        int h= picture.getH();
        picture.getColorArray()[x][h-y-1] = color;
    }


    // первый способ отрисовки прямой
    public static void drawSimpleLine(Picture picture, int x0, int y0, int x1, int y1, Color color, double dt) {
        for(double t = 0.0; t < 1.0; t+= dt) {
            drawPixel(picture,
                    (int)Math.round(x0 * (1-t) + x1 * t),
                    (int)Math.round(y0 * (1-t) + y1 * t),
                    color
             );
        }
    }

    // второй способ отрисовки прямой
    public static void drawSimpleLine2(Picture picture, int x0, int y0, int x1, int y1, Color color) {
        for(int x = x0; x < x1; x++) {
            double t = (x - x0) / (double)(x1 - x0);
            drawPixel(picture,
                    x,
                    (int)Math.round(y0 * (1-t) + y1 * t),
                    color
             );
        }
    }

    // третий способ отрисовки прямой
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
            int y=(int)Math.round(y0 * (1-t) + y1 * t);
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

    // итоговый способ отрисовки прямой
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


    // отрисовка звезды 1
    public static void drawStar(Picture picture, Color color) {
        for(int i = 0; i < 13; i++) {
            double alpha = 2 * Math.PI * i / 13;
            drawSimpleLine(
                    picture,
                    100, 100,
                    (int)Math.round(100 + 95 * Math.cos(alpha)),
                    (int)Math.round(100 + 95 * Math.sin(alpha)),
                    color,
                    0.1
            );
        }
    }

    // отрисовка звезды 2
    public static void drawStar2(Picture picture, Color color) {
        for(int i = 0; i < 13; i++) {
            double alpha = 2 * Math.PI * i / 13;
            drawSimpleLine2(
                    picture,
                    100, 100,
                    (int)Math.round(100 + 95 * Math.cos(alpha)),
                    (int)Math.round(100 + 95 * Math.sin(alpha)),
                    color
            );
        }
    }

    // отрисовка звезды 3
    public static void drawStar3(Picture picture, Color color) {
        for(int i = 0; i < 13; i++) {
            double alpha = 2 * Math.PI * i / 13;
            drawSimpleLine3(
                    picture,
                    100, 100,
                    (int)Math.round(100 + 95 * Math.cos(alpha)),
                    (int)Math.round(100 + 95 * Math.sin(alpha)),
                    color
            );
        }
    }

    // отрисовка звезды 4
    public static void drawStar4(Picture picture, Color color) {
        for(int i = 0; i < 13; i++) {
            double alpha = 2 * Math.PI * i / 13;
            drawLine(
                    picture,
                    100, 100,
                    (int)Math.round(100 + 95 * Math.cos(alpha)),
                    (int)Math.round(100 + 95 * Math.sin(alpha)),
                    color
            );
        }
    }


    // поворот картинки на 180 градусов
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


    // отрисовка треугольника
    public static Picture drawTriangle(Picture picture, Coord xtri, Coord ytri, Color color){
        int xmin = (int)Math.round(Math.min(xtri.getX(), Math.min(xtri.getY(), xtri.getZ())));
        int ymin = (int)Math.round(Math.min(ytri.getX(), Math.min(ytri.getY(), ytri.getZ())));
        int xmax = (int)Math.round(Math.max(xtri.getX(), Math.max(xtri.getY(), xtri.getZ())))+1;
        int ymax = (int)Math.round(Math.max(ytri.getX(), Math.max(ytri.getY(), ytri.getZ())))+1;
        if(xmin<0) xmin=0;
        if(xmax> picture.getW()) xmax=picture.getW();
        if(ymin<0) ymin=0;
        if(ymax> picture.getH()) ymax=picture.getH();
        for(int i=xmin; i<xmax; i++)
            for(int j=ymin; j<ymax; j++){
                Coord coord=MathTools.barycentric(i,j,xtri,ytri);
                if(coord.getX()>=0&&coord.getY()>=0&&coord.getZ()>=0)
                    drawPixel(picture,i,j,color);
            }
        return picture;
    }


    // отрисовка треугольника с z-буффером
    public static Picture drawTriangleZ(Picture picture, Coord xtri, Coord ytri, Coord ztri, Color color){
        int xmin = (int)Math.round(Math.min(xtri.getX(), Math.min(xtri.getY(), xtri.getZ())));
        int ymin = (int)Math.round(Math.min(ytri.getX(), Math.min(ytri.getY(), ytri.getZ())));
        int xmax = (int)Math.round(Math.max(xtri.getX(), Math.max(xtri.getY(), xtri.getZ())))+1;
        int ymax = (int)Math.round(Math.max(ytri.getX(), Math.max(ytri.getY(), ytri.getZ())))+1;
        if(xmin<0) xmin=0;
        if(xmax> picture.getW()) xmax=picture.getW();
        if(ymin<0) ymin=0;
        if(ymax> picture.getH()) ymax=picture.getH();
        double z=0;
        for(int i=xmin; i<xmax; i++)
            for(int j=ymin; j<ymax; j++){
                Coord coord=MathTools.barycentric(i,j,xtri,ytri);
                if(coord.getX()>=0&&coord.getY()>=0&&coord.getZ()>=0){
                    z=coord.getX()* ztri.getX()+ coord.getY()* ztri.getY()+ coord.getZ()* ztri.getZ();
                    if(z<picture.getZbuf(i,j)){
                        drawPixel(picture,i,j,color);
                        picture.setZbuf(i,j,z);
                    }
                }
            }
        return picture;
    }
}
