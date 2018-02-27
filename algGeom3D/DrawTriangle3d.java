package algGeom3D;

import algGeom.Draw;
import algGeom2D.Delaunay.Triangle_dt;
import javax.media.opengl.*;

public class DrawTriangle3d extends Draw {

    Triangle3d tr;
    Vect3d n;

    /**
     * Creates a new instance of VisuPunto
     * @param t
     */
    public DrawTriangle3d(Triangle3d t) {
        tr = t;
        n = tr.normal();
    }
    
    public DrawTriangle3d(Triangle_dt t){
        
        setTriangle3d_Delaunay(t);
    }
    
    public DrawTriangle3d(){
        tr=null;
        n=null;
    }
    

    @Override
    public void drawObject(GL g) {
            drawObjectC(g, 1, 0, 0);
      

    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        
        g.glColor3f(R, G, B);
        g.glPointSize(4.0f);
        g.glBegin(GL.GL_TRIANGLES);
            g.glNormal3f((float) n.x, (float) n.y, (float) n.y);
            g.glVertex3d((float) tr.a.x, (float) tr.a.y, (float) tr.a.z);
            g.glVertex3d((float) tr.b.x, (float) tr.b.y, (float) tr.b.z);
            g.glVertex3d((float) tr.c.x, (float) tr.c.y, (float) tr.c.z); 
        g.glEnd();
    }
    
    public void setTriangle3d(Triangle3d triangle){
        tr = triangle;
        n = tr.normal();
    }
    
    public void setTriangle3d_Delaunay(Triangle_dt t){
        
        tr=new Triangle3d(new Vect3d(t.p1().x(),t.p1().y(),0.0d), new Vect3d(t.p2().x(),t.p2().y(),0.0d), new Vect3d(t.p3().x(),t.p3().y(),0.0d));
        n=tr.normal();
    }
    
  
    

}
