//package algGeom;
//
//import com.sun.opengl.util.Animator;
//import java.awt.*;
//import javax.media.opengl.*;
//import java.awt.event.*;
//
//public class MainClass extends Frame implements GLEventListener {
//
//    static int HEIGHT = 800;
//    static int WIDTH = 800;
//    static GL gl; // interface to OpenGL
//    static GLCanvas canvas; // drawable in a frame
//    static GLCapabilities capabilities;
//    static boolean visualizarEjes = true;
//    
//    private Animator animator;
//    
//     
//    
//    //Variables para mover localizar un punto
//    public double currentPosX;
//    public double currentPosY;
//   
//    public static void main(String[] args) {
//        Draw.ALTO = HEIGHT;
//        Draw.ANCHO = WIDTH;
//        MainClass frame = new MainClass("Prac1. Algoritmo Geometricos");
//        frame.setSize(WIDTH, HEIGHT);
//        frame.setVisible(true);
//    }
//    
//
//    public MainClass(String title) {
//        super(title);
//             
//        currentPosX=0.0d;
//        currentPosY=0.0d;
//        
//        // 1. specify a drawable: canvas
//        capabilities = new GLCapabilities();
//        capabilities.setDoubleBuffered(false);
//        canvas = new GLCanvas();
//        //canvas = GLDrawableFactory.getFactory().createGLCanvas(capabilities);
//  
//        
//        
//        // 2. listen to the events related to canvas: reshape
//        canvas.addGLEventListener(this);
//
//        // 3. add the canvas to fill the Frame container
//        add(canvas, BorderLayout.CENTER);
//
//        // 4. interface to OpenGL functions
//        gl = canvas.getGL();
//
//      
//        
//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
////              animator.stop(); // stop animation
//
//                System.exit(0);
//            }
//        });
//        
//        addKeyListener(new KeyListener() {
//            long clock = 0;
//
//            /**
//             * Handle the key typed event from the text field.
//             */
//            public void keyTyped(KeyEvent e) {
////                        System.out.println(e + "KEY TYPED: ");
//            }
//
//            /**
//             * Handle the key pressed event from the text field.
//             */
//            public void keyPressed(KeyEvent e) {
//
//                switch (e.getKeyChar()) {
//                    case 'E':
//                    case 'e':                      
//                        visualizarEjes = !visualizarEjes;
//                        canvas.repaint();
//                        clock = e.getWhen();
//                      
//                        break;
//                        
//                    case 'w':
//                        currentPosY+=1;                        
//                    break;
//                    
//                    case 's':
//                        currentPosY-=1;
//                        break;
//                        
//                    case 'd':
//                        currentPosX+=1;
//                        break;
//                        
//                    case 'a':
//                        currentPosX-=1;
//                        break;
//                        
//                    case 27: // esc
//                        System.exit(0);
//                        
//                    default:
//                        System.out.println("Tecla no asignada");
//                        break;
//                }
//
//            }
//
//            /**
//             * Handle the key released event from the text field.
//             */
//            public void keyReleased(KeyEvent e) {
//                clock = e.getWhen();
////                        System.out.println(e + "KEY RELEASED: ");
//            }
//        });
//    }
//
//    // called once for OpenGL initialization
//    public void init(GLAutoDrawable drawable) {
//
//      //animator = new Animator(canvas);
//      //animator.start(); // start animator thread
//        
//// display OpenGL and graphics system information
//        System.out.println("INIT GL IS: " + gl.getClass().getName());
//        System.err.println(drawable.getChosenGLCapabilities());
//        System.err.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
//        System.err.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
//        System.err.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));
//       
//    }
//    
//
//    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
//            int height) {
//
//        Draw.ANCHO = WIDTH = width; // new width and height saved
//        Draw.ALTO = HEIGHT = height;
////      DEEP = deep;
//        if (Draw.ALTO < Draw.ANCHO) {
//            Draw.ANCHO = Draw.ALTO;
//        }
//        if (Draw.ALTO > Draw.ANCHO) {
//            Draw.ALTO = Draw.ANCHO;
//        }
//        // 7. specify the drawing area (frame) coordinates
//        gl.glMatrixMode(GL.GL_PROJECTION);
//        gl.glLoadIdentity();
//        gl.glOrtho(0, width, 0, height, -100, 100);
//        gl.glMatrixMode(GL.GL_MODELVIEW);
//        gl.glLoadIdentity();
//        if (HEIGHT < WIDTH) {
//            gl.glTranslatef((WIDTH - HEIGHT) / 2, 0, 0);
//        }
//        if (HEIGHT > WIDTH) {
//            gl.glTranslatef(0, (HEIGHT - WIDTH) / 2, 0);
//        }
//
//    }
//
//    // called for OpenGL rendering every reshape
//    public void display(GLAutoDrawable drawable) {
//
//        // limpiar la pantalla
//        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
//        /* El color de limpiado será cero */
//        gl.glClearDepth(1.0);
//        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
//
//        // Dibujar ejes.
//        if (visualizarEjes) {
//            DrawAxis ejes = new DrawAxis();
//            ejes.drawObject(gl);
//        }
//        
//        //----------------------  PRACTICA 1A ----------------
//        
////        PointCloud pc=new PointCloud(20);
////        DrawCloud draw=new DrawCloud(pc);
////        draw.drawObject(gl);
////        
////        SegmentLine segLine=new SegmentLine(pc.getPunto(0),pc.getPunto(10));
////        DrawSegment segment=new DrawSegment(segLine);
////        segment.drawObject(gl);
////        
////        Line recta=new Line(pc.getPunto(3),pc.getPunto(12));
////        DrawLine dline=new DrawLine(recta);
////        dline.drawObject(gl);
////        
////        RayLine rayo=new RayLine(pc.getPunto(9),pc.getPunto(18));
////        DrawRay ray=new DrawRay(rayo);
////        ray.drawObject(gl);
////        
////        
////        Point puntoCentral=pc.puntoCentral(); 
////        Circle c=new Circle(puntoCentral,10.0d);
////        DrawCircle dc=new DrawCircle(c);
////        dc.drawObject(gl);
////        
////        
////        
////        Polygon poligono=new Polygon();
////        poligono.anade(pc.mayorOrdenada());
////        poligono.anade(pc.menorAbcisa());
////        poligono.anade(pc.menorOrdenada());
////        poligono.anade(pc.mayorAbcisa());
////        
////           
////        DrawPolygon dp= new DrawPolygon(poligono);
////        dp.drawObject(gl);
//        
//
//
//        //----------------   PRACTICA 2B --------------------------
//
////        Circle circle=new Circle(new Point(20,20),15);
////        DrawCircle dc=new DrawCircle(circle);
////        dc.drawObject(gl);
////        
////        SegmentLine segmentLine=new SegmentLine(new Point(5,0),new Point(35,60));
////        DrawSegment drawSegmentLine=new DrawSegment(segmentLine);
////        drawSegmentLine.drawObject(gl);
////       
////        RayLine recta=new RayLine(new Point(-20,5),new Point(30,35));
////        DrawRay drawRay=new DrawRay(recta);
////        drawRay.drawObject(gl);
////        
////        Line line=new Line(new Point(-30,30), new Point(30,-30));
////        DrawLine dl=new DrawLine(line);
////        dl.drawObject(gl);
////        
////         
////        Vector corte1=new Vector();
////        Vector corte2=new Vector();
////        DrawVector dv1=new DrawVector(corte1);
////        DrawVector dv2=new DrawVector(corte2);
////            
////        System.out.println("Circulo-SegmentLine: "+circle.intersecta(segmentLine, corte1, corte2));        
////        dv1.drawObjectC(gl, 0, 0, 1);
////        dv2.drawObjectC(gl, 0, 0, 1);
////           
////        System.out.println("Circulo-RayLine: "+circle.intersecta(recta, corte1, corte2));        
////        dv1.drawObjectC(gl, 0, 0, 1);
////        dv2.drawObjectC(gl, 0, 0, 1);
////        
////        System.out.println("RayLine-SegmentLine: "+recta.intersecta(segmentLine, corte1));        
////        dv1.drawObjectC(gl, 0, 0, 1);
////         
////        System.out.println("RayLine-Line: "+recta.intersecta(line, corte1));        
////        dv1.drawObjectC(gl, 0, 0, 1);
//        
//        
//       
//
//
//
//        gl.glFlush();
//    }
//
//    // called if display mode or device are changed
//    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
//            boolean deviceChanged) {
//    }
//
//    
//}
