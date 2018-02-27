package algGeom2D;

import algGeom.BasicGeom;
import algGeom.Draw;
import javax.media.opengl.*;

public class DrawRay extends Draw {

    RayLine rl;

    /**
     * Creates a new instance of VisuPoint
     */
    public DrawRay(RayLine l) {
        rl = l;
    }

    public RayLine getRay() {
        return rl;
    }

    @Override
    /**
     * Se establece como punto base A y movil el B
    */
    public void drawObject(GL g) {
        double ax, ay, bx, by;

        Point a = rl.getA();
        Point b = rl.getB();

        double m = rl.pendiente();
        double c = rl.getC();
        ax = a.getX();
        ay = a.getY();

        boolean izquierda = ax > b.getX();
        boolean encima = ay < b.getY();
        if (m < BasicGeom.INFINITO) { //intersectamos con el lateral                           
            
            bx=(izquierda) ? (-1)*(double)BasicGeom.RANGO : (double)BasicGeom.RANGO;
            by=(m*bx)+c; 
            
        } else {
            //En caso de ser infinito, se calcula un recta perpendicular 
            bx=ax;
            //La direccion de la recta se calcula en funcion de si p2 esta arriba o abajo
            by=(encima) ?(double)BasicGeom.RANGO : (-1)*(double)BasicGeom.RANGO;
            
        }

        ax = convCoordX(ax);
        ay = convCoordX(ay);
        bx = convCoordX(bx);
        by = convCoordX(by);

        g.glBegin(GL.GL_LINES);
        g.glVertex2d(ax, ay);
        g.glVertex2d(bx, by);//the fourth (w) component is zero!
        g.glEnd();

    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glLineWidth(3.0f);
        g.glColor3f(R, G, B);
        drawObject(g);

    }

}
