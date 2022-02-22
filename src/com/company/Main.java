package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {

            // полностью чёрное изображение
            Picture picture0 = new Picture(205, 255, new Color(0,0,0));
            PictureUtils.savePicture(picture0, "pictures/picture0.png");

            // полностью белое изображение
            Picture picture1 = new Picture(205, 255, new Color(255,255,255));
            PictureUtils.savePicture(picture1, "pictures/picture1.png");

            // полностью красное изображение
            Picture picture2 = new Picture(205, 255, new Color(255,0,0));
            PictureUtils.savePicture(picture2, "pictures/picture2.png");

            // градиент
            Picture picture3 = new Picture(205, 255, new Color(0,0,200));
            PictureUtils.drawGradient(picture3);
            PictureUtils.savePicture(picture3, "pictures/picture3.png");

            // первый способ отрисовки прямых
            Picture picture4 = new Picture(200, 200, new Color(0,50,20));
            PictureUtils.drawStar(picture4, new Color(255, 0, 0));
            PictureUtils.savePicture(picture4, "pictures/picture4.png");

            // второй способ отрисовки прямых
            Picture picture5 = new Picture(200, 200, new Color(22,0,20));
            PictureUtils.drawStar2(picture5, new Color(255, 0, 0));
            PictureUtils.savePicture(picture5, "pictures/picture5.png");

            // третий способ отрисовки прямых
            Picture picture6 = new Picture(200, 200, new Color(22,100,20));
            PictureUtils.drawStar3(picture6, new Color(255, 0, 0));
            PictureUtils.savePicture(picture6, "pictures/picture6.png");

            // итоговый способ отрисовки прямых
            Picture picture7 = new Picture(200, 200, new Color(100,100,100));
            PictureUtils.drawStar4(picture7, new Color(255, 0, 255));
            PictureUtils.savePicture(picture7, "pictures/picture7.png");


            // получаем объект 3д-модели из файла
            SimpleObj stormTrooper = SimpleObjUtils.SimpleObjFromFile("obj/StormTrooper.obj");
            // все вершины
            Picture picture8 = SimpleObjUtils.SimpleObjToPicture1(stormTrooper,1000,1000,250,500,500);
            PictureUtils.savePicture(PictureUtils.rotatePicture(picture8), "pictures/picture8.png");

            // все рёбра всех полигонов
            Picture picture9 = SimpleObjUtils.SimpleObjToPicture2(stormTrooper,1000,1000,250,500,500);
            PictureUtils.savePicture(PictureUtils.rotatePicture(picture9), "pictures/picture9.png");


            /*
            SimpleObj test = SimpleObjUtils.SimpleObjFromFile("obj/deer.obj");
            Picture pictureTest = SimpleObjUtils.SimpleObjToPicture2(test,1000,1000,0.6,500,0);
            PictureUtils.savePicture(PictureUtils.rotatePicture(pictureTest), "pictures/pictureTest.png");
            */

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

