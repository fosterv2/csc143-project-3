import java.io.File;

public class Main {
    
    public static void main(String[] args)  {
        
        ShapeLibrary shapeLib  = new ShapeLibrary();
        Drawing drawing1 = new Drawing(shapeLib, new File("Instruct-Simple.txt"));
        Drawing drawing2 = new Drawing(shapeLib, new File("Instruct-Rand.txt"));
        Drawing drawing3 = new Drawing(shapeLib, new File("Instruct-Tree.txt"));
        Drawing drawing4 = new Drawing(shapeLib, new File("Instruct-Ghost.txt"));
        Drawing drawing5 = new Drawing(shapeLib, new File("Instruct-RepeatOffset.txt"));
        
        drawing5.draw();
        drawing4.draw();
        drawing3.draw();
        drawing2.draw();
        drawing1.draw();
        
        System.out.print("\u000c");
        
        System.out.println(shapeLib);
    }
   
}
