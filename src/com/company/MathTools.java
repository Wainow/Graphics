package com.company;

public class MathTools {
    // барицентрические координаты точки для данного треугольника
    public static Coord barycentric(int x, int y, Coord xtri, Coord ytri){
        double x0=xtri.getX();
        double x1=xtri.getY();
        double x2=xtri.getZ();
        double y0=ytri.getX();
        double y1=ytri.getY();
        double y2=ytri.getZ();
        return new Coord(
                ((x1 - x2)*(y - y2) - (y1 - y2)*(x - x2)) / ((x1 - x2)*(y0 - y2) - (y1 - y2)*(x0 - x2)),
                ((x2 - x0)*(y - y0) - (y2 - y0)*(x - x0)) / ((x2 - x0)*(y1 - y0) - (y2 - y0)*(x1 - x0)),
                ((x0 - x1)*(y - y1) - (y0 - y1)*(x - x1)) / ((x0 - x1)*(y2 - y1) - (y0 - y1)*(x2 - x1))
        );
    }

    // значение нормали треугольника
    public static Coord normal(Coord c0, Coord c1, Coord c2){
        double x0=c0.getX();
        double x1=c1.getX();
        double x2=c2.getX();
        double y0=c0.getY();
        double y1=c1.getY();
        double y2=c2.getY();
        double z0=c0.getZ();
        double z1=c1.getZ();
        double z2=c2.getZ();
        return new Coord(
                (y1-y0)*(z1-z2)-(z1-z0)*(y1-y2),
                (x1-x2)*(z1-z0)-(x1-x0)*(z1-z2),
                (x1-x0)*(y1-y2)-(x1-x2)*(y1-y0)
        );
    }

    // нормализованное скалярное произведение
    public static double normDotProduct(Coord n, Coord v){
        double nx=n.getX();
        double ny=n.getY();
        double nz=n.getZ();
        double vx=v.getX();
        double vy=v.getY();
        double vz=v.getZ();
        return (nx* vx+ny*vy+nz*vz)/Math.sqrt((nx*nx+ny*ny+nz*nz)*(vx*vx+vy*vy+vz*vz));
    }
}
