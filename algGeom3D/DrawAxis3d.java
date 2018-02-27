package algGeom3D;

import algGeom.Draw;
import javax.media.opengl.*;

public class DrawAxis3d extends Draw {

    public DrawAxis3d() {

    }

    @Override
    public void drawObject(GL g) {
        
          
        Line3d ejeX=new Line3d(new Vect3d(-1.0d,0,0),new Vect3d(1.0d,0,0));
        DrawLine3d drawEjeX=new DrawLine3d(ejeX);
        drawEjeX.drawObjectC(g,1,0,0);
       
       
        Line3d ejeY=new Line3d(new Vect3d(0,-1.0d,0),new Vect3d(0,1.0d,0));
        DrawLine3d drawEjeY=new DrawLine3d(ejeY);
        drawEjeY.drawObjectC(g,0,1,0);
        
        Line3d ejeZ=new Line3d(new Vect3d(0,0,-1.0d),new Vect3d(0,0,1.0d));
        DrawLine3d drawEjeZ=new DrawLine3d(ejeZ);
        drawEjeZ.drawObjectC(g,0,0,1);
       

        
          
    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        throw new UnsupportedOperationException();
    }

}
