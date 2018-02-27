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
public class DrawRay3d extends Draw{
    
    Ray3d ray3d;
    
    public DrawRay3d(Ray3d ray){
        ray3d=ray;
    }
    public DrawRay3d(){
        ray3d=null;
    }

    @Override
    public void drawObject(GL gl) {
        drawObjectC(gl, 1, 0, 0);
    }

    @Override
    public void drawObjectC(GL gl, float R, float G, float B) {
        
        //Formula utilizada: l(t)=A+t*u
        
        //Calculo de u
        Vect3d u= new Vect3d(ray3d.dest.resta(ray3d.orig));
        
        float t=100.0f;
        
        //Valor grande positivo
        Vect3d destLine=new Vect3d(ray3d.orig.suma(u.prodEscalar(t)));
                
        gl.glColor3f(R, G, B);
        gl.glLineWidth(2.0f);
        gl.glBegin(GL.GL_LINES);
            gl.glVertex3d(ray3d.orig.x, ray3d.orig.y, ray3d.orig.z);
            gl.glVertex3d(destLine.x, destLine.y, destLine.z);
        gl.glEnd();
    
    }
    
    public void setRay(Ray3d ray){
        this.ray3d=ray;
    }

    
}
