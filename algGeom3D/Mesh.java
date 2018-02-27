package algGeom3D;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public final class Mesh {

    ArrayList<Face> caras;
    ArrayList<Vect3d> vertices;
    boolean setNormal;

    /**
     * Construye una mesh a partir de un fichero Obj
     *
     * @param filename: nombre del objeto
     * @throws IOException
     */
    public Mesh(String filename) throws IOException {
        caras = new ArrayList<Face>();
        vertices = new ArrayList<Vect3d>();
        setNormal = false;
        String obj = "obj";

        File archivo;
        FileReader fr;
        BufferedReader reader;

        archivo = new File(filename);
        fr = new FileReader(archivo);
        reader = new BufferedReader(fr);

        String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
        if (!extension.equals(obj)) {
            System.out.println("Solo se soportan modelos en obj ");
        } else {
            loadobject(reader);
            setNormales();
            setNormal = true;
        }
    }
    
    public Mesh() {
        caras = null;
        vertices = null;
        setNormal = false;
    }
    
    public void loadNewMesh(ArrayList<Face> faceList,ArrayList<Vect3d> vertexList){
        
        caras=new ArrayList(faceList);
        
        vertices=new ArrayList(vertexList);
        
        setNormal=true;
        
    }
    
    
    

    /**
     *
     * @return una lista de triángulos del modelo
     */
    public ArrayList<Triangle3d> getTriangulos() {
        ArrayList<Triangle3d> triangulos = new ArrayList<Triangle3d>();
        for (int i = 0; i < caras.size(); i++) {
            Face f = caras.get(i);
            Vect3d a = vertices.get(f.v1 - 1);
            Vect3d b = vertices.get(f.v2 - 1);
            Vect3d c = vertices.get(f.v3 - 1);
            triangulos.add(new Triangle3d(a, b, c));
        }
        return triangulos;
    }

    /**
     *
     * @return Devuelve las coordenadas de todos los vértices de los triángulos en la mesh
     */
    public double[] getVerticesTriangulos() {
        double[] tri = new double[caras.size() * 9];
        for (int i = 0; i < caras.size(); i++) {
            Face f = caras.get(i);
            Vect3d a = vertices.get(f.v1 - 1);
            tri[i * 9] = a.x;
            tri[i * 9 + 1] = a.y;
            tri[i * 9 + 2] = a.z;
            Vect3d b = vertices.get(f.v2 - 1);
            tri[i * 9 + 3] = b.x;
            tri[i * 9 + 4] = b.y;
            tri[i * 9 + 5] = b.z;
            Vect3d c = vertices.get(f.v3 - 1);
            tri[i * 9 + 6] = c.x;
            tri[i * 9 + 7] = c.y;
            tri[i * 9 + 8] = c.z;
        }
        return tri;
    }

    /**
     *
     * @return devuelve las coordenadas de los vértices de la mesh sin ordenación aparente
     */
    public double[] getVertices() {
        double[] vertex = new double[3 * vertices.size()];
        int j = 0;
        for (int i = 0; i < vertices.size(); i++) {
            vertex[j++] = vertices.get(i).x;
            vertex[j++] = vertices.get(i).y;
            vertex[j++] = vertices.get(i).z;

        }
        return vertex;
    }

    /**
     *
     * @return los índices de las caras
     */
    public int[] getIndiceFaces() {
        int[] faces = new int[3 * caras.size()];
        int j = 0;
        for (int i = 0; i < caras.size(); i++) {
            faces[j++] = caras.get(i).v1;
            faces[j++] = caras.get(i).v2;
            faces[j++] = caras.get(i).v3;
        }
        return faces;
    }

    /**
     *
     * @param i índice del triángulo
     * @return el Triangulo con dicho índice
     */
    public Triangle3d getTriangulo(int i) {
        Face f = caras.get(i);
        Vect3d a = vertices.get(f.v1 - 1);
        Vect3d b = vertices.get(f.v2 - 1);
        Vect3d c = vertices.get(f.v3 - 1);
        return new Triangle3d(a, b, c);
    }

    /**
     *
     * @param i índice del vertice
     * @return el vertice
     */
    public Vect3d getVertice(int i) {
        return new Vect3d(vertices.get(i).getX(), vertices.get(i).getY(), vertices.get(i).getZ());
    }

    /**
     * establece la normal en todos los triángulos
     */
    public void setNormales() {
        for (int i = 0; i < caras.size(); i++) {
            Face f = caras.get(i);
            Vect3d a = vertices.get(f.v1 - 1);
            Vect3d b = vertices.get(f.v2 - 1);
            Vect3d c = vertices.get(f.v3 - 1);
            Triangle3d t = new Triangle3d(a, b, c);
            f.setNormal(t.normal());
        }
        setNormal = true;
    }

    /**
     *
     * @return todas las normales según la disposición de los triángulos
     */
    public double[] getNormales() {
        double[] nor = new double[3 * caras.size()];
        if (!setNormal) {
            setNormales();
        }
        for (int i = 0; i < caras.size(); i++) {
            Face f = caras.get(i);
            Vect3d b = f.getNormal();
            nor[i * 3] = b.x;
            nor[i * 3 + 1] = b.y;
            nor[i * 3 + 2] = b.z;
        }
        return nor;
    }

    /**
     *
     * @param r el rayo que choca
     * @param p el punto de intersección si existe
     * @param t el triángulo de interseccion si existe
     * @return true si hay intersección con el modelo
     */
    public boolean rayCast(Ray3d r, Vect3d p, Triangle3d t) {
       
       return t.rayTriangle3d(r, p);
        
    }

    /**
     *
     * @param lr el conj. de rayos
     * @param lp el conj. de puntos de intersección si existe
     * @param lt el conj. de triángulos de interseccion si existe
     * @return true si hay intersección con el modelo
     */
    public boolean rayCast(ArrayList<Ray3d> lr, ArrayList<Vect3d> lp, ArrayList<Triangle3d> lt) {
        
        Vect3d puntoColision=new Vect3d();
        
        for(Ray3d rayAux : lr){
            
            for(Triangle3d triangleAux: lt){
                
                if(triangleAux.rayTriangle3d(rayAux, puntoColision)){
                    lp.add(puntoColision);
                }
                
            }
            
        }
        
        //Si la lista de puntos esta vacia, no ha habido ninguna colision
        return lp.isEmpty();
        
    }

    /**
     * carga el modelo de la variable fichero
     *
     * @param br variable fichero
     */
    public void loadobject(BufferedReader br) {
        String line = "";

        try {
            while ((line = br.readLine()) != null) {
                line = line.trim();
                line = line.replaceAll("  ", " ");
                if (line.length() > 0) {
                    if (line.startsWith("v ")) {
                        float[] vert = read3Floats(line);
                        Vect3d punto = new Vect3d(vert[0], vert[1], vert[2]);
                        vertices.add(punto);
                    } else if (line.startsWith("vt")) {

                        continue;

                    } else if (line.startsWith("vn")) {

                        continue;
                    } else if (line.startsWith("f ")) {
                        int[] faces = read3Integer(line);
                        caras.add(new Face(faces));
                    } else if (line.startsWith("g ")) {
                        continue;
                    } else if (line.startsWith("usemtl")) {
                        continue;
                    } else if (line.startsWith("mtllib")) {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("GL_OBJ_Reader.loadObject() failed at line: " + line);
        }

        System.out.println("Lector de Obj: vértices " + vertices.size()
                + " caras " + caras.size());

    }

    public int getSizeCaras() {
        return caras.size();
    }

    public int getSizeVertices() {
        return vertices.size();
    }

    private int[] read3Integer(String line) {
        try {
            StringTokenizer st = new StringTokenizer(line, " ");
            st.nextToken();
            if (st.countTokens() == 2) {
                return new int[]{Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 0};
            } else {
                return new int[]{Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken())};
            }
        } catch (NumberFormatException e) {
            System.out.println("GL_OBJ_Reader.read3Floats(): error on line '" + line + "', " + e);
            return null;
        }
    }

    private float[] read3Floats(String line) {
        try {
            StringTokenizer st = new StringTokenizer(line, " ");
            st.nextToken();
            if (st.countTokens() == 2) {
                return new float[]{Float.parseFloat(st.nextToken()),
                    Float.parseFloat(st.nextToken()),
                    0};
            } else {
                return new float[]{Float.parseFloat(st.nextToken()),
                    Float.parseFloat(st.nextToken()),
                    Float.parseFloat(st.nextToken())};
            }
        } catch (NumberFormatException e) {
            System.out.println("GL_OBJ_Reader.read3Floats(): error on line '" + line + "', " + e);
            return null;
        }
    }

    public AABB getAABB() {
        // reset min/max points
        double minx = 9e99;
        double miny = 9e99;
        double minz = 9e99;
        double maxx = 0;
        double maxy = 0;
        double maxz = 0;

        for (int v = 0; v < vertices.size(); v++) {
            Vect3d vv = vertices.get(v);
            if (vv.x > maxx) {
                maxx = vv.x;
            }
            if (vv.y > maxy) {
                maxy = vv.y;
            }
            if (vv.z > maxz) {
                maxz = vv.z;
            }
            if (vv.x < minx) {
                minx = vv.x;
            }
            if (vv.y < miny) {
                miny = vv.y;
            }
            if (vv.z < minz) {
                minz = vv.z;
            }
        }
        Vect3d min = new Vect3d(minx, miny, minz);
        Vect3d max = new Vect3d(maxx, maxy, maxz);
        return new AABB(min, max);
    }
    
    /*
        True si el triangulo esta  por debajo de la malla
    */
    private boolean underPlane(Plane p, Triangle3d triangle){
        
        Triangle3d planeTriangle=new Triangle3d(p.a,p.b,p.c);
        
        
        if(Triangle3d.posicionPunto.POSITIV0!=planeTriangle.classify(triangle.a)){
            
             if(Triangle3d.posicionPunto.POSITIV0!=planeTriangle.classify(triangle.b)){
            
                 if(Triangle3d.posicionPunto.POSITIV0!=planeTriangle.classify(triangle.c)){
                    
                    return true;
                }
            }
        
        }
         
        return false;
        
    }
    
    
    
    
    public boolean crossSection(Plane p,Mesh newMesh){
        
        Plane.IntersectType intersectType= new Plane.IntersectType();
        Triangle3d triangleAux;
        Triangle3d planeTriangle=new Triangle3d(p.a,p.b,p.c);
        
        
        Map<Integer,Integer> faceMap=new TreeMap();
        ArrayList<Face> faceList=new ArrayList();
        ArrayList<Vect3d> vertexList=new ArrayList();
       
        ArrayList<Vect3d> tapaVertex=new ArrayList();
        
        //Loop sobre las caras del modelo
        for(int i=0;i<caras.size();i++){
           
            triangleAux=this.getTriangulo(i);
            
            //Selecciono los triangulos que esten por debajo de la malla
            if(underPlane(p,triangleAux)){
                
                Face face=new Face(caras.get(i));
                
                //Si mi nueva mesh no contiene el vertice asociado a la cara se a�ade
                if(!faceMap.containsKey(face.v1)){
                  
                    //A�ade el nuevo vertice
                    vertexList.add(vertices.get(face.v1 - 1));
                    //Actualiza la correspondencia entre el vertice y la cara
                    faceMap.put(face.v1,vertexList.size());
                    
                    face.v1=vertexList.size();                     
                    
                }else{
                    //En caso de estar ya a�adido, se actualiza la posicion de la cara respecto a la nueva malla
                    face.v1=faceMap.get(face.v1);
                }
                
                if(!faceMap.containsKey(face.v2)){

                    vertexList.add(vertices.get(face.v2 - 1));
                   
                    faceMap.put(face.v2,vertexList.size());

                    face.v2=vertexList.size(); 
                    
                }else{
                    face.v2=faceMap.get(face.v2);
                }
                
                if(!faceMap.containsKey(face.v3)){

                  vertexList.add(vertices.get(face.v3 - 1));
                  
                  faceMap.put(face.v3,vertexList.size());

                  face.v3=vertexList.size(); 
                    
                }else{
                    face.v3=faceMap.get(face.v3);
                }
                 
                faceList.add(face);
                  
            }   
            
            //Selecciono los triangulos que colisionan con el plano
            if(p.trianglePlane(triangleAux,intersectType)){
                
                //Interseccion que corta el triangulo 
                if(intersectType.getType()==Plane.TypeOfIntersection.Line){
                    
                    //Guardo el segmento de la colision
                    Line3d lineIntersect=(Line3d)intersectType.getIntersection();
                    
                    //Acoto el destino para generar un segmento que intersecte de forma impropia
                    lineIntersect.dest=lineIntersect.dest.suma(lineIntersect.orig);
                    
                    //Genero el segmento a partir de la recta
                    Segment3d collisionSegment=new Segment3d(lineIntersect.orig,lineIntersect.dest);

                    //Guardar los triangulos que se generan de la colision
                    ArrayList<Triangle3d> tList=new ArrayList();
                    triangleAux.split(tList, collisionSegment);
                    
                   
                    for(Triangle3d triangle:tList){
                        
                        //Quedarse solo con los triangulos que queden por debajo del plano
                        if(underPlane(p, triangle) ){

                            //Almacenar los vertices que estan justo en el plano 
                            if(planeTriangle.classify(triangle.a)==Triangle3d.posicionPunto.ENCIMA){
                                tapaVertex.add(new Vect3d(triangle.a));
                            }
                            if(planeTriangle.classify(triangle.b)==Triangle3d.posicionPunto.ENCIMA){
                                tapaVertex.add(new Vect3d(triangle.b));
                            }
                            if(planeTriangle.classify(triangle.c)==Triangle3d.posicionPunto.ENCIMA){
                                tapaVertex.add(new Vect3d(triangle.c));
                            }  
                            
                            
                           vertexList.add(new Vect3d(triangle.a));
                           vertexList.add(new Vect3d(triangle.b));
                           vertexList.add(new Vect3d(triangle.c));

                           faceList.add(new Face((vertexList.size())-2,(vertexList.size())-1,vertexList.size(),triangle.normal()));

                       }

                    }
                
                }else{
                     
                    if(planeTriangle.classify(triangleAux.a)==Triangle3d.posicionPunto.ENCIMA){
                        tapaVertex.add(new Vect3d(triangleAux.a));
                    }
                    if(planeTriangle.classify(triangleAux.b)==Triangle3d.posicionPunto.ENCIMA){
                        tapaVertex.add(new Vect3d(triangleAux.b));
                    }
                    if(planeTriangle.classify(triangleAux.c)==Triangle3d.posicionPunto.ENCIMA){
                        tapaVertex.add(new Vect3d(triangleAux.c));
                    }  
                    
                    //Interseccion que no corta el triangulo, pero pasa por el vertice
                    vertexList.add(new Vect3d(triangleAux.a));
                    vertexList.add(new Vect3d(triangleAux.b));
                    vertexList.add(new Vect3d(triangleAux.c));

                   faceList.add(new Face((vertexList.size())-2,(vertexList.size())-1,vertexList.size(),triangleAux.normal()));

                }
                 
            }
            
        } 
        
        //En caso de haber colision plano-mesh
        if(!faceList.isEmpty()){
            
           //a�ado los de la tapa
           LinkedList<Triangle3d> triangleTape=new LinkedList<Triangle3d>();        
          
           //Genero las polilineas para dar al poligono los vertices ordenados
           Polyline3d poliline=new Polyline3d();
           for(int i=0;i<tapaVertex.size();i+=2){
               
               poliline.add(new Segment3d(tapaVertex.get(i), tapaVertex.get((i+1)%tapaVertex.size())));             
           }
           
           
           Polygon3d tapa=new Polygon3d(poliline.getAll());
           
            //-----------   EL METODO DE TRIANGULACION NO FUNCIONA CORRECTAMENTE ----------------
            /*
                    LA CAUSA ES QUE POLYLINE SOLO ME GENERA 4 TRIANGULOS 
            
            */
           tapa.triangulate(triangleTape);
            
           //Guardo en newMesh los ultimos triangulos pertenecientes a la malla
            for(Triangle3d triangle:triangleTape){
                
                vertexList.add(new Vect3d(triangle.a));
                vertexList.add(new Vect3d(triangle.b));
                vertexList.add(new Vect3d(triangle.c));

                faceList.add(new Face((vertexList.size())-2,(vertexList.size())-1,vertexList.size(),triangle.normal()));
            }
            
            //Cargo la nueva mesh generada en newMesh
            newMesh.loadNewMesh(faceList, vertexList);
            
            return true;
        }
        return false;

    }
    

}
