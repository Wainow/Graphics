package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class PictureUtils {
    public static Boolean savePicture(Picture picture, String filename) throws IOException {
        BufferedImage png = new BufferedImage(picture.W, picture.H, TYPE_INT_RGB);
        for(int i = 0; i < picture.W; i++) {
            for(int j = 0; j < picture.H; j++) {
                Color color = picture.colorArray[i][j];
                png.setRGB(i, j, new java.awt.Color(color.r, color.g, color.b).getRGB());
            }
        }
        return ImageIO.write(png, "png", new File(filename));
    }

    public static void drawGradient(Picture picture) {
        for(int i = 0; i < picture.W; i++) {
            for(int j = 0; j < picture.H; j++) {
                int k = (i + j) % 256;
                picture.colorArray[i][j] = new Color(k,k,k);
            }
        }
    }

    public static void drawPixel(Picture picture, Pixel pixel) {
        picture.colorArray[pixel.x][pixel.y] = pixel.color;
    }

    public static void drawSimpleLine(Picture picture, Pixel pixel1, Pixel pixel2, double dt) {
        for(double t = 0.0; t < 1.0; t+= dt) {
            drawPixel(picture, new Pixel(
                    (int)(pixel1.x * (1-t) + pixel2.x * t),
                    (int)(pixel1.y * (1-t) + pixel2.y * t),
                    new Color()
             ));
        }
    }
    public static void drawSimpleLine2(Picture picture, Pixel pixel1, Pixel pixel2) {
        for(int x = pixel1.x; x < pixel2.x; x++) {
            double t = x - pixel1.x / (double)(pixel2.x - pixel1.x);
            drawPixel(picture, new Pixel(
                    x,
                    (int) (pixel1.y * (1-t) + pixel2.y * t),
                    new Color()
             ));
        }
    }

    public static void drawStar(Picture picture, Color color) {
        for(int i = 0; i < 13; i++) {
            double alpha = 2 * Math.PI * i / 13;
            drawSimpleLine(
                    picture,
                    new Pixel(100, 100, color),
                    new Pixel(
                            (int) (100 + 95 * Math.sin(alpha)),
                            (int) (100 + 95 * Math.cos(alpha))
                    ),
                    0.01
            );
        }
    }

    public static void drawStar2(Picture picture, Color color) {
        for(int i = 0; i < 13; i++) {
            double alpha = 2 * Math.PI * i / 13;
            drawSimpleLine2(
                    picture,
                    new Pixel(100, 100, color),
                    new Pixel(
                            (int) (100 + 95 * Math.sin(alpha)),
                            (int) (100 + 95 * Math.cos(alpha))
                    )
            );
        }
    }
}
