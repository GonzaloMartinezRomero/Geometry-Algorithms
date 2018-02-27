package algGeom3D;

import algGeom.BasicGeom;

public class Plane {

    public enum TypeOfIntersection{Plane,Line,Point};
    
    public static class IntersectType{
        
        private TypeOfIntersection type;
        private Line3d line;
        private Vect3d vector;
        
        
        public IntersectType(){
            line=null;
            vector=null; 
        }
        
        public void setType(TypeOfIntersection _type){
            type=_type;
        }
        
        public void setLine(Line3d _line){
            line=new Line3d(_line);
        }
        
        public void setPoint(Vect3d _vector){
            vector=new Vect3d(_vector);
        }
        
        
        public Object getIntersection(){
            if(line!=null)
                return line;
            
            if(vector!=null)
                return vector;
            
            return null;
        }
        
        public TypeOfIntersection getType(){
            return type;
        }
        
       
        
    }
    
    
    
    Vect3d a, b, c; //tres puntos cualquiera del plano  
    private boolean sonPuntos;
    
    /**
     *
     * @param p en pi = p+u * lambda + v * mu -> r en los puntos (R,S,T)
     * @param u en pi = p+u * lambda + v * mu -> d en los puntos (R,S,T)
     * @param v en pi = p+u * lambda + v * mu -> t en los puntos (R,S,T)
     * @param _sonPuntos = false entonces los parÃ¡metros son p+u * lambda + v * mu sino son los puntos (R,S,T)
     */
    public Plane(Vect3d p, Vect3d u, Vect3d v, boolean _sonPuntos) {
        
        sonPuntos=_sonPuntos;
        
        if (!sonPuntos) { // son vectores de la forma: pi =  p+u * lambda + v * mu 
            a = p;
            b = u.suma(a);
            c = v.suma(a);
        } else { // son 3 puntos del plano cualesquiera 
            a = p;
            b = u;
            c = v;
        }
    }

    /**
     *
     * @return el valor de A en AX+BY+CZ+D = 0;
     *
     */
    public double getA() {
        return (BasicGeom.determinante2x2(c.getZ() - a.getZ(), c.getY() - a.getY(), b.getY() - a.getY(), b.getZ() - a.getZ())); 
    }

    /**
     *
     * @return el valor de B en AX+BY+CZ+D = 0;
     *
     */
    public double getB() {

          return (BasicGeom.determinante2x2(c.getX() - a.getX(), c.getZ() - a.getZ(), b.getZ() - a.getZ(), b.getX() - a.getX()));
    }

    /**
     *
     * @return el valor de C en AX+BY+CZ+D = 0;
     *
     */
    public double getC() {

         return (BasicGeom.determinante2x2(c.getY() - a.getY(), c.getX() - a.getX(), b.getX() - a.getX(), b.getY() - a.getY()));
    }

    /**
     *
     * @return el valor de D en AX+BY+CZ+D = 0;
     *
     */
    public double getD() {

         return (-1) * (getA() * a.getX() + getB() * a.getY() + getC() * a.getZ());
    }

    /**
     *
     * @return el vector normal formado por (A,B,C) de la ecuaciÃ³n Ax+By+Cz+D = 0
     */
    public Vect3d getNormal() {
        double A = getA();
        double B = getB();
        double C = getC();

        return new Vect3d(A, B, C);
    }

    /**
     * Devuelve un punto usando la ecuación paramétrica (plano=p+u*lambda+v*mu) del plano a partir de unos parámetros
     * @param lambda
     * @param mu
     * @return 
     */
    public Vect3d getPointParametric(double lambda, double mu) {
        
        Vect3d u = b.resta(a),
               v = c.resta(a);

        return a.suma(u.prodEscalar(lambda)).suma(v.prodEscalar(mu));
    }

    /**
     * Calcula la distancia entre el plano y el punto dado
     * @param p
     * @return 
     */
    public double distance(Vect3d p) {
        
       
       double numerador=Math.abs((getA()*p.x)+(getB()*p.y)+(getC()*p.z)+(getD()));
       
       double denominador=Math.sqrt(Math.pow(getA(),2)+(Math.pow(getB(),2))+(Math.pow(getC(),2)));
        
       return numerador/denominador;
    
    }

    /**
     * Dice si un punto pertenece a un plano o no
     * @param p
     * @return 
     */
    public boolean coplanar(Vect3d p) {
       
        return BasicGeom.iguales(distance(p), BasicGeom.CERO);
        
    }

    /**
     * Calcula el punto más cercano en el plano a uno dado (la proyección del punto sobre el plano)
     * @param p
     * @return 
     */
    public Vect3d project(Vect3d p) {
        
        /*  
            Q=p+lambda*n
            lambda=-(n*p+d)/n * n
        */     
        
        Vect3d normal=new Vect3d(getNormal());
        double lambda= (-1*((normal.dot(p))+getD()))/(normal.dot(normal));
        
        return p.suma(normal.prodEscalar(lambda));
    
    }

    /**
     * Muestra en pantalla los valores los valores de Plano
     */
    public void out() {
        System.out.print("Plano->a: ");
        System.out.println(a);
        System.out.print("Plano->b: ");
        System.out.println(b);
        System.out.print("Plano->c: ");
        System.out.println(c);
    }
    
    
    public Vect3d getVectorA(){
        return a;
    }
    public Vect3d getVectorB(){
        return b;
    }
    public Vect3d getVectorC(){
        return c;
    }

    
    public boolean linePlane(Line3d line,Vect3d intersection){
        
        Vect3d directionLine=line.dest.resta(line.orig);
        
        double denominator=directionLine.dot(this.getNormal());
        
        double epsilon=BasicGeom.CERO;
        
        if(Math.abs(denominator)<epsilon){
            
            return Math.abs((line.orig.x*this.getA())+(line.orig.y*this.getB())+(line.orig.z*this.getC())+(this.getD()))<epsilon;
            
        }
        
        double t=-1.0d*((this.getA()*line.orig.x)+(this.getB()*line.orig.y)+(this.getC()*line.orig.z)+(this.getD()));
        
        t=t/denominator;
        
        intersection.setVect3d(line.orig.suma(directionLine.prodEscalar(t)));
        
        return true;
        
    }
    
    
    public boolean trianglePlane(Triangle3d triangle,IntersectType intersection){
        
        double epsilon=BasicGeom.CERO;
        Vect3d pointOfPlane=(sonPuntos) ? this.a : this.getPointParametric(0, 0);
        Vect3d normalPlane=this.getNormal();
        
        double dot1=normalPlane.dot(triangle.a.resta(pointOfPlane));
        double dot2=normalPlane.dot(triangle.b.resta(pointOfPlane));
        double dot3=normalPlane.dot(triangle.c.resta(pointOfPlane));
        
        if(Math.abs(dot1)<=epsilon)
            dot1=0.0d;
        
        if(Math.abs(dot2)<=epsilon)
            dot2=0.0d;
        
        if(Math.abs(dot3)<=epsilon)
            dot3=0.0d;
        
        
        if( dot1>0.0d && dot2>0.0d && dot3>0.0d ){
            //Todos los puntos estan encima del plano
            return false;
        }
        
        if( dot1<0.0d && dot2<0.0d && dot3<0.0d ){
            //Todos los puntos estan debajo del plano
            return false;
        }
        
        if((Math.abs(dot1)+Math.abs(dot2)+Math.abs(dot3))==0.0d){
            //Puntos coplanares
            intersection.setType(TypeOfIntersection.Plane);
            return true;
        }
        
        //-----  Intersecciones mas comunes -------
        
        if((dot1>0.0d && dot2 >0.0d && dot3<0.0d)||(dot1<0.0d && dot2 <0.0d && dot3>0.0d)){
            
            intersection.setType(TypeOfIntersection.Line);
            
            Line3d l1=new Line3d(triangle.a,triangle.c);
            Line3d l2=new Line3d(triangle.b,triangle.c);
            
            Vect3d point1=new Vect3d();
            Vect3d point2=new Vect3d();
            
            this.linePlane(l1,point1);
            this.linePlane(l2, point2);
            
            intersection.setLine(new Line3d(point1, point2.resta(point1)));
            
            return true;
        }
        
        if((dot2>0.0d && dot3>0.0d && dot1<0.0d)||(dot2<0.0d && dot3<0.0d && dot1>0.0d)){
            
            intersection.setType(TypeOfIntersection.Line);

            Line3d l1=new Line3d(triangle.b,triangle.a);
            Line3d l2=new Line3d(triangle.c,triangle.a);
            
            Vect3d point1=new Vect3d();
            Vect3d point2=new Vect3d();
            
            this.linePlane(l1,point1);
            this.linePlane(l2, point2);
            
            intersection.setLine(new Line3d(point1, point2.resta(point1)));
            
            return true;
            
        }
        
        
        if((dot1>0.0d && dot3>0.0d && dot2<0.0d)||(dot1<0.0d && dot3<0.0d && dot2>0.0d)){
            
            intersection.setType(TypeOfIntersection.Line);

            Line3d l1=new Line3d(triangle.a,triangle.b);
            Line3d l2=new Line3d(triangle.c,triangle.b);
            
            Vect3d point1=new Vect3d();
            Vect3d point2=new Vect3d();
            
            this.linePlane(l1,point1);
            this.linePlane(l2, point2);
            
            intersection.setLine(new Line3d(point1, point2.resta(point1)));
            
            return true;
        }
        
        //---- Caso B -----
        if((dot1==0.0d)&&((dot2>0.0d && dot3>0.0d)||(dot2<0.0d && dot3<0.0d))){
         
            intersection.setType(TypeOfIntersection.Point);
            
            intersection.setPoint(triangle.a);
            
            return true;
            
        }        
      
        
        if((dot2==0.0d)&&((dot1>0.0d && dot3>0.0d)||(dot1<0.0d && dot3<0.0d))){

           intersection.setType(TypeOfIntersection.Point);

           intersection.setPoint(triangle.b);

           return true;

        }

        if((dot3==0.0d)&&((dot2>0.0d && dot1>0.0d)||(dot2<0.0d && dot1<0.0d))){

            intersection.setType(TypeOfIntersection.Point);

            intersection.setPoint(triangle.c);

            return true;

        }
        
        //----- Caso C -------
        
        if((dot1==0.0d)&&((dot2>0.0d && dot3<0.0d)||(dot2<0.0d && dot3>0.0d))){
            
            intersection.setType(TypeOfIntersection.Line);
            
            Line3d l1=new Line3d(triangle.c,triangle.b);
            Vect3d point1=new Vect3d();            
            
            this.linePlane(l1, point1);
            
            intersection.setLine(new Line3d(triangle.a, point1.resta(triangle.a)));
            
            return true;
            
        }
        
        
         if((dot2==0.0d)&&((dot1>0.0d && dot3<0.0d)||(dot1<0.0d && dot3>0.0d))){
            
            intersection.setType(TypeOfIntersection.Line);
            
            Line3d l1=new Line3d(triangle.a,triangle.c);
            
            Vect3d point1=new Vect3d();            
            
            this.linePlane(l1, point1);
            
            intersection.setLine(new Line3d(triangle.b, point1.resta(triangle.b)));
            
            return true;
        }
        
        if((dot3==0.0d)&&((dot1>0.0d && dot2<0.0d)||(dot1<0.0d && dot2>0.0d))){

            intersection.setType(TypeOfIntersection.Line);

            Line3d l1=new Line3d(triangle.a,triangle.b);

            Vect3d point1=new Vect3d();            

            this.linePlane(l1, point1);

            intersection.setLine(new Line3d(triangle.c, point1.resta(triangle.c)));
            
            return true;

        }

        //-----   CASO D -------
        
        if(dot1==0.0d && dot2==0.0d){
            
            intersection.setType(TypeOfIntersection.Line);
            
            intersection.setLine(new Line3d(triangle.a,triangle.b.resta(triangle.a)));
        
            return true;
        }
        
        if(dot2==0.0d && dot3==0.0d){
            
            intersection.setType(TypeOfIntersection.Line);
            
            intersection.setLine(new Line3d(triangle.b,triangle.c.resta(triangle.b)));
        
            return true;
        }
        
        if(dot1==0.0d && dot3==0.0d){
            
            intersection.setType(TypeOfIntersection.Line);
            
            intersection.setLine(new Line3d(triangle.a,triangle.c.resta(triangle.a)));
        
            return true;
        }
        
        return false;
    }
}
