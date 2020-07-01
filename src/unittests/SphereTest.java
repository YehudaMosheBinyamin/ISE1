package unittests;
import geometries.Intersectable;
import geometries.Sphere;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.sqrt;
import static org.junit.Assert.*;

public class SphereTest {

    @Test
    public void getNormal() {
        //Equivalence partitioning tests
        //There is a single simple test here
        Sphere sph=new Sphere(1.0,new Point3D(0,0,1));
        assertEquals("Bad normal to sphere",new Vector(0,0,1),sph.getNormal(new Point3D(0,0,2)));

    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections()
    {
        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertEquals("Ray's line out of sphere", null,
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));
        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Intersectable.Geopoint> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).getPoint().get_x().get() > result.get(1).getPoint().get_x().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(p1, p2).size(), result.size());
        assertEquals("Ray crosses sphere", (List.of(p1, p2)).size(), result.size());
        //TC03: Ray starts inside the sphere (1 point)
        Sphere sp = new Sphere(1.41, new Point3D(1, 0, 0));
       List<Intersectable.Geopoint> result03=sphere.findIntersections(new Ray(new Point3D(0.5,0.5,0),new Vector(3,1,0)));
      List<Point3D> point=new LinkedList<Point3D>();
      for(Intersectable.Geopoint geo:result03)
      {point.add(geo.point);}
      assertEquals("Ray crosses sphere",List.of(p2).size(),point.size());
        //TC04: Ray starts after the sphere (0 points)
        Sphere s = new Sphere(0.54, new Point3D(-2, 2, 0));
        assertEquals("Ray's line is outside of sphere", null,
                sp.findIntersections(new Ray(new Point3D(-3.59,
                4.38,
                0), new Vector(-1.83, 2.25, 0))));


        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
            // TC11: Ray starts at sphere and goes inside (1 points)
            assertEquals("Ray from sphere inside",List.of(new Point3D(2, 0, 0)).size(),
                    sphere.findIntersections(new Ray(new Point3D(1, -1, 0), new Vector(1, 1, 0))).size());
            // TC12: Ray starts at sphere and goes outside (0 points)
            assertNull("Ray from sphere outside",sphere.findIntersections(new Ray(new Point3D(2,
                            0,
                            0), new Vector(1, 1, 0)))
                    );

            // **** Group: Ray's line goes through the center
            // TC13: Ray starts before the sphere (2 points)
            result = sphere.findIntersections(new Ray(new Point3D(1, -2, 0), new Vector(0, 1, 0)));

            assertEquals("Wrong number of points tc13",2, result.size());
            if (result.get(0).getPoint().get_y().get() > result.get(1).getPoint().get_y().get()) {
                result = List.of(result.get(1), result.get(0));
            }
        assertEquals("Line through O, ray crosses sphere",
                (List.of(new Point3D(1, -1, 0), new Point3D(1, 1, 0)).size()),
                result.size());

            // TC14: Ray starts at sphere and goes inside (1 points)
point.clear();
           LinkedList<Intersectable.Geopoint> result14=sphere.findIntersections(new Ray(new Point3D(1, -1, 0),
                   new Vector(0, 1, 0)));
            for(Intersectable.Geopoint geo:result14){
             point.add(geo.point);
           }

          assertEquals("Line through O,ray from and crosses sphere",List.of(new Point3D(1,1,0)),point);
            // TC15: Ray starts inside (1 points)
            assertEquals("Line through O, ray from inside sphere",List.of(new Point3D(1, 1, 0)).size(),
                    sphere.findIntersections(new Ray(new Point3D(1, 0.5, 0), new Vector(0, 1, 0))).size()
                    );

            // TC16: Ray starts at the center (1 points)
            assertEquals(
                    "Line through O, ray from O", List.of(new Point3D(1, 1, 0)).size(),
                    sphere.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0))).size()
                    );

            // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull("Line through O, ray from sphere outside",
                sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, 1, 0)))

                );

            // TC18: Ray starts after sphere (0 points)
        assertNull("Line through O, ray outside sphere",
                sphere.findIntersections(new Ray(new Point3D(1, 2, 0), new Vector(0, 1, 0)))

                );

            // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
            // TC19: Ray starts before the tangent point
        assertNull("Tangent line, ray before sphere",
                sphere.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(1, 0, 0)))

                );

            // TC20: Ray starts at the tangent point
        assertNull("Tangent line, ray at sphere",
                sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(1, 0, 0)))

                );

            // TC21: Ray starts after the tangent point
            assertNull("Tangent line, ray after sphere", sphere.findIntersections(new Ray(new Point3D(2,
                            1,
                            0), new Vector(1, 0, 0)))
                    );

            // **** Group: Special cases
            // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's
            // center line
        assertNull("Ray orthogonal to ray head -> O line",
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(0, 0, 1)))

                );

        }
    }