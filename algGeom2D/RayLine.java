package algGeom2D;


public class RayLine extends SegmentLine{
    
    public RayLine (Point a, Point b){
    	super (a,b);
    }
	
    public RayLine (SegmentLine s){
        a = s.a;
        b = s.b;
    }
    
    /* Operaciones de intersección de segmento no soportadas. */
    @Override
    public boolean intersectaSegmento (SegmentLine l) {
        throw new UnsupportedOperationException();
    }
    @Override
    public boolean intersecSegImpropia (SegmentLine l) {
        throw new UnsupportedOperationException();
    }
 
    /* Parámetro t debe ser positivo (t >= 0). */
    @Override
    protected void check_t(double t) throws Invalid_T_Parameter {
        if (t < 0) throw new Invalid_T_Parameter();
    }
 
    
    /** Muestra en pantalla la informacion del RayLine. */
    public void out () {
        System.out.println ("RayLine->");
        System.out.println ("Punto a: ");
        a.out ();
        System.out.println ("Punto b: ");
        b.out ();
    }
    
     
    @Override
    public boolean intersecta(Line r,Vector vectorInterseccion){
        
       Terna_Intersection interseccion=intersecta(this.a,this.b,r.a,r.b);
       
       vectorInterseccion.copia(ecuacionInterseccion(new Vector(this.a),new Vector(this.b),interseccion.s));
       
       return interseccion.intersectan && interseccion.s>=0.0d;
    }
    
    @Override
    public boolean intersecta(RayLine r,Vector vectorInterseccion){
        
       Terna_Intersection interseccion=intersecta(this.a,this.b,r.a,r.b);
       
       vectorInterseccion.copia(ecuacionInterseccion(new Vector(this.a),new Vector(this.b),interseccion.s));
       
       return interseccion.intersectan && interseccion.s>=0.0d && interseccion.t>=0.0d;
    }
    
    @Override
    public boolean intersecta(SegmentLine r,Vector vectorInterseccion){
        
       Terna_Intersection interseccion=intersecta(this.a,this.b,r.a,r.b);
       
       vectorInterseccion.copia(ecuacionInterseccion(new Vector(this.a),new Vector(this.b),interseccion.s));
       
       return interseccion.intersectan && interseccion.s>=0.0d && interseccion.t>=0.0d && interseccion.t<=1.0d;
    }
    
}