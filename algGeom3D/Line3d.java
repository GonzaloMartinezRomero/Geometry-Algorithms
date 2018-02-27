package algGeom3D;

public class Line3d extends Edge3d {

    public Line3d(Vect3d o, Vect3d d) {
        super(o, d);
    }
    
    public Line3d(Line3d line){
        super(line.orig,line.dest);
    }

    /**
     * Muestra en pantalla los valores los valores de Edge3d
     */
    @Override
    public void out() {
        System.out.print("Line3d->Origen: ");
        orig.out();
        System.out.print("Line3d->Destino: ");
        dest.out();
    }

    public double distance(Vect3d p) {
        
        //Calculo de u
        Vect3d u=new Vect3d(dest.resta(orig));
        
        //Calculo de AP
        Vect3d AP=new Vect3d(p.resta(orig));
        
        //|u x AP|
        double numerador=(u.xProduct(AP)).modulo();
        
        //|u|
        double denominador=u.modulo();
        
        //|u x AP| / |u|
        return numerador/denominador;
        
    }
}
