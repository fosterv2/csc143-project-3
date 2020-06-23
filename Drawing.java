import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Graphics;

/**
 * The class Drawing 
 *
 * @author Valerie Foster
 * @version 1/30/2018
 */
public class Drawing {

    // instance variables
    private ShapeLibrary shapeLib;
    private CanvasInstruction canvas;
    private ArrayList<DrawInstruction> drawArr;

    /**
     * Constructor for objects of class Drawing
     * 
     * @param   shapeLib    the ShapeLibrary object used to draw shapes
     * @param   file        a File with the instructions for what to draw
     */
    public Drawing(ShapeLibrary shapeLib, File file) {
        this.shapeLib = shapeLib;
        this.drawArr = new ArrayList<DrawInstruction>();
        try {
            Scanner input = new Scanner(file);
            this.readFile(input);
        } catch  (FileNotFoundException e) {
            System.out.println("The file was not found.");
        }
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    private void readFile(Scanner input) {
        String line = input.nextLine();
        Scanner canInst = new Scanner(line);
        canvas = CanvasInstruction.readFromFile(canInst);
        while (input.hasNext()) {
            String lines = input.nextLine();
            Scanner drawInst = new Scanner(lines);
            DrawInstruction draw = DrawInstruction.readFromFile(drawInst);
            drawArr.add(draw);
        }
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @return    
     */
    public void draw() {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        DrawingPanel drawing = new DrawingPanel(width, height);
        drawing.setBackground(canvas.getColorSolid());
        Graphics pb = drawing.getGraphics();
        for (DrawInstruction drwInst : drawArr) {
            pb.setColor(drwInst.getColor());
            String name = drwInst.getShapeName();
            Shape shape = shapeLib.getShapeFromName(name);
            int count = shape.getArrayList().size();
            int[] x = new int[count];
            int[] y = new int[count];
            int offset = 0;
            for (int rep = 0; rep < drwInst.getRepeats(); rep++) {
                int startX, startY;
                if (drwInst.getStartingX() == Integer.MIN_VALUE) {
                    startX = (int)(canvas.getWidth() * Math.random());
                } else {
                    startX = drwInst.getStartingX();
                }
                if (drwInst.getStartingY() == Integer.MIN_VALUE) {
                    startY = (int)(canvas.getHeight() * Math.random());
                } else {
                    startY = drwInst.getStartingY();
                }
                for (int idx = 0; idx < count; idx++) {
                    x[idx] = (int)(shape.getPoint(idx).getX() * drwInst.getScalePercent() * 0.01) + startX + drwInst.getRepeatOffsetX() * offset;
                    y[idx] = (int)(shape.getPoint(idx).getY() * drwInst.getScalePercent() * 0.01) + startY + drwInst.getRepeatOffsetY() * offset;
                }
                offset++;
                if (drwInst.getFilled()) {
                    pb.fillPolygon(x, y, count);
                } else {
                    pb.drawPolygon(x, y, count);
                }
            }
        }
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @return    
     */
    public String toString() {
        String str = "";
        return str;
    }
}
