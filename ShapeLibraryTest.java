import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class ShapeLibraryTest tests the ShapeLibrary class.
 *
 * @author  Valerie Foster
 * @version 1/30/2018
 */
public class ShapeLibraryTest {
    
    ShapeLibrary testLib;

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp() {
        testLib = new ShapeLibrary();
    }

    @Test
    public void testConstructor() {
        assertEquals("circle", testLib.getShape(0).getName());
    }
    
    @Test
    public void testGetShapeFromName() {
        assertEquals(testLib.getShape(0), testLib.getShapeFromName("circle"));
    }
    
}
