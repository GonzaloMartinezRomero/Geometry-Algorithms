/*
 * Esta clase hace las veces de circunferencia y de círculo, según las necesidades.

 */
package algGeom2D;

import algGeom.BasicGeom;

/**
 *
 * @author macondo
 */
public class Circle {
    /**
     * centro de la circunferencia
     */
    protected Point c;
    /**
     * radio de la circunferencia 
    */
    protected double r;
    
        
    
    public enum RelationCircles{
        CONCENTRICO,
        EXTERIOR,
        INTERIOR,
        SECANTE,
        TANG_INTERIOR,
        TANG_EXTERIOR    
    };
    
    public enum RelationCircleLine{
        INTERSECTAN,
        TANGENTES,
        NO_INTERSECTAN        
    };
    
    
    /**
     * Crea una circunferencia por defecto
     */
    public Circle() {
        c = new Point();
        r = 0;
    }

    /**
     * Crea una circunferencia dados un punto correspondiente al centro y un valor del radio
     * @param centro
     * @param radio
     */
    public Circle(Point centro, double radio) {
        c = centro; 
        r = radio;
    }

    /**
     * Crea una circunferencia mediante copia de otra
     * @param cc
     */
    public Circle (Circle cc) {
        c = cc.c;
        r = cc.r;
    }

    /**
     * get centro
     * @return punto central
     */
    public Point getCentro() {
        return c;
    }

    /**
     * get radio
     * @return radio
     */
    public double getRadio() {
        return r;
    }

    
    /**
     * 
     * @return area del círculo
     */
    public double area(){
        return BasicGeom.PI * r * r;
        
    }
    

    /**
     * 
     * @return diámetro de la circunferencia
     */
    public double diametro(){
        return BasicGeom.PI * 2 * r;
        
    }
    
    public boolean dentro(Point p){
       return getCentro().distancia(p)<=getRadio();
    }
    
   
    public RelationCircles relacionaCir(Circle c){
        
        double distanciaCentros=this.getCentro().distancia(c.getCentro());
        double sumaRadios=this.getRadio()+c.getRadio();
        double restaRadios=this.getRadio()-c.getRadio();
        
        
        if(BasicGeom.iguales(BasicGeom.CERO, distanciaCentros)){
            return RelationCircles.CONCENTRICO;
        }else{
            if(distanciaCentros>sumaRadios){
                return RelationCircles.EXTERIOR;
            }else{
                if(distanciaCentros<sumaRadios){
                    return RelationCircles.INTERIOR;
                }else{
                    if(restaRadios<distanciaCentros && restaRadios>distanciaCentros){
                        return RelationCircles.SECANTE;
                    }else{
                        if(distanciaCentros==sumaRadios){
                            return RelationCircles.TANG_EXTERIOR;
                        }else{
                            return RelationCircles.TANG_INTERIOR;
                        }
                            
                    }
                }
            }
        }
            
    }
    
    public RelationCircleLine relacionLinea(Line l){
        
        //Q-P
        Vector dQP=new Vector(l.b.resta(l.a));
        
        //"triangulo"
        Vector incremento=new Vector(l.a.resta(this.c));        
        
        //Sigma=(d x Triangulo)^2 - |d|^2(|Triangulo|^2 - r^2)
        double sigma=Math.pow(dQP.dot(incremento), 2) -( Math.pow(dQP.dot(), 2)*(Math.pow(incremento.dot(), 2)-Math.pow(r, 2)) ) ;
        
        if(BasicGeom.iguales(sigma, BasicGeom.CERO)){
            return RelationCircleLine.TANGENTES;
        }else{
             if(sigma<BasicGeom.CERO){
                return RelationCircleLine.NO_INTERSECTAN;        
             }else{
                 return RelationCircleLine.INTERSECTAN;
             }
        }
        
    }
    
    
    public boolean intersecta(Line l,Vector a,Vector b){
       
        //Vector P
        Vector P=new Vector(l.a);
        
         //Q-P=d
        Vector dQP=new Vector(l.b.resta(l.a));
        
        //"triangulo"=P-C
        Vector incremento=new Vector(l.a.resta(this.c));        
   
        
        //Sigma=(d x Triangulo)^2 - |d|^2(|Triangulo|^2 - r^2)
        double sigma=Math.pow(dQP.dot(incremento), 2) -( Math.pow(dQP.dot(), 2)*(Math.pow(incremento.dot(), 2)-Math.pow(r, 2)) ) ;
        
        //---TANGENTES, INTERSECTA EN 1 SOLO PUNTO
        if(BasicGeom.iguales(sigma, BasicGeom.CERO)){            
                
            //Valores t1  para establecer la posicion de los cortes
            double t1;   

           //Primer punto de interseccion 
           //FORMULA HALLAR T1: t=(-d x incremento)+-Raiz(sigma) / |d|^2
           t1=(-1*dQP.dot(incremento))+Math.sqrt(sigma)/Math.pow(dQP.dot(), 2);

           //Sobrecargo el vector a 
           //FORMULA POSICION DEL VECTOR: P+t(Q-P)
           a.copia((dQP.prodEsc(t1)).suma(P));
           
           return true;
               
        }else{
              //---- INTERSECTA EN 2 PUNTOS 
              if(sigma>BasicGeom.CERO){
               
                //Valores t1 y t2 para establecer la posicion de los cortes
                double t1,t2;   

               //Primer punto de interseccion 
               //FORMULA HALLAR T1: t=(-d x incremento)+-Raiz(sigma) / |d|^2
               t1=(-1*dQP.dot(incremento))+Math.sqrt(sigma)/Math.pow(dQP.dot(), 2);

               //Sobrecargo el vector a 
               //FORMULA POSICION DEL VECTOR: P+t(Q-P)
               a.copia((dQP.prodEsc(t1)).suma(P));

               //Segundo punto de interseccion  
               t2=(-1*dQP.dot(incremento))-Math.sqrt(sigma)/Math.pow(dQP.dot(), 2);
               //Sobrecargo el vector b 
               b.copia((dQP.prodEsc(t2)).suma(P));
               
               return true;
            }
        }
        
        //---SIGMA<0 POR LO QUE NO HAY INTERSECCION
        return false;        
    }
    
    
     /*
        True si segmentLine intersecta 
        Vector a y b quedan sobrecargados indicando los vectores por los que corta
    */
    public boolean intersecta(SegmentLine l,Vector a,Vector b){
              
        //Vector P
        Vector P=new Vector(l.a);
        
        //Q-P
        Vector dQP=new Vector(l.b.resta(l.a));
        
        //"triangulo"
        Vector incremento=new Vector(l.a.resta(this.c));        
          
        //Sigma=(d x Triangulo)^2 - |d|^2(|Triangulo|^2 - r^2)
        double sigma=Math.pow(dQP.dot(incremento), 2) -( Math.pow(dQP.dot(), 2)*(Math.pow(incremento.dot(), 2)-Math.pow(r, 2)) ) ;
          
        //---NO INTERSECTAN
        if(sigma<BasicGeom.CERO){
            
           return false;
           
        }else{
           
            //Valores t1 y t2 para establecer la posicion de los cortes
            double t1,t2;   

            //Primer punto de interseccion            
            t1=((-1*dQP.dot(incremento))+Math.sqrt(sigma))/Math.pow(dQP.dot(), 2); 

            //Segundo punto de interseccion  
            t2=((-1*dQP.dot(incremento))-Math.sqrt(sigma))/Math.pow(dQP.dot(), 2);
               
            //A partir de aqui se supone que hay interseccion, pero 0<=t1,t2<=1
            if(t1>=0.0d && t1<=1.0d && t2>=0.0d && t2<=1.0d){
                  
               
                if(BasicGeom.iguales(sigma, BasicGeom.CERO)){
                    //---TANGENTES, INTERSECTA EN 1 SOLO PUNTO
                    
                    //Sobrecargo el vector a 
                    a.copia((dQP.prodEsc(t1)).suma(P)); 
                  
                }else{            
                    //---INTERSECTAN POR 2 PUNTOS
                     
                    //Sobrecargo el vector a                      
                    a.copia((dQP.prodEsc(t1)).suma(P));
                     
                    //Sobrecargo el vector b 
                    b.copia((dQP.prodEsc(t2)).suma(P));
                }
                return true;
                
                
            }else{
                
                return false;
            }
           
        }
    
    }
    /*
        True si rayLine intersecta 
        Vector a y b quedan sobrecargados indicando los vectores por los que corta
    */
    public boolean intersecta(RayLine l,Vector a,Vector b){
         
        //Vector P
        Vector P=new Vector(l.a);
        
         //Q-P
        Vector dQP=new Vector(l.b.resta(l.a));
        
        //"triangulo"
        Vector incremento=new Vector(l.a.resta(this.c));        
        
        //Sigma=(d x Triangulo)^2 - |d|^2(|Triangulo|^2 - r^2)
        double sigma=Math.pow(dQP.dot(incremento), 2) -( Math.pow(dQP.dot(), 2)*(Math.pow(incremento.dot(), 2)-Math.pow(r, 2)) ) ;
        
        //---NO INTERSECTAN
        if(sigma<BasicGeom.CERO){
            
           return false;
           
        }else{
           
            //Valores t1 y t2 para establecer la posicion de los cortes
            double t1,t2;   

            //Primer punto de interseccion 
            t1=((-1*dQP.dot(incremento))+Math.sqrt(sigma))/Math.pow(dQP.dot(), 2); 

            //Segundo punto de interseccion  
            t2=((-1*dQP.dot(incremento))-Math.sqrt(sigma))/Math.pow(dQP.dot(), 2);
              
             
            //A partir de aqui se supone que hay interseccion, pero 0<=t1,t2
            if(t1>=0.0d && t2>=0.0d){
                  
               
                if(BasicGeom.iguales(sigma, BasicGeom.CERO)){
                     
                   //---TANGENTES, INTERSECTA EN 1 SOLO PUNTO
                    a.copia((dQP.prodEsc(t1)).suma(P)); 
                  
                }else{             
                    
                    //---INTERSECTAN POR 2 PUNTOS
                      
                    //Sobrecargo el vector a                      
                    a.copia((dQP.prodEsc(t1)).suma(P));
                     
                    //Sobrecargo el vector b 
                    b.copia((dQP.prodEsc(t2)).suma(P));
                    
                   
                }
                return true;
                
                
            }else{
                return false;
            }
           
        }
    
    }
    
}
