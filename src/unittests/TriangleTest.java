package unittests;
import geometries.Plane;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static java.lang.Math.sqrt;
import static org.junit.Assert.*;
public class TriangleTest {
    @Test
    public void getNormal()
    { Triangle tr=new Triangle(new Point3D(0,1,0),new Point3D(0,0,1),new Point3D(1,1,1));
        assertEquals("is wrong normal",new Vector(1/sqrt(3),1/sqrt(3),-1/sqrt(3)),tr.getNormal(new Point3D(1,2,3)));
    }
    @Test
    public void findIntersections()///(Ray ray)
    {    Plane pv=new Plane(new Point3D(1,0,0),new Point3D(2,0,0),new Point3D(0,0,1));
        //////EP Tests========================///////////////////////
        //Test one-hits side of triangle

        assertEquals("wrong amount of intersections with plane where triangle is",
                1,
                pv.findIntersections(new Ray(new Point3D(-1.78,1.4,0),new Vector(-0.06,-1.4,0.55))).size());
        Triangle tr=new Triangle(new Point3D(1,0,0.2),new Point3D(0.57,0,0.7),new Point3D(-0.35,0,0.2));
        assertNull("wrong amount of intersections when goes past side of triangle",

        tr.findIntersections(new Ray(new Point3D(-1.78,1.4,0),new Vector(-0.06,-1.4,0.55))));
        ////Test two-hits middle of triangle
        assertEquals("wrong amount of intersections with planefor when ray hits middle of triangle",
                1,
                pv.findIntersections(new Ray(new Point3D(-1.78,1.4,0),new Vector(2.31,-1.4,0.42))).size());
        assertEquals("wrong amount of intersections for when ray hits middle of triangle",
                1,
                tr.findIntersections(new Ray(new Point3D(-1.78,1.4,0),new Vector(2.31,-1.4,0.42))).size());
        //////Test three-hits just below opposite triangle
        assertEquals("wrong amount of intersections for when ray hits below triangle with plane",
                1,pv.findIntersections(new Ray(new Point3D(-1.78,1.4,0),new Vector(1.72,-2.8,-2))).size());
        assertNull("wrong amount of intersections for when ray hits below triangle",
                tr.findIntersections(new Ray(new Point3D(-1.78,1.4,0),new Vector(1.72,-2.8,-2))));
        ////////BVA tests========================================////
        Triangle tri=new Triangle(new Point3D(-0.4,0,0.23),new Point3D(1,0,0.2),new Point3D(0.52,0,1.48));
        ////3 tests of ray starts  before plane
        //////Test one-hits middle of triangle

        assertEquals( "ray starts before plane bva middle triangle  ",1,
                pv.findIntersections(new Ray(new Point3D(1.76,-4.92,0),new Vector(-0.95,4.92,0.45))).size());
        assertNull( "ray starts before plane bva middle triangle  ",
                tri.findIntersections(new Ray(new Point3D(1.76,-4.92,0),new Vector(-0.95,4.92,0.45))));
        //test two-hits bottom of triangle

        assertEquals("ray starts before plane,bva bottom of triangle ",1,
                   pv.findIntersections(new Ray(new Point3D(-1.48,-3.89,0),new Vector(2.48,3.89,0.2))).size());
        assertNull("ray starts before plane,bva bottom of triangle ",
                tri.findIntersections(new Ray(new Point3D(-1.48,-3.89,0),new Vector(2.48,3.89,0.2))));
        //Test one-hits continuation of triangle
        //assertEquals( "ray before plane,hits continuation",1,
                //pv.findIntersections(new Ray(new Point3D(-3.39,-2.65,0),new Vector(3.69,2.65,2))).size());
        //assertEquals( "ray before plane,hits continuation",0,
               // tri.findIntersections(new Ray(new Point3D(-3.39,-2.65,0),new Vector(3.69,2.65,2))).size());
        assertEquals( "ray before plane,hits continuation",1,
                pv.findIntersections(new Ray(new Point3D(-3.39,-2.65,0),new Vector(3.71,2.65,2.23))).size());
        assertNull( "ray before plane,hits continuation",
                tri.findIntersections(new Ray(new Point3D(-3.39,-2.65,0),new Vector(3.71,2.65,2.23))));
        /////3 tests ray begins at plane
        //test one-ray hits middle of tzela

        assertEquals("hits middle of tzela",0,
                pv.findIntersections(new Ray(new Point3D(0.78,0,0.78),new Vector(-4.14,-4.25,-0.78))).size());
        assertNull("hits middle of tzela of triangle",
                tri.findIntersections(new Ray(new Point3D(0.78,0,0.78),new Vector(-4.14,-4.25,-0.78))));
        ///////test two ray hits bottom point
        assertEquals("ray hits point",0,pv.findIntersections(new Ray(new Point3D(1,0,0.2),
                new Vector(-2.5,-2.14,-0.2))).size());
        assertNull("ray hits point",tri.findIntersections(new Ray(new Point3D(1,0,0.2),
                new Vector(-2.5,-2.14,-0.2))));
        ///////test three-ray hits continuation of tsela
        assertEquals("ray hits continuation of tzela",0,
                pv.findIntersections(new Ray(new Point3D(0.3,0,2),new Vector(-4.8,-3.69,-2))).size());
        assertEquals("ray hits continuation of tzela",null,
                tri.findIntersections(new Ray(new Point3D(0.3,0,2),new Vector(-4.8,-3.69,-2))));
    }
}