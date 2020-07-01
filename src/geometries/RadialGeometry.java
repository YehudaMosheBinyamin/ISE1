package geometries;

import primitives.Color;
import primitives.Material;

import java.util.Objects;

import static primitives.Util.isZero;

/**
 * class that represents an abstract body that has a radius
 */
public abstract class RadialGeometry extends Geometry {
    double _radius;
   Material _material;
   Color _color;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RadialGeometry that = (RadialGeometry) o;
        return Double.compare(that._radius, _radius) == 0;
    }


    /**
     * constructor
     * @param _radius
     */
    public RadialGeometry(double _radius)
    {
        this(Color.BLACK,new Material(0,0,0),_radius);

    }

    /**
     * constructor
     * @param material
     * @param radius
     */
    public RadialGeometry(Material material,double radius){
        this(Color.BLACK,material,radius);
}
    /**
     * constructor
     * @param color
     * @param material
     * @param radius
     */
    public RadialGeometry(Color color,Material material,double radius)
    {
    if (isZero(radius)||_radius < 0.0)
        {throw new IllegalArgumentException("radius "+ _radius +" is not valid");}
this._color=color;
this._material=material;
this._radius=radius;

}

    /**
     * copy constructor
     * @param other
     */
    public  RadialGeometry (RadialGeometry other)
    {
        this._radius = other._radius;
    }

    /**
     * return _radius
     * @return
     */
    public double get_radius() {
        return _radius;
    }

}
