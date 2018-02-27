package algGeom;

import algGeom2D.Delaunay.*;
import algGeom2D.DrawAxis;
import algGeom2D.DrawPoint;
import algGeom2D.DrawTriangle;
import algGeom2D.Point;
import com.sun.opengl.util.Animator;
import java.awt.*;
import javax.media.opengl.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import GUI.Menu;
import algGeom2D.Circle;
import algGeom2D.DrawCircle;
import algGeom2D.DrawLine;
import algGeom2D.Line;
import algGeom2D.PointCloud;
import javax.swing.JFrame;
 

public class Practice4 extends Frame implements GLEventListener, MouseListener, MouseMotionListener {

    
    public class Pr4a{
        
        private Delaunay_Triangulation delaunay;
          
        private DrawTriangle drawTriangle;
        
        private DrawPoint drawPoint;
        
        private DrawCircle drawCircle;
        
        private DrawLine drawLine;
             
        private ArrayList<Triangle_dt> triangleList;
        
        private ArrayList<Point_dt> pointList;
        
        private ArrayList<Circle_dt> circleList;
        
        private ArrayList<Point_dt> pointConvexList;
        
        private Pr4a selfReference;
        
        private boolean isMenuLoaded;
        
        private boolean verPuntos;
        
        private boolean verTriangulos;
        
        private boolean verCircuferencias;
        
        private boolean verCH;
                
        public Pr4a(){
            
            drawTriangle=new DrawTriangle();
            
            drawPoint=new DrawPoint();    
            
            drawCircle=new DrawCircle();
            
            drawLine=new DrawLine();
            
            isMenuLoaded=false;
            
            verPuntos=false;
            
            verTriangulos=false;
            
            verCircuferencias=false;
            
            verCH=false;
            
        }
        
        //Carga desde fichero .tsin
        public void loadTsinFich(String nombreFichero){
            
            pointList=new ArrayList();
           
            delaunay=new Delaunay_Triangulation();
            
            try{
                
                //--------------    CARGAR LOS PUNTOS --------------
                
                
                //Carga los puntos desde el fichero
                Delaunay_Triangulation delaunayAux=new Delaunay_Triangulation(nombreFichero);
                
                //Boundingbox
                BoundingBox boundingBox=delaunayAux.getBoundingBox();
                
                //Los puntos no estan adaptados al canvas
                Iterator<Point_dt> iteratorPoints=delaunayAux.verticesIterator();
            
                while(iteratorPoints.hasNext()){
                        
                    //Genero una nueva copia
                    Point_dt pointAux=new Point_dt(iteratorPoints.next());                
                         
                    //Modifica los puntos para adaptarlo al canvas
                    scalePoint(pointAux,boundingBox.minX(),boundingBox.minY(),
                               boundingBox.maxX(),boundingBox.maxY(),Draw.ALTO,Draw.ANCHO);
 
                    //Anaden a la lista
                    pointList.add(pointAux);
                    
                    //Anado a delaunay los puntos ya adaptados al canvas
                    delaunay.insertPoint(pointAux);

                }
                
                //-----------     CARGAR LOS TRIANGULOS      --------
                
                loadAllElements();
            
                
            
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            
        
        }
     
       
        //Carga nube puntos aleatoria
        public void loadRandomPointCloud(int numPuntos){
            
            pointList=new ArrayList();
           
            delaunay=new Delaunay_Triangulation();
            
            try{
                
                //--------------    CARGAR LOS PUNTOS --------------
                  
                Random rand=new Random();
                
                for(int i=0;i<numPuntos;i++){

                    Point_dt point=new Point_dt(rand.nextDouble()*Draw.ANCHO,rand.nextDouble()*Draw.ALTO);
                    pointList.add(point);
                    delaunay.insertPoint(point);

                }
                 
                
                //-----------     CARGAR LOS TRIANGULOS      --------
                
                loadAllElements();
            
                
                
                
                
            
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            
            
            
        }
        
        //Carga los puntos desde fichero
        public void loadPointCloud(String nombreFichero){
            
            pointList=new ArrayList();
           
            delaunay=new Delaunay_Triangulation();
            
            
            try{
            
                //----------  CARGAR PUNTOS ------------
                
                
                PointCloud pc=new PointCloud(nombreFichero);
                
                for(int i=0;i<pc.tama();i++){
                    
                    Point pointAux=pc.getPunto(i);
                    
                    Point_dt pointDelaunay=new Point_dt(pointAux.getX(), pointAux.getY());
                    
                    scalePoint(pointDelaunay, pc.menorAbcisa().getX(), pc.menorOrdenada().getY(),
                               pc.mayorAbcisa().getX(),  pc.mayorOrdenada().getY(), Draw.ANCHO,Draw.ALTO);
                    
                    pointList.add(pointDelaunay);
                    
                    delaunay.insertPoint(pointDelaunay);
                    
                }
                
                //----------  CARGAR TRIANGULOS ------------
                 
                loadAllElements();
               
            
            }catch(Exception e){
                System.out.println(e.getMessage());
            }            
            
        }
        
        
        public void visualizarTriangulos(){        
            
            if(triangleList!=null){
            
                for(Triangle_dt triangleAux : triangleList){
              
                    drawTriangle.loadNewTriangle(triangleAux);  
                    drawTriangle.drawObjectC(gl, 1, 0, 0);
                 
                }   
            }
            
            
        }
        
        public void visualizarPuntos(){
            
            if(pointList!=null){
                    
                for(Point_dt pointAux:pointList){
              
                    drawPoint.setPointDelaunay(pointAux);
                    drawPoint.drawObjectNoTransform(gl);
                }
                
            }
           
                         
            
        }
        

        /*
            AL DIBUJAR LAS CIRCUFERENCIAS, LOS RADIOS QUE DAN SON 
            EXAGERADAMENTE GRANDES
        */
        public void visualizarCircuferencias(){
            
            if(circleList!=null){
               
                for(Circle_dt circleAux : circleList){
                
                    Point_dt pointDT=circleAux.Center();
                    Point point=new Point(pointDT.x(),pointDT.y());

                    drawCircle.loadNewCircle(new Circle(point,circleAux.Radius()));
                    drawCircle.drawObject(gl);
                    
                }
            }
            
            
            
        }
        
        /*
            NO SIEMPRE LO HACE BIEN, YA QUE DEPENDE
            DE LA LOCALIZACION DE LOS PUNTOS 
            
        */
        public void visualizarCH(){
            
            if(pointConvexList!=null){
            
                for(int i=0;i<pointConvexList.size();i++){
                
                    Point_dt pointA=pointConvexList.get(i);
                    Point_dt pointB=pointConvexList.get((i+1)%pointConvexList.size());

                    drawLine.loadNewLine(new Line(new Point(pointA.x(),pointA.y()),new Point(pointB.x(),pointB.y())));
                    drawLine.drawObjectNoTransform(gl,1,0,0);
                   
                }
                
            }
            
        }
        
        
        public void clearAll(){
            verPuntos=false;
            verTriangulos=false;
            pointList=null;
            delaunay=null;
            triangleList=null;         
            circleList=null;
            pointConvexList=null;
        }
        
        
        
        /*
          Transforma un punto a corrdenadas de pantalla
        */
        private void scalePoint(Point_dt point,double minX,double minY,double maxX,double maxY,double ancho,double alto){
            
            double ratioX=ancho/(maxX-minX);
            double ratioY=alto/(maxY-minY);    
            
            point.setX((point.x()-minX)*ratioX);
            point.setY((point.y()-minY)*ratioY);
            
        }
        
        /*
           Carga en una lista los triangulos resultantes de la triangulacion
           Carga las circuferencias de cada triangulo
           Carga la convex hull
        */
        private void loadAllElements(){
            
            
            if(delaunay!=null){                
                
                triangleList=new ArrayList();
                circleList=new ArrayList();
                pointConvexList=new ArrayList();
                

                //---- CARGAR LOS TRIANGULOS -----

                Iterator<Triangle_dt> iteratorTriangles = delaunay.trianglesIterator();     

                while(iteratorTriangles.hasNext()){

                    Triangle_dt aux=iteratorTriangles.next();

                    if (!aux.isHalfplane()) {

                        //Crea un nuevo triangulo
                        Triangle_dt triangle=new Triangle_dt(new Point_dt(aux.p1()),new Point_dt(aux.p2()),new Point_dt(aux.p3()) );

                        //---- CARGAR LOS CIRCULOS -----
                        circleList.add(new Circle_dt(new Circle_dt(triangle.circumcircle())));

                        //Como los triangulos ya estan adaptados, se almacenan directamente                     
                        triangleList.add(triangle);
                    }
                }

                try{
                    //---- CARGAR ENVOLVENTE CONVEXA -----
                   
                  
                    Iterator<Point_dt> iteratorPoint= delaunay.CH_vertices_Iterator();
                        
                    while(iteratorPoint.hasNext()){

                        pointConvexList.add(new Point_dt(iteratorPoint.next()));
                    }
                    
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
            
        }
        
       
        //Inicializa el menu en otra ventana
        public void initMenu(){
        
            selfReference=this;
            
            java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(new Menu(selfReference));
                frame.pack();
                frame.setVisible(true);
            }
        });
        
            isMenuLoaded=true;
            
            
        }
        
       
        public boolean isMenuLoad(){
            return isMenuLoaded;
        }
        
        
        
        public void setVerPuntos(boolean estado){
            verPuntos=estado;
        }
        
        public void setVerTriangulos(boolean estado){
            verTriangulos=estado;
        }
        
        public void setVerCircunferencias(boolean estado){
            verCircuferencias=estado;
        }
        
        public void setVerCH(boolean estado){
            verCH=estado;
        }
        
        public boolean verPuntos(){
            return verPuntos;
        }
        
        public boolean verTriangulos(){
            return verTriangulos;
        }
        
        public boolean verCircuferencias(){
            return verCircuferencias;
        }
                
        public boolean verCH(){
            return verCH;
        }
        
        
        public void addNewPoint(double posX,double posY){
            
            Point_dt point=new Point_dt(posX, posY);
            
            if(delaunay==null && pointList==null){
                
                delaunay=new Delaunay_Triangulation();
                pointList=new ArrayList();
                
            }            
            
            delaunay.insertPoint(point);
            
            pointList.add(point);
            
            loadAllElements();
            
        }
        
        
        
    }
    
        
    static int HEIGHT = 1000;
    static int WIDTH = 1000;
    static GL gl; // interface to OpenGL
    static GLCanvas canvas; // drawable in a frame
    static GLCapabilities capabilities;
    static boolean visualizarEjes = true;
    private Animator animator;
    
    
    
    //Variables para mover localizar un punto
    public double currentPosX;
    public double currentPosY;
    
    // -------PRACTICA 4------
    public Pr4a pr4a;
     
      
    public static void main(String[] args) {
        
      
        Draw.ALTO = HEIGHT;
        Draw.ANCHO = WIDTH;
        Practice4 frame = new Practice4("Practice4");
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        
    }
    

    public Practice4(String title) {
        super(title);
             
         
        currentPosX=0.0d;
        currentPosY=0.0d;
        
        // 1. specify a drawable: canvas
        capabilities = new GLCapabilities();
        capabilities.setDoubleBuffered(false);
        canvas = new GLCanvas();
        //canvas = GLDrawableFactory.getFactory().createGLCanvas(capabilities);  
        
        // 2. listen to the events related to canvas: reshape
        canvas.addGLEventListener(this);

        canvas.addMouseListener(this);
        
        canvas.addMouseMotionListener(this);
        
        
        // 3. add the canvas to fill the Frame container
        add(canvas, BorderLayout.CENTER);

        // 4. interface to OpenGL functions
        gl = canvas.getGL();

      
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//              animator.stop(); // stop animation

                System.exit(0);
            }
        });
        
        addKeyListener(new KeyListener() {
            long clock = 0;

            /**
             * Handle the key typed event from the text field.
             */
            public void keyTyped(KeyEvent e) {
//                        System.out.println(e + "KEY TYPED: ");
            }

            /**
             * Handle the key pressed event from the text field.
             */
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyChar()) {
                    case 'E':
                    case 'e':                      
                        visualizarEjes = !visualizarEjes;
                        canvas.repaint();
                        clock = e.getWhen();
                      
                        break;
                        
                    case 'w':
                        currentPosY+=1;                        
                    break;
                    
                    case 's':
                        currentPosY-=1;
                        break;
                        
                    case 'd':
                        currentPosX+=1;
                        break;
                        
                    case 'a':
                        currentPosX-=1;
                        break;
                        
                    case 27: // esc
                        System.exit(0);
                        
                    default:
                        System.out.println("Tecla no asignada");
                        break;
                }

            }

            /**
             * Handle the key released event from the text field.
             */
            public void keyReleased(KeyEvent e) {
                clock = e.getWhen();
//                        System.out.println(e + "KEY RELEASED: ");
            }
        });
    }

    // called once for OpenGL initialization
    public void init(GLAutoDrawable drawable) {

      animator = new Animator(canvas);
      animator.start(); // start animator thread
        
// display OpenGL and graphics system information
        System.out.println("INIT GL IS: " + gl.getClass().getName());
        System.err.println(drawable.getChosenGLCapabilities());
        System.err.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
        System.err.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
        System.err.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));
        
        //Inicializacion pr4
        pr4a=new Pr4a();
          
    }
    

    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
            int height) {
       
        Draw.ANCHO = WIDTH = width; // new width and height saved
        Draw.ALTO = HEIGHT = height;
//      DEEP = deep;
        if (Draw.ALTO < Draw.ANCHO) {
            Draw.ANCHO = Draw.ALTO;
        }
        if (Draw.ALTO > Draw.ANCHO) {
            Draw.ALTO = Draw.ANCHO;
        }
        // 7. specify the drawing area (frame) coordinates
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, width, 0, height, -100, 100);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
      
        if (HEIGHT < WIDTH) {
            gl.glTranslatef((WIDTH - HEIGHT) / 2, 0, 0);
        }
        if (HEIGHT > WIDTH) {
            gl.glTranslatef(0, (HEIGHT - WIDTH) / 2, 0);
        }

    }

    // called for OpenGL rendering every reshape
    public void display(GLAutoDrawable drawable) {

        // limpiar la pantalla
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        /* El color de limpiado será cero */
        gl.glClearDepth(1.0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        // Dibujar ejes.
        if (visualizarEjes) {
            DrawAxis ejes = new DrawAxis();
            ejes.drawObject(gl);
        }
        
        if(!pr4a.isMenuLoad())    
            pr4a.initMenu();                    
        
        
        if(pr4a.verPuntos())           
            pr4a.visualizarPuntos();
        
        
        if(pr4a.verTriangulos())
            pr4a.visualizarTriangulos();
        
        
        if(pr4a.verCircuferencias())
            pr4a.visualizarCircuferencias();
     
        if(pr4a.verCH())
            pr4a.visualizarCH();
        
        
          
        gl.glFlush();
    }

    // called if display mode or device are changed
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
            boolean deviceChanged) {
    }

    
     public void mouseClicked(MouseEvent me) {
      
         if(me.getButton()==1){
                
            double ratioX=(double)Draw.ANCHO/(double)WIDTH;
            double ratioY=(double)Draw.ALTO/(double)HEIGHT;

            java.awt.Point pointAux=me.getPoint();

            double posX=(pointAux.getX()*ratioX); //La x esta en el mismo eje que la del raton y no hay que restar         
            double posY=Math.abs((pointAux.getY()*ratioY)-Draw.ALTO);

            pr4a.addNewPoint(posX, posY);


            if(!pr4a.verPuntos())
                pr4a.setVerPuntos(true);
            
         }
          
    }

    public void mousePressed(MouseEvent me) {
        
    }

    public void mouseReleased(MouseEvent me) {
        
    }

    public void mouseEntered(MouseEvent me) {
          
    }

    public void mouseExited(MouseEvent me) {
        
    }

    public void mouseDragged(MouseEvent me) {
         
    }

    public void mouseMoved(MouseEvent me) {
          
    }
    
    
}
