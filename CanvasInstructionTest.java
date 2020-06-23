import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class testing the CanvasInstruction production class
 *
 * @author      Bill Barry
 * @version     2017-09-11
 */
public class CanvasInstructionTest {
    /**
     * Default constructor for test class CanvasInstructionTest
     */
    public CanvasInstructionTest() {
    }

    /**
     * Sets up the test fixture.  Called before every test case method.
     */
    @Before
    public void setUp() {
    }

    /**
     * Tears down the test fixture.  Called after every test case method.
     */
    @After
    public void tearDown() {
    }
    
    //-----------------------------------------------------------------------------------
    //      CASES THAT DEAL DIRECTLY WITH OBJECTS
    //-----------------------------------------------------------------------------------
    
    /**
     * Tests a simple case with height, width, and a solid fill
     */
    @Test
    public void testConstrSolid() {
        CanvasInstruction ci = new CanvasInstruction(400, 300, new Color(50, 100, 200), null, null);
        assertEquals(400, ci.getWidth());
        assertEquals(300, ci.getHeight());
        assertEquals(new Color(50, 100, 200), ci.getColorSolid());
        assertFalse(ci.getIsGradient());
    }

    /**
     * Tests a straightforward gradient fill
     */
    @Test
    public void testConstrGrad() {
        CanvasInstruction ci = new CanvasInstruction(500, 200, null, new Color(50, 51, 52),  new Color(100, 101, 102));
        assertEquals(500, ci.getWidth());
        assertEquals(200, ci.getHeight());
        assertEquals(new Color(50, 51, 52), ci.getColorTop());
        assertEquals(new Color(100, 101, 102), ci.getColorBottom());
        assertTrue(ci.getIsGradient());
    }
    
    /**
     * Tests a gradient case with a supplied bottom but null top
     */
    @Test
    public void testConstrGradTopNull() {
        CanvasInstruction ci = new CanvasInstruction(100, 300, null, null,  new Color(100, 101, 102));        
        assertNull(ci.getColorTop());
        assertNull(ci.getColorBottom());
        assertNotNull(ci.getColorSolid());
        assertFalse(ci.getIsGradient());
    }

    /**
     * Tests a gradient case with a supplied top but null bottom
     */
    @Test
    public void testConstrGradBottomNull() {
        CanvasInstruction ci = new CanvasInstruction(100, 300, null, new Color(100, 101, 102), null);
        assertNull(ci.getColorTop());
        assertNull(ci.getColorBottom());
        assertNotNull(ci.getColorSolid());
        assertFalse(ci.getIsGradient());
    }

    /**
     * Tests a gradient case with the top and bottom colors the same (yielding a solid fill instead)
     */
    @Test
    public void testConstrGradTopBottomSame() {
        CanvasInstruction ci = new CanvasInstruction(100, 300, null, new Color(200, 201, 202), new Color(200, 201, 202));
        assertNull(ci.getColorTop());
        assertNull(ci.getColorBottom());
        assertEquals(new Color(200, 201, 202), ci.getColorSolid());
        assertFalse(ci.getIsGradient());
    }

    /**
     * Tests a case where no colors are provided, yielding a default solid fill
     */
    @Test
    public void testConstrNoColors() {
        CanvasInstruction ci = new CanvasInstruction(100, 300, null, null, null);
        assertNull(ci.getColorTop());
        assertNull(ci.getColorBottom());
        assertEquals(new Color(255, 255, 255), ci.getColorSolid());
        assertFalse(ci.getIsGradient());
    }
    
    /**
     * Tests validation of canvas width and height, with moves to defaults
     */
    @Test
    public void testValidations() {
        CanvasInstruction ci = new CanvasInstruction(0, 0, null, null, null);
        assertEquals(1, ci.getWidth());
        assertEquals(1, ci.getHeight());
    }

    //-----------------------------------------------------------------------------------
    //      CASES THAT READ INSTRUCTIONS FROM FILES
    //-----------------------------------------------------------------------------------
    
    /**
     * Tests a read case with height, width only
     * 
     * @throws FileNotFoundException if the test file can't be found
     */
    @Test
    public void testReadWidthHeight() throws FileNotFoundException {
        File testFile = new File("TestCanvasInstr-Simple.txt");
        Scanner in = new Scanner(testFile);
        CanvasInstruction ci = CanvasInstruction.readFromFile(in);
        in.close();
        assertEquals(601, ci.getWidth());
        assertEquals(402, ci.getHeight());
        assertFalse(ci.getIsGradient());
        assertEquals(new Color(255, 255, 255), ci.getColorSolid());
        assertNull(ci.getColorTop());
        assertNull(ci.getColorBottom());
    }
    
    /**
     * Tests a read case with boundary challenges for height and width, yielding defaults
     * 
     * @throws FileNotFoundException if the test file can't be found
     */
    @Test
    public void testReadWidthHeightBoundsLow() throws FileNotFoundException {
        File testFile = new File("TestCanvasInstr-SizeBoundsLow.txt");
        Scanner in = new Scanner(testFile);
        CanvasInstruction ci = CanvasInstruction.readFromFile(in);
        in.close();
        assertEquals(1, ci.getWidth());
        assertEquals(1, ci.getHeight());
    }
    
    /**
     * Tests a read case with a solid fill
     * 
     * @throws FileNotFoundException if the test file can't be found
     */
    @Test
    public void testReadSolid() throws FileNotFoundException {
        File testFile = new File("TestCanvasInstr-Solid.txt");
        Scanner in = new Scanner(testFile);
        CanvasInstruction ci = CanvasInstruction.readFromFile(in);
        in.close();
        assertEquals(100, ci.getWidth());
        assertEquals(100, ci.getHeight());
        assertFalse(ci.getIsGradient());
        assertEquals(new Color(100, 102, 101), ci.getColorSolid());
    }

    /**
     * Tests a read case with a gradient fill (top and bottom supplied)
     * 
     * @throws FileNotFoundException if the test file can't be found
     */
    @Test
    public void testReadGrad() throws FileNotFoundException {
        File testFile = new File("TestCanvasInstr-Grad.txt");
        Scanner in = new Scanner(testFile);
        CanvasInstruction ci = CanvasInstruction.readFromFile(in);
        in.close();
        assertTrue(ci.getIsGradient());
        assertEquals(new Color(100, 101, 102), ci.getColorTop());
        assertEquals(new Color(200, 201, 202), ci.getColorBottom());
        assertEquals(new Color(255, 255, 255), ci.getColorSolid());
    }

    /**
     * Tests a read case with gradient fill where top is supplied but bottom is omitted
     * 
     * @throws FileNotFoundException if the test file can't be found
     */
    @Test
    public void testReadGradNoBottom() throws FileNotFoundException {
        File testFile = new File("TestCanvasInstr-Grad-NoBottom.txt");
        Scanner in = new Scanner(testFile);
        CanvasInstruction ci = CanvasInstruction.readFromFile(in);
        in.close();
        assertTrue(ci.getIsGradient());
        assertEquals(new Color(100, 101, 102), ci.getColorTop());
        assertEquals(new Color(255, 255, 255), ci.getColorBottom());
        assertEquals(new Color(255, 255, 255), ci.getColorSolid());
    }

    /**
     * Tests a read case with gradient fill where bottom is supplied but top is omitted
     * 
     * @throws FileNotFoundException if the test file can't be found
     */
    @Test
    public void testReadGradNoTop() throws FileNotFoundException {
        File testFile = new File("TestCanvasInstr-Grad-NoTop.txt");
        Scanner in = new Scanner(testFile);
        CanvasInstruction ci = CanvasInstruction.readFromFile(in);
        in.close();
        assertTrue(ci.getIsGradient());
        assertEquals(new Color(255, 255, 255), ci.getColorTop());
        assertEquals(new Color(200, 201, 202), ci.getColorBottom());
        assertEquals(new Color(255, 255, 255), ci.getColorSolid());
    }

    /**
     * Tests a read case with out-of-bounds RGB values on solid fills
     * 
     * @throws FileNotFoundException if the test file can't be found
     */
    @Test
    public void testReadRgbBoundsSolid() throws FileNotFoundException {
        File testFile = new File("TestCanvasInstr-RgbBounds-Solid.txt");
        Scanner in = new Scanner(testFile);
        CanvasInstruction ci = CanvasInstruction.readFromFile(in);
        assertFalse(ci.getIsGradient());
        assertEquals(new Color(  0,   0,   0), ci.getColorSolid());
        ci = CanvasInstruction.readFromFile(in);
        assertEquals(new Color(255, 255, 255), ci.getColorSolid());
        in.close();
    }

    /**
     * Tests a read case with out-of-bounds RGB values on gradient top fills
     * 
     * @throws FileNotFoundException if the test file can't be found
     */
    @Test
    public void testReadRgbBoundsGradTop() throws FileNotFoundException {
        File testFile = new File("TestCanvasInstr-RgbBounds-GradTop.txt");
        Scanner in = new Scanner(testFile);
        CanvasInstruction ci = CanvasInstruction.readFromFile(in);
        assertTrue(ci.getIsGradient());
        assertEquals(new Color(  0,   0,   0), ci.getColorTop());
        assertEquals(new Color(200, 225, 250), ci.getColorBottom());
        ci = CanvasInstruction.readFromFile(in);
        assertEquals(new Color(255, 255, 255), ci.getColorTop());
        assertEquals(new Color(200, 225, 250), ci.getColorBottom());
        in.close();
    }

    /**
     * Tests a read case with out-of-bounds RGB values on gradient bottom fills
     * 
     * @throws FileNotFoundException if the test file can't be found
     */
    @Test
    public void testReadRgbBoundsGradBottom() throws FileNotFoundException {
        File testFile = new File("TestCanvasInstr-RgbBounds-GradBottom.txt");
        Scanner in = new Scanner(testFile);
        CanvasInstruction ci = CanvasInstruction.readFromFile(in);
        assertTrue(ci.getIsGradient());
        assertEquals(new Color(  0,   0,   0), ci.getColorBottom());
        assertEquals(new Color(200, 225, 250), ci.getColorTop());
        ci = CanvasInstruction.readFromFile(in);
        assertEquals(new Color(255, 255, 255), ci.getColorBottom());
        assertEquals(new Color(200, 225, 250), ci.getColorTop());
        in.close();
    }
    
    /**
     * Tests spec's claim that commands and values are case insensitive
     * 
     * @throws FileNotFoundException if the test file can't be found
     */
    @Test
    public void testReadSpecClaimsCaseInsens() throws FileNotFoundException {
        File testFile = new File("TestCanvasInstr-SpecCase.txt");
        Scanner in = new Scanner(testFile);
        CanvasInstruction ci = CanvasInstruction.readFromFile(in);
        in.close();
        assertEquals(500, ci.getWidth());
        assertEquals( 25, ci.getHeight());
        assertTrue(ci.getIsGradient());
        assertEquals(new Color( 23,  24,  25), ci.getColorSolid());
        assertEquals(new Color(100, 101, 102), ci.getColorTop());
        assertEquals(new Color(200, 201, 202), ci.getColorBottom());
    }

    /**
     * Tests spec's claim that white space within an instruction will be ignored
     * 
     * @throws FileNotFoundException if the test file can't be found
     */
    @Test
    public void testReadSpecClaimsWhiteSpace() throws FileNotFoundException {
        File testFile = new File("TestCanvasInstr-SpecWhiteSpace.txt");
        Scanner in = new Scanner(testFile);
        CanvasInstruction ci = CanvasInstruction.readFromFile(in);
        in.close();
        assertEquals(500, ci.getWidth());
        assertEquals( 25, ci.getHeight());
        assertTrue(ci.getIsGradient());
        assertEquals(new Color( 23,  24,  25), ci.getColorSolid());
        assertEquals(new Color(100, 101, 102), ci.getColorTop());
        assertEquals(new Color(200, 201, 202), ci.getColorBottom());
    }
}
