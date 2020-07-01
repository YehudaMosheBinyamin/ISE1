package geometries;
import primitives.Point3D;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * class for  traversal of a list of geometries,even if they are of different types
 */
public class Geometries implements Intersectable {
    //public LinkedList<Intersectable> l1 = new LinkedList<Intersectable>();
    public LinkedList<Intersectable> l1;
/********constructors******************/
    /**
     * constructor birit mechabel for case of which empty occurence of Geometries is made
     */
    public Geometries() {
        this.l1 = new LinkedList<Intersectable>();
    }

    /**
     * constructor that receives a list of geometries and adds them to list of geometries
     * @param geometries
     */
    public Geometries(Intersectable... geometries)
    {
       // this.l1 = l1;
        this.add(geometries);
    }

    /**
     * adds one or more geometry to LinkedList of geometries in composite
     * @param geometries
     */
    public void add(Intersectable... geometries){
        //l1.addAll(Arrays.asList(geometries));
        for(Intersectable geom:geometries){
        l1.push(geom);
       }

    }

    /**
     * return  list of geometries in list which have intersection,with their intersection point and type of geometry
     * @param ray
     * @param maxdistance
     * @return
     */
    @Override
    public LinkedList<Geopoint> findIntersections(Ray ray,double maxdistance) {
        LinkedList<Geopoint> intersectionPoints = null;
        for (Intersectable geom : l1)
        {
            List<Geopoint> geometryIntersections = geom.findIntersections(ray,maxdistance);
            //try {
            if(geometryIntersections!=null) {  //{for (Geopoint intersection : geometryIntersections) {
                //      intersectionPoints.add(intersection);}}}
                // }
                if (intersectionPoints == null)
                {intersectionPoints = new LinkedList<Geopoint>();}
                    intersectionPoints.addAll(geometryIntersections);

            }
        }
                //throw new NullPointerException("adding from null list");
            //} catch (NullPointerException exp) {}}
            return intersectionPoints;
        }}


