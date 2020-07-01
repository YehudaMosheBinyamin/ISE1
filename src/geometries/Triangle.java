package geometries;
import primitives.*;
import primitives.Color;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 *class that represents a triangle in a 3d environment
 */
public class Triangle extends Polygon {
    //****CONSTRUCTOR*****/
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        this(new Material(0, 0, 0), p1, p2, p3, Color.BLACK);
    }

    public Triangle(Material material, Point3D p1, Point3D p2, Point3D p3) {
        super(Color.BLACK, material, p1, p2, p3);
    }

    public Triangle(Material material, Point3D p1, Point3D p2, Point3D p3, Color emmission) {
        super(emmission, material, p1, p2, p3);

    }

    public Triangle(Point3D point3D, Point3D p1, Point3D p2, Color color) {
        this(new Material(0, 0, 0), point3D, p1, p2, color);
    }

    /**
     * return vector normal
     *
     * @param _other
     * @return Vector
     */
    public Vector getNormal(Point3D _other) {
        return (super.getNormal(_other));
    }


    /**
     * returns intersections of ray with triangle,if no intersections returns null
     *
     * @param ray
     * @return LinkedList
     */
    @Override
    public LinkedList<Geopoint> findIntersections(Ray ray, double maximumdistance) {
        LinkedList<Geopoint> listgeop = new LinkedList<Geopoint>();
        List<Geopoint> arl = _plane.findIntersections(ray);
        if (arl==null) {
            return null;
        }
        if(arl.isEmpty())
        {return null;}
        Vector v1 = ray.get_p0().subtract(super.get_vertices().get(0));
        Vector v2 = ray.get_p0().subtract(super.get_vertices().get(1));
        Vector v3 = ray.get_p0().subtract(super.get_vertices().get(2));
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();
        if ((ray.get_p0().subtract(arl.get(0).point).dotProduct(n1)) *
                (ray.get_p0().subtract(arl.get(0).point).dotProduct(n2)) *
                (ray.get_p0().subtract(arl.get(0).point).dotProduct(n3)) > 0.0) {
            for (Geopoint geo : arl) {
                geo.geometry = this;
            }

            listgeop.add(new Geopoint(this, arl.get(0).getPoint()));
            return listgeop;
        }
        return null;
    }

}