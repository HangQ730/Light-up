/**
 * AkariViewer represents an interface for a player of Akari.
 *
 * @author Lyndon While
 * @version 2021
 * 
 * Group Members: Hang Qi       22449507
 *                Kate Gilbert  22968719
 * 
 */
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AkariViewer implements MouseListener
{    
    private Akari puzzle; 
    private SimpleCanvas sc; 
    private int size;
    private int gridsize = 50;
    private int offset = gridsize/10;
    
    // All the colour settings. You can change the colours if you like.
    private final Color bgColor  = Color.white;
    private final Color boardLineColor  = Color.black;
    private final Color wallColor  = Color.black;
    private final Color gridColor  = Color.white;
    private final Color litColor = Color.yellow;
    private final Color numColor = Color.white;
    private final Color fontColor = Color.black;
    private final Color blueBulb = Color.blue;
    private final Color redBulb = Color.red;
    // Used to store the cross position.
    private ArrayList cross = new ArrayList<String>();

    /**
     * Constructor for objects of class AkariViewer.
     * Sets all fields and displays the initial puzzle.
     */
    public AkariViewer(Akari puzzle)
    {
        // TODO 10
        this.puzzle = puzzle;
        size = puzzle.getSize();
        // Initialize canvas
        sc = new SimpleCanvas("Akari Puzzle", size * gridsize + 50,  
                              size * gridsize + 150, bgColor);
        sc.addMouseListener(this);
        displayPuzzle();
        
        
    }
    
    /**
     * Selects from among the provided files in folder Puzzles. 
     * The number xyz selects pxy-ez.txt. 
     */
    public AkariViewer(int n)
    {
        this(new Akari("Puzzles/p" + n / 10 + "-e" + n % 10 + ".txt"));
    }
    
    /**
     * Uses the provided example file on the LMS page.
     */
    public AkariViewer()
    {
        this(77);
    }
    
    /**
     * Returns the internal state of the puzzle.
     */
    public Akari getPuzzle()
    {
        // TODO 9a
        return this.puzzle;
    }
    
    /**
     * Returns the canvas.
     */
    public SimpleCanvas getCanvas()
    {
        // TODO 9b
        return this.sc;
    }
    
    /**
     * Used to draw a single grid with board lines.
     */    
    private void drawBoard(int x, int y, Color c)
    {
        // Font settings
        Font numfont = new Font("Calibri", Font.PLAIN, 20);
        sc.drawRectangle(y * gridsize +25 , x * gridsize+25 , 
                (y + 1) * gridsize+26, (x + 1) * gridsize+26, boardLineColor);
        sc.drawRectangle(y * gridsize +26 , x * gridsize+26 , 
                (y + 1) * gridsize+25, (x + 1) * gridsize+25, c);
        // Add numbers to walls according to different wall types.
        switch(puzzle.getBoard(x,y)){
            case ZERO:
                sc.setFont(numfont);
                sc.drawString("0", (int) (23+gridsize * (y + 0.45)), 
                            (int) (27+gridsize * (x + 0.6)), numColor);
                break;
            case ONE:
                sc.setFont(numfont);
                sc.drawString("1", (int) (23+gridsize * (y + 0.45)), 
                            (int) (27+gridsize * (x + 0.6)), numColor);
                break;
            case TWO:
                sc.setFont(numfont);
                sc.drawString("2", (int) (23+gridsize * (y + 0.45)), 
                            (int) (27+gridsize * (x + 0.6)), numColor);
                break;
            case THREE:
                sc.setFont(numfont);
                sc.drawString("3", (int) (23+gridsize * (y + 0.45)), 
                            (int) (27+gridsize * (x + 0.6)), numColor);
                break;
            case FOUR:
                sc.setFont(numfont);
                sc.drawString("4", (int) (23+gridsize * (y + 0.45)), 
                            (int) (27+gridsize * (x + 0.6)), numColor);
                break;
        
        }
    }
        
    /**
     * Displays the initial puzzle; see the LMS page for a suggested layout. 
     */
    private void displayPuzzle()
    {
        // TODO 11
        Font font = new Font("SansSerif", Font.PLAIN, 18);
        Color c;
        boolean isBulb = false;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){   
                if (puzzle.getBoard(i,j) == Space.EMPTY) c = gridColor;
                else if (puzzle.getBoard(i,j) == Space.BULB){
                    c=litColor;
                    isBulb = true;                    
                }
                else c = wallColor; 
                // Draw all the EMPTY squares and BULB squares.
                drawBoard(i, j, c);
                // If a square is BULB, then draw a bulb icon over it.
                if (isBulb){
                    sc.setFont(font);
                    cross.remove(""+i+j);
                    sc.drawString("\uD83D\uDCA1",j*gridsize+40, i*gridsize+57, blueBulb);
                    isBulb = false;
                }
                // Draw the squares which are lit by a bulb.
                if (puzzle.getBoard(i,j)==Space.EMPTY && puzzle.canSeeBulb(i,j)){           
                    drawBoard(i, j, litColor);
                }
                // Change the bulb colour to red if there is a clashing bulb.
                if (puzzle.getBoard(i,j)==Space.BULB && puzzle.canSeeBulb(i,j)){
                    sc.drawString("\uD83D\uDCA1", j*gridsize+40, i*gridsize+57, redBulb);
                }
            }
        }
        // Check the cross and display.
        for (Object i:cross){
            boolean drawX = true; // Used to indicate whether or not to draw a cross.
            String s = (String)i;
            // Obtain the coordinate.
            int x = Integer.parseInt(String.valueOf(s.charAt(0)));
            int y = Integer.parseInt(String.valueOf(s.charAt(1)));
            // If a bulb is placed over a cross, then delete that cross.
            if (puzzle.getBoard(x,y)==Space.BULB){
                cross.remove(i);
                drawX = false;
            }
            // Draw cross icon.
            if (drawX)
                sc.setFont(font);
                sc.drawString ("\u26CC", y*gridsize+40,x*gridsize+57,Color.red);
                
        }
        sc.setFont(font);
        sc.drawString ("SOLVED?", 65,size*gridsize+60, fontColor);
        sc.drawString ("CLEAR", 265,size*gridsize+60, fontColor);
        sc.drawRectangle(65,size*gridsize+70, 265,size*gridsize+120, bgColor);
        
    }
    
    /**
     * Performs a left click on the square at r,c if the indices are legal, o/w does nothing. 
     * Updates both puzzle and the display. 
     */
    public void leftClick(int r, int c)
    {
        // TODO 12
        puzzle.leftClick(r,c);
        displayPuzzle();
    }
    
    /**
     * Performs a right click on the square at r,c if the indices are legal, o/w does nothing. 
     * Updates both puzzle and the display. 
     * If r,c is empty, a cross is placed; if it has a bulb, that bulb is removed and place a cross;
     * if it has a cross, then remove it.
     */
    public void rightClick(int r, int c)
    {
        // TODO 12
        puzzle.rightClick(r,c);
        String cross_i = ""+r+c;
        // If r,c is already in cross array, then remove it. If not, add it into cross.
        boolean has = true;
        for (int i=0;i<cross.size();i++){
            if (cross.contains(cross_i)){
                cross.remove(cross_i);
                has = false;
                break;
            }
        }
        if (has && Space.isMutable(puzzle.getBoard(r,c))) cross.add(cross_i);
        
        displayPuzzle();
    }
    
    // TODO 13
    public void mousePressed (MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)){
            leftClick((e.getY()-25) / gridsize, (e.getX()-25) / gridsize);
            // Perform the SOLVED? function.
            if (e.getX()>55 && e.getX()<145 && 
                e.getY()>size*gridsize+35 && e.getY()<size*gridsize+75){
                    displayPuzzle();
                    sc.drawString (puzzle.isSolution(), 65,size*gridsize+100, 
                                    fontColor);                
                }
            // Perform the CLEAR funtion
            if (e.getX()>255 && e.getX()<335 && 
            e.getY()>size*gridsize+35 && e.getY()<size*gridsize+75){
                puzzle.clear();
                cross.clear();
                displayPuzzle();
            }
        }else
        if (SwingUtilities.isRightMouseButton(e)){
            rightClick((e.getY()-25) / gridsize, (e.getX()-25) / gridsize);
        }
        
    }
    public void mouseClicked (MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered (MouseEvent e) {}
    public void mouseExited  (MouseEvent e) {}
}
