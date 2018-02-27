package algGeom2D;

import algGeom.BasicGeom;
import algGeom.Draw;
import javax.media.opengl.*;

public class DrawAxis extends Draw {

    public DrawAxis() {

    }

    @Override
    public void drawObject(GL g) {

        // Segmentos del eje X.    
        for (int i = -BasicGeom.RANGO; i <= BasicGeom.RANGO; i += 10) {
            SegmentLine sl = new SegmentLine(i, -1.5, i, 1.5);
            DrawSegment dsl = new DrawSegment(sl);
            dsl.drawObjectC(g, 1f, 0f, 0f);
        }

        // Segmentos del eje Y.
        for (int i = -BasicGeom.RANGO; i <= BasicGeom.RANGO; i += 10) {
            SegmentLine sl = new SegmentLine(-1.5, i, 1.5, i);
            DrawSegment dsl = new DrawSegment(sl);
            dsl.drawObjectC(g, 0f, 1f, 0f);
        }

        // Eje X.
        RayLine x = new RayLine(new Point(-BasicGeom.RANGO, 0), new Point(0, 0));
        DrawRay dx_red = new DrawRay(x);
        dx_red.drawObjectC(g, 1f, 0f, 0f);

        // Eje Y.
        RayLine y = new RayLine(new Point(0, -BasicGeom.RANGO), new Point(0, 0));
        DrawRay dy_green = new DrawRay(y);
        dy_green.drawObjectC(g, 0f, 1f, 0f);

        // Origen.
        Point p = new Point(0, 0);
        DrawPoint dp = new DrawPoint(p);
        dp.drawObjectC(g, 1f, 1f, 1f);

    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        throw new UnsupportedOperationException();
    }

}
