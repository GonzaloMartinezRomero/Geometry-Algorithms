package algGeom3D;


import algGeom.Draw;
import javax.media.opengl.GL;


public class DrawMesh extends Draw {

    Mesh m;

    /**
     * Creates a new instance of VisuPunto
     * @param m
     */
    public DrawMesh(Mesh m) {
        this.m = m;
    }
    
    public DrawMesh(){
        m=null;
    }
    
    
    @Override
    public void drawObject(GL g) {
         
        if(m!=null){
            
            for (int i = 0; i < m.getSizeCaras(); i++) {
            
                Triangle3d t = new Triangle3d(m.getTriangulo(i));
                DrawTriangle3d dt = new DrawTriangle3d(t);
                dt.drawObject(g);
            }
            
        }
        
      

    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glColor3f(R, G, B);
        drawObject(g);
    }
    
      private void drawTriangleWire(GL gl,float width,Triangle3d tr){
        
        gl.glLineWidth(width);
         
        // V1 a V2 
        gl.glBegin(GL.GL_LINES);
            gl.glVertex3d(tr.a.x, tr.a.y, tr.a.z);
            gl.glVertex3d(tr.b.x, tr.b.y, tr.b.z);
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
            gl.glVertex3d(tr.b.x, tr.b.y, tr.b.z);
            gl.glVertex3d(tr.c.x, tr.c.y, tr.c.z);
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
            gl.glVertex3d(tr.c.x, tr.c.y, tr.c.z);
            gl.glVertex3d(tr.a.x, tr.a.y, tr.a.z);
        gl.glEnd();
    }
    
    
    
    public void drawObjectWire(GL g, float R, float G, float B){
        
        
        g.glColor3f(R, G, B);
        
        if(m!=null){
           
            for(Triangle3d triangleAux: m.getTriangulos()){
            
                drawTriangleWire(g, 2.0f, triangleAux);
            }
        }
        
        
        
    }
    
    
}
