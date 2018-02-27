//package algGeom;
//
// 
//import algGeom3D.*; 
//import com.sun.opengl.util.Animator;
//import java.awt.Dimension;
//import java.awt.Frame;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
//import java.awt.event.MouseWheelEvent;
//import java.awt.event.MouseWheelListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.util.ArrayList;
//import java.util.Random;
//import javax.media.opengl.GL;
//import javax.media.opengl.GLAutoDrawable;
//import javax.media.opengl.GLCanvas;
//import javax.media.opengl.GLEventListener;
//import javax.media.opengl.glu.GLU;
//
//public class Practice2 implements GLEventListener,
//        MouseListener,
//        MouseMotionListener,
//        MouseWheelListener {
//    
//    
//    
//    
//    public class Practica2a{
//        
//        private Cloud3d cloud;
//        private Plane plane;
//        private DrawPlane drawPlane;
//        private DrawTriangle3d drawTriangle,drawTriangleProyect;
//        private Triangle3d triangle;
//        private DrawVect3d drawVector;
//        private ArrayList<Vect3d> puntosLadoPositivo;
//        
//        //Evitan tener que calcular en cada frame los datos requeridos para realizar unicamente el dibujado en pantalla
//        private boolean seguirLlenando,elegirPuntos,elegirPuntoLejano,interseccionPlano;
//        private  Random rand;
//        private Vect3d puntoMasLejano,puntoMasLejanoNeg;
//        private DrawRay3d rayo;
//        private Vect3d interseccion;
//        private DrawVect3d puntoInterseccion;
//        
//        
//        public Practica2a(){
//            
//            //Crear nube 50 puntos    
//            cloud=new Cloud3d(50,50.0d);
//            
//             //Con los 3 primeros puntos generar un plano
//            plane=new Plane(cloud.getPunto(0),cloud.getPunto(1),cloud.getPunto(2) ,true);
//            
//            drawPlane= new DrawPlane(plane);
//            
//            triangle=new Triangle3d();
//            
//            drawVector=new DrawVect3d();
//             
//            puntosLadoPositivo=new ArrayList<Vect3d>();
//            
//            seguirLlenando=elegirPuntos=elegirPuntoLejano=interseccionPlano=true;
//            
//            rand=new Random(BasicGeom.semilla);
//            
//            puntoMasLejano=puntoMasLejanoNeg=null;
//            
//            interseccion=new Vect3d(0,0,0);
//        }
//        
//        
//        public void ejecutar(GL gl){
//          
//            //Plano generado por el triangulo    
//            drawPlane.drawObjectC(gl,1,0,1);
//             
//            //Triangulo para clasificar los puntos
//            triangle.setA(plane.getVectorA());
//            triangle.setB(plane.getVectorB());
//            triangle.setC(plane.getVectorC());
//            
//            
//            //Clasificar los puntos
//            for(int i=3;i<cloud.tama();++i){
//
//                Triangle3d.posicionPunto pos=triangle.classify(cloud.getPunto(i));
//                drawVector.setVector(cloud.getPunto(i));  
//                
//                if(pos==Triangle3d.posicionPunto.ENCIMA){      
//                    drawVector.drawObjectC(gl, 0, 0, 0);
//
//                }else{
//                    if(pos==Triangle3d.posicionPunto.NEGATIV0){   
//                         
//                        //Eleccion del punto mas lejano de la parte negativa
//                        if(elegirPuntoLejano){
//                            
//                            if(puntoMasLejanoNeg==null){
//                                puntoMasLejanoNeg=cloud.getPunto(i);
//                            }
//                            else{
//
//                                if(plane.distance(cloud.getPunto(i))>plane.distance(puntoMasLejanoNeg)){
//                                        
//                                    puntoMasLejanoNeg=cloud.getPunto(i);                            
//                                }
//                            }
//                        }   
//                        
//                        //Dibujar el punto
//                        drawVector.drawObjectC(gl, 0, 1, 0);
//
//                    }else{           
//                       
//                        //Eleccion del punto mas lejano de la parte positiva
//                        if(elegirPuntoLejano){
//                             
//                            if(puntoMasLejano==null){
//                                puntoMasLejano=cloud.getPunto(i);
//                            }        
//                            else{
//                                if(plane.distance(cloud.getPunto(i))>plane.distance(puntoMasLejano)){
//                                    
//                                    puntoMasLejano=cloud.getPunto(i);
//
//                                }
//                            }
//                        }
//                        
//                        //Dibujar el punto
//                        drawVector.drawObjectC(gl, 0, 0, 1);
//                        
//                        //Almacenar todos los puntos positivos en el vector
//                        if(seguirLlenando){
//                             puntosLadoPositivo.add(cloud.getPunto(i));
//                        
//                        }
//                            
//                    }
//                }
//               
//            }
//            
//            
//            seguirLlenando=elegirPuntoLejano=false;
//            
//            if(interseccionPlano){
//
//                //Crear el rayo con los puntos mas lejanos + y -
//                Ray3d ray=new Ray3d(puntoMasLejano,puntoMasLejanoNeg);
//                rayo=new DrawRay3d(ray);
//           
//                //Localizar punto de interseccion rayo-triangulo
//                triangle.rayTriangle3d(ray, interseccion);
//                
//                //Localizar el punto de dibujado
//                puntoInterseccion=new DrawVect3d(interseccion,8.0f); 
//                
//                interseccionPlano=false;
//                
//            }
//          
//            //Pintar rayo    
//            rayo.drawObject(gl);
//            
//            //Dibujar punto de interseccion con el plano
//            puntoInterseccion.drawObject(gl);
//            
//            
//            //Elegir 3 puntos aleatorios de la nube de la parte positiva del plano
//            if(elegirPuntos){
//                
//                //Triangulo generado por 3 puntos al azar
//                Vect3d v1=puntosLadoPositivo.get(Math.abs(rand.nextInt()%puntosLadoPositivo.size()));
//                Vect3d v2= puntosLadoPositivo.get(Math.abs(rand.nextInt()%puntosLadoPositivo.size()));
//                Vect3d v3= puntosLadoPositivo.get(Math.abs(rand.nextInt()%puntosLadoPositivo.size()));
//                drawTriangle =new DrawTriangle3d(new Triangle3d(v1,v2,v3));
//                
//                
//                //Triangulo proyectado en el plano
//                Vect3d v1Proyect=plane.project(v1);
//                Vect3d v2Proyect=plane.project(v2);
//                Vect3d v3Proyect=plane.project(v3);                
//                drawTriangleProyect=new DrawTriangle3d(new Triangle3d(v1Proyect,v2Proyect,v3Proyect));
//                    
//                elegirPuntos=false;
//            }
//            
//            drawTriangle.drawObject(gl);
//            
//            drawTriangleProyect.drawObjectC(gl,0,0,0);
//             
//        }
//        
//    }
//    
//    public class Practica2b{
//         
//        private Mesh mesh;
//        private DrawMesh drawMesh;
//        private AABB aabb;
//        private DrawAABB drawAABB;
//        private Cloud3d cloud ;
//        private DrawCloud3d drawCloud;
//        private ArrayList<Ray3d> rayList; 
//        private ArrayList<Vect3d> puntosColision;
//        private DrawRay3d drawRay;
//        private Vect3d ptoCorte;
//        private DrawVect3d drawVector;
//        
//        
//        public Practica2b(){
//            try{
//                //Cargar el OBJ
//                mesh=new Mesh("lata_cerveza.obj");
//                drawMesh=new DrawMesh(mesh);
//                
//                //Obtener la caja envolvente
//                aabb=mesh.getAABB(); 
//                drawAABB=new DrawAABB(aabb);
//                
//                //Tam y rango de la nube
//                cloud=new Cloud3d(5, 50.0d);
//                drawCloud=new DrawCloud3d(cloud);
//                
//                rayList=new ArrayList<Ray3d>();
//                puntosColision=new ArrayList<Vect3d>();
//                 
//                ptoCorte=new Vect3d(); 
//                
//                drawRay=new DrawRay3d();
//                drawVector=new DrawVect3d();
//                
//                
//                //Generar rayo desde posicion de la camara hasta el punto de la nube i
//                for(int i=0;i<cloud.tama();i++){
//                  
//                    //Creo y almaceno el rayo
//                    Ray3d rayAux=new Ray3d(cameraPosition, cloud.getPunto(i));
//                    rayList.add(rayAux);
//                    
//                    //En caso de colision guardo los puntos
//                    if(aabb.rayCast(rayAux, ptoCorte)){
//                        
//                        puntosColision.add(new Vect3d(ptoCorte));
//                         
//                    } 
//                } 
//               
//            }catch(Exception e){
//                System.out.println(e.getMessage());
//            }
//             
//        }
//        
//        public void ejecutar(GL gl){
//   
//            try{
//                   
//                //Dibujar el OBJ
//                gl.glTranslatef(0.0f, 0.0f, 0.0f);
//                gl.glScalef(0.2f, 0.2f, 0.2f);
//                drawMesh.drawObject(gl);
//                   
//                //Dibujar su caja envolvente   
//                drawAABB.drawObjectC(gl,1,0,0); 
//
//                //Dibujar una nube con 5 puntos
//                drawCloud.drawObjectC(gl,0,0,0);
//
//                //Dibujar los rayos
//                for(Ray3d rayAux : rayList){
//                    
//                    drawRay.setRay(rayAux);
//                    drawRay.drawObject(gl);
//
//                }
//                
//                //Dibujar los puntos en los que ha colisionado
//                for(Vect3d vectAux:puntosColision){
//                          
//                    drawVector.setVector(vectAux);
//                    drawVector.setSize(10.0f);
//                    drawVector.drawObjectC(gl,0,0,1);
//                }
//
//
//            }catch(Exception e){
//                System.out.println(e.getMessage());
//            }
//                
//        }
//        
//    }
//    
//    
//    
//    
//
//    public static void main(String[] args) {
//        Draw.ALTO = HEIGHT;
//        Draw.ANCHO = WIDTH;
//        Draw.FONDO = 100;
//        
//       
//        
//        Frame frame = new Frame("Alg. Geom. Pract. 2"); 
//        GLCanvas canvas = new GLCanvas();
//        canvas.addGLEventListener(new Practice2());
//        frame.add(canvas);
//        frame.setSize(HEIGHT, WIDTH);
//        final Animator animator = new Animator(canvas);
//        frame.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                // Run this on another thread than the AWT event queue to
//                // make sure the call to Animator.stop() completes before
//                // exiting
//                new Thread(new Runnable() {
//                    public void run() {
//                        animator.stop();
//                        System.exit(0);
//                    }
//                }).start();
//            }
//        });
//        // Center frame
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//        animator.start();
//    }
//
//    // rotating the scene
//    private float view_rotx = 20.0f; //20
//    private float view_roty = 30.0f; //30
//    // remember last mouse position
//    private int oldMouseX;
//    private int oldMouseY;
//    static int HEIGHT = 800, WIDTH = 800;
//    //static int HEIGHT = 10, WIDTH = 10;
//
//    Vect3d cameraPosition, cameraLookAt, cameraUp;
//
//    public Practica2a pr2a;
//    public Practica2b pr2b;
//    
//    public void init(GLAutoDrawable drawable) {
//        GL gl = drawable.getGL();
//        // Set backgroundcolor and shading mode
//        gl.glEnable(GL.GL_BLEND);
//        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
//
//        gl.glEnable(GL.GL_POINT_SMOOTH);
//        // Set backgroundcolor and shading mode
//        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
//        gl.glShadeModel(GL.GL_FLAT);
//
//        System.out.println("INIT GL IS: " + gl.getClass().getName());
//        System.err.println(drawable.getChosenGLCapabilities());
//        System.err.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
//        System.err.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
//        System.err.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));
//
//        cameraPosition = new Vect3d(100, 100, 100);
//        cameraLookAt = new Vect3d(0, 0, 0);
//        cameraUp = new Vect3d(0, 1, 0);
//
//        drawable.addMouseListener(this);
//        drawable.addMouseMotionListener(this);
//        drawable.addMouseWheelListener(this);
//        
//        pr2a=new Practica2a();
//        pr2b=new Practica2b();
//        
//    }
//
//    public void reshape(GLAutoDrawable drawable,
//            int x, int y, int width, int height) {
//        WIDTH = width; // new width and height saved
//        HEIGHT = height;
//
//        GL gl = drawable.getGL();
//        GLU glu = new GLU();
//        if (height <= 0) {
//            height = 1;
//        }
//        // keep ratio
//        final float h = (float) width / (float) height;
//        gl.glViewport(0, 0, width, height);
//        gl.glMatrixMode(GL.GL_PROJECTION);
//        gl.glLoadIdentity();
//        glu.gluPerspective(45.0f, h, 0.1, 2000.0);
//        gl.glMatrixMode(GL.GL_MODELVIEW);
//        gl.glLoadIdentity();
//
//    }
//
// 
//    
//    public void display(GLAutoDrawable drawable) {
//        GL gl = drawable.getGL();
//        GLU glu = new GLU(); // needed for lookat
//        // Clear the drawing area
//        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
//
//        gl.glLoadIdentity();
//        glu.gluLookAt(cameraPosition.getX(), cameraPosition.getY(), cameraPosition.getZ(), // eye pos
//                cameraLookAt.getX(), cameraLookAt.getY(), cameraLookAt.getZ(), // look at
//                cameraUp.getX(), cameraUp.getY(), cameraUp.getZ());  // up
//
//        gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
//        gl.glRotatef(view_roty, 0.0f, 1.0f, 0.0f);
//
//        //Dibujar los ejes
//        DrawAxis3d axis=new DrawAxis3d();
//        axis.drawObject(gl);
//        
//        //Ejecutar pr2a
//        // pr2a.ejecutar(gl);
//       
//        //Ejecutar pr2b
//        pr2b.ejecutar(gl);
//       
//        
//       
//        
//        
//        
//        gl.glFlush();
//    }
//
//    public void displayChanged(GLAutoDrawable drawable,
//            boolean modeChanged, boolean deviceChanged) {
//    }
//
//    public void mouseClicked(MouseEvent e) {
//    }
//
//    public void mouseEntered(MouseEvent e) {
//    }
//
//    public void mouseExited(MouseEvent e) {
//    }
//
//    public void mouseReleased(MouseEvent e) {
//    }
//
//    public void mousePressed(MouseEvent e) {
//        oldMouseX = e.getX();
//        oldMouseY = e.getY();
//    }
//
//    public void mouseDragged(MouseEvent e) {
//        int x = e.getX();
//        int y = e.getY();
//        Dimension size = e.getComponent().getSize();
//        float thetaY = 360.0f * ((float) (x - oldMouseX) / (float) size.width);
//        float thetaX = 360.0f * ((float) (oldMouseY - y) / (float) size.height);
//        oldMouseX = x;
//        oldMouseY = y;
//        view_rotx += thetaX;
//        view_roty += thetaY;
//    }
//
//    public void mouseMoved(MouseEvent e) {
//    }
//
//    public void mouseWheelMoved(MouseWheelEvent e) {
//         
//        double alpha=5.0d*(double)e.getWheelRotation();
//        cameraPosition=cameraPosition.suma(new Vect3d(alpha,alpha,alpha));
//       
//    }
//}
