package algGeom3D;

public class Edge3d {

    Vect3d orig;
    Vect3d dest;

    public Edge3d() {
        orig = new Vect3d();
        dest = new Vect3d();
    }

    public Edge3d(Vect3d o, Vect3d d) {
        orig = new Vect3d(o);
        dest = new Vect3d(d);
    }

    public Vect3d getPoint(double t) {
        return new Vect3d(orig.suma((dest.resta(orig).prodEscalar(t))));

    }
    
    public Vect3d getOrigin() {
        return orig;
    }
    
    public Vect3d getDestination() {
        return dest;
    }
    /**
     * Muestra en pantalla los valores los valores de Edge3d
     */
    public void out() {
        System.out.print("Edge3d->Origen: ");
        orig.out();
        System.out.print("Edge3d->Destino: ");
        dest.out();
    }
}
