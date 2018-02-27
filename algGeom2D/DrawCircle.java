/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGeom2D;

/**
 *
 * @author lidia
 */

import algGeom.BasicGeom;
import algGeom.Draw;
import javax.media.opengl.*;

public class DrawCircle extends Draw {
    
    Circle dc;

    public DrawCircle(Circle c) {
        dc = c;
    }

    public DrawCircle(){}
    
    public void loadNewCircle(Circle c){
        dc=c;
    }
    
    public Circle getCircle() {
        return dc;
    }

   
    @Override
    /*
        lod(level of detail)
    */
    public void drawObject(GL g) {
        
      
        //Nivel de detalle con el que se pinta el circulo
        int lod=1000;
     
        double x,y;        
        double currentAngle;
        double constant=2.0d*BasicGeom.PI/(double)lod;
        
        double radio=dc.getRadio();
        
        double despX=dc.c.x;
        double despY=dc.c.y;
                
        g.glPointSize(6.0f);
        g.glBegin(GL.GL_POINTS);
        
        for(int i=0;i<lod;i++){
            
            currentAngle=constant*(double)i;
            x=(radio*Math.cos(currentAngle))+despX;
            y=(radio*Math.sin(currentAngle))+despY;
       
            g.glVertex2d(convCoordX(x), convCoordY(y));
            
        }
        
        g.glEnd();
        
       
    }
    
    @Override
    public void drawObjectC(GL g, float R, float G, float B) {

        g.glColor3f(R, G, B);
        drawObject(g);
        
      
    }
    
    
    /**
     * Dibuja el círculo relleno del color indicado 
     * @param g
     * @param R
     * @param G
     * @param B 
     */
    public void drawObjectCircle(GL g, float R, float G, float B) {

       //Nivel de detalle con el que se pinta el circulo
        int lod=300;
     
        double x,y;        
        double currentAngle;
        double constant=2.0d*BasicGeom.PI/(double)lod;
        
        double radio=dc.getRadio();
        
        double despX=dc.c.x;
        double despY=dc.c.y;
           
        for(int i=0;i<lod;i++){
            
            currentAngle=constant*(double)i;
            x=(radio*Math.cos(currentAngle))+despX;
            y=(radio*Math.sin(currentAngle))+despY;
            
            g.glPointSize(5.0f);
            g.glColor3f(R, G, B);
            g.glBegin(GL.GL_POINTS);
                g.glVertex2d(convCoordX(x), convCoordY(y));
            g.glEnd();
            
            g.glBegin(GL.GL_LINES);
                g.glVertex2d(convCoordX(dc.c.x),convCoordY(dc.c.y));
                g.glVertex2d(convCoordX(x), convCoordY(y));
            g.glEnd();
            
            
        }
              
    }
           
}
