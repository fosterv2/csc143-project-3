import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.Color;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class DrawInstructionTest.
 *
 * @author        Bill Barry
 * @version       2017-09-12
 */
public class DrawInstructionTest {
    /**
     * Default constructor for test class DrawInstructionTest
     */
    public DrawInstructionTest() {
    }

    /**
     * Sets up the test fixture; called before every test case method
     */
    @Before
    public void setUp() {
    }

    /**
     * Tears down the test fixture; called after every test case method
     */
    @After
    public void tearDown() {
    }
    
    //-----------------------------------------------------------------------------------
    //      CASES THAT DEAL DIRECTLY WITH OBJECTS
    //-----------------------------------------------------------------------------------
    
    /**
     * Tests the constructor and accessors
     */
    @Test
    public void testConstructor() {
        DrawInstruction di = new DrawInstruction("foo", 125, 33, 66, 1, 25, 50, true, 
                                                 new Color(100, 150, 200), 10, 5);
                                                 
        assertEquals("foo",                     di.getShapeName());
        assertEquals(125,                       di.getScalePercent());
        assertEquals(33,                        di.getStartingX());
        assertEquals(66,                        di.getStartingY());
        assertEquals(1,                         di.getRepeats());
        assertEquals(25,                        di.getRepeatOffsetX());
        assertEquals(50,                        di.getRepeatOffsetY());
        assertTrue(                             di.getFilled());
        assertEquals(new Color(100, 150, 200),  di.getColor());
        assertEquals(10,                        di.getRotate());
        assertEquals(5,                         di.getRepeatRotate());
    }
    
    @Test
    public void testValidations() {
        DrawInstruction di = new DrawInstruction("circle", 0, 10, 20, 0, 30, 40, false, 
                                                    new Color(101, 102, 103), 0, 0);
        assertEquals(1,     di.getScalePercent());
        assertEquals(1,     di.getRepeats());
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testPrecondConstrShapeNameNull() {
        DrawInstruction di = new DrawInstruction(null, 99, 9, 9, 9, 99, 99, true, 
                                                 new Color(99, 99, 99), 9, 9);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testPrecondConstrShapeNameEmpty() {
        DrawInstruction di = new DrawInstruction("", 99, 9, 9, 9, 99, 99, true, 
                                                 new Color(99, 99, 99), 9, 9);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testPrecondConstrColorNull() {
        DrawInstruction di = new DrawInstruction("bar", 99, 9, 9, 9, 99, 99, true, null, 9, 9);
    }

    //-----------------------------------------------------------------------------------
    //      CASES THAT READ INSTRUCTIONS FROM FILES
    //-----------------------------------------------------------------------------------

    @Test
    public void testReadMost() throws FileNotFoundException {
        File testFile = new File("TestDrawInstr-Most.txt");
        Scanner in = new Scanner(testFile);
        DrawInstruction di = DrawInstruction.readFromFile(in);
        in.close();
        assertEquals("square",                      di.getShapeName());
        assertEquals(123,                           di.getScalePercent());
        assertEquals(101,                           di.getStartingX());
        assertEquals(52,                            di.getStartingY());
        assertTrue(                                 di.getFilled());
        assertEquals(new Color(101, 102, 103),      di.getColor());
        assertEquals(14,                            di.getRotate());
        assertEquals(3,                             di.getRepeats());
        assertEquals(10,                            di.getRepeatOffsetX());
        assertEquals(15,                            di.getRepeatOffsetY());
        assertEquals(13,                            di.getRepeatRotate());
    }

    @Test 
    public void testReadRgbBounds() throws FileNotFoundException {
        File testFile = new File("TestDrawInstr-RgbBounds.txt");
        Scanner in = new Scanner(testFile);
        DrawInstruction di = DrawInstruction.readFromFile(in);
        assertEquals(new Color(0, 0, 0), di.getColor());
        di = DrawInstruction.readFromFile(in);
        assertEquals(new Color(255, 255, 255), di.getColor());
        in.close();
    }
}
