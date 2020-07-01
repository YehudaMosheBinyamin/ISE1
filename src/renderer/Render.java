package renderer;
import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometry;
import geometries.Intersectable;
import primitives.Color;
import primitives.Point3D;
import primitives.*;
import primitives.Vector;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Math.sqrt;
import static primitives.Util.alignZero;
/**
 * class to render an image based on geometries ,light and color
 */
public class Render
{private static final int MAX_CALC_COLOR_LEVEL=10;
    private static final  double MIN_CALC_COLOR_K=0.0001;
    boolean adaptiveSupersampling;

    public boolean isAdaptiveSupersampling() {
        return adaptiveSupersampling;
    }

    public void setAdaptiveSupersampling(boolean adaptiveSupersampling) {
        this.adaptiveSupersampling = adaptiveSupersampling;
    }
    public void doAdaptiveSuperSampling(){setAdaptiveSupersampling(true);}

    Scene _scene;
    ImageWriter _imageWriter;
    //constant to ensure we don't include geometry this as a geometry blocking light from this
    private static final double DELTA=0.1;/**/

    /**
     * function that checks whether area is shaded  or not
     * @param l
     * @param n
     * @param gp
     * @return boolean
     */
    private boolean unshaded(Vector l, Vector n, Intersectable.Geopoint gp,LightSource ls) {

        Vector lightDirection=l.scale(-1);
        Vector deltaVector=n.scale(n.dotProduct(lightDirection)>0?DELTA:-DELTA);
        Point3D point=(gp.point.add(deltaVector)).get_head();
        //Ray lightRay=new Ray(gp.point,lightDirection,n);
        Ray lightRay=new Ray(gp.point,lightDirection);
        List<Intersectable.Geopoint>intersections=_scene.get_geometries().findIntersections(lightRay);
        if(intersections==null){return true;}
        int counter=0;
        double lightDistance=ls.getDistance(gp.point);
        for(Intersectable.Geopoint geop:intersections){
            if((alignZero(geop.point.distance(geop.point)-lightDistance)<=0)&&
                    (geop.geometry.get_material().get_kT()==0))
                return false;
        }
        //return intersections.isEmpty();
        return true;
    }

    /**
     * calculates how transparent the pixel is
     * @param l
     * @param n
     * @param gp
     * @param ls
     * @return
     */
    private double transparency(Vector l, Vector n, Intersectable.Geopoint gp,LightSource ls) {
        double ktr=1.0;
        Vector lightDirection=l.scale(-1);
        Vector deltaVector=n.scale(n.dotProduct(lightDirection)>0?DELTA:-DELTA);
        Point3D point=(gp.point.add(deltaVector)).get_head();
        Ray lightRay=new Ray(gp.point,lightDirection,n);
        List<Intersectable.Geopoint>intersections=_scene.get_geometries().findIntersections(lightRay);
        if(intersections==null){return ktr;}
        double lightDistance=ls.getDistance(gp.point);

        for(Intersectable.Geopoint geop:intersections){
            if((alignZero(geop.point.distance(geop.point)-lightDistance)<=0)){
                ktr*=geop.geometry.get_material().get_kT();
                if(ktr<MIN_CALC_COLOR_K){return 0.0;}}
        }
        return ktr;
    }

    /**
     * constructor
     * @param _imageWriter
     * @param _scene
     */
    public Render(ImageWriter _imageWriter, Scene _scene) {
       this(_imageWriter,_scene,false);
    }
    public Render(ImageWriter _imageWriter,Scene _scene,boolean adaptivesupersampling)
    {this._scene=_scene;
    this._imageWriter=_imageWriter;
    this.adaptiveSupersampling=adaptivesupersampling;
    }

    /**
     * function that finds the closest point to camera
     * @param intersectionPoints
     * @return
     */
    private Intersectable.Geopoint getClosestPoint(List<Intersectable.Geopoint> intersectionPoints)
    {if(intersectionPoints==null)return null;
        Intersectable.Geopoint closest = intersectionPoints.get(0);
        double mindist=Double.MAX_VALUE;
        double currentDistance;
        for (Intersectable.Geopoint geopoint : intersectionPoints) {
            currentDistance=_scene.get_camera().get_p0().distance(geopoint.getPoint());
            if ((_scene.get_camera().get_p0().distance(geopoint.getPoint())<
                    _scene.get_camera().get_p0().distance(closest.getPoint())))
                mindist=currentDistance;
            closest=geopoint;

        }
        return closest;
    }

    /**
     * to find closest intersection of ray with scene
     * @param ray
     * @return Intersectable.Geopoint
     */
    private Intersectable.Geopoint getClosestPoint(Ray ray) {
        if (ray == null) return null;
        //Intersectable.Geopoint closest=null;
        double closestDistance = POSITIVE_INFINITY;
        Point3D ray_p0 = ray.get_p0();
        List<Intersectable.Geopoint> intersectionList = _scene.get_geometries().findIntersections(ray);
        if (intersectionList == null) return null;

        Intersectable.Geopoint closest=null;
        for (Intersectable.Geopoint geopoint : intersectionList) {
            if (ray_p0.distance(geopoint.getPoint()) < closestDistance)
                closest = geopoint;
            closestDistance = ray_p0.distance(geopoint.getPoint());

        }
        return closest;
    }

    /**
     * enables calculation of color of pixel also if not given all parameters(so the original software will work after refactoring)
     * @param geopoint
     * @param inRay
     * @return
     */

    public Color calcColor(Intersectable.Geopoint geopoint, Ray inRay)
    {
        return calcColor(geopoint,inRay,MAX_CALC_COLOR_LEVEL,1.0).add(_scene.get_ambientLight().get_intensity());
    }


    //In the intersectionPoints -returns color of intersected point of geometry
  public Color calcColor(Intersectable.Geopoint geopoint,Ray ray,int level,double k)
    {       //if(level==0)
        if(level==1)
    {return Color.BLACK;}
        Color color=geopoint.getGeometry().get_emmission();
        Geometry geometryGeo=geopoint.geometry;//newly added
        Vector v=geopoint.getPoint().subtract(_scene.get_camera().get_p0()).normalize();
        Vector n=geopoint.getGeometry().getNormal(geopoint.point);///
        int nShininess=geopoint.getGeometry().get_material().get_nShininess();
        double kd=geopoint.getGeometry().get_material().get_kD();
        double ks=geopoint.getGeometry().get_material().get_kS();
        for(LightSource lightsource:_scene.get_lights())
        {
            Vector I=lightsource.getL(geopoint.getPoint());
            if(alignZero((n.dotProduct(I)*(n.dotProduct(v))))>0.0)
            {
                double ktr=transparency(I, n, geopoint, lightsource);

                if(ktr*k>MIN_CALC_COLOR_K){
                    Color lightIntensity= lightsource.getIntensity(geopoint.getPoint()).scale(ktr);
                    color=color.add(calcDiffusive(kd,I,n,lightIntensity),
                            calcSpecular(ks,I,n,v,nShininess,lightIntensity));}}
        }
        double kr=geopoint.geometry.get_material().get_kR(),kkr=k*kr;
        if(kkr>MIN_CALC_COLOR_K){
            Ray reflectedRay=constructReflectedRay(n,geopoint.point,ray);
            Intersectable.Geopoint reflectedPoint=getClosestPoint(reflectedRay);
            if(reflectedPoint !=null){
                color=color.add(calcColor(reflectedPoint,reflectedRay,level-1,kkr).scale(kr));
            }
            double kt=geopoint.geometry.get_material().get_kT(),kkt=kt*k;
            if(kkt>MIN_CALC_COLOR_K)
            {
                Ray refractedRay=constructRefractedRay(n,geopoint.point,ray);
                Intersectable.Geopoint refractedPoint=getClosestPoint(refractedRay);//findclosestintersection
                if(refractedPoint!=null){
                    color=color.add(calcColor(refractedPoint,refractedRay,level-1,kkt).scale(kt));
                }
            }
        }

        return color;
    }

    private Ray constructReflectedRay(Vector n, Point3D point, Ray ray)
    {if(n.get_head()== Point3D.ZERO)return null;
        Vector r=n.scale(2*(ray.get_dir().dotProduct(n))).subtract(ray.get_dir());
        return new Ray(point,r,n);
    }

    private Ray constructRefractedRay(Vector n, Point3D point, Ray ray)
    {
        Vector direction=ray.get_dir();
        return new Ray(point,direction,n);

    }

    /**
     * returns specular light
     * @param ks
     * @param i
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return
     */
    private Color calcSpecular(double ks, Vector i, Vector n, Vector v, int nShininess, Color lightIntensity)
    { Vector r=i.subtract(n.scale((((i.dotProduct(n))*2.0))));
        Vector vOpDirection=v.scale(-1);
        Double dotVopR=vOpDirection.dotProduct(r);
        Double choice;
        if(dotVopR>0.0){
            choice=dotVopR;
        }
        else{
            choice=0.0;
        }
        for(int shiny=nShininess;shiny>0;--shiny)
        {
            choice*=choice;
        }
        Color specReturn=lightIntensity.scale(ks*choice);
        return specReturn;
    }

    /**
     * returns the degree of how much light spreads over surface of object
     * @param kd
     * @param i
     * @param n
     * @param lightIntensity
     * @return Color
     */
    private Color calcDiffusive(double kd, Vector i, Vector n, Color lightIntensity) {
        Double lin=i.dotProduct(n);
        Color col=lightIntensity.scale(kd*lin);
        return col;

    }

    public void printGrid(int interval, java.awt.Color colorsep) {
        double coloumns = _imageWriter.getNx();
        double rows = this._imageWriter.getNy();

        //Writing the lines.
        for (int row = 0; row < rows; row++) {
            for (int collumn = 0; collumn < coloumns; collumn++) {
                if (collumn % interval == 0 || row % interval == 0) {
                    _imageWriter.writePixel(collumn, row, colorsep);}}}}
    public void writeToImage() {
        _imageWriter.writeToImage();
    }

    /**
     * creates an image based on the contents of scene
     */
    public void renderImage()
    {
        Camera camera = _scene.get_camera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();
        AmbientLight ambientLight=_scene.get_ambientLight();
        int nX = _imageWriter.getNx();
        int nY=_imageWriter.getNy();
        double distance=_scene.get_distance();
        double height=_imageWriter.getHeight();
        double width=_imageWriter.getWidth();
        //for each point (i,j) in the view plane // i is pixel row number and j is pixel in the row number
        if(adaptiveSupersampling==false)
        { for(int i=0;i<nY;i++)
        {
            for (int j = 0; j < nX; j++) {
                Ray ray = camera.constructRayThroughPixel(nX, nY, j, i, distance, width, height);
                Intersectable.Geopoint closestpoint=getClosestPoint(ray);
                //List<Intersectable.Geopoint> intersectionPoints = geometries.findIntersections(ray);
                //if (intersectionPoints.isEmpty())
                if(closestpoint==null){
                    _imageWriter.writePixel(j, i,_scene.get_background().getColor());
                }
                else
                {
                    _imageWriter.writePixel(j, i, calcColor(closestpoint,ray).getColor());

                }}}
        }
        else{ for(int i=0;i<nY;i++)
        {
            for (int j = 0; j < nX; j++){
                Ray ray = camera.constructRayThroughPixel(nX, nY, j, i, distance, width, height);
                Intersectable.Geopoint closestpoint=getClosestPoint(ray);
                if(closestpoint==null){
                    _imageWriter.writePixel(j, i,_scene.get_background().getColor());
                }
                else{
                Point3D Pij=camera.getPIJ(nX,nY,j,i,distance,width,height);
              Coordinate Pijx= Pij.get_x();
              Coordinate Pijy=Pij.get_y();
              double rx=nX/width;
              double ry=nY/height;
              double x0=Pijx.get()-rx/2.0;
              double y0=Pijy.get()-ry/2.0;
              double xn=Pijx.get()+rx/2.0;
              double yn=Pijy.get()+ry/2.0;
             _imageWriter.writePixel(j,i,Sampler(x0,y0,xn,yn,Pij).getColor());}
            }
            }}}

    /**
     * calculates average color of pixel by constructing beam through pixel center and four corners of pixel
     * @param x0
     * @param y0
     * @param xn
     * @param yn
     * @param pij
     * @return Color
     */
    public Color Sampler(double x0, double y0, double xn, double yn,Point3D pij)
    {
        boolean differentColors=false;
             Color background=_scene.get_background();
            Color average=Color.BLACK;
            Color  ColorCounter=Color.BLACK;
            LinkedList<Intersectable.Geopoint> intersections=new LinkedList<Intersectable.Geopoint>();
        LinkedList<Ray> raysToIntersect=_scene.get_camera().constructBeamThroughPixel(x0,y0,xn,yn,pij);
        for(Ray ray:raysToIntersect)
        {
         Intersectable.Geopoint point=getClosestPoint(ray);
         intersections.add(point);
         if(point!=null)
         {ColorCounter=ColorCounter.add(calcColor(point,ray,MAX_CALC_COLOR_LEVEL,1d)); }
         else{ColorCounter=ColorCounter.add(background);}
        ColorCounter=ColorCounter.add(_scene.get_ambientLight().get_intensity());}

//for(Intersectable.Geopoint geo:intersections){if(geo==null)intersections.remove(geo);}
        average=ColorCounter.reduce(intersections.size());
     for(Intersectable.Geopoint geo:intersections)
      {
      //according to sRGB measure of distance between colors
          if(geo!=null)
          {differentColors=sqrt((geo.geometry.get_emmission().getColor().getRed()-average.getColor().getRed())*
                (geo.geometry.get_emmission().getColor().getRed()-average.getColor().getRed())+
             geo.geometry.get_emmission().getColor().getGreen()-average.getColor().getGreen())*
                (geo.geometry.get_emmission().getColor().getGreen()-average.getColor().getGreen()+
                        geo.geometry.get_emmission().getColor().getBlue()-average.getColor().getBlue())*
                (geo.geometry.get_emmission().getColor().getBlue()-average.getColor().getBlue())>5.0;

            if(differentColors)
        {
            double wi=xn-x0;
            double hi=yn-y0;
            double xcoordinate=(x0-wi/4.0);
            double ycoordinate=y0+hi/4.0;
            double zcoordinate=pij.get_z().get();
            Point3D pijlu=new Point3D(xcoordinate,ycoordinate,zcoordinate);
           Color average1=Sampler(x0,y0,(xn+x0/2.0),(yn+y0)/2.0,pijlu);//for upper left side quarter of pixel
            double wid=xn-x0;
            double hei=yn-y0;
            double xcoord=(x0+wid/4.0);
            double ycoord=y0-hei/4.0;
            double zcoord=pij.get_z().get();
            Point3D pijl=new Point3D(xcoord,ycoord,zcoord);
           Color average2=Sampler(x0,y0,(x0+xn)/2,(y0+yn/2.0),pijl);//for lower left side quarter of pixel
            double width=xn-x0;
            double height=yn-y0;
            double x=(x0+width/4.0);
            double y=y0+height/4.0;
            double z=pij.get_z().get();
            Point3D pijr=new Point3D(x,y,z);
            Color average3=Sampler((x0+xn)/2,(y0+yn)/2,xn,yn,pijr);//for upper right side quarter of pixel
            double x2=x0+width/4.0;
            double y2=y0-height/4.0;
            double z2=pij.get_z().get();
            Point3D pijrl=new Point3D(x2,y2,z2);
            Color average4=Sampler((x0+xn)/2,y0,xn,(y0+yn)/2,pijrl);//for lower right side quarter of pixel
            ColorCounter=ColorCounter.add(average1);
            ColorCounter=ColorCounter.add(average2);
            ColorCounter=ColorCounter.add(average3);
            ColorCounter=ColorCounter.add(average4);
             average=ColorCounter.scale(0.25);
        average=average.add(new Color(1,0,24));}
        }}
//average=average.add(new Color(12,45,245));
            return average;}}

