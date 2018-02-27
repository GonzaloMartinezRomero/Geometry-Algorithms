package algGeom3D;

import algGeom.Draw;
import javax.media.opengl.*;

public class DrawVect3d extends Draw {

    Vect3d v3;
    private float size;

    
    public DrawVect3d(){
        v3=null;
        size=4.0f;
    }
    
    /**
     * Creates a new instance of VisuPoint
     */
    public DrawVect3d(Vect3d p) {
        v3 = p;
        size=4.0f;
    }

     public DrawVect3d(Vect3d p,float _size) {
        v3 = p;
        size=_size; 
    }
    
    
    public Vect3d getPoint() {
        return v3;
    }

    @Override
    public void drawObject(GL g) {
        drawObjectC(g, 1, 0, 0);
    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        if(v3!=null){
            
            g.glColor3f(R, G, B);
            g.glPointSize(size);
            g.glBegin(GL.GL_POINTS);
            g.glVertex3d(v3.x, v3.y, v3.z);
            g.glEnd();
        }
       

    }
    
    public void setVector(Vect3d p){
        v3=p;
    }
    
    public void setSize(float _size){
        size=_size;
    }
}
