package algGeom3D;

import algGeom.BasicGeom;

public class AABB {

    Vect3d min; //menor x,y,z
    Vect3d max; //max x, y, z
    
   private class PuntosCorte{
       
        double t0[];//Espacio reservado para x,y,z
        double t1[];
        double tIntersect;
        
        public PuntosCorte(){
            t0=new double[3];
            t1=new double[3];
            tIntersect=0.0d;
        }
       
   }

    public void setMin(Vect3d min) {
        this.min = min;
    }

    public void setMax(Vect3d max) {
        this.max = max;
    }

    public AABB(Vect3d menor, Vect3d mayor) {
        min = menor;
        max = mayor;
    }

    public AABB(Cloud3d p) {
        
        min = new Vect3d(BasicGeom.INFINITO, BasicGeom.INFINITO, BasicGeom.INFINITO);
        max = new Vect3d(-BasicGeom.INFINITO, -BasicGeom.INFINITO, -BasicGeom.INFINITO);
        for (int i = 0; i < p.tama(); i++) {
            Vect3d aux = p.getPunto(i);
            if (min.getX() > aux.getX()) {
                min.setX(aux.getX());
            }
            if (min.getY() > aux.getY()) {
                min.setY(aux.getY());
            }
            if (min.getZ() > aux.getZ()) {
                min.setZ(aux.getZ());
            }
            if (max.getX() < aux.getX()) {
                max.setX(aux.getX());
            }
            if (max.getY() < aux.getY()) {
                max.setY(aux.getY());
            }
            if (max.getZ() < aux.getZ()) {
                max.setZ(aux.getZ());
            }
        }
    }

    public AABB(Mesh m) {
        min = new Vect3d(BasicGeom.INFINITO, BasicGeom.INFINITO, BasicGeom.INFINITO);
        max = new Vect3d(-BasicGeom.INFINITO, -BasicGeom.INFINITO, -BasicGeom.INFINITO);
        for (int i = 0; i < m.getSizeVertices(); i++) {
            Vect3d aux = m.getVertice(i);
            if (min.getX() > aux.getX()) {
                min.setX(aux.getX());
            }
            if (min.getY() > aux.getY()) {
                min.setY(aux.getY());
            }
            if (min.getZ() > aux.getZ()) {
                min.setZ(aux.getZ());
            }
            if (max.getX() < aux.getX()) {
                max.setX(aux.getX());
            }
            if (max.getY() < aux.getY()) {
                max.setY(aux.getY());
            }
            if (max.getZ() < aux.getZ()) {
                max.setZ(aux.getZ());
            }
        }
    }

    /**
     * devuelve el punto de la esquina inferior
     */
    public Vect3d getMin() {
        return min;
    }

    /**
     * devuelve el punto de la esquina superior
     */
    public Vect3d getMax() {
        return max;
    }

    public boolean rayCast(Ray3d r, Vect3d ptoCorte) {
    
        PuntosCorte puntosCorte=new PuntosCorte();
        if(AABB_Algorithm(r,puntosCorte)){
           
            Vect3d d=new Vect3d(r.dest.resta(r.orig)); 
          
            ptoCorte.setVect3d(r.orig.suma(d.prodEscalar(puntosCorte.tIntersect)));            
            
            return true;
        }
        return false;
    }
    
    private boolean AABB_Algorithm(Ray3d r,PuntosCorte puntosCorte){        
        
        Vect3d d=new Vect3d(r.dest.resta(r.orig));
        
        double tNear=-BasicGeom.INFINITO;
        double tFar=BasicGeom.INFINITO;
        
        double epsilon=BasicGeom.CERO;
        
        double tmp;
        
        for(int i=0;i<3;i++){
        
            if(Math.abs(d.getVert()[i])<epsilon){
                
                if((r.orig.getVert()[i]<this.min.getVert()[i])||(r.orig.getVert()[i]<this.max.getVert()[i])){
                   
                    return false;
                }
            }
          
            
            puntosCorte.t0[i]=(this.min.getVert()[i] - r.orig.getVert()[i])/d.getVert()[i];
            puntosCorte.t1[i]=(this.max.getVert()[i] - r.orig.getVert()[i])/d.getVert()[i];
        
            
            if(puntosCorte.t0[i]>puntosCorte.t1[i]){
                
                tmp=puntosCorte.t0[i];
                puntosCorte.t0[i]=puntosCorte.t1[i];
                puntosCorte.t1[i]=tmp;
            }
          
            
            if(puntosCorte.t0[i]>tNear){
                tNear=puntosCorte.t0[i];
            }

            if(puntosCorte.t1[i]<tFar){
                tFar=puntosCorte.t1[i];
            }
           
            
            if(tNear>tFar){
                return false;
            }
             
            if(tFar<0.0d){
                return false;
            }
        
        }
       
        if(tNear>0.0d)
            puntosCorte.tIntersect=tNear;
        else
            puntosCorte.tIntersect=tFar;
          
        return true;
       
    }
    

}
