/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGeom3D;
 
/**
 *
 * @author Drebin
 */
public class DrawFace extends DrawTriangle3d{

    private final float [] color;
   
    public DrawFace(Triangle3d triangle){
     
        super(triangle);
        
        color=new float[3];       
        
    }
    
    public void setColor(float R, float G, float B){
        
        color[0]=R;
        color[1]=G;
        color[2]=B;
    }   
    
    public float[] getColor(){
        return color;
    }
    
    
}
