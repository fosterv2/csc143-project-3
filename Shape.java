import java.io.Serializable;

/**
 * The class Shape has information for drawing a shape with DrawingPanel. It has a String name to identify the shape and
 * an ArrayList of Points to use in the polygon that creates the shape.
 *
 * @author Valerie Foster
 * @version 1/30/2018
 */
public class Shape implements Serializable {
    
    // instance variables
    private String name;
    ArrayList<Point> pointArr;

    /**
     * Constructor for objects of class Shape
     * 
     * @param   name    a String name for the shape
     * @throws  IllegalArgumentException if the name is empty or null
     */
    public Shape(String name) {
        if (name == "" || name == null) {
            throw new IllegalArgumentException("The name cannot be empty or null.");
        }
        this.name = name;
        pointArr = new ArrayList<Point>();
    }

    /**
     * An accessor - returns the shape's name
     * 
     * @return  name    a String of the shape's name
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * An accessor - returns the ArrayList that stores all of the Points to create the shape
     *
     * @return    pointArr  the ArrayList of Points
     */
    public ArrayList<Point> getArrayList() {
        return this.pointArr;
    }
    
    /**
     * This method returns a Point from the ArrayList at the given index
     *
     * @param   ind     an integer for the index
     * @return          a Point from the ArrayList
     */
    public Point getPoint(int ind) {
        return pointArr.get(ind);
    }

    /**
     * This method adds a given Point to the ArrayList of Points
     *
     * @param   the Point to add to the pointArr
     */
    public void addPoint(Point point) {
        pointArr.add(point);
    }
    
    /** 
     * Creates and returns a string representation of this Shape
     * 
     * @return  a String showing basic information about the shape
     */
    @Override
    public String toString() {
        String str ="";
        str += "Name: " + name + "\n";
        str += "Points: ";
        for (Point pnt : pointArr) {
            str += pnt + " ";
        }
        return str;
    }

}
