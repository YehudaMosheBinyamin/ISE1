package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements LightSource
{
    Point3D _position;
    double _kC;
    double _kL;
    double _kQ;

    /**
     * constructor
     * @param _intensity
     * @param _position
     * @param _kC
     * @param _kL
     * @param _kQ
     */
    public PointLight(Color _intensity, Point3D _position, double _kC, double _kL, double _kQ) {
        super(_intensity);
        this._position = _position;
        this._kC = _kC;
        this._kL = _kL;
        this._kQ = _kQ;
    }

    /**
     * function that returns vector from light to geometry
     * @param point
     * @return Vector
     */
    public Vector getL(Point3D point)
{
        Vector disToShape=point.subtract(_position).normalize();
        return disToShape;
}

    @Override
    public double getDistance(Point3D point) {
        return 0;
    }

    @Override
    public Color getIntensity(Point3D point) {
        //return point.subtract(_position).normalize();
        double distance=point.distance(_position);
        //Color il=getIntensity(point).scale(1/_kC+_kL*distance+_kQ*distance*distance);
        Color il=_intensity.scale(1/_kC+_kL*distance+_kQ*distance*distance);
        return il;
    }
}
