package algGeom2D;

import algGeom.BasicGeom;
import algGeom.Draw;
import javax.media.opengl.*;

public class DrawLine extends Draw {

    Line vl;

    public DrawLine(Line l) {
        vl = l;
    }
    
    public DrawLine(){}
    
    public void loadNewLine(Line l){
        vl=l;
    }

    public Line getLine() {
        return vl;
    }

    @Override
    /**
     * La linea dibujada se establece con los limites del RANGO
    */
    public void drawObject(GL g) {

             /**
         * pasar a coordenadas de pantalla
         */
        double ax, ay, bx, by;
        double m = vl.pendiente();
        double c = vl.getC();
        if (m < BasicGeom.INFINITO) { //intersectamos con el canvas lateral
           
          /**
           * Si el punto A esta a la derecha de B, la A.x debe ser MAX(X)
           * En caso contrario A debe ser MIN(X)=-1*MAX(X)
          */
            if(vl.a.x>vl.b.x){
                ax=(double)BasicGeom.RANGO;
                bx=(-1)*ax;
                
            }else{
                 bx=(double)BasicGeom.RANGO;
                 ax=(-1)*bx;
            }
           ay=(m*ax)+c;  
           by=(m*bx)+c;
            
        } else {
            //Si esta en el infinito, la recta es totalmente vertical
            ax=bx=0.0;
            ay=(double)BasicGeom.RANGO;
            by=(-1)*(double)BasicGeom.RANGO;
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

        g.glLineWidth(1.5f);
        g.glColor3f(R, G, B);
        drawObject(g);
    }

    public void drawObjectNoTransform(GL g,float R, float G, float B){
        
        g.glLineWidth(1.5f);
        g.glColor3f(R, G, B);
        
        g.glBegin(GL.GL_LINES);
        g.glVertex2d(vl.a.x, vl.a.y);
        g.glVertex2d(vl.b.x, vl.b.y);//the fourth (w) component is zero!
        g.glEnd();
        
    }
    
  
    
    
}
