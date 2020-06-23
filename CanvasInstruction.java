import java.awt.Color;
import java.util.Scanner;

/**
 * Code for the CanvasInstruction class, managing a single canvas instruction
 *
 * @author      Bill Barry
 * @version     2017-09-07
 */
public class CanvasInstruction {

    private int width, height;
    private Color colorSolid, colorTop, colorBottom;
    private boolean isGradient;

    //**********************************************************************************************
    //          CONSTRUCTORS
    //**********************************************************************************************
    
    /**
     * Constructor for the CanvasInstruction class
     *
     * @param   width           the desired width of the drawing canvas
     * @param   height          the desired height of the drawing canvas
     * @param   colorSolid      the desired background Color for the canvas; use null for gradient fills
     * @param   colorTop        for gradient fills, the color to be drawn at the top of the canvas; use null for solid fills
     * @param   colorBottom     for gradient fills, the color to be drawn at the bottom of the canvas; use null for solid fills
     */
    public CanvasInstruction(int width, int height, Color colorSolid, Color colorTop, Color colorBottom) {
        this.width = width;
        this.height = height;
        this.colorSolid = colorSolid;
        this.colorTop = colorTop;
        this.colorBottom = colorBottom;
        isGradient = false;

        // If top or bottom is null, set a solid color instead
        if (this.colorTop == null && this.colorBottom != null) {
            this.colorSolid = this.colorBottom;
            this.colorTop = this.colorBottom = null;
        } else if (this.colorBottom == null && this.colorTop != null) {
            this.colorSolid = this.colorTop;
            this.colorTop = this.colorBottom = null;
        } 
        
        // With non-null top and bottom, it appears they want a gradient
        if (this.colorTop != null && this.colorBottom != null) {
            isGradient = true;
        } 
        
        // If top and bottom are the same color, set a solid color instead
        if (isGradient && this.colorTop.equals(colorBottom)) {
            this.colorSolid = this.colorTop;
            this.colorTop = this.colorBottom = null;
            isGradient = false;
        }
        
        // If no gradient, and no solid, set a white solid fill
        if (!isGradient && this.colorSolid == null) {
            this.colorSolid = new Color(255, 255, 255);
        }
        validateOrDefault(this);
    }

    //**********************************************************************************************
    //          INSTANCE METHODS
    //**********************************************************************************************
    /**
     * Private, empty constructor for CanvasInstruction class, useful for reading instructions from files, for example.  Sets all values to initial defaults as indicated in documentation
     *
     */
    private CanvasInstruction() {
        this(100, 100, new Color(255, 255, 255), null, null);
    }

    /**
     * Retrieves the canvas width
     *
     * @return      the canvas width
     */
    public int getWidth() { 
        return width; 
    }

    /**
     * Retrieves the canvas height
     *
     * @return      the canvas height
     */
    public int getHeight() {
        return height; 
    }

    /**
     * Retrieves the background Color of the canvas
     *
     * @return      the Canvas's background color
     */
    public Color getColorSolid() {
        return colorSolid; 
    }
    
    /**
     * Retrieves the gradient Color used at the top of the canvas
     *
     * @return      the Canvas's top gradient color
     */
    public Color getColorTop() {
        return colorTop;
    }

    /**
     * Retrieves the gradient Color used at the bottom of the canvas
     *
     * @return      the Canvas's bottom gradient color
     */
    public Color getColorBottom() {
        return colorBottom;
    }
    
    /**
     * Retrieves whether the canvas will be drawn with a gradient color scheme
     * 
     * @return      true, if a gradient will be used; false, if not
     */
    public boolean getIsGradient() {
        return isGradient;
    }
    
    //**********************************************************************************************
    //          STATIC METHODS
    //**********************************************************************************************
    
    /**
     * Reads a canvas instruction from the supplied Scanner, and returns a corresponding CanvasInstruction object
     *
     * @param   fileIn  the Scanner object to use for retrieving instruction information
     * @return          an CanvasInstruction object containing the read instructions (plus defaults)
     */
    public static CanvasInstruction readFromFile(Scanner fileIn) {
        CanvasInstruction instruction = new CanvasInstruction();

        // Grab next instruction and split it up
        String instructLine = fileIn.nextLine();
        String[] instructFields = instructLine.split(",");
        int       red = 255,       green = 255,       blue = 255;
        int    topRed = 255,    topGreen = 255,    topBlue = 255;
        int bottomRed = 255, bottomGreen = 255, bottomBlue = 255;
        boolean attemptGradient = false;

        // Separate out the individual instructions and interpret them
        for (String field : instructFields) {
            String[] pieces = field.split("=");
            String cmd   = pieces[0] = pieces[0].trim().toLowerCase();
            String value = pieces[1] = pieces[1].trim().toLowerCase();

            switch (cmd) {
                case "width"       : instruction.width = Integer.parseInt(value);                    break;
                case "height"      : instruction.height = Integer.parseInt(value);                   break;
                case "red"         : red   = Integer.parseInt(value);                                break;
                case "green"       : green = Integer.parseInt(value);                                break;
                case "blue"        : blue  = Integer.parseInt(value);                                break;
                case "topred"      : topRed = Integer.parseInt(value);
                                     attemptGradient = true;                                         break;
                case "topgreen"    : topGreen = Integer.parseInt(value);
                                     attemptGradient = true;                                         break;
                case "topblue"     : topBlue = Integer.parseInt(value);
                                     attemptGradient = true;                                         break;
                case "bottomred"   : bottomRed = Integer.parseInt(value);
                                     attemptGradient = true;                                         break;
                case "bottomgreen" : bottomGreen = Integer.parseInt(value);
                                     attemptGradient = true;                                         break;
                case "bottomblue"  : bottomBlue = Integer.parseInt(value);
                                     attemptGradient = true;                                         break;
                default       : /* do nothing  */                                                    break;
            }
        }

        // Validate RGB value ranges and create Color objects
        red   = Utility.rgbRangeLimit(red);
        green = Utility.rgbRangeLimit(green);
        blue  = Utility.rgbRangeLimit(blue);
        instruction.colorSolid = new Color(red, green, blue);
        if (attemptGradient) {
            topRed   = Utility.rgbRangeLimit(topRed);
            topGreen = Utility.rgbRangeLimit(topGreen);
            topBlue  = Utility.rgbRangeLimit(topBlue);
            instruction.colorTop = new Color(topRed, topGreen, topBlue);
            bottomRed   = Utility.rgbRangeLimit(bottomRed);
            bottomGreen = Utility.rgbRangeLimit(bottomGreen);
            bottomBlue  = Utility.rgbRangeLimit(bottomBlue);
            instruction.colorBottom = new Color(bottomRed, bottomGreen, bottomBlue);
            instruction.isGradient = true;
        } else {
            instruction.colorTop = null;
            instruction.colorBottom = null;
            instruction.isGradient = false;
        }
        validateOrDefault(instruction);
        return instruction;
    }

    /**
     * Validates the instruction information in an existing Instruction, setting any invalid data to defaults
     *
     * @param   instruction     an existing Instruction to validate
     * @throws                  IllegalArgumentException if the Instruction reference is null
     */
    private static void validateOrDefault(CanvasInstruction instruction) {
        if (instruction == null) {
            throw new IllegalArgumentException("Instruction references must not be null");
        }
        
        // Width must be >= 1
        instruction.width  = Math.max(1, instruction.width);

        // Height  must be >= 1
        instruction.height = Math.max(1, instruction.height);
    }

}
