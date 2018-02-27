package algGeom3D;

import algGeom2D.Point;
import algGeom2D.SegmentLine;
import java.util.LinkedList;

public class Polygon3d {

    LinkedList<Vect3d> vertices;

    public Polygon3d(LinkedList<Vect3d> v) {
        vertices = v;
    }
    
    public Polygon3d(){
        vertices=new LinkedList<Vect3d>();
    }
    
    public void addVertices(Vect3d vertex){
        vertices.add(vertex);
    }
    
    
    public boolean isDiagonal(int i0, int i1) {        
           
        int result=((i0 - 1) % vertices.size());
        
        if(result<0)
            result+=vertices.size();
        
        int im = result;
        int ip = (i0 + 1) % vertices.size();
        
         
        Vect3d pi0 = vertices.get(i0);
        Vect3d pi1 = vertices.get(i1);
        Vect3d pim = vertices.get(im);
        Vect3d pip = vertices.get(ip);
        
        Vect3d v1 = pip.resta(pi0);
        Vect3d v2 = pim.resta(pi0);
        Vect3d v3 = pi1.resta(pi0);
         
        if (isBetween(v1, v2, v3)){
            
            SegmentLine s0=new SegmentLine(new Point(pi0.getX(),pi0.getY()),new Point(pi1.getX(),pi1.getY()));
        

            for (int i=0; i<vertices.size(); ++i){

                int j = (i+1) %vertices.size();

                if (!(i == i0 || i == i1 || j == i0 || j == i1)){
                    Vect3d a = vertices.get(i);
                    Vect3d b = vertices.get(j);
                    SegmentLine s1=new SegmentLine((new Point(a.getX(),a.getY())), new Point(b.getX(),b.getY()));

                    if (s0.intersecSegImpropia(s1)) {
                        return false;
                    }
                }

            }
            return true;  
        }
            
        return false;
             
    }
    
    
    public boolean segmentInCone(Vect3d v1, Vect3d v2, Vect3d v3) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * 
     * @param v1
     * @param v2
     * @param v3
     * @return True cuando v3 esta entre v1 y v2 en sentido antihorario 
     */
    
    public boolean isBetween(Vect3d v1, Vect3d v2, Vect3d v3){    
              
        
        double cross1=v1.dot(v2);
        double cross2=v1.dot(v3);
        double cross3=v3.dot(v2);
        
        
        return (cross1 >= 0 && cross2 >= 0 && cross3 >= 0) || (cross1 < 0 && cross2 >= 0 && cross3 >= 0);
        
    }


    

    public void triangulate(LinkedList<Triangle3d> triangles) {
        int n = vertices.size();

        if (n == 3) {
            Triangle3d t = new Triangle3d(vertices.get(0), vertices.get(1), vertices.get(2));
            triangles.add(t);
            return;
        }
        
        
        for (int i = 0; i < n; ++i) {
            for (int j = (i + 2) % n; j != i; j = (j + 1) % n) {
                LinkedList<Vect3d> subList1 = new LinkedList<Vect3d>();
                LinkedList<Vect3d> subList2 = new LinkedList<Vect3d>();
                
                if (split(i, j, subList1, subList2)) {
                    Polygon3d aux1 = new Polygon3d(subList1);
                    Polygon3d aux2 = new Polygon3d(subList2);
                    aux1.triangulate(triangles);
                    aux2.triangulate(triangles);
                    return;
                }
            }
        }

    }
    
    public boolean split(int i0, int i1, LinkedList<Vect3d> subList1, LinkedList<Vect3d> subList2) {
        if (!isDiagonal(i0, i1)) 
            return false;
        
        int n = vertices.size();
        for (int i=i0; i != i1; i = (i + 1) % n) 
            subList1.addLast(vertices.get(i));
        subList1.addLast(vertices.get(i1));
        
        for (int i=i1; i != i0; i = (i + 1) % n) 
            subList2.addLast(vertices.get(i));
        subList2.addLast(vertices.get(i0));    //ERROR-> MODIFICAR i1 por i0    
    
        return true;
    }

}
