/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGeom3D;
 
import algGeom.Draw;
import java.util.ArrayList;
import javax.media.opengl.GL;


/**
 *
 * @author Drebin
 */
public class DrawOctree extends Draw {

    private ArrayList<DrawAABB> listDrawAABB;
    
    
    public DrawOctree(Octree octree){
        
        listDrawAABB=new ArrayList();
        
        for(AABB aabb : octree.getAABB()){
            
            listDrawAABB.add(new DrawAABB(aabb));
            //System.out.println("Pos x:"+aabb.max.x+aabb.max.y+aabb.max.z+" Pos y: "+aabb.min.x+aabb.min.y+aabb.min.z);
        }
        
       // System.out.println("creadas "+listDrawAABB.size());
    }
    
    
    @Override
    public void drawObject(GL gl) {
       drawObjectC(gl,1,0,0);
    }

    @Override
    public void drawObjectC(GL gl, float R, float G, float B) { 
        
        for(DrawAABB drawAABB: listDrawAABB){
            
            drawAABB.drawObjectC(gl,R, G, B);
            
        }
        
        
        
    }

    
    
}
