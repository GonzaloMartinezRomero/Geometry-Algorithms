package algGeom2D;

import algGeom.BasicGeom;

/**
 * Representa un SegmentLine definido por dos Points.
 */
public class SegmentLine {

    protected Point a;
    protected Point b;

    /**
     * Juegos de excepciones
     */
    /* Configuracion de restricciones del parametro t en un segmento. */
    static class Invalid_T_Parameter extends Exception {
    }
    
    protected static class Terna_Intersection{
        public double s,t;
        boolean intersectan;
        public Terna_Intersection(double _s,double _t,boolean _intersectan){
            s=_s;
            t=_t;
            intersectan=_intersectan;
        }
    }
    

    protected void check_t(double t) throws Invalid_T_Parameter {
        if ((t < 0) || (t > 1)) {
            throw new Invalid_T_Parameter();
        }
    }

    /**
     * Contruye un SegmentLine con valores por defecto (igual a (0,0).
     */
    public SegmentLine() {
        b = new Point();
        a = new Point();
    }

    /**
     * Construye un SegmentLine a partir de dos Points
     */
    public SegmentLine(Point ii, Point ss) {
        a = ii;
        b = ss;
    }

    /**
     * Construye un SegmentLine copia del SegmentLine sg.
     */
    public SegmentLine(SegmentLine sg) {
        a = sg.a;
        b = sg.b;
    }

    /**
     * Construye un SegmentLine a partir de las cuatro coordenadas
     */
    public SegmentLine(double ax, double ay, double bx, double by) {
        a = new Point(ax, ay);
        b = new Point(bx, by);
    }

    /**
     * Obtiene el doble area formada por el triangulo compuesto por el SegmentLine actual y la union de los extremos de dicho SegmentLine con el Point p.
     */
    public double areaTriangulo2(Point p) {
        return p.areaTriangulo2(a, b);
    }

    /**
     * Devuelve la longitud del SegmentLine, utilizando el metodo distancia de la clase Point.
     */
    public double longitud() {
        return a.distancia(b);
    }

    /**
     * Compara el SegmentLine actual con el SegmentLine sg, devolviendo true si son iguales, es decir coinciden y false en caso contrario.
     */
    public boolean igual(SegmentLine sg) {
        return (a.iguales(sg.a) && b.iguales(sg.b)) || (a.iguales(sg.b) && b.iguales(sg.a));
    }

    /**
     * Compara el SegmentLine actual con el SegmentLine sg, devolviendo true si son distintos, es decir el Point a o b es distinto, y false en caso contrario.
     */
    public boolean distinto(SegmentLine sg) {
        return !(a.iguales(sg.a) && b.iguales(sg.b)) || (a.iguales(sg.b) && b.iguales(sg.a));
    }

    /**
     * Obtiene una copia del SegmentLine actual.
     */
    public SegmentLine copia() {
        return new SegmentLine(a, b);
    }

    /**
     * Obtiene una copia en el SegmentLine actual del SegmentLine sg.
     */
    public void copia(SegmentLine sg) {
        a.copia(sg.a);
        b.copia(sg.b);
    }

    /**
     * Obtiene el SegmentLine actual.
     */
    public SegmentLine lee() {
        return this;
    }

    /**
     * Obtiene el Point a.
     */
    public Point leea() {
        return a;
    }

    /**
     * Obtiene la coordenada X del Point a.
     */
    public double leeax() {
        return a.getX();
    }

    /**
     * Obtiene la coordenada Y del Point a.
     */
    public double leeay() {
        return a.getY();
    }

    /**
     * Obtiene el Point b.
     */
    public Point leeb() {
        return b;
    }

    /**
     * Obtiene la coordenada X del Point b.
     */
    public double leebx() {
        return b.getX();
    }

    /**
     * Obtiene la coordenada Y del Point b.
     */
    public double leeby() {
        return b.getY();
    }

    /**
     * Establece el Point a con las coordenadas del Point p.
     */
    public void asignai(Point p) {
        a.copia(p);
    }

    /**
     * Establece el Point a con los valores xx e yy para las coordenadas X e Y, respectivamente.
     */
    public void asignaa(double xx, double yy) {
        a.asigna(xx, yy);
    }

    /**
     * Establece la coordenada X del Point a.
     */
    public void asignaax(double xx) {
        a.asignax(xx);
    }

    /**
     * Establece la coordenada Y del Point a.
     */
    public void asignaay(double yy) {
        a.asignay(yy);
    }

    /**
     * Establece el Point b con las coordenadas del Point p.
     */
    public void asignab(Point p) {
        b.copia(p);
    }

    /**
     * Establece el Point a con los valores xx e yy para las coordenadas X e Y, respectivamente.
     */
    public void asignab(double xx, double yy) {
        b.asigna(xx, yy);
    }

    /**
     * Establece la coordenada X del Point a.
     */
    public void asignabx(double xx) {
        b.asignax(xx);
    }

    /**
     * Establece la coordenada Y del Point a.
     */
    public void asignaby(double yy) {
        b.asignay(yy);
    }

    /**
     * Indica si el SegmentLine es horizontal, en cuyo caso se devuelve true, y en caso contrario se devuelve false.
     */
    public boolean esHorizontal() {       
        return BasicGeom.iguales(a.x, b.x);
    }

    /**
     * Indica si el SegmentLine es vertical, en cuyo caso se devuelve true, y en caso contrario se devuelve false.
     */
    public boolean esVertical() {
       return BasicGeom.iguales(a.y, b.y);
    }

    /**
     * Indica si el Point p esta a la izquierda del SegmentLine. Para ello, se utiliza el metodo "izquierda" definido en la clase Point.
     */
    public boolean izquierda(Point p) {
        return p.izquierda(a, b);
    }

    /**
     * Devuelve el punto con dicho valor de t; 0<=t<=1
     *
     * @param t: a + t (b-a)
     *
     */
    Point GetPoint(double t) throws Invalid_T_Parameter {
        check_t(t);        
        return a.suma((b.resta(a)).producto(t));
    }

    

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }

    public double pendiente() {
       return a.pendiente(b);       
    }

    public double getC() {
        
        double pendiente=pendiente();
        /*
            La variable c se ha decidido calcular con el pto1, ya que, 
            una vez se tiene la pendiente, C=PT1=PT2
        */
        return pendiente==BasicGeom.INFINITO ? BasicGeom.INFINITO : a.y-(pendiente*a.x);
            
    }

    /**
     * Devuelve true si dos segmentos intersectan de forma propia o impropia.
     *
     * @param: l: segmento con el que puede intersectar nota: dos segmentos intersectan de forma impropia cuando se cruzan (intersecci�n propia) o con colineales y un extremo pertenece al segmento (usar la funci�n Tema2:clasifica())
     */
    public boolean intersecSegImpropia(SegmentLine l) {
        return (a.clasifica(l.a, l.b)==clasificaPuntoEje.ENMEDIO) || (b.clasifica(l.a, l.b)==clasificaPuntoEje.ENMEDIO);
    }

    
    /**
     * Devuelve true si dos segmentos intersectan de forma propia pero no impropia.
     *
     * @param: l: segmento con el que puede intersectar nota: dos segmentos intersectan de forma impropia cuando se cruzan (intersecci�n propia) o con colineales y un extremo pertenece al segmento (usar la funci�n Tema2:clasifica())
     */
    public boolean intersectaSegmento(SegmentLine l) {
        Point a=this.a;
        Point b=this.b;
        Point c=l.a;
        Point d=l.b;
        
        if((a.colineal(c, d) || b.colineal(c, d)) || c.colineal(a, b) || d.colineal(a, b))
            return false;
        else
            return ((a.izquierda(c, d)^b.izquierda(c, d))&&(c.izquierda(a, b)^d.izquierda(a, b)));
        
    }
    
    
    /**
     * Muestra en pantalla la informacion del SegmentLine.
     */
    public void out() {
        System.out.println("Punto a: ");
        a.out();
        System.out.println("Punto b: ");
        b.out();
    }
   
    /*
        Sobrecarga los valores s,t 
        Devuelve true si puede haber interseccion
        Devuelve false si son paralelas
    
    */

    /**
     * Dados los 4 puntos se obtiene una terna que indica
     * si puede haber colision y los valores de s y t 
     * @return 
     */
    protected Terna_Intersection intersecta(Point A,Point B,Point C, Point D){
        
        double xCD=D.x-C.x;
        double yCD=D.y-C.y;
        
        double xAB=B.x-A.x;
        double yAB=B.y-A.y;
        
        double yAC=C.y-A.y;
        double xAC=C.x-A.x;
        
        double denominador=(xCD*yAB)-(xAB*yCD);
      
        //Caso de ser paralelas
        if(BasicGeom.iguales(denominador,BasicGeom.CERO)){
            return new Terna_Intersection(-1, -1, false);
            
        }else{
            
            double s=((xCD*yAC)-(xAC*yCD))/denominador;
            double t=((xAB*yAC)-(xAC*yAB))/denominador;
            
            return new Terna_Intersection(s, t, true);
            
        }
        
        
    }
    
   
    /*
        Ecuacion de interseccion: A+s(B-A)
    */
    protected Vector ecuacionInterseccion(Vector A,Vector B,double s ){
       
       return A.suma((B.resta(A)).prodEsc(s));
    }
    
    
    
    
    /*
        True si intersecta SegmentLine con Line r
        Sobrecarga el vector de interseccion con el punto de corte
    */
    public boolean intersecta(Line r,Vector vectorInterseccion){
    
        Terna_Intersection intersection=intersecta(this.a, this.b, r.a, r.b);

        vectorInterseccion.copia(ecuacionInterseccion(new Vector(this.a),new Vector(this.b),intersection.s));

        return intersection.intersectan && intersection.s>=0.0d && intersection.s<=1.0d;
         
    }
        
    
    public boolean intersecta(RayLine r,Vector vectorInterseccion){
    
        Terna_Intersection intersection=intersecta(this.a, this.b, r.a, r.b);
     
        vectorInterseccion.copia(ecuacionInterseccion(new Vector(this.a),new Vector(this.b),intersection.s));
      
        return intersection.intersectan && intersection.s>=0.0d && intersection.s<=1.0d && intersection.t>=0.0d;
     
    }    
  

     public boolean intersecta(SegmentLine r,Vector vectorInterseccion){
    
        Terna_Intersection intersection=intersecta(this.a, this.b, r.a, r.b);
     
        vectorInterseccion.copia(ecuacionInterseccion(new Vector(this.a),new Vector(this.b),intersection.s));
      
        return intersection.intersectan && intersection.s>=0.0d && intersection.s<=1.0d && intersection.t>=0.0d && intersection.t<=1.0d;     
     
    }    
    
}
    


