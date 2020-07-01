package unittests;

import elements.Camera;
import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class IntegrationTests {
    @Test
    public void CameraSphereIntersections(){
        //case one
       Sphere sp=new Sphere(1,new Point3D(0,0,3));
        Camera cam1=new Camera(new Point3D(0,0,-0.5),new Vector(0,0,1),new Vector(0,-1,0));
        Camera cam2=new Camera(new Point3D(0,0,-0.5),new Vector(0,0,1),new Vector(0,-1,0));
       Ray r1=cam1.constructRayThroughPixel(3,3,1,1,1,3,3);
        assertEquals("two intersections required",2,sp.findIntersections(r1).size());
        //case two
        Sphere sph=new Sphere(2.5,new Point3D(0,0,2.5));
        //Camera camer=new Camera(new Point3D(0,0,-0.5),new Vector(0,0.5,-0.5),new Vector(0,0,0));
        //Ray r2=cam2.constructRayThroughPixel(3,3,1,1,1,3,3);
       // assertEquals("18 intersections expected",18,sph.findIntersections(r2).size());
     int Nx=3;
     int Ny=3;
     int counter=0;
     List<Intersectable.Geopoint> results;
     for(int i=0;i<Nx;++i)
      for(int j=0;j<Ny;++j)
      {results=sph.findIntersections(cam2.constructRayThroughPixel(Nx,Ny,j,i,1,3,3));
      if(results!=null)
      {
       counter+=results.size();
      }
      }

 assertEquals("18 intersections expected",18,counter);

        //zero intersection points
        Sphere s=new Sphere(1,new Point3D(0,0,-1));
        Camera came=new Camera(new Point3D(0,-4,0),new Vector(0,0,1),new Vector(-3,-2,0));
        Ray ray=came.constructRayThroughPixel(3,3,1,1,1,3,3);
        assertNull("no intersections expected",s.findIntersections(ray));
        //nine intersection points,plane straight
        //plane
        Plane pl=new Plane(new Point3D(0,0,0),new Point3D(0,1,1),new Point3D(0,0,1));
        Camera camera2=new Camera(new Point3D(9.68,0,0),new Vector(-9.96,-2.08,0),new Vector(0,0,1));
       // Ray ray2=camera2.constructRayThroughPixel(3,3,1,1,1,3,3);
     Nx=3;
     Ny=3;
     counter=0;
     List<Intersectable.Geopoint> intersectionsList;
     //Camera camera=new Camera(new Point3D(0,0,-0.5),new Vector(0,0.5,-0.5),new Vector(0,0,0));
     for(int i=0;i<Nx;++i)
      for(int j=0;j<Ny;++j)
      {intersectionsList=pl.findIntersections(camera2.constructRayThroughPixel(Nx,Ny,j,i,1,3,3));
       if(intersectionsList!=null)
       {
        counter+=intersectionsList.size();
       }
      }
        assertEquals("there needs to be nine intersections",9,counter);

    }
}
