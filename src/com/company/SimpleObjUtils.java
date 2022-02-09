package com.company;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class SimpleObjUtils {
    public static SimpleObj SimpleObjFromFile(String filename) throws Exception {

        SimpleObj simpleObj=new SimpleObj();
        String str;
        Scanner lineScanner;

        FileReader fr= new FileReader(filename);
        Scanner fileScanner = new Scanner(fr);

        while (fileScanner.hasNextLine()) {
            str=fileScanner.nextLine();
            if(str.charAt(0)=='v'&&str.charAt(1)==' '){
                lineScanner = new Scanner(str.substring(1)).useLocale(Locale.ENGLISH);
                simpleObj.addCoord(new Coord(lineScanner.nextDouble(),lineScanner.nextDouble(),lineScanner.nextDouble()));
            }
            else if(str.charAt(0)=='f'){
                Polygon currPolygon=new Polygon();
                int v=0, vt=0, vn=0;
                int k=0; // v/vt/vn
                boolean isNeg=false;
                int currInt=0;
                for(int i=2; i<str.length(); i++){
                    char currChar=str.charAt(i);
                    if(currChar==' '||currChar=='/'){
                        if(currInt!=0){
                            if(isNeg) currInt*=-1;
                            if(k==0) v=currInt;
                            else if(k==1) vt=currInt;
                            else vn=currInt;
                            currInt=0;
                        }
                        isNeg=false;
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
                    else if(currChar=='-') isNeg = true;
                    else if(currChar>='0'&&currChar<='9')
                        currInt=currInt*10+currChar-'0';
                }
                if(v!=0)
                    currPolygon.addVertice(new Vertice(v, vt, vn));
                simpleObj.addPolygon(currPolygon);
            }
        }

        fr.close();
        return simpleObj;
    }

    public static Picture SimpleObjToPicture1(SimpleObj simpleObj, int w, int h, double scale, int xShift, int yShift){
        Picture picture=new Picture(w,h);
        ArrayList<Coord> coords=simpleObj.getCoords();
        for(int i=0; i<coords.size();i++)
            PictureUtils.drawPixel(picture, (int)(coords.get(i).getX()*scale+xShift), (int)(coords.get(i).getY()*scale+yShift), new Color(255,255,255));
        return picture;
    }
    public static Picture SimpleObjToPicture2(SimpleObj simpleObj, int w, int h, double scale, int xShift, int yShift){
        Picture picture=new Picture(w,h);
        ArrayList<Coord> coords=simpleObj.getCoords();
        ArrayList<Polygon> polygons= simpleObj.getPolygons();
        for(int i=0; i<polygons.size();i++){
            ArrayList<Vertice> vertices = polygons.get(i).getVertices();
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
