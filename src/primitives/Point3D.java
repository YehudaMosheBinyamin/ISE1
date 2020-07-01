package primitives;

import java.util.Objects;

import static java.lang.Math.sqrt;

/**
 * Point3D class for representing a point in 3D environment
 */
public class Point3D {
    Coordinate _x;
    Coordinate _y;
    Coordinate _z;
/*******************Constructor*****************/
    /**
     *
     * @param _x coordinate on the x axis
     * @param _y coordinate on the y axis
     * @param _z coordinate on the z axis
     */
    public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
        this._x = _x;
        this._y = _y;
        this._z = _z;
    }

    public Point3D(double _x,double _y,double _z){
        this(new Coordinate(_x),new Coordinate(_y),new Coordinate(_z));
    }
    public Point3D(Vector other){this(new Coordinate(other._head._x ),
            new Coordinate(other._head._y),
            new Coordinate(other._head._z));}

    public Point3D(Point3D other){this._x=other._x;this._y=other._y;this._z=other._z;}

    public static Point3D ZERO=new Point3D(new Coordinate(0.0),new Coordinate(0.0),new Coordinate(0.0));


    /*************getters ***********/


    /**
     *
     * @return NEW COORDINATE WITH x value
     */
    public Coordinate get_x() {
        return new Coordinate(_x);
    }

    /**
     * returns y
     * @return Coordinate
     */
    public Coordinate get_y() {
        return new Coordinate(_y);
    }

    /**
     * returns z
     * @return Coordinate
     */
    public Coordinate get_z() {
        return new Coordinate(_z);
    }
    /*********toString() and equals()******/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals( point3D._x) &&
                _y.equals(point3D._y) &&
                _z.equals(point3D._z);
    }

    @Override
    public String toString() {
        return "(" +
                _x +","+
                _y +","+
                _z +
                ")";
    }

    /**
     * subtraction between point and vector
     * @param vector
     * @return Vector
     */
    public Vector subtract (Point3D vector){
        Vector newV=Vector.ZERO;
        newV=new Vector(vector.get_x().get()-this.get_x().get(),
                vector.get_y().get()-this.get_y().get(),
                vector.get_z().get()-this.get_z().get());
        return newV;
    }
    public Vector subtractPoint(Point3D point){
return new Vector(new Point3D(this._x._coord-point._x._coord,this._y._coord-point._y._coord,this._z._coord-point._z._coord));
    }

    /**
     * addition between two vectors
     * @param vector
     * @return Vector
     */
    public Vector add(Vector  vector)
    {Vector newV=Vector.ZERO;
        newV= new Vector(this.get_x().get()+vector.get_head().get_x().get(),
            this.get_y().get()+vector.get_head().get_y().get(),
            this.get_z().get()+vector.get_head().get_z().get());
        //throw new NullPointerException("null");}catch (NullPointerException exp){}
    return newV;
    }
public Point3D addForPoint(Vector vector)
{ return new Point3D(this._x._coord+vector._head._x._coord,
        this._y._coord+vector._head._y._coord,this._z._coord+vector._head._z._coord);
}
    public Point3D subtractForPoint(Vector vector)
    { return new Point3D(this.get_x().get()-vector.get_head().get_x().get(),
            this.get_y().get()-vector.get_head().get_y().get(),this._z.get()-vector.get_head().get_z()
            .get());
    }

    /**
     * returns distance squared between point and vector
     * @param vector1
     * @return double
     */
    public double distanceSquared(Point3D vector1)
    {return this.get_x().get()-vector1.get_x().get()*this.get_x().get()-vector1.get_x().get()
            +this.get_y().get()-vector1.get_y().get()*this.get_y().get()-vector1.get_y().get()
            +this.get_z().get()-vector1.get_z().get()*this.get_z().get()-vector1.get_z().get();


        /**
         * calculates distance
         * @param anyvector
         * @return double
         */
    }
    public  double distance(Point3D anyVector)
    {
        double answer = sqrt(distanceSquared(anyVector));
        return answer;

    }
}

