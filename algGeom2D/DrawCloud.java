package algGeom2D;

import algGeom.Draw;
import javax.media.opengl.*;

public class DrawCloud extends Draw {

    PointCloud pc;

    
    public DrawCloud(PointCloud p) {
        pc = p;
    }

    public PointCloud getPoint() {
        return pc;
    }

    @Override
    public void drawObject(GL g) {
            
        for(Point point:pc.nubepuntos){
            
            float x = convCoordX(point.getX());
            float y = convCoordY(point.getY());
          
            g.glPointSize(5.0f);
            g.glBegin(GL.GL_POINTS);
            g.glVertex2d(x, y);
            g.glEnd();
        }

    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {

        g.glColor3f(R, G, B);
        drawObject(g);
      
    }
    

}
