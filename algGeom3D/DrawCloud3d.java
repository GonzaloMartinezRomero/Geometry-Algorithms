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
public class DrawCloud3d extends Draw{
    
    Cloud3d cloud3d;
    
    public DrawCloud3d(Cloud3d _cloud3d){
        cloud3d=_cloud3d;
    }

    @Override
    public void drawObject(GL gl) {
         
        drawObjectC(gl, 1, 0, 0);
        
        
        
    }

    @Override
    public void drawObjectC(GL gl, float R, float G, float B) {
        
        gl.glPointSize(5.0f);
        gl.glColor3f(R, G, B);
        
        for(Vect3d vect3d:cloud3d.nubepuntos){ 

          gl.glBegin(GL.GL_POINTS);
          gl.glVertex3d(vect3d.getX(), vect3d.getY(),vect3d.getZ());        
          gl.glEnd();

      }
    }
    
    
    
}
