package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Picture picture0 = new Picture(205, 255, new Color(0,0,0));
            PictureUtils.savePicture(picture0, "picture0.png");

            Picture picture1 = new Picture(205, 255, new Color(255,255,255));
            PictureUtils.savePicture(picture1, "picture1.png");

            Picture picture2 = new Picture(205, 255, new Color(255,0,0));
            PictureUtils.savePicture(picture2, "picture2.png");

            Picture picture3 = new Picture(205, 255, new Color(0,0,200));
            PictureUtils.drawGradient(picture3);
            PictureUtils.savePicture(picture3, "picture3.png");

            Picture picture4 = new Picture(200, 200, new Color(0,50,20));
            PictureUtils.drawStar(picture4, new Color(255, 0, 0));
            PictureUtils.savePicture(picture4, "picture4.png");

            Picture picture5 = new Picture(200, 200, new Color(22,0,20));
            PictureUtils.drawStar2(picture5, new Color(255, 0, 0));
            PictureUtils.savePicture(picture5, "picture5.png");;

            /*

            PictureUtils.savePicture(picture, "picture1.png");

            PictureUtils.savePicture(picture, "picture2.png");

             */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

