package com.company;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

// методы для работы с obj-объектом
public class SimpleObjUtils {

    // парсинг объекта из .obj файла
    public static SimpleObj SimpleObjFromFile(String filename) throws Exception {

        SimpleObj simpleObj=new SimpleObj();
        String str;
        Scanner lineScanner;

        FileReader fr= new FileReader(filename);
        Scanner fileScanner = new Scanner(fr);

        while (fileScanner.hasNextLine()) {
            str=fileScanner.nextLine();
            // пропускаем пустые строки
            if(str.length()<3) continue;
            // если это строка вершины
            if(str.charAt(0)=='v'&&str.charAt(1)==' '){
                lineScanner = new Scanner(str.substring(1)).useLocale(Locale.ENGLISH);
                // считываем трёхмерные координаты
                simpleObj.addCoord(new Coord(lineScanner.nextDouble(),lineScanner.nextDouble(),lineScanner.nextDouble()));
            }
            // если это строка нормали
            if(str.charAt(0)=='v'&&str.charAt(1)=='n'){
                lineScanner = new Scanner(str.substring(2)).useLocale(Locale.ENGLISH);
                // считываем трёхмерные координаты
                simpleObj.addNormal(new Coord(lineScanner.nextDouble(),lineScanner.nextDouble(),lineScanner.nextDouble()));
            }
            // если это строка полигона
            else if(str.charAt(0)=='f'){
                Polygon currPolygon=new Polygon();
                int v=0, vt=0, vn=0;
                int k=0; // v/vt/vn
                boolean isNeg=false;
                int currInt=0;
                // проходим по всей строке
                for(int i=2; i<str.length(); i++){
                    char currChar=str.charAt(i);
                    // если закончили считывать текущий int
                    if(currChar==' '||currChar=='/'){
                        // если мы считали число
                        if(currInt!=0){
                            if(isNeg) currInt*=-1;
                            // понимаем, за какой компонент вершины отвечает данное число
                            if(k==0) v=currInt;
                            else if(k==1) vt=currInt;
                            else vn=currInt;
                            currInt=0;
                        }
                        isNeg=false;
                        // если закончили считывать вершины - сохраняем её
                        if(currChar==' '){
                            k=0;
                            if(v!=0)
                                currPolygon.addVertice(new Vertice(v, vt, vn));
                            v=0;
                            vt=0;
                            vn=0;
                        }
                        else k++;
                    }
                    // если текущий символ '-' - запоминаем это
                    else if(currChar=='-') isNeg = true;
                    // если считываем цифру, записываем ее в текущее число
                    else if(currChar>='0'&&currChar<='9')
                        currInt=currInt*10+currChar-'0';
                }
                // после считывания строки сохраняем последнюю вершину при необходимости
                if(currInt!=0){
                    if(isNeg) currInt*=-1;
                    // понимаем, за какой компонент вершины отвечает данное число
                    if(k==0) v=currInt;
                    else if(k==1) vt=currInt;
                    else vn=currInt;
                    currInt=0;
                }
                if(v!=0)
                    currPolygon.addVertice(new Vertice(v, vt, vn));
                // добавляем полигон в список полигонов
                simpleObj.addPolygon(currPolygon);
            }
        }

        fr.close();
        return simpleObj;
    }

    // создание картинки со всеми вершинами объекта
    public static Picture SimpleObjToPicture1(SimpleObj simpleObj, int w, int h, double scale, int xShift, int yShift){
        Picture picture=new Picture(w,h);
        ArrayList<Coord> coords=simpleObj.getCoords();
        // проходим по всем вершинам и рисуем соответствующий пиксель
        for(int i=0; i<coords.size();i++)
            PictureUtils.drawPixel(picture, (int)Math.round(coords.get(i).getX()*scale+xShift), (int)Math.round(coords.get(i).getY()*scale+yShift), new Color(255,255,255));
        return picture;
    }

    // создание картинки со всеми рёбрами
    public static Picture SimpleObjToPicture2(SimpleObj simpleObj, int w, int h, double scale, int xShift, int yShift){
        Picture picture=new Picture(w,h);
        ArrayList<Coord> coords=simpleObj.getCoords();
        ArrayList<Polygon> polygons= simpleObj.getPolygons();
        // проходим по всем полигонам
        for(int i=0; i<polygons.size();i++){
            ArrayList<Vertice> vertices = polygons.get(i).getVertices();
            // соединяем последовательно все соседние вершины отрезками
            for(int j=0; j<vertices.size()-1; j++){
                int v0=vertices.get(j).getV()>0?vertices.get(j).getV()-1:coords.size()+vertices.get(j).getV();
                int v1=vertices.get(j+1).getV()>0?vertices.get(j+1).getV()-1:coords.size()+vertices.get(j+1).getV();
                PictureUtils.drawLine(picture,
                        (int)Math.round(coords.get(v0).getX()*scale+xShift),
                        (int)Math.round(coords.get(v0).getY()*scale+yShift),
                        (int)Math.round(coords.get(v1).getX()*scale+xShift),
                        (int)Math.round(coords.get(v1).getY()*scale+yShift),
                        new Color(255,255,255)
                        );
            }
            // соединяем последовательно все вершины с первой отрезками
            for(int j=2; j<vertices.size(); j++){
                int v0=vertices.get(0).getV()>0?vertices.get(0).getV()-1:coords.size()+vertices.get(0).getV();
                int v1=vertices.get(j).getV()>0?vertices.get(j).getV()-1:coords.size()+vertices.get(j).getV();
                PictureUtils.drawLine(picture,
                        (int)Math.round(coords.get(v0).getX()*scale+xShift),
                        (int)Math.round(coords.get(v0).getY()*scale+yShift),
                        (int)Math.round(coords.get(v1).getX()*scale+xShift),
                        (int)Math.round(coords.get(v1).getY()*scale+yShift),
                        new Color(255,255,255)
                );
            }
        }
        return picture;
    }

    // создание картинки из цветных полигонов
    public static Picture SimpleObjToPicture3(SimpleObj simpleObj, int w, int h, double scale, int xShift, int yShift){
        Picture picture=new Picture(w,h);
        ArrayList<Coord> coords=simpleObj.getCoords();
        ArrayList<Polygon> polygons= simpleObj.getPolygons();
        // проходим по всем полигонам
        final Random random = new Random();
        for(int i=0; i<polygons.size();i++){
            ArrayList<Vertice> vertices = polygons.get(i).getVertices();
            // рисуем все треугольники с двумя соседними вершинами и нулевой вершиной
            int v0=vertices.get(0).getV()>0?vertices.get(0).getV()-1:coords.size()+vertices.get(0).getV();
            for(int j=1; j<vertices.size()-1; j++){
                int v1=vertices.get(j).getV()>0?vertices.get(j).getV()-1:coords.size()+vertices.get(j).getV();
                int v2=vertices.get(j+1).getV()>0?vertices.get(j+1).getV()-1:coords.size()+vertices.get(j+1).getV();
                PictureUtils.drawTriangle(picture,
                        new Coord(coords.get(v0).getX()*scale+xShift, (coords.get(v1).getX()*scale+xShift), (coords.get(v2).getX()*scale+xShift)),
                        new Coord(coords.get(v0).getY()*scale+yShift, (coords.get(v1).getY()*scale+yShift), (coords.get(v2).getY()*scale+yShift)),
                        new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            }
        }
        return picture;
    }

    // без полигонов повернутых от камеры
    public static Picture SimpleObjToPicture4(SimpleObj simpleObj, int w, int h, double scale, int xShift, int yShift){
        Picture picture=new Picture(w,h);
        ArrayList<Coord> coords=simpleObj.getCoords();
        ArrayList<Polygon> polygons= simpleObj.getPolygons();
        // проходим по всем полигонам
        final Random random = new Random();
        for(int i=0; i<polygons.size();i++){
            ArrayList<Vertice> vertices = polygons.get(i).getVertices();
            // рисуем все треугольники с двумя соседними вершинами и нулевой вершиной
            int v0=vertices.get(0).getV()>0?vertices.get(0).getV()-1:coords.size()+vertices.get(0).getV();
            for(int j=1; j<vertices.size()-1; j++){
                int v2=vertices.get(j).getV()>0?vertices.get(j).getV()-1:coords.size()+vertices.get(j).getV();
                int v1=vertices.get(j+1).getV()>0?vertices.get(j+1).getV()-1:coords.size()+vertices.get(j+1).getV();
                if(MathTools.normDotProduct(MathTools.normal(coords.get(v0),coords.get(v1),coords.get(v2)),new Coord(0,0,1))<0)
                    PictureUtils.drawTriangle(picture,
                            new Coord(coords.get(v0).getX()*scale+xShift, (coords.get(v1).getX()*scale+xShift), (coords.get(v2).getX()*scale+xShift)),
                            new Coord(coords.get(v0).getY()*scale+yShift, (coords.get(v1).getY()*scale+yShift), (coords.get(v2).getY()*scale+yShift)),
                            new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            }
        }
        return picture;
    }

    // базовое освещение
    public static Picture SimpleObjToPicture5(SimpleObj simpleObj, int w, int h, double scale, int xShift, int yShift){
        Picture picture=new Picture(w,h);
        ArrayList<Coord> coords=simpleObj.getCoords();
        ArrayList<Polygon> polygons= simpleObj.getPolygons();
        // проходим по всем полигонам
        final Random random = new Random();
        for(int i=0; i<polygons.size();i++){
            ArrayList<Vertice> vertices = polygons.get(i).getVertices();
            // рисуем все треугольники с двумя соседними вершинами и нулевой вершиной
            int v0=vertices.get(0).getV()>0?vertices.get(0).getV()-1:coords.size()+vertices.get(0).getV();
            for(int j=1; j<vertices.size()-1; j++){
                int v2=vertices.get(j).getV()>0?vertices.get(j).getV()-1:coords.size()+vertices.get(j).getV();
                int v1=vertices.get(j+1).getV()>0?vertices.get(j+1).getV()-1:coords.size()+vertices.get(j+1).getV();
                double normal=MathTools.normDotProduct(MathTools.normal(coords.get(v0),coords.get(v1),coords.get(v2)),new Coord(0,0,1));
                if(normal<0)
                    PictureUtils.drawTriangle(picture,
                            new Coord(coords.get(v0).getX()*scale+xShift, (coords.get(v1).getX()*scale+xShift), (coords.get(v2).getX()*scale+xShift)),
                            new Coord(coords.get(v0).getY()*scale+yShift, (coords.get(v1).getY()*scale+yShift), (coords.get(v2).getY()*scale+yShift)),
                            new Color((int)Math.round(-200*normal), (int)Math.round(-200*normal), (int)Math.round(-200*normal)));
            }
        }
        return picture;
    }

    // с z-буффером
    public static Picture SimpleObjToPicture6(SimpleObj simpleObj, int w, int h, double scale, int xShift, int yShift){
        Picture picture=new Picture(w,h);
        ArrayList<Coord> coords=simpleObj.getCoords();
        ArrayList<Polygon> polygons= simpleObj.getPolygons();
        // проходим по всем полигонам
        for(int i=0; i<polygons.size();i++){
            ArrayList<Vertice> vertices = polygons.get(i).getVertices();
            // рисуем все треугольники с двумя соседними вершинами и нулевой вершиной
            int v0=vertices.get(0).getV()>0?vertices.get(0).getV()-1:coords.size()+vertices.get(0).getV();
            for(int j=1; j<vertices.size()-1; j++){
                int v2=vertices.get(j).getV()>0?vertices.get(j).getV()-1:coords.size()+vertices.get(j).getV();
                int v1=vertices.get(j+1).getV()>0?vertices.get(j+1).getV()-1:coords.size()+vertices.get(j+1).getV();
                double normal=MathTools.normDotProduct(MathTools.normal(coords.get(v0),coords.get(v1),coords.get(v2)),new Coord(0,0,1));
                                if(normal<0)
                    PictureUtils.drawTriangleZ(picture,
                            new Coord(coords.get(v0).getX()*scale+xShift, (coords.get(v1).getX()*scale+xShift), (coords.get(v2).getX()*scale+xShift)),
                            new Coord(coords.get(v0).getY()*scale+yShift, (coords.get(v1).getY()*scale+yShift), (coords.get(v2).getY()*scale+yShift)),
                            new Coord(coords.get(v0).getZ()*scale, (coords.get(v1).getZ()*scale), (coords.get(v2).getZ()*scale)),
                            new Color((int)Math.round(-200*normal), (int)Math.round(-200*normal), (int)Math.round(-200*normal)));
            }
        }
        return picture;
    }

    // проективное преобразование
    public static Picture SimpleObjToPicture7(SimpleObj simpleObj, int w, int h, double ax, double ay, double x0, double y0, double zShift){
        Picture picture=new Picture(w,h);
        ArrayList<Coord> coords=simpleObj.getCoords();
        ArrayList<Polygon> polygons= simpleObj.getPolygons();
        // проходим по всем полигонам
        for(int i=0; i<polygons.size();i++){
            ArrayList<Vertice> vertices = polygons.get(i).getVertices();
            // рисуем все треугольники с двумя соседними вершинами и нулевой вершиной
            int v0=vertices.get(0).getV()>0?vertices.get(0).getV()-1:coords.size()+vertices.get(0).getV();
            for(int j=1; j<vertices.size()-1; j++){
                int v2=vertices.get(j).getV()>0?vertices.get(j).getV()-1:coords.size()+vertices.get(j).getV();
                int v1=vertices.get(j+1).getV()>0?vertices.get(j+1).getV()-1:coords.size()+vertices.get(j+1).getV();
                double normal=MathTools.normDotProduct(MathTools.normal(coords.get(v0),coords.get(v1),coords.get(v2)),new Coord(0,0,1));
                if(normal<0){
                    double z0=coords.get(v0).getZ()+zShift;
                    double z1=coords.get(v1).getZ()+zShift;
                    double z2=coords.get(v2).getZ()+zShift;
                    PictureUtils.drawTriangleZ(picture,
                            new Coord(coords.get(v0).getX()*ax/z0+x0, coords.get(v1).getX()*ax/z1+x0, coords.get(v2).getX()*ax/z2+x0),
                            new Coord(coords.get(v0).getY()*ax/z0+y0, coords.get(v1).getY()*ax/z1+y0, coords.get(v2).getY()*ax/z2+y0),
                            new Coord(z0, z1, z2),
                            new Color((int)Math.round(-200*normal), (int)Math.round(-200*normal), (int)Math.round(-200*normal)));
                }
            }
        }
        return picture;
    }

    // камера, свет
    public static Picture SimpleObjToPicture8(SimpleObj simpleObj, int w, int h, Camera camera, Light light){
        double ex=1/Math.tan(camera.getXAngle()/2);
        double ey=1/Math.tan(camera.getYAngle()/2);
        double n=camera.getN();
        double f=camera.getF();
        Coord camDirection=MathTools.rotate(new Coord(0,0,1), camera.getAlpha(), camera.getBeta(),0);

        double[][] A=new double[3][3];
        Coord xe=MathTools.rotate(new Coord(1,0,0), camera.getAlpha(), camera.getBeta(), 0);
        Coord ye=MathTools.rotate(new Coord(0,1,0), camera.getAlpha(), camera.getBeta(), 0);
        Coord ze=MathTools.rotate(new Coord(0,0,1), camera.getAlpha(), camera.getBeta(), 0);
        A[0][0]=xe.getX(); A[0][1]=ye.getX(); A[0][2]=ze.getX();
        A[1][0]=xe.getY(); A[1][1]=ye.getY(); A[1][2]=ze.getY();
        A[2][0]=xe.getZ(); A[2][1]=ye.getZ(); A[2][2]=ze.getZ();

        double[][] AInv=MathTools.inversion(A,3);

        double[][] newO = new double[3][1];
        newO[0][0]=camera.getPosition().getX();
        newO[1][0]=camera.getPosition().getY();
        newO[2][0]=camera.getPosition().getZ();

        double[][] oldO = MathTools.matMul(MathTools.scalMul(AInv,3,3,-1),newO, 3, 3, 1);

        Picture picture=new Picture(w,h);
        ArrayList<Coord> coords=simpleObj.getCoords();
        ArrayList<Polygon> polygons= simpleObj.getPolygons();
        // проходим по всем полигонам
        for(int i=0; i<polygons.size();i++){
            ArrayList<Vertice> vertices = polygons.get(i).getVertices();
            // рисуем все треугольники с двумя соседними вершинами и нулевой вершиной
            int[] v=new int[3];
            v[0]=vertices.get(0).getV()>0?vertices.get(0).getV()-1:coords.size()+vertices.get(0).getV();
            for(int j=1; j<vertices.size()-1; j++){
                v[2]=vertices.get(j).getV()>0?vertices.get(j).getV()-1:coords.size()+vertices.get(j).getV();
                v[1]=vertices.get(j+1).getV()>0?vertices.get(j+1).getV()-1:coords.size()+vertices.get(j+1).getV();
                Coord normal=MathTools.normal(coords.get(v[0]),coords.get(v[1]),coords.get(v[2]));
                double brightness=-MathTools.normDotProduct(normal,light.getDirection())* light.getBrightness();
                if(brightness<0) brightness=0;
                if(MathTools.normDotProduct(normal,camDirection)<=0){
                    Coord[] tri=new Coord[3];
                    for(int k=0; k<3; k++){
                        Coord coord=MathTools.ncs(coords.get(v[k]),AInv,oldO);
                        tri[k]=new Coord();
                        tri[k].setX((1+ex*coord.getX()/coord.getZ())*w/2);
                        tri[k].setY((1+ey*coord.getY()/coord.getZ())*w/2);
                        tri[k].setZ((f+n)/(f-n)-2*f*n/((f-n)*coord.getZ()));
                    }

                    PictureUtils.drawTriangleZ2(picture, tri, new Color((int)Math.round(255*brightness)));
                }
            }
        }
        return picture;
    }

    // тонировка Гуро
    public static Picture SimpleObjToPicture9(SimpleObj simpleObj, int w, int h, Camera camera, Light light){
        double ex=1/Math.tan(camera.getXAngle()/2);
        double ey=1/Math.tan(camera.getYAngle()/2);
        double n=camera.getN();
        double f=camera.getF();
        Coord camDirection=MathTools.rotate(new Coord(0,0,1), camera.getAlpha(), camera.getBeta(),0);

        double[][] A=new double[3][3];
        Coord xe=MathTools.rotate(new Coord(1,0,0), camera.getAlpha(), camera.getBeta(), 0);
        Coord ye=MathTools.rotate(new Coord(0,1,0), camera.getAlpha(), camera.getBeta(), 0);
        Coord ze=MathTools.rotate(new Coord(0,0,1), camera.getAlpha(), camera.getBeta(), 0);
        A[0][0]=xe.getX(); A[0][1]=ye.getX(); A[0][2]=ze.getX();
        A[1][0]=xe.getY(); A[1][1]=ye.getY(); A[1][2]=ze.getY();
        A[2][0]=xe.getZ(); A[2][1]=ye.getZ(); A[2][2]=ze.getZ();

        double[][] AInv=MathTools.inversion(A,3);

        double[][] newO = new double[3][1];
        newO[0][0]=camera.getPosition().getX();
        newO[1][0]=camera.getPosition().getY();
        newO[2][0]=camera.getPosition().getZ();

        double[][] oldO = MathTools.matMul(MathTools.scalMul(AInv,3,3,-1),newO, 3, 3, 1);

        Picture picture=new Picture(w,h);
        ArrayList<Coord> coords=simpleObj.getCoords();
        ArrayList<Coord> normals=simpleObj.getNormals();
        ArrayList<Polygon> polygons= simpleObj.getPolygons();
        // проходим по всем полигонам
        for(int i=0; i<polygons.size();i++){
            ArrayList<Vertice> vertices = polygons.get(i).getVertices();
            // рисуем все треугольники с двумя соседними вершинами и нулевой вершиной
            int[] v=new int[3];
            int[] vn=new int[3];
            v[0]=vertices.get(0).getV()>0?vertices.get(0).getV()-1:coords.size()+vertices.get(0).getV();
            vn[0]=vertices.get(0).getVn()>0?vertices.get(0).getVn()-1:coords.size()+vertices.get(0).getVn();
            for(int j=1; j<vertices.size()-1; j++){
                v[2]=vertices.get(j).getV()>0?vertices.get(j).getV()-1:coords.size()+vertices.get(j).getV();
                v[1]=vertices.get(j+1).getV()>0?vertices.get(j+1).getV()-1:coords.size()+vertices.get(j+1).getV();
                vn[2]=vertices.get(j).getVn()>0?vertices.get(j).getVn()-1:coords.size()+vertices.get(j).getVn();
                vn[1]=vertices.get(j+1).getVn()>0?vertices.get(j+1).getVn()-1:coords.size()+vertices.get(j+1).getVn();
                Coord normal=MathTools.normal(coords.get(v[0]),coords.get(v[1]),coords.get(v[2]));
                Coord[] vNormals=new Coord[3];
                double[] brightness=new double[3];
                for(int k=0; k<3; k++){
                    vNormals[k]=normals.get(vn[k]);
                    brightness[k]=-MathTools.normDotProduct(vNormals[k],light.getDirection())*light.getBrightness();
                }
                if(MathTools.normDotProduct(normal,camDirection)<=2){
                    Coord[] tri=new Coord[3];
                    for(int k=0; k<3; k++){
                        Coord coord=MathTools.ncs(coords.get(v[k]),AInv,oldO);
                        tri[k]=new Coord();
                        tri[k].setX((1+ex*coord.getX()/coord.getZ())*w/2);
                        tri[k].setY((1+ey*coord.getY()/coord.getZ())*w/2);
                        tri[k].setZ((f+n)/(f-n)-2*f*n/((f-n)*coord.getZ()));
                    }

                    PictureUtils.drawTriangleZ3(picture, tri, brightness);
                }
            }
        }
        return picture;
    }

    // поворот объекта
    public static void RotateSimpleObj(SimpleObj simpleObj, double alpha, double beta, double gamma){
        ArrayList<Coord> origCoords=simpleObj.getCoords();
        ArrayList<Coord> newCoords=new ArrayList<Coord>();
        for (int i=0; i< origCoords.size(); i++){
            newCoords.add(MathTools.rotate(origCoords.get(i),alpha,beta,gamma));
        }
        simpleObj.setCoords(newCoords);
    }

}
