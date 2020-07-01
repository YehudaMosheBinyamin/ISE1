package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.sqrt;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class that represents sphere in a 3d environment
 */
public class Sphere extends RadialGeometry {
    Point3D _center;

    /**
     * constructor
     * @param _radius
     * @param _center
     */
    public Sphere(double _radius, Point3D _center) {
this(Color.BLACK,new Material(0,0,0),_radius,_center);

    }

    /**
     * constructor
     * @param color
     * @param material
     * @param radius
     * @param point3D
     */
    public Sphere(Color color, Material material,double radius, Point3D point3D) {
        super(color,material,radius);
        this._center=point3D;
    }

    /**
     * constructor
     * @param material
     * @param radius
     * @param point3D
     */
    public Sphere(Material material,double radius,Point3D point3D)
    {this(Color.BLACK,material,radius,point3D);

    }

    /**
     * returns normal
     * @param _other
     * @return Vector
     */
    public Vector getNormal(Point3D _other){
        return  _center.subtract(_other).normalize();
    }

    /**
     * returns _center
     * @return Point3D
     */
    public Point3D get_center() {
        return _center;
    }



    /**
     * function that calculate intersection points between ray and sphere
     * @param ray
     * @param maximumdistance
     * @return
     */
    @Override
    public LinkedList<Geopoint> findIntersections(Ray ray,double maximumdistance) {
        //LinkedList<Geopoint> arl=null;
        Point3D p1;
        Point3D p2;
        //Vector u = ray.get_p0().subtract(this.get_center());
        Vector u;
        try{ u=ray.get_p0().subtract(this.get_center());}catch (IllegalArgumentException e)
        {LinkedList<Geopoint>link=new LinkedList<Geopoint>();
           link.add (new Geopoint(this,(ray.getPoint3D(this._radius))));
            return link;}
        double tm = alignZero(ray.get_dir().dotProduct(u));
        double d = sqrt(u.lengthSquared() - tm * tm);
        if(d>_radius)return null;
        double th = sqrt(alignZero(this.get_radius() * this.get_radius() - d * d));
        double thSquared=alignZero(th*th);
        if(thSquared<=0)return null;
        //double th=alignZero(Math.sqrt(thSquared));
        //if(th==0)return null;

        double t1 = alignZero(tm + th);
        double t2 = alignZero(tm - th);
        double distancetone=alignZero(maximumdistance-t1);
        double distancettwo=alignZero(maximumdistance-t2);
       // if ((t1 >0)&&(t2>0)&&(distancetone<=0)&&(distancettwo<=0)) {arl=new LinkedList<>();
          //  p1 = (ray.get_p0().add(ray.get_dir().scale(t1))).get_head();arl.add(new Geopoint(this,p1));
            // p2 = (ray.get_p0().add(ray.get_dir().scale(t2))).get_head();
           //  arl.add(new Geopoint(this,p2));
        //}
if(t1<=0&&t2<=0)
{return null;}
if((t1>0)&&(t2>0)) {
    if(distancetone>0&&distancettwo>0) {
    LinkedList<Geopoint> listy = new LinkedList<Geopoint>();
    listy.add(new Geopoint(this, ray.getPoint3D(t1)));
    listy.add(new Geopoint(this, ray.getPoint3D(t2)));
    return listy;
    }
}
else if(distancetone>0){ LinkedList<Geopoint> listy = new LinkedList<Geopoint>();
            listy.add(new Geopoint(this,ray.getPoint3D(t1)));
            }
else if(distancettwo>0)
{LinkedList<Geopoint> listy = new LinkedList<Geopoint>();
listy.add(new Geopoint(this,ray.getPoint3D(t2)));}
        if(t1>0){LinkedList<Geopoint> listy = new LinkedList<Geopoint>();
            listy.add(new Geopoint(this,ray.getPoint3D(t1)));
            return listy;
        }
        else if(t2>0){
            LinkedList<Geopoint> listy = new LinkedList<Geopoint>();
            listy.add(new Geopoint(this,ray.getPoint3D(t2)));
            return listy;
        }
        return null;
    }}