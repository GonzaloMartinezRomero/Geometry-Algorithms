package algGeom2D;

import algGeom.BasicGeom;
import algGeom2D.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

class ErrorAlGuardar extends Exception {

    private static final long serialVersionUID = 1L;
}

class ErrorAlLeerFichero extends Exception {

    private static final long serialVersionUID = 2L;
}

public class PointCloud {

    ArrayList<Point> nubepuntos;

    /**
     * Constructor vac�o, sin tama�o predeterminado, debe llenarse con
     * addPunto()
     */
    public PointCloud() {
        nubepuntos = new ArrayList<Point>();
    }

    /**
     *
     * @param tam : crea una nube de puntos aleatoria
     */
    public PointCloud(int tam) {
        nubepuntos = new ArrayList<Point>();
        
        //Nota: Se deberia introducir una semilla
        Random rand=new Random();
        double x,y;
               
        for(int i=0;i<tam;i++){
             x=(rand.nextBoolean())? rand.nextDouble()*BasicGeom.RANGO : (-1)*rand.nextDouble()*BasicGeom.RANGO;
             y=(rand.nextBoolean())? rand.nextDouble()*BasicGeom.RANGO : (-1)*rand.nextDouble()*BasicGeom.RANGO;
            nubepuntos.add(new Point(x,y));
            
        }
    }

    /**
     * Añade un nuevo punto a la nube de puntos
     *
     * @param p: punto de entrada a la nube
     */
    public void AddPunto(Point p) {
        nubepuntos.add(p);
    }

    /**
     *
     * @return devuelve el tamaño de la nube
     */
    public int tama() {
        return nubepuntos.size();
    }

    /**
     * Constructor de la nube a partir de un fichero con el formato x0 y0 x1 y1
     * ... xn-1 yn-1
     *
     * @param nombre del fichero
     * @throws ErrorAlLeerFichero si no existe el fichero se lanza una
     * excepción
     * 
     * Estructura del fichero debe ser:
     *          Posicion X del punto 0
     *          Posicion Y del punto 0
     *          Posicion X del punto 1
     *          Posicion Y del punto 1
     *          Posicion X del punto 2
     *          Posicion Y del punto 2
     * 
     */
    public PointCloud(String nombre) throws ErrorAlLeerFichero {
        this(); // Construyo primero la nube
        
        
            try{
            FileReader f = new FileReader(nombre);
            BufferedReader b = new BufferedReader(f);
            //Todos los puntos se almacenan en la misma fila, sin saltos de linea
            String cadena; 
           
            double coorX;
            double coorY;
            
            while((cadena=b.readLine())!=null){
                
                coorX=Double.parseDouble(cadena);
                cadena=b.readLine();
                
                //Si el fichero esta bien, pero la estructura no, lanza excepcion
                if(cadena==null)
                    throw new ErrorAlLeerFichero();
                
                coorY=Double.parseDouble(cadena);
               
                this.nubepuntos.add(new Point(coorX,coorY));
                
            }
            
            b.close();
             
        }catch(Exception e){
           throw new ErrorAlLeerFichero();
        }
    }

    /**
     * Guarda la nube de puntos en fichero con el mismo formato que utiliza el
     * constructor
     *
     * @param nombre del fichero
     * @throws ErrorAlGuardar, se lanza una excepción si exite algún problema
     * al abrir el fichero
     */
    public void salvar(String nombre) throws ErrorAlGuardar {
        FileWriter fichero=null;
        try
        {
            fichero = new FileWriter(nombre);
            PrintWriter pw = new PrintWriter(fichero);

            for (int i = 0; i < nubepuntos.size(); i++){
                pw.println( nubepuntos.get(i).x);
                pw.println(nubepuntos.get(i).y);
            }
            fichero.close();
        } catch (Exception e) {
            throw new ErrorAlGuardar();
        }finally{
            if(fichero!=null){
                try{
                    fichero.close();
                }catch(Exception e){}
            }
                
        } 
    }

    public Point getPunto(int pos) {
        if ((pos >= 0) && (pos < nubepuntos.size())) {
            return nubepuntos.get(pos);
        }
        return null;
    }
    
    
    public Point puntoCentral(){
        
       //Calcular la media para saber cual es en "centro"
       double mediaX=0.0;
       double mediaY=0.0;
       
       for(Point point:this.nubepuntos){
           mediaX+=point.x;
           mediaY+=point.y;
       }
       
       //Punto medio
       mediaX=mediaX/(double)nubepuntos.size();
       mediaY=mediaY/(double)nubepuntos.size();
       
       
       //Ir viendo que puntos se acercan mas al centro
       Point puntoCentral=new Point(mediaX,mediaY); 
       Point puntoMasCercano=nubepuntos.get(0);
       
       
       for(Point point:nubepuntos){
        if(point.distancia(puntoCentral)<puntoMasCercano.distancia(puntoCentral))
            puntoMasCercano=point;
                    
        }
       
        return puntoMasCercano;
    }
    
    
    public Point menorAbcisa(){
        Point point=nubepuntos.get(0);
        
        for(Point pointAux:this.nubepuntos){
            if(pointAux.x<point.x)
                point=pointAux;
        }
        return point;
    }
    
    public Point mayorAbcisa(){
        Point point=nubepuntos.get(0);
        
        for(Point pointAux:this.nubepuntos){
            if(pointAux.x>point.x)
                point=pointAux;
        }
        return point;
    }
    
    public Point mayorOrdenada(){
        Point point=nubepuntos.get(0);
        
        for(Point pointAux:this.nubepuntos){
            if(pointAux.y>point.y)
                point=pointAux;
        }
        return point;
    }
    
    public Point menorOrdenada(){
        Point point=nubepuntos.get(0);
        
        for(Point pointAux:this.nubepuntos){
            if(pointAux.y<point.y)
                point=pointAux;
        }
        return point;
    }

}
