/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGeom3D;

import algGeom.Draw;
import javax.media.opengl.GL;

/**
 *
 * @author Drebin
 */
public class DrawAABB extends Draw{

    private Vect3d cara1ArribaDer,cara1ArribaIzq,cara1DebDer,cara1DebIzq;
    private Vect3d cara2ArribaDer,cara2ArribaIzq,cara2DebDer,cara2DebIzq;
    
    private double distX,distY,distZ;
    
    public DrawAABB(AABB aabb){
            
        distX=aabb.max.x-aabb.min.x;
        distY=aabb.max.y-aabb.min.y;
        distZ=aabb.max.z-aabb.min.z;
        
        //---------   Cara frontal -------------
        cara1ArribaDer=new Vect3d(aabb.max.x,aabb.max.y,aabb.max.z);
        cara1ArribaIzq=new Vect3d(aabb.max.x-distX,aabb.max.y,aabb.max.z);
        cara1DebDer=new Vect3d(aabb.max.x,aabb.max.y-distY,aabb.max.z);
        cara1DebIzq=new Vect3d(aabb.max.x-distX,aabb.max.y-distY,aabb.max.z);

        //---------- Cara trasera---------------
        
        cara2ArribaDer=new Vect3d(aabb.min.x+distX,aabb.min.y+distY,aabb.min.z);
        cara2ArribaIzq=new Vect3d(aabb.min.x,aabb.min.y+distY,aabb.min.z);
        cara2DebDer=new Vect3d(aabb.min.x+distX,aabb.min.y,aabb.min.z);
        cara2DebIzq=new Vect3d(aabb.min.x,aabb.min.y,aabb.min.z);

    }
    
    @Override
    public void drawObject(GL gl) {
        drawObjectC(gl,1,0,0);
    }

    @Override
    public void drawObjectC(GL gl, float R, float G, float B) {
    
        gl.glColor3f(R, G, B);
        gl.glLineWidth(2.0f);
        
        //Cara frontal
        gl.glBegin(GL.GL_LINES);
            gl.glVertex3d(cara1ArribaDer.x, cara1ArribaDer.y, cara1ArribaDer.z);
            gl.glVertex3d(cara1ArribaIzq.x, cara1ArribaIzq.y, cara1ArribaIzq.z);
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
           gl.glVertex3d(cara1ArribaIzq.x, cara1ArribaIzq.y, cara1ArribaIzq.z);
            gl.glVertex3d(cara1DebIzq.x, cara1DebIzq.y, cara1DebIzq.z);
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
           gl.glVertex3d(cara1DebIzq.x, cara1DebIzq.y, cara1DebIzq.z);
            gl.glVertex3d(cara1DebDer.x, cara1DebDer.y, cara1DebDer.z);
        gl.glEnd();
    
        gl.glBegin(GL.GL_LINES);
           gl.glVertex3d(cara1DebDer.x, cara1DebDer.y, cara1DebDer.z);
            gl.glVertex3d(cara1ArribaDer.x, cara1ArribaDer.y, cara1ArribaDer.z);
        gl.glEnd();
        
        //--Cara trasera
        gl.glBegin(GL.GL_LINES);
         gl.glVertex3d(cara2ArribaDer.x, cara2ArribaDer.y, cara2ArribaDer.z);
         gl.glVertex3d(cara2ArribaIzq.x, cara2ArribaIzq.y, cara2ArribaIzq.z);
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
           gl.glVertex3d(cara2ArribaIzq.x, cara2ArribaIzq.y, cara2ArribaIzq.z);
            gl.glVertex3d(cara2DebIzq.x, cara2DebIzq.y, cara2DebIzq.z);
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
           gl.glVertex3d(cara2DebIzq.x, cara2DebIzq.y, cara2DebIzq.z);
            gl.glVertex3d(cara2DebDer.x, cara2DebDer.y, cara2DebDer.z);
        gl.glEnd();
    
        gl.glBegin(GL.GL_LINES);
           gl.glVertex3d(cara2DebDer.x, cara2DebDer.y, cara2DebDer.z);
            gl.glVertex3d(cara2ArribaDer.x, cara2ArribaDer.y, cara2ArribaDer.z);
        gl.glEnd();
        
        
        //---Union
        
        gl.glBegin(GL.GL_LINES);
           gl.glVertex3d(cara1ArribaDer.x, cara1ArribaDer.y, cara1ArribaDer.z);
            gl.glVertex3d(cara2ArribaDer.x, cara2ArribaDer.y, cara2ArribaDer.z);
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
           gl.glVertex3d(cara1ArribaIzq.x, cara1ArribaIzq.y, cara1ArribaIzq.z);
            gl.glVertex3d(cara2ArribaIzq.x, cara2ArribaIzq.y, cara2ArribaIzq.z);
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
           gl.glVertex3d(cara1DebDer.x, cara1DebDer.y, cara1DebDer.z);
            gl.glVertex3d(cara2DebDer.x, cara2DebDer.y, cara2DebDer.z);
        gl.glEnd();
        
         gl.glBegin(GL.GL_LINES);
           gl.glVertex3d(cara1DebIzq.x, cara1DebIzq.y, cara1DebIzq.z);
            gl.glVertex3d(cara2DebIzq.x, cara2DebIzq.y, cara2DebIzq.z);
        gl.glEnd();
        
        
    }
    
}
