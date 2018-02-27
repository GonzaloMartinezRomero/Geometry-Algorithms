/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGeom3D;

import algGeom.BasicGeom;

public class Vect3d {

    double x, y, z;

    public Vect3d(double aa, double bb, double cc) {
        x = aa;
        y = bb;
        z = cc;
    }

    public Vect3d(Vect3d v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    public Vect3d() {
        x = 0;
        y = 0;
        z = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double[] getVert() {
        double[] vt = {x, y, z};
        return vt;
    }
    
    public void setX(double xx){
        x=xx;
    }
    
    public void setY(double yy){
        y=yy;
    }
    
    public void setZ(double zz){
        z=zz;
    }
    
    

    public void setVert(double aa, double bb, double cc) {
        x = aa;
        y = bb;
        z = cc;
    }
    
    public void setVect3d(Vect3d newVect){
        this.setVert(newVect.x,newVect.y ,newVect.z);
    }

    /**
     * result = this - b
     */
    public Vect3d resta(Vect3d b) {
        return new Vect3d(x - b.getX(), y - b.getY(), z - b.getZ());

    }

    /**
     * result = this + b
     */
    public Vect3d suma(Vect3d b) {
        return new Vect3d(x + b.getX(), y + b.getY(), z + b.getZ());

    }

    /**
     * producto por un escalar result = this * valorEscalar
     */
    public Vect3d prodEscalar(double val) {
        return new Vect3d(x * val, y * val, z * val);
    }

    /**
     * producto escalar result = this.dot(b)
     */
    public double dot(Vect3d v) {
        return (x * v.getX() + y * v.getY() + z * v.getZ());
    }

    /**
     * devuelve this X b (producto cruzado)
     */
    public Vect3d xProduct(Vect3d b) {
        return new Vect3d(y * b.getZ() - z * b.getY(),
                z * b.getX() - x * b.getZ(),
                x * b.getY() - y * b.getX());
    }

    /**
     * devuelve la longitud del vector
     */
    public double modulo() {
        return Math.sqrt(x * x + y * y + z * z);
    }
    
    public double distance(Vect3d p) {
        return Math.sqrt(Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2) + Math.pow(p.z - z, 2));
    }

    public boolean collinear(Vect3d a, Vect3d b) {
        
        Triangle3d triangle=new Triangle3d(this, a, b);
       
        return triangle.area()<BasicGeom.CERO;
    }
    
    /**
     * Muestra en pantalla los valores de las coordenadas del Point.
     */
    public void out() {
        System.out.print("Coordenada x: ");
        System.out.println(x);
        System.out.print("Coordenada y: ");
        System.out.println(y);
        System.out.print("Coordenada z: ");
        System.out.println(z);
    }
    
    @Override
    public boolean equals(Object obj){
        
        Vect3d vect3dAux=(Vect3d)obj;
        
        if(BasicGeom.iguales(this.x, vect3dAux.x)){
            if(BasicGeom.iguales(this.y, vect3dAux.y)){
                if(BasicGeom.iguales(this.z, vect3dAux.z)){
                    return true;
                }        
            }
        }
        return false;
                
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }

}
