package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class to represent tube in a 3d environment
 */
public class Tube extends RadialGeometry
{
    Ray _axisRay;

    /**
     * constructor
     *
     * @param _radius
     * @param _axisRay
     */
    public Tube(double _radius, Ray _axisRay) {
        this(Color.BLACK,new Material(0,0,0),_radius,_axisRay);

    }

    /**
     * constructor
     * @param material
     * @param _radius
     * @param _axisRay
     */
    public Tube(Material material,double _radius,Ray _axisRay)
    {
        this(Color.BLACK,material,_radius,_axisRay);

    }

    /**
     * constructor
     * @param color
     * @param material
     * @param _radius
     * @param _axisRay
     */
    public Tube(Color color, Material material, double _radius, Ray _axisRay)
    {
        super(color,material,_radius);
        this._axisRay=_axisRay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tube tube = (Tube) o;
        return Objects.equals(_axisRay, tube._axisRay);
    }



    /**
     * returns _axisRay
     *
     * @return Ray
     */
    public Ray get_axisRay() {
        return _axisRay;
    }

    /**
     * return normal
     *
     * @param point
     * @return Vector
     */
    public Vector getNormal(Point3D point)
    {
        Point3D p0 = _axisRay.get_p0();
        Vector v = _axisRay.get_dir();
        double t=alignZero((p0.subtract(point)).dotProduct(v));
        if (!isZero(t)) {
            p0=(p0.add(v.scale(t))).get_head();
        }
        Vector check = p0.subtract(point);

        return check.normalize();
    }
    @Override
    public String toString() {
        return "ray: " + _axisRay +
                ", radius: " + _radius;
    }
    public LinkedList<Geopoint> findIntersections(Ray ray,double maxdistance){return null;}
}

