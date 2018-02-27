package algGeom2D;

import algGeom.Draw;
import algGeom2D.Delaunay.Point_dt;
import javax.media.opengl.*;

public class DrawPoint extends Draw {

    
    private double x;
    private double y;

    /**
     * Creates a new instance of VisuPoint
     * @param p
     */
    public DrawPoint(Point p) {
        x=p.x;
        y=p.y;
    }
    public DrawPoint(Point_dt p){
        
        x=p.x();
        y=p.y();
        
    }
    
    public DrawPoint(){  }

    public Point getPoint() {
        return new Point(x,y);
    }
    
    public Point_dt getPointDelaunay(){
        return new Point_dt(x,y);
    }
    
    
    public void setPoint(Point p){
        x=p.x;
        y=p.y;
    }
    
     public void setPointDelaunay(Point_dt p){        
        x=p.x();
        y=p.y();
    }
    
  
   
    

    @Override
    public void drawObject(GL g) {

        /**
         * pasar a coordenadas de pantalla
         */
        float x = convCoordX(this.x);
        /**
         * convierte la y en posiciones de pantalla
         */
        float y = convCoordY(this.y);
        /**
         * pasar a coordenadas de pantalla
         */

        drawPointGL(g, x, y);

    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {

        g.glColor3f(R, G, B);
        drawObject(g);

    }
    
    /*
        No aplica transformaciones al objeto, ya que se supone que lo estan
    */
    public void drawObjectNoTransform(GL g){
        
        drawPointGL(g,this.x,this.y);
    }
    
    
    private void drawPointGL(GL g,double x,double y){
         
        g.glPointSize(5.0f);
        g.glBegin(GL.GL_POINTS);
        g.glVertex2d(x, y);
        g.glEnd();
    }
    
}
