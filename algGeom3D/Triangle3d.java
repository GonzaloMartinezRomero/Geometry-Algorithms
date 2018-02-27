package algGeom3D;

import algGeom.BasicGeom; 
import algGeom2D.Point;
import algGeom2D.SegmentLine;
import java.util.ArrayList; 
import algGeom2D.Line;
import java.util.LinkedList;

public class Triangle3d {

    public static enum posicionPunto {
        POSITIV0, NEGATIV0, ENCIMA, UNDEFINED
    };

    public enum posicionTrianguloRecta {
        PARALELO, COLINEAL, INTERSECTA, NO_INTERSECTA
    };
    
    
    public posicionPunto posicion;
    
    /**
     * un triangulo viene definido por tres puntos en el espacio
     */
    protected Vect3d a, b, c;

    /**
     * Constructor por defecto a valor (0,0)
     */
    public Triangle3d() {
        a = new Vect3d();
        b = new Vect3d();
        c = new Vect3d();
    }

    /**
     * Constructor a partir de coordenadas de los tres puntos
     */
    public Triangle3d(double ax, double ay, double az,
            double bx, double by, double bz,
            double cx, double cy, double cz) {
        a = new Vect3d(ax, ay, az);
        b = new Vect3d(bx, by, bz);
        c = new Vect3d(cx, cy, cz);
    }

    /**
     * Constructor copia
     */
    public Triangle3d(Triangle3d t) {
        a = new Vect3d(t.a);
        b = new Vect3d(t.b);
        c = new Vect3d(t.c);
    }

    /**
     * Constructor a partir de tres Vect3d de javax
     */
    public Triangle3d(Vect3d va, Vect3d vb, Vect3d vc) {
        a = new Vect3d(va);
        b = new Vect3d(vb);
        c = new Vect3d(vc);
    }

    /**
     * modifica los valores de los vertices de los triangulos
     */
    public void set(Vect3d va, Vect3d vb, Vect3d vc) {
        a = va;
        b = vb;
        c = vc;
    }

    /**
     * Obtiene el Vect3d de a
     */
    public Vect3d getA() {
        return a;
    }

    /**
     * Obtiene el Punto b
     */
    public Vect3d getB() {
        return b;
    }

    /**
     * Obtiene el Punto c
     */
    public Vect3d getC() {
        return c;
    }

    /**
     * Obtiene el punto i
     */
    public Vect3d getPoint(int i) {
        return (i == 0 ? a : (i == 1 ? b : c));
    }

    public Vect3d[] getPoints() {
        Vect3d[] vt = {a, b, c};
        return vt;
    }

    /**
     * Devuelve una copia del objeto Punto
     */
    public Triangle3d copy() {
        return new Triangle3d(a, b, c);
    }

    /**
     * Modifica el valor de a
     */
    public void setA(Vect3d pa) {
        a = pa;
    }

    /**
     * Modifica el valor de b
     */
    public void setB(Vect3d pb) {
        b = pb;
    }

    /**
     * Modifica el valor de c
     */
    public void setC(Vect3d pc) {
        c = pc;
    }

    /**
     * devuelve la normal al triÃ¡ngulo
     * @return 
     */
    public Vect3d normal() {
        Vect3d v1 = new Vect3d(b.resta(a));
        Vect3d v2 = new Vect3d(c.resta(a));
        Vect3d n = new Vect3d(v1.xProduct(v2));
        double longi = n.modulo();

        return (n.prodEscalar(1.0 / longi));
    }

    /**
     * Muestra un punto 3d en pantalla
     */
    public void out() {
        System.out.println("Triangle3d: (" + a + "-" + b + "-" + c + ")");
    }

    public double area() {
        
        Vect3d AB=new Vect3d(b.x-a.x,b.y-a.y,b.z-a.z);
        Vect3d AC=new Vect3d(c.x-a.x,c.y-a.y,c.z-a.z);
        
        double primerTermino=(AB.y*AC.z)-(AB.z*AC.y);
        double segundoTermino=(AB.z*AC.x)-(AB.x*AC.z);
        double tercerTermino=(AB.x*AC.y)-(AB.y*AC.x);
        
         
        return 0.5d*(Math.sqrt((Math.pow(primerTermino,2))+(Math.pow(segundoTermino,2))+(Math.pow(tercerTermino,2))));
    
    }

    public posicionPunto classify(Vect3d p) {
        
        Vect3d v=new Vect3d(p.resta(a));
        double len=v.modulo();
        
        
        if(BasicGeom.iguales(len, 0.0d)){
            return posicionPunto.ENCIMA;
        }
        
        v=v.prodEscalar(1.0d/len);
        
        double d=v.dot(normal());
        
        if(d>BasicGeom.CERO)
            return posicionPunto.POSITIV0;
        else {
            if(d<((-1)*BasicGeom.CERO))
                return posicionPunto.NEGATIV0;
            else
                return posicionPunto.ENCIMA;
        }
    }

    public boolean coplanar(Vect3d p) {
        
        //Crear un plano que contenga los vertices del triangulo
        Plane planoTriangulo=new Plane(a,b,c,false);
        
        return  planoTriangulo.coplanar(p);
        
    }

    protected boolean edgeTriangle3d(Edge3d r, Vect3d intersection) {
       
        Vect3d e1=new Vect3d(b.resta(a));
        
        Vect3d e2=new Vect3d(c.resta(a));
        
        Vect3d lineDirection=new Vect3d(r.dest.resta(r.orig));
         
        Vect3d p=new Vect3d(lineDirection.xProduct(e2));
        
        double tmp=p.dot(e1);
        
        double epsilon=BasicGeom.CERO;
         
        if((tmp> -epsilon) && (tmp<epsilon))
            return false;
        
        tmp=1.0d/tmp;
        
        Vect3d s=new Vect3d(r.orig.resta(a));
        
        double u= tmp* s.dot(p);
        
        if(u<0.0d || u>1.0d)
            return false;
        
        Vect3d q=new Vect3d(s.xProduct(e1));
        
        double v= tmp* (lineDirection.dot(q));   //PUEDE QUE HAYA UN ERROR EN EL PSEUDOCODIGO
        
        if(v<0.0d || v>1.0d)
            return false;
        
        if(u+v>1.0d){                   //ESTA PARTE NO ESTA EN EL PSEUDOCODIGO
            return false;
        }
        
        double t=tmp* e2.dot(q);
        
        Vect3d intersectionAux=r.orig.suma(lineDirection.prodEscalar(t));
        
        intersection.setVert(intersectionAux.x, intersectionAux.y, intersectionAux.z);
        
        
        return true;
        
        
    }

    public boolean lineTriangle3d(Line3d r, Vect3d p) {
           return  edgeTriangle3d(r, p);
        
    }

    public boolean rayTriangle3d(Ray3d r, Vect3d p) {
        
          return edgeTriangle3d(r, p);
    }
    
    
    private boolean comprobarColision(Segment3d s,Vect3d vertex,Vect3d opColision){
        
        if(vertex.equals(s.orig)){
            
            opColision.setVect3d(s.dest);
            return true;
            
        }else{
            
            if(vertex.equals(s.dest)){
                
                opColision.setVect3d(s.orig);
                return true;
            } 
            
        }
        return false;
        
    }
    
   
    
    public void split (ArrayList<Triangle3d> tlist, Segment3d s) {
           
        //Comprobar que algun vertice coincide con alguno del triangulo, lo cual genera 2 triangulos
        Vect3d vectOpuesto=new Vect3d();

        //Colision en A
        if(comprobarColision(s, a, vectOpuesto)){

            tlist.add(new Triangle3d(a, b, vectOpuesto));
            tlist.add(new Triangle3d(a, vectOpuesto, c));
            return;
        }
        //Colision B
        if(comprobarColision(s, b, vectOpuesto)){

            tlist.add(new Triangle3d(a, b, vectOpuesto));
            tlist.add(new Triangle3d(b, c, vectOpuesto));
            return;
        }
        //Colision en C
        if(comprobarColision(s, c, vectOpuesto)){

            tlist.add(new Triangle3d(a, vectOpuesto,c ));
            tlist.add(new Triangle3d(c, vectOpuesto, b));
            return;
        }
        
        //Si no coincide con el vertice, se generan 3 triangulos
        /*
                    -- ALGORITMO --
            Dados los 3 vertices de un triangulo y un segmento, siempre va a ver 2 vertices
            del triangulo involucrados en la partición
            Con los 2 vertices + los 2 del segmento, se realiza la triangulacion generando 2 triangulos              
                Los 2 vertices se obtienen con el metodo derecha de la clase Point2D, ya que
                siempre va a ver 2 vertices que esten o no esten a la derecha
            Con el otro vertice + los 2 del segmento, se genera el 3 triangulo
        
        */
        
        Point puntoOrig=new Point(s.orig.x,s.orig.y);
        Point puntoDest=new Point(s.dest.x,s.dest.y);        
        Point vertexA=new Point(a.x,a.y);
        Point vertexB=new Point(b.x,b.y);
        Point vertexC=new Point(c.x,c.y);           
          
        //Vertices involucrados en la particion
        boolean verticeA=vertexA.derecha(puntoOrig, puntoDest);
        boolean verticeB=vertexB.derecha(puntoOrig, puntoDest);
        boolean verticeC=vertexC.derecha(puntoOrig, puntoDest);
        
        //Comprobar si hay que realizar la triangulacion entre A y B     
        if(verticeA==verticeB){
                       
            //Vector restante, en este caso C,genera triangulo con los 2 vertices del segmento
            tlist.add(new Triangle3d(c,s.orig,s.dest));
            
            //Comprobar qué vertice del segmento esta entre A y C para realizar la triangulacion
            //hacia un sentido o hacia otro
            if(puntoOrig.entre(vertexA, vertexC)){

                tlist.add(new Triangle3d(s.orig,b,a));
                tlist.add(new Triangle3d(s.orig,s.dest,b));

            }else{

                tlist.add(new Triangle3d(s.dest,b,a));
                tlist.add(new Triangle3d(s.dest,s.orig,b));
            }
            
                        
        }else{
            
            if(verticeA==verticeC){
              
                tlist.add(new Triangle3d(b,s.orig,s.dest));
            
                if(puntoOrig.entre(vertexA, vertexB)){

                    tlist.add(new Triangle3d(s.orig,c,a));
                    tlist.add(new Triangle3d(s.orig,s.dest,c));
                    
                }else{
                     
                    tlist.add(new Triangle3d(s.dest,c,a));
                    tlist.add(new Triangle3d(s.dest,s.orig,c));
                }
                
                
            }else{
                
                tlist.add(new Triangle3d(a,s.orig,s.dest));
                
                if(puntoOrig.entre(vertexA, vertexB)){
                    
                    tlist.add(new Triangle3d(s.orig,c,b));
                    tlist.add(new Triangle3d(s.orig,s.dest,c));
                    
                }else{
                     
                    tlist.add(new Triangle3d(s.dest,c,b));
                    tlist.add(new Triangle3d(s.dest,s.orig,c));
                }
                
                
            }
        }
        
        
    }
    
    
}
