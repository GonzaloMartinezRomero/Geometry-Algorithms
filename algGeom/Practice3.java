//package algGeom;
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
//import java.io.IOException;
//import javax.media.opengl.GL;
//import javax.media.opengl.GLAutoDrawable;
//import javax.media.opengl.GLCanvas;
//import javax.media.opengl.GLEventListener;
//import javax.media.opengl.glu.GLU;
//
//public class Practice3 implements GLEventListener,
//        MouseListener,
//        MouseMotionListener,
//        MouseWheelListener {
//    
//    public class Practica3a{
//        
//        private Mesh mesh;
//        private DrawMesh drawMesh;
//        private Plane plane;
//        private DrawPlane drawPlane;
//        private Plane.IntersectType intersect;
//        private DrawTriangle3d drawTriangle;
//        
//        private Vect3d v1,v2,v3;
//        private Triangle3d planeTriangle;
//        
//        public Practica3a(){
//            
//            try{
//                
//                //Malla con el objeto
//                mesh=new Mesh("cat.obj");
//                drawMesh=new DrawMesh(mesh);
//                
//                //Generacion del plano
//                
//                v1=new Vect3d(-100, 60, 110);
//                v2=new Vect3d(100, 60, 110);
//                v3=new Vect3d(100, 60, -110);
//                plane=new Plane(v1,v2,v3,true);
//                
//                drawPlane=new DrawPlane(plane);
//                
//                drawTriangle=new DrawTriangle3d();
//               
//                intersect=new Plane.IntersectType();
//                
//                planeTriangle=new Triangle3d(v1, v2, v3);
//                
//                
//            }catch(Exception e){
//                System.out.println("ERROR EN PR3A");
//            }
//          
//        }
//        
//        
//        public void ejecutar(GL gl){
//          
//            //Dibujar el plano
//            drawPlane.drawObjectC(gl,0,0,1);
//            
//            drawMesh.drawObjectWire(gl,0, 0, 0);
//            
//            
//            //Dibujar los triangulos           
//            for(Triangle3d triangleAux : mesh.getTriangulos()){
//              
//                drawTriangle.setTriangle3d(triangleAux);
//                
//                
//                //Dibuja los triangulos que intersectan con el plano
//                if(plane.trianglePlane(triangleAux,intersect)){
//                     
//                    drawTriangle.drawObjectC(gl, 1, 0, 1);
//                    
//                }else{
//               
//                    //Comprobar que el triangulo esta encima
//                    if(planeTriangle.classify(triangleAux.getA())==Triangle3d.posicionPunto.POSITIV0){
//                        
//                         drawTriangle.drawObjectC(gl, 1, 1, 0);    
//                    }
//                    else{
//                     
//                        drawTriangle.drawObjectC(gl, 0, 1, 1 ); 
//                    }                            
//                  
//                }                
//                
//            }            
//            
//        }
//        
//    }
//    
//    public class Practica3b{
//    
//        private Mesh mesh;
//        private DrawMesh drawMesh;
//        
//        private Mesh cutMesh;
//        private DrawMesh drawCutMesh;
//        
//        private Octree octree;
//        private DrawOctree drawOctree;
//        
//        private Plane plane;
//        private Vect3d v1,v2,v3;
//        private DrawPlane drawPlane;
//         
//        
//        public Practica3b(){
//        
//            try{
//                
//                v1=new Vect3d(-100, 60, 110);
//                v2=new Vect3d(100, 60, 110);
//                v3=new Vect3d(100, 60, -110);
//                plane=new Plane(v1,v2,v3,true);
//                
//                drawPlane=new DrawPlane(plane);
//                
//                
//                //Malla con el objeto
//                mesh=new Mesh("cat.obj");
//                drawMesh=new DrawMesh(mesh);
//                
//                cutMesh=new Mesh();
//                if(mesh.crossSection(plane, cutMesh)){                  
//                  
//                    drawCutMesh=new DrawMesh(cutMesh);
//              
//                }else{
//                   
//                    drawCutMesh=new DrawMesh();
//                }
//                
//                  
//                 
//                octree=new Octree(mesh,2,10);
//                
//                // System.out.println("Creaction optimus "+octree.isOptimus());
//                
//                drawOctree=new DrawOctree(octree);
//                 
//                
//            }catch(IOException e){
//              
//                System.out.println(e.getMessage());
//            }
//          
//        }
//        
//        public void ejecutarCutMesh(GL gl){
//            
//            //drawPlane.drawObjectC(gl,0,0,1);
//           
//            drawCutMesh.drawObjectWire(gl, 0, 0, 0); 
//            drawCutMesh.drawObjectC(gl, 0, 1, 0); 
//            
//            
//        }
//        
//        public void ejecutarOctree(GL gl){
//            
//            drawMesh.drawObject(gl);
//            drawMesh.drawObjectWire(gl, 0, 0, 0);
//            drawOctree.drawObjectC(gl,0, 0, 1);
//            
//        }
//        
//        
//        
//    }
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
//        Frame frame = new Frame("Algoritmos Geometricos"); 
//        GLCanvas canvas = new GLCanvas();
//        canvas.addGLEventListener(new Practice3());
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
//    public Practica3a pr3a;
//    public Practica3b pr3b;
//   
//    
//    public void init(GLAutoDrawable drawable) {
//        
//        GL gl = drawable.getGL();
//        // Set backgroundcolor and shading mode
//        gl.glEnable(GL.GL_BLEND);
//        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
//
//        gl.glEnable(GL.GL_POINT_SMOOTH);
//        // Set backgroundcolor and shading mode
//        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
//        //gl.glShadeModel(GL.GL_FLAT);
//           
//        gl.glEnable(GL.GL_DEPTH_TEST);
//
//        initLight(gl);
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
//        pr3a=new Practica3a();
//        pr3b=new Practica3b();
//       
//        
//    }
//    
//     public void initLight(GL gl) {
//       //gl.glPushMatrix();
//        gl.glShadeModel(GL.GL_SMOOTH);
//
//        // descomentar esto para poder ver las sombras de los modelos 
//        float ambient[] = {1.0f, 1.0f, 1.0f, 1.0f};
//        float diffuse[] = {1.0f, 1.0f, 1.0f, 1.0f};
//        //float specular[] = {0.2f, 0.0f, 0.0f, 1.0f};
//        float position[] = {20.0f, 30.0f, 20.0f, 0.0f};
//        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambient, 0);
//        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuse, 0);
//        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 0);
//        //gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specular, 0);
//        gl.glEnable(GL.GL_LIGHTING);
//        gl.glEnable(GL.GL_LIGHT0);
//
//        gl.glEnable(GL.GL_NORMALIZE);
//        gl.glEnable(GL.GL_COLOR_MATERIAL);
//        //gl.glPopMatrix();
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
//        //----Ejecutar practica 3a---
//        //pr3a.ejecutar(gl);
//        
//        //----Ejecutar practica 3b ---
//       
//        pr3b.ejecutarCutMesh(gl);        
//       
//        //pr3b.ejecutarOctree(gl);
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
