package algGeom3D;

import java.util.LinkedList;

public class Polyline3d {

    LinkedList<Vect3d> path;

    public Polyline3d() {
        path = new LinkedList<Vect3d>();
    }

    public boolean add(Segment3d newSegment) {
        if (path.isEmpty()) {
            path.addFirst(newSegment.getOrigin());
            path.addLast(newSegment.getDestination());

            return true;

        } else if (newSegment.getDestination().equals(path.getFirst())) {
            path.addFirst(newSegment.getOrigin());

            return true;

        } else if (newSegment.getOrigin().equals(path.getFirst())) {
            path.addFirst(newSegment.getDestination());

            return true;

        } else if (newSegment.getDestination().equals(path.getLast())) {
            path.addLast(newSegment.getOrigin());

            return true;

        } else if (newSegment.getOrigin().equals(path.getLast())) {
            path.addLast(newSegment.getDestination());

            return true;
        }
    
        return false;
    }
    
    public int size() {
        return path.size();
    }
    
    public Vect3d get(int i){
        return path.get(i);
    }
    
    public LinkedList<Vect3d> getAll(){
        return path;
    }
    

}
