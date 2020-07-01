package unittests;

import org.junit.Test;
import primitives.Vector;

import static org.junit.Assert.*;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class VectorTest {

    @Test
    public void dotProduct() {

        // ============ Equivalence Partitions Tests ==============
        //checks whether the method returns correct dot product of two vectors
        Vector one=new Vector(1,2,3);
        Vector two=new Vector(4,5,6);
       assertEquals("returns wrong dot product",32,one.dotProduct(two),0.00000001);
        // =============== Boundary Values Tests ==================
        //checks whether a vector zero can be created,without an exception,a forbidden scenario,using orthogonal vectors
        try{
            Vector three=new Vector(0,-3,2);
            double dotProduct=three.dotProduct(one);
            if(dotProduct==0){
            throw new IllegalArgumentException("did not throw zero vector exception");}
        }catch(IllegalArgumentException e){}



    }

    @Test
    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */

    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);

        // Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v3)));

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-productof co-lined vectors
        try {
            v1.crossProduct(v2);
            //assertTrue("crossProduct() for parallel vectors does not throw an exception",false);
            if(v1.crossProduct(v2)==new Vector(0,0,0))
                throw new IllegalArgumentException("it didn't do exception for zero product of co lined vectors");
        } catch (Exception e) {}
    }


    @Test
    public void scale() {
        //============ Equivalence Partitions Tests ==============
        //checks if scale() calculates the scalar times vector correctly
        Vector one=new Vector(0,0,5);
        double scalar=5.0;
        assertEquals("wrong answer for scalar times a vector",new Vector(0,0,25),one.scale(scalar));
        // =============== Boundary Values Tests ==================
        //checks whether scalar times vector can be zero vector,without throwing an exception
        double scalar2=0.0;
        try{
            one.scale(scalar2);
            fail("method of scale created a zero vector");
        }catch(Exception e){}

    }

    @Test
    public void length() {
        //============ Equivalence Partitions Tests ==============
        //check if length of vector is calculated correctly
        Vector v1=new Vector(0,3,4);
        assertEquals("wrong length of vector calculated",5,v1.length(),0.00000001);
    }

    @Test
    public void normalize() {
        //============ Equivalence Partitions Tests ==============
        //checks if new vector normal of vector is proper
        Vector one = new Vector(0, 0, 2);


           assertEquals("method calculated wrong vector",new Vector(0.0,0.0,1.0),one.normalize());
            //checks if new Vector is of length one
            assertEquals("vector is not properly normalized", 1d, one.normalize().length(),0.0000000001);
        }

        @Test
        public void add () {
            Vector v1 = new Vector(0, 1, 2);
            Vector vr = new Vector(0, -1, -2);
            // ============ Equivalence Partitions Tests ==============
            Vector v2 = new Vector(1, -1, -2);
            Vector v3 = v1.add(v2);
            assertEquals("wrong addition", new Vector(1, 0, 0), v3);
            // =============== Boundary Values Tests ==================
            //check if operation will create vector zero and not throw an exception,as needed
            try {
                v1.add(vr);
                throw new IllegalArgumentException("add() operation didn't throw an exception!");

            } catch (Exception e) {
            }

        }

        @Test
        public void subtract () {
            Vector v1 = new Vector(0, 1, 2);
            Vector vr = new Vector(0, -1, -2);
            // ============ Equivalence Partitions Tests ==============
            //checks if subtract method returns the correct subtraction
            Vector v2 = new Vector(1, -1, -2);
            Vector v3 = v1.subtract(v2);
            assertEquals("wrong subtraction", new Vector(1, -2, -4), v3);
            // =============== Boundary Values Tests ==================
            //check if operation will create vector zero and not throw an exception,as needed
            try {
                v1.add(vr);
                throw new IllegalArgumentException("subtract() operation didn't throw an exception!");

            } catch (Exception e) {
            }
        }
    }