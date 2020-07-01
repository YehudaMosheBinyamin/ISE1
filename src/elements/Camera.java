package elements;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.Random;

import static primitives.Util.isZero;

public class Camera {
    private static final Random rnd = new Random();
    Point3D _p0;
    Vector _vTo;
    Vector _vUp;
    Vector _vRight;

    public Camera(Point3D _p0, Vector _vTo, Vector _vUp) {

        //if the the vectors are not orthogonal, throw exception.
        if (_vUp.dotProduct(_vTo) != 0)
            throw new IllegalArgumentException("the vectors must be orthogonal");

        this._p0 = new Point3D(_p0);
        this._vTo = _vTo.normalized();
        this._vUp = _vUp.normalized();

        _vRight = this._vTo.crossProduct(this._vUp).normalize();

    }


    public Point3D get_p0() {
        return new Point3D(_p0);
    }

    public Vector get_vTo() {
        return new Vector(_vTo);
    }

    public Vector get_vUp() {
        return new Vector(_vUp);
    }

    public Vector get_vRight() {
        return new Vector(_vRight);
    }

    /**
     * returns point ij of viewplane
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @param screenDistance
     * @param screenWidth
     * @param screenHeight
     * @return Point3D
     */
    public Point3D getPIJ(int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight) {
    if (isZero(screenDistance)) {
        throw new IllegalArgumentException("distance cannot be 0");
    }
    Point3D Pc = _p0;
    Pc = (Pc.addForPoint(_vTo.scale(screenDistance)));
    double Ry = screenHeight / nY;
    double Rx = screenWidth / nX;

    double yi = ((i - nY / 2d) * Ry + Ry / 2d);
    double xj = ((j - nX / 2d) * Rx + Rx / 2d);

    Point3D Pij = Pc;
    if (!isZero(xj)) {
        Pij = (Pij.addForPoint(_vRight.scale(xj)));
    }
    if (!isZero(yi)) {
        //Pij = (_vUp.scale(yi).get_head().subtract(Pij)).get_head(); // Pij.add(_vUp.scale(-yi))
        //Pij=Pij.subtract(_vUp.scale(yi).get_head()).get_head();
        //Pij=Pij.subtractForPoint(_vUp.scale(yi));
        Pij = Pij.addForPoint(_vUp.scale(-yi));
    }
    return Pij;
}
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight) {
        if (isZero(screenDistance)) {
            throw new IllegalArgumentException("distance cannot be 0");
        }
       Point3D Pc=_p0;
         Pc = (Pc.addForPoint(_vTo.scale(screenDistance)));
        double Ry = screenHeight / nY;
        double Rx = screenWidth / nX;

        double yi = ((i - nY / 2d) * Ry + Ry / 2d);
        double xj = ((j - nX / 2d) * Rx + Rx / 2d);

        Point3D Pij = Pc;
        if (!isZero(xj)) {
            Pij = (Pij.addForPoint(_vRight.scale(xj)));
        }
        if (!isZero(yi)) {
            //Pij = (_vUp.scale(yi).get_head().subtract(Pij)).get_head(); // Pij.add(_vUp.scale(-yi))
            //Pij=Pij.subtract(_vUp.scale(yi).get_head()).get_head();
            //Pij=Pij.subtractForPoint(_vUp.scale(yi));
            Pij=Pij.addForPoint(_vUp.scale(-yi));
        }
        Vector Vij = _p0.subtract(Pij);
        //return new Ray(_p0, Vij.normalize());
return new Ray(_p0,Vij);
    }

    /**
     * constructs and five rays one in center and the rest on the corners of pixel
     * @param x0
     * @param y0
     * @param xn
     * @param yn
     * @param pij
     * @return
     */
    public LinkedList<Ray> constructBeamThroughPixel(double x0, double y0, double xn, double yn,Point3D pij) {
    {
       LinkedList<Ray> beamRays=new LinkedList<Ray>();
  Point3D Pij=pij;
    Vector Vij=_p0.subtract(Pij);
    beamRays.add(new Ray(_p0,Vij));
        double z=pij.get_z().get();
        double width=xn-x0;
        double height=yn-y0;
        double xlupper=(x0-width/2.0);//calculating left upper corner
        double ylupper=y0+height/2.0;

        Point3D pijlupper=new Point3D(xlupper,ylupper,z);
        Vij=(_p0).subtract(pijlupper);
        beamRays.add(new Ray(_p0,Vij));
       //calculating left lower corner

        double xllower=(x0-width/2.0);
        double yllower=y0-height/2.0;
        Point3D pijllower=new Point3D(xllower,yllower,z);
    Vij=(_p0).subtract(pijllower);
        beamRays.add(new Ray(_p0,Vij));
    //calculating upper  right corner of pixel
        double xrupper=(x0+width/2.0);
        double yrupper=y0+height/2.0;

        Point3D pijru=new Point3D(xrupper,yrupper,z);

    Vij=(_p0).subtract(pijru);
    beamRays.add(new Ray(_p0,Vij));
        //calculating lower  right corner of pixel
        double xrlower=(x0+width/2.0);
        double yrlower=y0-height/2.0;
        Point3D pijrlower=new Point3D(xrlower,yrlower,z);

    Vij=(_p0).subtract(pijrlower);
    beamRays.add(new Ray(_p0,Vij));
    return beamRays;
}
}}