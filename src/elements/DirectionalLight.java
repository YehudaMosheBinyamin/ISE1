package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{
Vector _direction;
    public DirectionalLight(Color _intensity,Vector _direction) {
        super(_intensity);
        this._direction=_direction;
    }

    @Override
    public Color getIntensity(Point3D point) {
        return _intensity;
    }

    @Override
    public Vector getL(Point3D point) {
        return _direction;
    }

    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }
}
