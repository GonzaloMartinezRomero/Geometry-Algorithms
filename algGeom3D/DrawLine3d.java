/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGeom3D;

import algGeom.Draw;
import javax.media.opengl.*;

/**
 *
 * @author Drebin
 */
public class DrawLine3d extends Draw{
    
    Line3d l3;
    
    public DrawLine3d(Line3d l){
        l3=l;
    }

    @Override
    public void drawObject(GL gl) {
        
        drawObjectC(gl, 1, 0, 0);
    }

    @Override
    public void drawObjectC(GL gl, float R, float G, float B) {
       
        //Formula utilizada: l(t)=A+t*u
        
        //Calculo de u
        Vect3d u= new Vect3d(l3.dest.resta(l3.orig));
        
        float t=100.0f;
        
        //Valor grande positivo
        Vect3d origLine=new Vect3d(l3.orig.suma(u.prodEscalar(t)));
        
        //Valor grande negativo
        Vect3d destLine=new Vect3d(l3.dest.suma(u.prodEscalar(-t)));
       
        
        gl.glColor3f(R, G, B);
        gl.glLineWidth(2.0f);
        gl.glBegin(GL.GL_LINES);
            gl.glVertex3d(origLine.x, origLine.y, origLine.z);
            gl.glVertex3d(destLine.x, destLine.y, destLine.z);
        gl.glEnd();
    
    }
    
}
