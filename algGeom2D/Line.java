package algGeom2D;

public class Line extends SegmentLine {

    public Line(Point a, Point b) {
        super(a, b);
    }

    public Line(SegmentLine s) {
        a = s.a;
        b = s.b;
    }

    /* Operaciones de interseccion de segmento no soportadas. */
    @Override
    public boolean intersectaSegmento(SegmentLine l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean intersecSegImpropia(SegmentLine l) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void check_t(double t) throws Invalid_T_Parameter {
    }

    /**
     * Muestra en pantalla la informacion del RayLine.
     */
    public void out() {
        System.out.println("Line->");
        System.out.println("Punto a: ");
        a.out();
        System.out.println("Punto b: ");
        b.out();
    }
    
   
    
    public double distPuntoRecta(Vector v){
        
        //Calculo de Q-P=d
        Vector d=new Vector(this.a.resta(b));
        
        //Calculo de A-P
        Vector AP=new Vector(v.resta(a));   
        
        //P
        Vector P=new Vector(a);
        
        //Q
        Vector Q=new Vector(b);
       
        //Calculo de t0=d x (A-P) / d x d
        double t=(d.dot(AP))/(d.dot(d));
        
        //L=A-(P+t0 d)
        Vector recta=P.suma((Q.resta(P)).prodEsc(t));
        
        //Producto escalar de la recta
        return (v.resta(recta)).dot();
    }

    @Override
    public boolean intersecta(Line r,Vector vectorInterseccion){
        
        Terna_Intersection intersection=intersecta(this.a,this.b,r.a,r.b);
              
        vectorInterseccion=ecuacionInterseccion(new Vector(this.a),new Vector(this.b),intersection.s);
        
        return intersection.intersectan;
    }
    
    @Override
    public boolean intersecta(RayLine r,Vector vectorInterseccion){
        
        Terna_Intersection intersection=intersecta(this.a,this.b,r.a,r.b);
              
        vectorInterseccion=ecuacionInterseccion(new Vector(this.a),new Vector(this.b),intersection.s);
        
        return intersection.intersectan && intersection.t>=0.0d;
    }
    
    @Override
    public boolean intersecta(SegmentLine r,Vector vectorInterseccion){
        
        Terna_Intersection intersection=intersecta(this.a,this.b,r.a,r.b);
              
        vectorInterseccion=ecuacionInterseccion(new Vector(this.a),new Vector(this.b),intersection.s);
        
        return intersection.intersectan && intersection.t>=0.0d && intersection.t<=1.0d;
    }
    
}
