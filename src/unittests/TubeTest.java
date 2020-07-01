package unittests;

import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static java.lang.Math.sqrt;
import static org.junit.Assert.*;

public class TubeTest
{

    @Test
    public void getNormal()
    {
       Tube tOne= new Tube(1.0,new Ray(new Point3D(0,0,1),new Vector(0,1,0)));
       //double sqrt4=sqrt(2);
        assertEquals("Bad normal to tube",new Vector(1/sqrt(2),0.0,-1/sqrt(2)),tOne.getNormal(new Point3D(1,0,0)));
        //assertEquals("Bad normal to tube",new Vector(-1/sqrt(2),0.0,0.8164965809277261),tOne.getNormal(new Point3D(1,0,0)));
    }
}