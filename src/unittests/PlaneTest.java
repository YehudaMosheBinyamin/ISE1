package unittests;
import geometries.Plane;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static java.lang.Math.sqrt;
import static org.junit.Assert.*;

public class PlaneTest {
    @Test
    public void getNormal() {
        Plane newP=new Plane(new Point3D(0.0,1.0,0.0),
                new Point3D(0.0,0.0,1.0),
                new Point3D(1.0,1.0,1.0));
        double squareroot=1d/sqrt(3);
        assertEquals("wrong normal",new Vector(squareroot,-squareroot,-squareroot),newP.getNormal());
    }
    @Test
    public void findIntersections(){
        //Ep Tests=================================//
        //Test one:ray cuts the plane once
       Plane pv=new Plane(new Point3D(1,0,0),
               new Point3D(2,0,0),
               new Point3D(0,0,1));
       assertEquals("wrong number of points",1,pv.findIntersections(new Ray(new Point3D(-8.23, -3.1, 0),new Vector(12.97,3.1,10))).size());
       //EP2 -does not intersect the plane
        assertEquals("calculated wrong number of points",0,pv.findIntersections(new Ray(new Point3D(-9.31,-3.25,0),
                new Vector(2.34,-8.17,10))).size());
        //BVA tests==========================================================================================//
        //test one ray parallel and  included in plane
        try{
            pv.findIntersections(new Ray(new Point3D(0.64,
                    -11.63,
                    0),new Vector(7.56,0,10)));
        }catch(IllegalArgumentException a){System.out.println("Did not throw exception for limitless amount of intersections,ray parallel and not included");}
        //test two,parallel  not included in plane
        try{pv.findIntersections(new Ray(new Point3D(-9.31,-3.25,0),
                new Vector(-2.64,0,10)));}
        catch(IllegalArgumentException exp){System.out.println("didn't throw exception,parallel not included");}


      //test three 90 degrees to plane,before plane
        try{
            pv.findIntersections(new Ray(new Point3D(-10.99,-10.51,0),new Vector(0,21.03,0)));
        }catch(IllegalArgumentException e){System.out.println("didnt throw exception for 90 degrees to plane,before plane");}
        /////test four 90 degrees in plane
        try{
            pv.findIntersections(new Ray(new Point3D(0,0,1),new Vector(0,-1,0)));
        }catch(IllegalArgumentException exp){System.out.println("Did not throw exception for 90 degrees to plane in plane");}
        //////test five after plane
        try{pv.findIntersections(new Ray(new Point3D(2.29,-3.9,0),new Vector(0,-11.42,0)));}
        catch (IllegalArgumentException exp){System.out.println("didn't throw exception for 90 degrees third case didnt throw exception,after plane");}

        ////////test six not orthogonal or parallel but begins in plane
        try{
            pv.findIntersections(new Ray(new Point3D(-1,0,0),new Vector(6.82,1.36,0)));
        }catch(IllegalArgumentException exp){System.out.println("didn't throw exception for ray that begins in plane");}
////test seven starts as same point as reference point of plane(Q)
        try{
            pv.findIntersections(new Ray(new Point3D(1,0,0),new Vector(4.82,1.36,0)));
        }catch(IllegalArgumentException exp){System.out.println("didn't throw exception for plane the starts at reference point");}





    }
}
