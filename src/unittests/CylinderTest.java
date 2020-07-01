package unittests;

import geometries.Cylinder;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * checks whether get normal of cylinder works properly
 */
public class CylinderTest {
    @Test
    public void getNormal()
    {///*****Equivalence Partition*****///////
        Cylinder one=new Cylinder(1.0);
         //assertEquals("wrong normal",new Vector(-105d/15303,132d/15303,159d/15303),
                 //one.getNormal(new Point3D(0,0,1)));
        assertEquals("wrong normal",new Vector(-0.39542938690089663,-0.6204527396677052,-0.6772547510457346),
                one.getNormal(new Point3D(0,0,1)));

    }
}