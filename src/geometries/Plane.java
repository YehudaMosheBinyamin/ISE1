package geometries;
import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Plane extends Geometry {
    Point3D _p;
    primitives.Vector _normal;
    Material _material;

    /**
     * constructor
     *
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        this(Color.BLACK, p1, p2, p3, new Material(0, 0, 0));
    }

    public Plane(Color emmi, Point3D p1, Point3D p2, Point3D p3, Material material) {
        super(emmi);
        _p = new Point3D(p1);
        Vector U = new Vector(p1, p2);
        Vector V = new Vector(p1, p3);
        Vector N = U.crossProduct(V);
        //Vector N=new Vector(-1,-1,1);
        N.normalize();
        _normal = N.scale(-1);
        _material = material;

    }

    public Plane(Color emmi, Point3D _p, Vector _normal, Material _material) {
        super(emmi);
        this._p = _p;
        this._normal = _normal;
        this._material = _material;
    }

    public Plane(Point3D _p, Vector _normal, Material _material) {
        this(Color.BLACK, _p, _normal, _material);
    }

    /**
     * constructor
     *
     * @param p1
     * @param _normal
     */
    public Plane(Point3D p1, Vector _normal) {
        this(Color.BLACK, p1, _normal, new Material(0, 0, 0));

    }

    /**
     * return vector normal
     *
     * @param p
     * @return
     */
    @Override
    public Vector getNormal(Point3D p) {
        return _normal;

    }

    //in polygon it sends without parameters so this function is also needed
    public Vector getNormal() {
        return getNormal(null);

    }

    /**
     * returns intersections of ray with plane
     *
     * @param ray
     * @return ArrayList
     */
    @Override
    public LinkedList<Geopoint> findIntersections(Ray ray, double positiveinfinity)
    {Vector p0Q;
    try{p0Q=ray.get_p0().subtract(_p);}catch(IllegalArgumentException exp){return null;}
        LinkedList<Geopoint> listgeop = new LinkedList<Geopoint>();
        //double t = alignZero(this.getNormal().dotProduct
                //
        // (ray.get_p0().subtract(this.getNormal().get_head())) / getNormal().dotProduct(ray.get_dir()));
        double nv=_normal.dotProduct(ray.get_dir());
        if(isZero(nv)){return null;}
        double t=alignZero(_normal.dotProduct(ray.get_dir()));

        Point3D p = (ray.get_p0().add((ray.get_dir().scale(t)))).get_head();
        if (t <=0 ||positiveinfinity-t<=0){return null;}

            else {
                //LinkedList<Geopoint> listgeop = new LinkedList<Geopoint>();
                listgeop.addFirst(new Geopoint(this, ray.getPoint3D(t)));

            }
            //return  (LinkedList)List.of(new Geopoint(this,p));
return listgeop;
    }}
