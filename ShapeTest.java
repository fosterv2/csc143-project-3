import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class ShapeTest tests the Shape class
 *
 * @author  Valerie Foster
 * @version 1/30/2018
 */
public class ShapeTest {
    
    Shape testShape;
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp() {
        testShape = new Shape("square");
        testShape.addPoint(new Point(0, 0));
        testShape.addPoint(new Point(100, 0));
        testShape.addPoint(new Point(100, 100));
        testShape.addPoint(new Point(0, 100));
    }
    
    @Test
    public void testConstructor() {
        assertEquals("square", testShape.getName());
        assertEquals(4, testShape.getArrayList().size());
        assertEquals(0.0, testShape.getPoint(0).getX(), 0.001);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testPrecon() {
        Shape badShape = new Shape("");
    }
    
    @Test
    public void testAddPoint() {
        testShape.addPoint(new Point(15, 85));
        assertEquals(5, testShape.getArrayList().size());
    }
    
}
