package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            //lab1
            /*
            // полностью чёрное изображение
            Picture picture0 = new Picture(205, 255, new Color(0,0,0));
            PictureUtils.savePicture(picture0, "pictures/lab1/picture0.png");

            // полностью белое изображение
            Picture picture1 = new Picture(205, 255, new Color(255,255,255));
            PictureUtils.savePicture(picture1, "pictures/lab1/picture1.png");

            // полностью красное изображение
            Picture picture2 = new Picture(205, 255, new Color(255,0,0));
            PictureUtils.savePicture(picture2, "pictures/lab1/picture2.png");

            // градиент
            Picture picture3 = new Picture(205, 255, new Color(0,0,200));
            PictureUtils.drawGradient(picture3);
            PictureUtils.savePicture(picture3, "pictures/lab1/picture3.png");

            // первый способ отрисовки прямых
            Picture picture4 = new Picture(200, 200, new Color(0,50,20));
            PictureUtils.drawStar(picture4, new Color(255, 0, 0));
            PictureUtils.savePicture(picture4, "pictures/lab1/picture4.png");

            // второй способ отрисовки прямых
            Picture picture5 = new Picture(200, 200, new Color(22,0,20));
            PictureUtils.drawStar2(picture5, new Color(255, 0, 0));
            PictureUtils.savePicture(picture5, "pictures/lab1/picture5.png");

            // третий способ отрисовки прямых
            Picture picture6 = new Picture(200, 200, new Color(22,100,20));
            PictureUtils.drawStar3(picture6, new Color(255, 0, 0));
            PictureUtils.savePicture(picture6, "pictures/lab1/picture6.png");

            // итоговый способ отрисовки прямых
            Picture picture7 = new Picture(200, 200, new Color(100,100,100));
            PictureUtils.drawStar4(picture7, new Color(255, 0, 255));
            PictureUtils.savePicture(picture7, "pictures/lab1/picture7.png");
            */

            // получаем объект 3д-модели из файла
            SimpleObj stormTrooper = SimpleObjUtils.SimpleObjFromFile("obj/StormTrooper.obj");

            /*
            // все вершины
            Picture picture8 = SimpleObjUtils.SimpleObjToPicture1(stormTrooper,1000,1000,250,500,500);
            PictureUtils.savePicture(PictureUtils.rotatePicture(picture8), "pictures/lab1/picture8.png");

            // все рёбра всех полигонов
            Picture picture9 = SimpleObjUtils.SimpleObjToPicture2(stormTrooper,1000,1000,250,500,500);
            PictureUtils.savePicture(PictureUtils.rotatePicture(picture9), "pictures/lab1/picture9.png");
            */


            //lab2
            /*
            // проверка суммы барицентрических координат
            Coord barycentricCoord = MathTools.barycentric(
                    10,10, new Coord(0.5, 5, 5.5), new Coord(-0.5, 500, -9.9));
            System.out.println(barycentricCoord.getX()+ barycentricCoord.getY()+ barycentricCoord.getZ());

            // проверка функции отрисовки треугольников
            Picture picture0 = new Picture(400, 200, new Color(220,180,220));
            PictureUtils.drawTriangle(picture0,
                    new Coord(-1, -5, -6), new Coord(1, 2, 3),
                    new Color(255,255,255));
            PictureUtils.drawTriangle(picture0,
                    new Coord(-20.5, 50.5, 70), new Coord(150, 250, 160.4),
                    new Color(0,100,0));
            PictureUtils.drawTriangle(picture0,
                    new Coord(200, 250, 290), new Coord(50, 100, 90),
                    new Color(255,255,0));
            PictureUtils.savePicture(picture0, "pictures/lab2/picture0.png");

            // все полигоны случайным цветом
            Picture picture1 = SimpleObjUtils.SimpleObjToPicture3(stormTrooper,1000,1000,250,500,500);
            PictureUtils.savePicture(PictureUtils.rotatePicture(picture1), "pictures/lab2/picture1.png");

            // полигоны без нелицевых граней
            Picture picture2 = SimpleObjUtils.SimpleObjToPicture4(stormTrooper,1000,1000,250,500,500);
            PictureUtils.savePicture(PictureUtils.rotatePicture(picture2), "pictures/lab2/picture2.png");

            // базовое освещение
            Picture picture3 = SimpleObjUtils.SimpleObjToPicture5(stormTrooper,1000,1000,250,500,500);
            PictureUtils.savePicture(PictureUtils.rotatePicture(picture3), "pictures/lab2/picture3.png");

            // z-буффер
            Picture picture4 = SimpleObjUtils.SimpleObjToPicture6(stormTrooper,1000,1000,250,500,500);
            PictureUtils.savePicture(PictureUtils.rotatePicture(picture4), "pictures/lab2/picture4.png");
            */


            //lab3
            /*
            */
            // проективное преобразование
            Picture picture0 = SimpleObjUtils.SimpleObjToPicture7(stormTrooper,1000,1000,900, 900,500,500, 5);
            PictureUtils.savePicture(picture0, "pictures/lab3/picture0.png");

            // с поворотом
            SimpleObjUtils.RotateSimpleObj(stormTrooper,1.05*Math.PI,0.3,0);
            Picture picture1 = SimpleObjUtils.SimpleObjToPicture7(stormTrooper,1000,1000,900, 900,500,500, 5);
            PictureUtils.savePicture(picture1, "pictures/lab3/picture1.png");

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

