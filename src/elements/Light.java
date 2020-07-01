package elements;

import primitives.Color;

/**
 *class to represent the abstract idea of Light for project
 */
public abstract class Light {
   protected Color _intensity;

    /**
     * constructor that receives intensity for light
     * @param _intensity
     */
    public Light(Color _intensity) {
        this._intensity = _intensity;
    }

    public Color get_intensity() {
        return _intensity;
    }

}
