/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGeom2D;

import algGeom.Draw;
import algGeom2D.Delaunay.Point_dt;
import algGeom2D.Delaunay.Triangle_dt;
import javax.media.opengl.GL;

/**
 *
 * @author Drebin
 */
public class DrawTriangle extends Draw{

    private Point_dt vertexA;
    private Point_dt vertexB;
    private Point_dt vertexC;
    
    
    
    public DrawTriangle(Triangle_dt triangle){
    
        vertexA=new Point_dt(triangle.p1());
        vertexB=new Point_dt(triangle.p2());
        vertexC=new Point_dt(triangle.p3());
        
    }
    public DrawTriangle(){
        vertexA=null;
        vertexB=null;
        vertexC=null;
    }
    
    public void loadNewTriangle(Triangle_dt triangle){
         
        vertexA=new Point_dt(triangle.p1());
        vertexB=new Point_dt(triangle.p2());
        vertexC=new Point_dt(triangle.p3());
    }
    
    
    @Override
    public void drawObject(GL gl) {
            drawObjectC(gl, 1, 0, 0);
    }

    @Override
    public void drawObjectC(GL gl, float R, float G, float B) {
  
        
        gl.glColor3f(R, G, B);
        gl.glLineWidth(3.0f);        
       
        
        /*
            Debido a que GL_TRIANGLE rellena el triangulo, se usa GL_LINES para obtener
            una mejor visualización
        */
        
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(vertexA.x(), vertexA.y());       
        gl.glVertex2d(vertexB.x(), vertexB.y());
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(vertexB.x(), vertexB.y());
        gl.glVertex2d(vertexC.x(), vertexC.y());
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(vertexC.x(), vertexC.y());
        gl.glVertex2d(vertexA.x(), vertexA.y());
        gl.glEnd();
    
     
        
    }
    
}
