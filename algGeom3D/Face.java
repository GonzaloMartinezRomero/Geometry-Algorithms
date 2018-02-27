package algGeom3D;

public class Face {

	int v1, v2, v3;
	Vect3d n;
	
	public Face (int vv1, int vv2, int vv3, Vect3d normal){
		v1 = vv1;
		v2 = vv2;
		v3 = vv3;
		n = normal;
	}

	public Face (int[] caras){
		v1 = caras[0];
		v2 = caras[1];
		v3 = caras[2];
	}
        
        public Face(Face _face){
            v1=_face.v1;
            v2=_face.v2;
            v3=_face.v3;
            n=_face.n;
        }
        
        
	
	public void setNormal (Vect3d normal){
		n = normal;
	}
	
	public Vect3d getNormal(){
		return n;
	}
        

}
