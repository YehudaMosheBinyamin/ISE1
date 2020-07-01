package geometries;

import primitives.*;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Cylinder extends Tube  {
        double _height;

    /**
     *
     * @param he
     */
    //used to be radius 0.0
    public Cylinder(double he){super(1.0,new Ray(new Point3D(1,2,3),new Vector(4,5,6)));_height = he;}

    /**
     *
     * @param _radius
     * @param _axisRay
     * @param _height
     */
    public Cylinder(double _radius, Ray _axisRay, double _height) {
        this(Color.BLACK,new Material(0,0,0),_radius,_axisRay,_height);
    }

    /**
     * constructor
     * @param material
     * @param _radius
     * @param _axisRay
     * @param _height
     */
    public Cylinder(Material material, double _radius, Ray _axisRay, double _height) {
        this(Color.BLACK,material, _radius, _axisRay,_height);

    }

    /**
     * constructor
     * @param color
     * @param material
     * @param _radius
     * @param _axisRay
     * @param _height
     */
    public Cylinder(Color color, Material material, double _radius, Ray _axisRay, double _height) {
        super(color, material, _radius, _axisRay);
        this._height = _height;
    }

    /*****toString
     *
     * @return string of height
     */
    @Override
    public String toString()
    {
        return "Cylinder{" +
                "_height=" + _height +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cylinder cylinder = (Cylinder) o;
        return Double.compare(cylinder._height, _height) == 0;
    }

//**************************getters********************************//
    /**
     * returns normal
     * @param _other
     * @return
     */

    @Override
    public Vector getNormal(Point3D _other)
    {
        Point3D o = _axisRay.get_p0();
       Vector v = _axisRay.get_dir();
        double t;
        try {
          t = alignZero(_other.subtract(o).dotProduct(v));
        } catch (IllegalArgumentException e) { // o = 0
            return v;
     }
        //if the point is at a base
       if (t == 0 || isZero(_height - t)) // if it's close to 0, we'll get ZERO vector exception
           return v;
        o = (o.add(v.scale(t))).get_head();
        return o.subtract(_other).normalize();

    }
    public double get_height() {
        return _height;
    }
}

