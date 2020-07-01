package elements;

import primitives.Color;

/**
 * class to represent AmbientLight
 */
public class AmbientLight extends Light {
    /**
     * constructor for "mekadmei hanchata" kA and iA
     * @param iA
     * @param kA
     */
    public AmbientLight(Color iA,double kA)
    {
       super(iA.scale(kA));
    }

}
