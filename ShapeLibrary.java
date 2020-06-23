import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * The class ShapeLibrary has an ArrayList filled with deserializes shapes from the shapes folder and has methods to get a shape
 * from an index or from the shape's name.
 *
 * @author Valerie Foster
 * @version 1/30/2018
 */
public class ShapeLibrary {

    // instance variable
    private ArrayList<Shape> shapeArr;

    /**
     * Constructor for objects of class ShapeLibrary
     */
    public ShapeLibrary() {
        shapeArr = new ArrayList<Shape>();
        this.readShapes();
    }

    /**
     * This method returns a Shape from the ArrayList at the given index
     *
     * @param   ind     an integer for the index
     * @return          a Shape from the shapeArr
     */
    public Shape getShape(int ind) {
        return shapeArr.get(ind);
    }
    
    /**
     * This method returns a Shape from the ArrayList with the given name
     *
     * @param   name    a String for the shape name
     * @return          a Shape from the shapeArr
     */
    public Shape getShapeFromName(String name) {
        Shape shape = null;
        for (int ind = 0; ind < shapeArr.size(); ind++) {
            if (name.equals(shapeArr.get(ind).getName())) {
                shape = shapeArr.get(ind);
            }
        }
        return shape;
    }

    /**
     * This private method deserializes the previously serialized shape files from the shapes folder and puts them into the shapeArr
     */
    private void readShapes() {
        try {
            File shapeFolder = new File("shapes");
            File[] files = shapeFolder.listFiles();
            for (File fil : files) {
                FileInputStream shapeFile = new FileInputStream(fil);
                ObjectInputStream in = new ObjectInputStream(shapeFile);
                shapeArr.add((Shape)in.readObject());
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file was not found.");
        } catch (IOException e) {
            System.out.println("The file did not serialize correctly.");
        } catch (ClassNotFoundException e) {
            System.out.println("The class the object was casted to was not found.");
        }
    }

    /** 
     * Creates and returns a string representation of this ShapeLibrary
     * 
     * @return  a String showing basic information about the shape library
     */
    @Override
    public String toString() {
        String str ="";
        str += "Shapes in the Library:" + "\n";
        for (Shape shp : shapeArr) {
            str += shp + "\n";
        }
        return str;
    }

}
