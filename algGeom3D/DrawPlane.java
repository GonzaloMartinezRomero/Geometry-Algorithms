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
public class DrawPlane extends Draw{

    private Plane plane;
    
    public DrawPlane(Plane _plane){
        plane=_plane;
    }
    
    @Override
    public void drawObject(GL gl) {
        drawObjectC(gl,1,0,0);
          
    }

    @Override
    public void drawObjectC(GL gl, float R, float G, float B) {
  
        //Vector D=(A-B)+C
        Vect3d d=new Vect3d((plane.a.resta(plane.b)).suma(plane.c));
      
        gl.glColor3f(R, G, B);
        gl.glBegin(GL.GL_QUADS);
        
        gl.glVertex3d(plane.a.x, plane.a.y, plane.a.z);
        gl.glVertex3d(plane.b.x, plane.b.y, plane.b.z);
        gl.glVertex3d(plane.c.x, plane.c.y, plane.c.z);
        gl.glVertex3d( d.x,  d.y,  d.z);
        
        gl.glEnd();
    }
    
}
