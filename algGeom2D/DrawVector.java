package algGeom2D;

import algGeom.Draw;
import javax.media.opengl.GL;

public class DrawVector extends Draw {

    private Vector vector;

    public DrawVector(Vector v) {
        vector = v;
    }

    public void drawObject(GL g) {
       
          /**
         * pasar a coordenadas de pantalla
         */
        float x = convCoordX(vector.leex());
        /**
         * convierte la y en posiciones de pantalla
         */
        float y = convCoordY(vector.leey());
        /**
         * pasar a coordenadas de pantalla
         */

        g.glPointSize(5.0f);
        g.glBegin(GL.GL_POINTS);
        g.glVertex2d(x, y);
        g.glEnd();
    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glColor3f(R, G, B);
        drawObject(g);
    }
}
