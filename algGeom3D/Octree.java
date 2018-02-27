/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGeom3D;
 
import java.util.ArrayList;

/**
 *
 * @author Drebin
 */
public class Octree {
 
     
    private class Node{
    
        //Referencia al nodo padre
        private Node father;
        
        //EEDD para almacenar los objetos
        private ArrayList<Object> container;
        
        //AABB
        private Vect3d min,max;
        
        //Nivel del nodo
        private int level;
         
        //Hijos
        private Node [] children;
        private boolean isChildrenCreated;
        
        //Indica que el nodo se encuentra a nivel profundidad maxima
        private boolean isFinalNode;
        
        //Numero maximo de objetos por nodo
        private int maxObjects;
        
       
        //Nivle maximo estandar definido por el usuario
        private int limitLevel;
        
         //Limite de expasion en caso de tener que dividir
        private int maxLevel;
        
        
        public Node(){}
        
        
        /**
         * 
         * @param _father  Puntero al padre
         * @param _level   Nivel actual
         * @param _max     AABB max
         * @param _min     AABB min
         * @param _maxObjects   Maximo numero objetos por nodo
         * @param _maxLevel     Nivel maximo considerado para almacenar datos
         * @param _limitLevel   Nivel maximo considerado para seguir exapandiendo en caso de supererar maxObjects
         */
        public Node(Node _father,int _level,Vect3d _max,Vect3d _min,int _maxObjects,int _maxLevel,int _limitLevel){
            
            father=_father;
            level=_level;
            max=_max;
            min=_min;  
            maxLevel=_maxLevel;
            isFinalNode=(level>=_maxLevel);            
            isChildrenCreated=false;
            maxObjects=_maxObjects; 
            limitLevel=_limitLevel;           
            
            if(isFinalNode){                                
                container=new ArrayList<Object>();                
            }            
        }
        
        //Devuelve la caja envolvente que hace referencia al nodo
        public AABB getAABB(){
            return new AABB(min,max);
        }
                
        public void insertarDato(Vect3d p){
           
            //Si es nodo final, significa que he llegado al nivel establecido y almaceno el objeto
            if(isFinalNode){    
                    
                //Si el contenedor supera el numero maximo de objetos, hay que volver a dividir
                if(container.size()>maxObjects){
                    
                    //Si se iguala el limitLevel, dejo de dividir e indico que el octree no es optimo 
                    if(level==limitLevel){
                        
                        creacionStatus=false;
                        container.add(p);
                        
                    }else{
                        
                        isFinalNode=false;
                    
                        //Genero los hijos
                        generarHijos();

                        //Redistribuyo los datos entre los hijos
                        for(Object obj:container){

                            seleccionarHijo((Vect3d)obj);
                        }

                        //Introduzco el ultimo dato
                        seleccionarHijo(p);

                        //Elimino el contenedor del nodo actual --------------->>¿LA ELIMINACION ES CORRECTA?
                        container=null;
                    }                    
                   
                }else{
                    container.add(p);
                }
                
                               
            }else{
                
                //Caso contrario genero 8 hijos en caso de no estar ya generados
                if(!isChildrenCreated){
                    generarHijos();
                }
                
                //Seleccionar el hijo al que se le va a introducir el dato
                seleccionarHijo(p);
                 
            }
                
        }
        
        
        private void seleccionarHijo(Vect3d p){
            
            double coorX=p.getX(); double coorY=p.getY();
            double coorZ=p.getZ();

            double minX=min.getX();  double minY=min.getY();
            double minZ=min.getZ();
            double maxX=max.getX();  double maxY=max.getY();
            double maxZ=max.getZ();
            double medX=(maxX+minX)/2.0d; double medY=(maxY+minY)/2.0d;
            double medZ=(maxZ+minZ)/2.0d;

            if(coorY >= medY){

                if(coorX >= medX){
                     if(coorZ >= medZ){
                         children[7].insertarDato(p); 
                     }
                     else{ 
                         children[5].insertarDato(p); 
                     }
                 }else {
                     if(coorZ >= medZ){ 
                         children[6].insertarDato(p); 
                     }
                     else { 
                         children[4].insertarDato(p);
                     }
                 }
             }else{
                 if(coorX >= medX){

                     if(coorZ >= medZ){ 
                         children[3].insertarDato(p); 
                     }
                     else{ 
                         children[1].insertarDato(p); 
                     }
                 } else{
                         if(coorZ >= medZ){ 
                             children[2].insertarDato(p); 
                         }
                         else { 
                             children[0].insertarDato(p); 
                         }
                     }
                 }
            
            
        }
        
        
        
        private void generarHijos(){
            
            isChildrenCreated=true;
            
            children=new Node[8];
            
            double minX=min.getX();  double minY=min.getY();
            double minZ=min.getZ();
            double maxX=max.getX();  double maxY=max.getY();
            double maxZ=max.getZ();
            double medX=(maxX+minX)/2.0d; double medY=(maxY+minY)/2.0d;
            double medZ=(maxZ+minZ)/2.0d;
            
            children[6] = new Node(this,level+1,new Vect3d(medX,maxY,maxZ),new Vect3d(minX,medY,medZ),maxObjects,maxLevel,limitLevel);
            children[4] = new Node(this,level+1,new Vect3d(medX,maxY,medZ),new Vect3d(minX,medY,minZ),maxObjects,maxLevel,limitLevel);
            children[5] = new Node(this,level+1,new Vect3d(maxX,maxY,medZ),new Vect3d(medX,medY,minZ),maxObjects,maxLevel,limitLevel);
            children[7] = new Node(this,level+1,new Vect3d(maxX,maxY,maxZ),new Vect3d(medX,medY,medZ),maxObjects,maxLevel,limitLevel);
            children[2] = new Node(this,level+1,new Vect3d(medX,medY,maxZ),new Vect3d(minX,minY,medZ),maxObjects,maxLevel,limitLevel);
            children[0] = new Node(this,level+1,new Vect3d(medX,medY,medZ),new Vect3d(minX,minY,minZ),maxObjects,maxLevel,limitLevel);
            children[1] = new Node(this,level+1,new Vect3d(maxX,medY,medZ),new Vect3d(medX,minY,minZ),maxObjects,maxLevel,limitLevel);
            children[3] = new Node(this,level+1,new Vect3d(maxX,medY,maxZ),new Vect3d(medX,minY,medZ),maxObjects,maxLevel,limitLevel);
            
            
        }
                
        
        public Node[] getChildren(){
            return (isChildrenCreated) ? children : null;
        }
        
    }
    
       
    private int maxLevel;
    private Node root;
    private boolean creacionStatus;
    
    
    public Octree(){
        
        maxLevel=1;
        root=new Node();        
        creacionStatus=true;
        
    }
    /**
     * 
     * @param cloud
     * @param _maxLevel Nivel de profundidad limite
     * @param maxObjects Numero max objetos/nodo
     */
    public Octree(Cloud3d cloud,int _maxLevel,int maxObjects){        
       
        maxLevel=_maxLevel;
        AABB aabb=new AABB(cloud);
        creacionStatus=true;
        
        root=new Node(null,0,aabb.max,aabb.min,maxObjects,maxLevel,maxLevel+2);
        
        for(Vect3d aux : cloud.nubepuntos ){
            
            root.insertarDato(aux);
        }
        
    }
    
    /**
     * 
     * @param mesh
     * @param _maxLevel Nivel profundidad maximo
     * @param maxObjects  Maxmimo numero objetos/nodo
     */
      public Octree(Mesh mesh,int _maxLevel,int maxObjects){
       
        maxLevel=_maxLevel;
        AABB aabb=new AABB(mesh);
        creacionStatus=true;
        root=new Node(null,0,aabb.max,aabb.min,maxObjects,maxLevel,maxLevel+2);
        
        for(Vect3d aux : mesh.vertices){
            
            root.insertarDato(aux); 
            
        }
        
    }
    
    
    
    
    
    public int getLimite(){
        return maxLevel;
    }
    
    //Recorre todos los nodos del octree guardando sus AABB
    private void busquedaNodosHoja(Node node,ArrayList<AABB> arrayList){
        
        Node [] children=node.getChildren();
        
        if(children!=null){
            
            busquedaNodosHoja(children[0], arrayList);
            busquedaNodosHoja(children[1], arrayList);
            busquedaNodosHoja(children[2], arrayList);
            busquedaNodosHoja(children[3], arrayList);        
            busquedaNodosHoja(children[4], arrayList);        
            busquedaNodosHoja(children[5], arrayList);
            busquedaNodosHoja(children[6], arrayList);
            busquedaNodosHoja(children[7], arrayList);
        }
        
        arrayList.add(node.getAABB());
        
    }
    
    
    public ArrayList<AABB> getAABB(){
        
      ArrayList<AABB> listaAABB=new ArrayList();
      
      busquedaNodosHoja(root,listaAABB);
      
      return listaAABB;
        
    }
 
    /*
        En caso de haber superado el numero maximo de vertices por nodo y
        el limite de expansion,se considera que el octree es correcto
        pero no es optimo
    */
    public boolean isOptimus(){
        return creacionStatus;
    }
   
    
}
