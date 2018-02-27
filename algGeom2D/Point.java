package algGeom2D;

import algGeom.BasicGeom;

/**
 * Representa un Point 2D.
 */
enum clasificaPuntoEje {
    IZDA, DECHA, DELANTE, DETRAS, ENMEDIO, ORIGEN, DESTINO
};

public class Point {

    /**
     * Coordenada X del Point.
     */
    protected double x;
    /**
     * Coordenada Y del Point.
     */
    protected double y;
    
    protected boolean polar;
    protected double alpha;
    protected double rp;
    

    /**
     * Crea un nuevo Point con coordenadas por defecto (iguales a 0)
     */
    public Point() {
        x = 0;
        y = 0;
    }

    /**
     * Crea un nuevo Point con coordenadas X e Y igual a xx e yy,
     * respectivamente.
     * @param xx
     * @param yy
     */
    public Point(double xx, double yy) {
        x = xx;
        y = yy;
    }

    /**
     * Crea un nuevo Point copiando el Point p.
     * @param p
     */
    public Point(Point p) {
        x = p.x;
        y = p.y;
    }

    /**
     * Constructor a partir de un angulo alpha (radianes) y el modulo del vector
     * rp
     * Si polar == true se construye con las coordenadas polares
     * @param _alpha
     * @param _rp
     * @param _polar
     */
    public Point(double _alpha, double _rp, boolean _polar) {
        
        if (_polar) {
            
           this.polar=_polar;
           this.alpha=_alpha;
           this.rp=_rp;
           
           x=rp*Math.cos(alpha);
           y=rp*Math.sin(alpha);
            
        } else {
            x = _alpha;
            y = _rp;
        }
    }

    /**
     * get x
     */
    public double getX() {
        return x;
    }

    /**
     * get y
     */
    public double getY() {
        return y;
    }

    /**
     * devolver el angulo *
     * 
     * Dependiendo del cuadrante se calcula de una forma distinta:
     * 
     */
    public double getAlpha() {
        
        //Si ya se tiene el angulo polar,se devuelve directamente
        if(polar)
            return this.alpha;        
        else{
            
           if(x>=0){
               //Primer y cuarto cuadrante
               return Math.atan(y/x);            
           }else{
               //Segundo y tercer cuadrante
               return Math.atan(y/x)+Math.PI;
           }             
        }      
    }
  
    /**
     * modulo, distancia al origen
     */
    public double getModulo() {
        if(polar)
            return this.rp;
        return Math.sqrt((x*x)+(y*y));
    }

    /*
        Resta a this point el point p
        this-p
    */
    public Point resta(Point p){
        return new Point(this.x-p.x,this.y-p.y); 
    }
    
     public Point suma(Point p){
        return new Point(this.x+p.x,this.y+p.y);
    } 
    
     public Point producto(double var){
         return new Point(this.x*var,this.y*var);
     }
    
    
    /*
        Calcula el modulo del punto
    */
    public double dot(){
        return Math.sqrt(x*x+y*y);
    }
    
    
    /** funcion que clasifica la posicion de this con respecto a p1, p2**/
    public clasificaPuntoEje clasifica (Point p0, Point p1) {
        
        double area=this.areaTriangulo2(p0, p1);
        Point a=p1.resta(p0);
        Point b=this.resta(p0);
          
        if(area>0.0)
            return clasificaPuntoEje.IZDA;
        
        if(area<0.0)
            return clasificaPuntoEje.DECHA;
        
        if((a.x*b.x<0.0)||(a.y*b.y<0.0))
            return clasificaPuntoEje.DETRAS;
        
        if(a.dot()<b.dot())
            return clasificaPuntoEje.DELANTE;
        
        if(p0.iguales(this))
            return clasificaPuntoEje.ORIGEN;
        
        if(p1.iguales(this))
            return clasificaPuntoEje.DESTINO;
              
        return clasificaPuntoEje.ENMEDIO;
    }

    /*dice si dos puntos son practicamente iguales */
    boolean iguales(Point pt) {
        return (BasicGeom.iguales(x, pt.x) && BasicGeom.iguales(y, pt.y));
    }

    /**
     * Obtiene la distancia del Point actual al Point p, calculada como:
     * distancia = raiz ((p.x - x)2 + (p.y - y)2).
     * @param p
     * @return 
     */
    public double distancia(Point p) {
         return Math.sqrt(Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2));
    }
    /**
     * Distancia desde el punta a al punto b
     */
    public double distancia(Point a, Point b){
        return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
    }

    /**
     * Obtiene el doble del �rea que forman el triangulo definido por el Point
     * actual y los Points a y b, en el orden (*this, a, b).
     */
    public double areaTriangulo2(Point a, Point b) {
       
        return (a.x*b.y)-(a.y*b.x)+(b.x*this.y)-(b.y*this.x)+(this.x*a.y)-(this.y*a.x);
        
    }


    /**
     * Compara el Point actual con el Point p, devolviendo true si son distintos
     * (si alguna de las coordenadas no coinciden), y false en caso contrario.
     */
    public boolean distinto(Point p) {
        
        return (Math.abs(x - p.x) > BasicGeom.CERO || Math.abs(y - p.y) > BasicGeom.CERO);
    }

    /**
     * Obtiene un Point copia del actual.
     */
    public Point copia() {
        return new Point(x, y);
    }

    /**
     * El Point actual pasa a ser una copia del Point p.
     */
    public void copia(Point p) {
        x = p.x;
        y = p.y;
    }

    /**
     * Devuelve el Point para ser leido.
     */
    public Point lee() {
        return this;
    }

    /**
     * Devuelve la coordenada X del Point.
     */
    public double leex() {
        return x;
    }

    /**
     * Devuelve la coordenada Y del Point.
     */
    public double leey() {
        return y;
    }

    /**
     * Establece las coordenadas X e Y del Point con los valores de xx e yy,
     * respectivamente.
     */
    public void asigna(double xx, double yy) {
        x = xx;
        y = yy;
    }

    /**
     * Establece la coordenada X del Point.
     */
    public void asignax(double xx) {
        x = xx;
    }

    /**
     * Establece la coordenada Y del Point.
     */
    public void asignay(double yy) {
        y = yy;
    }

    /**
     * Indica si el Point esta a la izquierda del segmento definido por los
     * Points ab.
     */
    public boolean izquierda(Point a, Point b) {
        return clasifica(a, b) == clasificaPuntoEje.IZDA;
    }

    /**
     * Indica si el Point esta a la derecha del segmento definido por los Points
     * ab.
     */
    public boolean derecha(Point a, Point b) {
         return clasifica(a, b) == clasificaPuntoEje.DECHA;
    }

    /**
     * Indica si los tres Points son colineales.
     */
    public boolean colineal(Point a, Point b) {
        clasificaPuntoEje resultado = clasifica(a, b);
        return (resultado != clasificaPuntoEje.IZDA) && (resultado != clasificaPuntoEje.DECHA);
    }

    /**
     * Indica si el Point esta a la izquierda o es colineal al segmento definido
     * por los Points ab.
     */
    public boolean izquierdaSobre(Point a, Point b) {
        clasificaPuntoEje resultado = clasifica(a, b);
        return (resultado == clasificaPuntoEje.IZDA) || (resultado != clasificaPuntoEje.DECHA);
    }

    /**
     * Indica si el Point esta a la derecha o es colineal al segmento definido
     * por los Points ab.
     */
    public boolean derechaSobre(Point a, Point b) {
        clasificaPuntoEje resultado = clasifica(a, b);
        return (resultado == clasificaPuntoEje.DECHA) || (resultado != clasificaPuntoEje.IZDA);
    }

    
    /**
     * Indica la pendiente que forma con el Point p.
     */
    public double pendiente(Point p) {        
        return (p.x-this.x==BasicGeom.CERO)||(p.x==this.x) ?  BasicGeom.INFINITO : (p.y-this.y)/(p.x-this.x);
    }

    /* Dice si esta entre los puntos a y b. */
    public boolean entre(Point a, Point b) {
        return clasifica(a, b) == clasificaPuntoEje.ENMEDIO;
    }
    
    /** Indica si el Point esta delante  al segmento definido
     por los Points ab. */
    public boolean delante (Point a, Point b) {
        return clasifica(a, b) == clasificaPuntoEje.DELANTE;
    }
    
    /** Indica si el Point esta detras  al segmento definido
     por los Points ab.
     **/
    public boolean detras (Point a, Point b) {
        return clasifica(a, b) == clasificaPuntoEje.DETRAS;
    }

    
    
    
    
    
    /**
     * Muestra en pantalla los valores de las coordenadas del Point.
     */
    public void out() {
        System.out.print("Coordenada x: ");
        System.out.println(x);
        System.out.print("Coordenada y: ");
        System.out.println(y);
    }

    
   
    
}
