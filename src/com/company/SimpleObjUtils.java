package com.company;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;
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
            PictureUtils.drawPixel(picture, (int)(coords.get(i).getX()*scale+xShift), (int)(coords.get(i).getY()*scale+yShift), new Color(255,255,255));
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
                        (int)(coords.get(v0).getX()*scale+xShift),
                        (int)(coords.get(v0).getY()*scale+yShift),
                        (int)(coords.get(v1).getX()*scale+xShift),
                        (int)(coords.get(v1).getY()*scale+yShift),
                        new Color(255,255,255)
                        );
            }
            // соединяем последовательно все вершины с первой отрезками
            for(int j=2; j<vertices.size(); j++){
                int v0=vertices.get(0).getV()>0?vertices.get(0).getV()-1:coords.size()+vertices.get(0).getV();
                int v1=vertices.get(j).getV()>0?vertices.get(j).getV()-1:coords.size()+vertices.get(j).getV();
                PictureUtils.drawLine(picture,
                        (int)(coords.get(v0).getX()*scale+xShift),
                        (int)(coords.get(v0).getY()*scale+yShift),
                        (int)(coords.get(v1).getX()*scale+xShift),
                        (int)(coords.get(v1).getY()*scale+yShift),
                        new Color(255,255,255)
                );
            }
        }
        return picture;
    }
}
