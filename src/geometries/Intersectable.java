package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * interface for enabling polymorphism-looking at geometries as ones which are intersectable,without needing to know their inner qualities
 */
public interface Intersectable
{
    public default LinkedList<Geopoint>findIntersections(Ray ray){
        return findIntersections(ray,Double.POSITIVE_INFINITY);
    }
    public LinkedList<Geopoint> findIntersections(Ray ray,double positiveinfinity);

    /**
     * Geopoint class for implementation of returning both the intersection point and the type of intersected geometry
     */
    public static class Geopoint{
        public Geometry geometry;
        public Point3D point;

        public Geometry getGeometry() {
            return geometry;
        }

        public Point3D getPoint() {
            return point;
        }

        /**
         *
         * @param geometry
         * @param point
         */
        public Geopoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Geopoint geopoint = (Geopoint) o;
            return Objects.equals(geometry, geopoint.geometry) &&
                    Objects.equals(point, geopoint.point);
        }

    }
}
