package algGeom2D;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * Representa un Polygon con nVertexs Vertexs.
 */
public class Polygon {

    /**
     * Numero de Vertexs.
     */
    protected int nVertexs;
    /**
     * Vector de Vertexs que pertenecen al Polygon.
     */
    protected ArrayList<Vertex> Vertexs;

    /**
     * Contruye un Polygon por defecto (sin ningun Vertex).
     */
    public Polygon() {
        Vertexs = new ArrayList<Vertex>();
        nVertexs = 0;
    }

    /**
     * Construye un Polygon con un numero de Vertexs nV.
     */
    public Polygon(int nV) {
        Vertexs = new ArrayList<Vertex>(nV);
        nVertexs = nV;
    }

    /**
     * Construye un Polygon como p1. Para ello se utiliza el metodo clone, sin
     * necesidad de reservar memoria, ya que para usar este metodo no es
     * necesario.
     */
    public Polygon(Polygon pl) {
        Vertexs = new ArrayList<Vertex>(pl.Vertexs);
        nVertexs = pl.nVertexs;
    }

    /**
     * Construye un Polygon con el numero de Vertex nV, los cuales estaran en el
     * vector vert.
     */
    public Polygon(ArrayList<Vertex> vert, int nV) {
        Vertexs = new ArrayList<Vertex>(vert);
        nVertexs = nV;
    }

    /**
     * Devuelve una copia del Polygon actual.
     */
    public Polygon copia() {
        Polygon nuevoPolygon = new Polygon(nVertexs);
        nuevoPolygon.Vertexs = new ArrayList<Vertex>(Vertexs);
        nuevoPolygon.nVertexs = nVertexs;
        return nuevoPolygon;
    }

    /**
     * Obtiene una copia del Polygon pl.
     */
    public void copia(Polygon pl) {
        Vertexs.clear();                           //Se limpia el vector.
        Vertexs = new ArrayList<Vertex>(pl.Vertexs);    //Se copia el vector.
        nVertexs = pl.nVertexs;
    }

    /**
     * Establece el Vertex v en la posicion pos del Polygon actual. Teniendo en
     * cuenta que el vector esta indexado desde 0.
     */
    public void modifica(Vertex v, int pos) {
        if (pos >= 0 && pos < nVertexs) {
            Vertex antiguo = new Vertex((Vertex) Vertexs.get(pos));
            antiguo.modificaPolygon(null);
            antiguo.modificaPosicion(-1);
            Vertexs.set(pos, (Vertex) v);
            v.modificaPolygon(this);
            v.modificaPosicion(pos);
        }
    }

    /**
     * Establece el vertice v en la ultima posicion del poligono actual.
     */
    public void anade(Vertex v) {
        Vertexs.add((Vertex) v);
        v.modificaPolygon(this);
        v.modificaPosicion(nVertexs);
        nVertexs++;
    }

    /**
     * Establece el vertice v en la ultima posicion del poligono actual.
     */
    public void anade(Point p) {
        Vertex v = new Vertex(p, this, nVertexs);
        Vertexs.add((Vertex) v);
        nVertexs++;
    }

    /**
     * Devuelve el v?rtice v que ocupa la posici?n pos.
     */
    public Vertex lee(int pos) {
        if (pos >= 0 && pos < nVertexs) {
            return (Vertex) Vertexs.get(pos);
        } else {
            return null;
        }
    }

    /**
     * Devuelve una copia del v?rtice v que ocupa la posici?n pos.
     */
    public Vertex leeAsigna(int pos) {
        if (pos >= 0 && pos < nVertexs) {
            return new Vertex((Vertex) Vertexs.get(pos));
        } else {
            return null;
        }
    }

    /**
     * Devuelve el numero de v?rtices que tiene el pol?gono.
     */
    public int numeroVertices() {
        return nVertexs;
    }

    /**
     * devuelve la arista poligono[i]-poligono[i+1] en sentido antihorario
     */
    public SegmentLine getArista(int i) {
        return new SegmentLine(lee(i), lee((i + 1) % nVertexs));
    }

    /**
     * Crea un poligono a partir de las coordenadas de los vertices ubicados en
     * fichero
     *
     * @param nombre nobre del fichero
     * @throws ErrorAlLeerFichero excepci�n si hay error en la lectura del
     * fichero
     * 
     * Estructura del fichero debe ser:
     *          Posicion X del vertice 0
     *          Posicion Y del vertice 0
     *          Posicion X del vertice 1
     *          Posicion Y del vertice 1
     *          Posicion X del vertice 2
     *          Posicion Y del vertice 2
     *                  ......    
     * 
     */
    public Polygon(String nombre) throws ErrorAlLeerFichero {
        this(); // Construyo primero el poligono
         
        try{
            FileReader f = new FileReader(nombre);
            BufferedReader b = new BufferedReader(f);
            //Todos los puntos se almacenan en la misma fila, sin saltos de linea
            String cadena; 
           
            double coorX;
            double coorY;
            int pos=0;
            
            while((cadena=b.readLine())!=null){
                
                coorX=Double.parseDouble(cadena);
                cadena=b.readLine();
                
                //Si el fichero esta bien, pero la estructura no, lanza excepcion
                if(cadena==null)
                    throw new ErrorAlLeerFichero();
                
                coorY=Double.parseDouble(cadena);
               
                Vertexs.add(new Vertex(new Point(coorX,coorY),this,pos));
                pos++;
            }
            
            b.close();
           
            
        }catch(Exception e){
           throw new ErrorAlLeerFichero();
        }
            

    }

    /**
     * Guarda el poligono en fichero con el mismo formato que utiliza el
     * constructor
     *
     * @param nombre del fichero
     * @throws ErrorAlGuardar, se lanza una excepci�n si exite alg�n problema al
     * abrir el fichero
     */
    public void salvar(String nombre) throws ErrorAlGuardar {
        FileWriter fichero=null;
        try
        {
            fichero = new FileWriter(nombre);
            PrintWriter pw = new PrintWriter(fichero);

            for (int i = 0; i < Vertexs.size(); i++){
                pw.println(Vertexs.get(i).x);
                pw.println(Vertexs.get(i).y);
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

    /**
     * Dado un poligono conocido como covexo, determina si el punto P est� o no
     * dentro del pol�gono
     *
     * @return true si el punto est� dentro
     */
    boolean puntoEnPoligConvex(Point pt) {
    	
        double maxX=0.0;
        double minX=0.0;
        
        double maxY=0.0;
        double minY=0.0;
        
        for(Vertex vAux : Vertexs){
            
            if(vAux.x>maxX){
                maxX=vAux.x;
            }else{
                if(vAux.x<minX){
                    minX=vAux.x;
                }
            }
            
            if(vAux.y>maxY){
                maxY=vAux.y;
            }else{
                if(vAux.y<minY){
                    minY=vAux.y;
                }
            }
            
        }
        
        if((pt.x<=maxX)&&(pt.x>=minX)){
           if((pt.y<=maxY)&&(pt.y>=minY))
               return true;
        }
        return false;
    }

    /**
     * Dado un poligono determinar si es convexo
     *
     * @return true si el pologono es convexo
     */
    boolean convexo() {
        
        for (int i = 0; i < Vertexs.size(); ++i)
        {
            if(!Vertexs.get(i).convexo())
                return false;
        }
            
        return true;
    }


    
    /**
     * Muestra por pantalla la informaci?n del pol?gono.
     */
    public void out() {
        Vertex v = new Vertex();
        for (int i = 0; i < nVertexs; i++) {
            v = (Vertex) Vertexs.get(i);
            v.out();
        }
    }
    
    
    public boolean intersecta(Line r,Vector vectorInterseccion){    
        
        //SegmentLine generado por vertices del poligono
        SegmentLine segmentLine=new SegmentLine();
        
          
        for(Vertex vAux:Vertexs){
            
            //Crear el semento dados los 2 vertices
            segmentLine.asignai(vAux.getPoint());
            segmentLine.asignab((vAux.siguiente()).getPoint());
            
            //Comprobar que intersectan
            if(r.intersecta(segmentLine, vectorInterseccion))
                return true;
            
        }
        
        return false;  
    }
    
    
    public boolean intersecta(RayLine r,Vector vectorInterseccion){
    
        //SegmentLine generado por vertices del poligono
        SegmentLine segmentLine=new SegmentLine();
        
          
        for(Vertex vAux:Vertexs){
            
            //Crear el semento dados los 2 vertices
            segmentLine.asignai(vAux.getPoint());
            segmentLine.asignab((vAux.siguiente()).getPoint());
            
            //Comprobar que intersectan
            if(r.intersecta(segmentLine, vectorInterseccion))
                return true;
            
        }
        
        return false;  
    }

      
    public boolean intersecta(SegmentLine r,Vector vectorInterseccion){
     
        //SegmentLine generado por vertices del poligono
        SegmentLine segmentLine=new SegmentLine();
        
          
        for(Vertex vAux:Vertexs){
            
            //Crear el semento dados los 2 vertices
            segmentLine.asignai(vAux.getPoint());
            segmentLine.asignab((vAux.siguiente()).getPoint());
            
            //Comprobar que intersectan
            if(r.intersecta(segmentLine, vectorInterseccion))
                return true;
            
        }
        
        return false;  
    }
}
