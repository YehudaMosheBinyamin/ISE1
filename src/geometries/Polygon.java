package geometries;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import primitives.*;
import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected List<Point3D> _vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected Plane _plane;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex></li>
     *                                  </ul>
     */
    public Polygon (Point3D...vertices){
        this(Color.BLACK,new Material(0,0,0),vertices);
    }

    public Polygon(Color color,Material material,Point3D... vertices) {
        super(color,material);
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        if(vertices[vertices.length-1]==vertices[0])
            throw new IllegalArgumentException("First point same as last,forbidden");
        _vertices = List.of(vertices);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon

        _plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3) return; // no need for more tests for a Triangle

        Vector n = _plane.getNormal();

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        //Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
       // Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);
        Vector edge1 = vertices[vertices.length - 2].subtract(vertices[vertices.length - 1]);
        Vector edge2 = vertices[vertices.length-1].subtract(vertices[0]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            //if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
              if(!isZero((vertices[0].subtract(vertices[i]).dotProduct(n))))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            //edge2=vertices[i].subtract(vertices[i-1]);
            edge2=vertices[i-1].subtract(vertices[i]);

            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }


    @Override
    public Vector getNormal(Point3D point) {
        return _plane.getNormal();
    }

    public List<Point3D> get_vertices() {
        return _vertices;
    }

    /**
     * returns intersections of ray with polygon
     * @param ray
     * @param maximumdistance
     * @return
     */
    public LinkedList<Geopoint> findIntersections(Ray ray,double maximumdistance)
    {//LinkedList<Geopoint> geoList=new LinkedList<Geopoint>();
        List<Geopoint>planelist=_plane.findIntersections(ray);
    if(planelist==null){return null;}
     Point3D pointRay=ray.get_p0();
     Vector direction=ray.get_dir();
      //Vector one=_vertices.get(1).subtract(pointRay);
      //Vector two=_vertices.get(0).subtract(pointRay);
        Vector one=pointRay.subtract(_vertices.get(1));
        Vector two=pointRay.subtract(_vertices.get(0));

        double sign=(direction.dotProduct(one.crossProduct(two).normalized()));
        boolean positive=sign>0;
      if(isZero(sign))
      {
        return null;
      }
      //int i=_vertices.size();
      //while(i>0){
          //one=two;
          //two=_vertices.get(i).subtract(pointRay);
          //two=pointRay.subtract(_vertices.get(i));
          //if(isZero(alignZero(direction.dotProduct(one.crossProduct(two).normalized()<0.0)))
                  //{return geoList;}
             // return null;
          //--i;
      //}
        for(int i=_vertices.size();i>0;--i)
        {one=two;
        two=pointRay.subtract(_vertices.get(i));
        sign=alignZero(direction.dotProduct(one.crossProduct(two).normalized()));
        if(isZero(sign)){return null;}
        if(positive!=(sign>0)){return null;}

        }
        LinkedList<Geopoint> geoList=new LinkedList<Geopoint>();
      for(Geopoint geo:planelist){geoList.add(new Geopoint(this,geo.getPoint()));}
          return geoList;
      }

}



