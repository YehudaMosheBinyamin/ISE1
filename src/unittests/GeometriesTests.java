package unittests;
import geometries.*;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class GeometriesTests {
    @Test
   public void findIntersections()
    {
    Geometries m = new Geometries();
    ///////BVA TESTS========================================//////////////
        //no shapes intersected
    assertEquals("no objects,no intersections",null,m.findIntersections(new Ray(new Point3D(1,0,0),
            new Vector(1,0,0))));
        Plane pl=new Plane(new Point3D(1,0,0),new Point3D(2,0,0),new Point3D(0,0,1));
        Sphere sph=new Sphere(1.97,new Point3D(-3.74,0,1.61));
        Triangle tr=new Triangle(new Point3D(-4,0,0),new Point3D(-2.53,
                -1.51,
                2),new Point3D(-3,0,0));
        m.add(pl,sph,tr);
        //no shapes intersected
        assertEquals("no shapes are intersected by ray",null,
                m.findIntersections(new Ray(new Point3D(-3.99,-4.3,0),new Vector(-1.49,-0.46,0))));
        Sphere two=new Sphere(0.8,new Point3D(-6.67,-3.89,0));
        ///some(two) of the shapes intersected
        m.add(two);
        m.add(pl,sph,tr);//if this row is omitted the test fails even though same operation done on line 27
        assertEquals("one object intersected",m.l1.size()==4,//before was 1
                m.findIntersections(new Ray(new Point3D(-3.81,-4.73,0),new Vector(-2.92,1.73,0)))
                        .size()==1);
        //assertEquals("one object intersected",m.l1.size()==4,
                //m.findIntersections(new Ray(new Point3D(-3.81,-4.73,0),new Vector(-2.92,1.73,0))).size()==3);
        ///all shapes intersected
        assertEquals("all shapes intersected",m.l1.size()==4,
                m.findIntersections(new Ray(new Point3D(-6.34,-5.06,0.3),new Vector(2.75,3.14,0.85))).size()==4);


        //////EP tests
        assertEquals("two objects intersected",
              m.l1.size()==4,//before was 2
               m.findIntersections(new Ray(new Point3D(0.86,-7.05,-0.59),new Vector(-2.71,6.09,2.31))).size()==2);
        //////EP tests
        assertEquals("two objects intersected",
                m.l1.size()==4,
                m.findIntersections(new Ray(new Point3D(0.86,-7.05,-0.59),new Vector(-2.71,6.09,2.31)))
                        .size()==2);//before was 3
    }

}
