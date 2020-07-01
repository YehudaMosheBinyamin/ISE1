package geometries;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface to force implementation of getNormal
 */
public abstract class Geometry implements Intersectable{
    protected Color _emmission;
    protected Material _material;

    //=======================constructors=============================//
    public Geometry(Color emmi){
        _emmission=emmi;
    }
    public Geometry()
    {
        this(Color.BLACK,new Material(0,0,0));

    }
    public Geometry(Color emmission, Material material) {
        //this._emmission = _emmission;
        //this._material = _material;
        this._emmission = emmission;
        this._material = material;
    }
//*******************getters*************************************//
    /**
     *
     * @return Material
     */
    public Material get_material() {
        return _material;
    }

    /**
     * returns normal
     * @param point
     * @return
     */
    public abstract Vector getNormal(Point3D point);

    /**
     * returns emmission
     * @return Color
     */
    public Color get_emmission() {
        return _emmission;
    }

}

