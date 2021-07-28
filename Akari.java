/**
 * Akari represents a single puzzle of the game Akari.
 *
 * @author Lyndon While 
 * @version 2021
 * 
 * Group Members: Hang Qi       22449507
 *                Kate Gilbert  22968719
 *                
 */
import java.util.ArrayList; 

public class Akari
{
    private String filename; // the name of the puzzle file
    private int size;        // the board is size x size
    private Space[][] board; // the board is a square grid of spaces of various types

    /**
     * Constructor for objects of class Akari. 
     * Creates and initialises the fields with the contents of filename. 
     * The layout of a puzzle file is described on the LMS page; 
     * you may assume the layout is always correct. 
     */
    public Akari(String filename)
    {
        // TODO 3
        this.filename = filename;
        FileIO fio = new FileIO(filename);
        this.size = Integer.valueOf(fio.getLines().get(0));
        this.board = new Space[this.size][this.size];
        // Used to store the file contents to avoid reading disk files repeatedly.
        String[] filetext = new String[6];
        // Store the types of walls for future work.
        Space[] spacetext = {Space.BLACK, Space.ZERO, Space.ONE, Space.TWO, 
                             Space.THREE, Space.FOUR};
        // Store the file contents in filetext.
        for (int i=0;i<=5;i++){
            filetext[i] = fio.getLines().get(i+1);
        }
        // Initialize board to EMPTY.
        for (int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                board[i][j] = Space.EMPTY;
            }
        }
        // Add the puzzle contents according to the file.
        for (int i=0;i<=5;i++){
            for (int j=0;j<convertLocation(filetext[i]).size();j++){
                int[] location = (int[])convertLocation(filetext[i]).get(j);
                board[location[0]][location[1]] = spacetext[i];
            }            
        }
        
    }     
    
    /**
     * This function is used to parse the file content. 
     * Convert Strings to coordinates so that can be used directly.
     */
    private ArrayList convertLocation(String coordinate){
        ArrayList<int[]> location = new ArrayList<int[]>();
        String[] colist = coordinate.split(" ");
        for (int i=0; i<colist.length; i++){
            if (colist[i].length()>=2){
                int x = parseIndex(colist[i].charAt(0));
                for (int j=1; j<colist[i].length(); j++){
                    int y = parseIndex(colist[i].charAt(j));
                    int[] loactioni = {x,y};
                    location.add(loactioni);
                }
            }
        }
        return location;

    }
    
    /**
     * Uses the example file from the LMS page.
     */
    public Akari()
    {
        this("Puzzles/p7-e7.txt");
    }
    
    /**
     * Returns the name of the puzzle file.
     */
    public String getFilename()
    {
        // TODO 1a
        return filename;
    }
    
    /**
     * Returns the size of the puzzle.
     */
    public int getSize()
    {
        // TODO 1b
        return size;
    }
    
    /**
     * Returns true iff k is a legal index into the board. 
     */
    public boolean isLegal(int k)
    {
        // TODO 5
        return k >= 0 && k < this.size; 
    }
    
    /**
     * Returns true iff r and c are both legal indices into the board. 
     */
    public boolean isLegal(int r, int c)
    {
        // TODO 6
        return isLegal(r) && isLegal(c);  
    }
    
    /**
     * Returns the contents of the square at r,c if the indices are legal, 
     * o/w throws an illegal argument exception. 
     */
    public Space getBoard(int r, int c)
    {
        // TODO 7
        if (isLegal(r,c)) return board[r][c];
        else throw new IllegalArgumentException();
        
    }
    
    /**
     * Returns the int equivalent of x. 
     * If x is a digit, returns 0 -> 0, 1 -> 1, etc; 
     * if x is an upper-case letter, returns A -> 10, B -> 11, etc; 
     * o/w throws an illegal argument exception. 
     */
    public static int parseIndex(char x)
    {
        // TODO 2
        if (Character.isDigit(x)) return (int)(x-48);
        else 
        if (Character.isUpperCase(x)) return (int)(x-55);
        else throw new IllegalArgumentException();
    }
    
    /**
     * Performs a left click on the square at r,c if the indices are legal, o/w does nothing. 
     * If r,c is empty, a bulb is placed; if it has a bulb, that bulb is removed.
     */
    public void leftClick(int r, int c)
    {
        // TODO 8
        if (isLegal(r,c)){ 
            if (board[r][c]==Space.EMPTY){
                board[r][c]=Space.BULB;
            }else if (board[r][c]==Space.BULB){
                board[r][c]=Space.EMPTY;
            }
        }
    }
    
    /**
     * Performs a right click on the square at r,c if the indices are legal, o/w does nothing. 
     * If r,c is mutable, then change to EMPTY.
     */
    public void rightClick(int r, int c)
    {
        // TODO 8
        if (isLegal(r,c)){ 
            if (Space.isMutable(board[r][c])){
                board[r][c]=Space.EMPTY;
            }
        }
    }
    
    /**
     * Sets all mutable squares on the board to empty.
     */
    public void clear()
    {
        // TODO 4
        for (int i=0;i<board.length;i++){
            for (int j=0;j<board.length;j++){
                if (Space.isMutable(board[i][j])){
                    board[i][j] = Space.EMPTY;
                }
            }
        }
    }
    
    /**
     * Returns the number of bulbs adjacent to the square at r,c. 
     * Throws an illegal argument exception if r,c are illegal coordinates.
     */
    public int numberOfBulbs(int r, int c)
    {
        // TODO 14
        int n = 0;
        if (isLegal(r,c)){
            // Check the four adjacent positions.
            if (isLegal(r+1,c) && board[r+1][c]==Space.BULB) n++;
            if (isLegal(r-1,c) && board[r-1][c]==Space.BULB) n++;
            if (isLegal(r,c+1) && board[r][c+1]==Space.BULB) n++;
            if (isLegal(r,c-1) && board[r][c-1]==Space.BULB) n++;            
        }else throw new IllegalArgumentException();
        return n;
    }
    
    /**
     * Returns true iff the square at r,c is lit by a bulb elsewhere. 
     * Throws an illegal argument exception if r,c are illegal coordinates.
     */
    public boolean canSeeBulb(int r, int c)
    {
        // TODO 15
        if (isLegal(r,c)){
            // Check the four directions for any other bulbs
            for (int i=1;i<this.size;i++){
                if ((r+i)<this.size && !Space.isMutable(board[r+i][c])) break;
                if ((r+i)<this.size && board[r+i][c]==Space.BULB) return true;
            }
            for (int i=1;i<this.size;i++){
                if ((r-i)>=0 && !Space.isMutable(board[r-i][c])) break;
                if ((r-i)>=0 && board[r-i][c]==Space.BULB) return true;
            }
            for (int i=1;i<this.size;i++){
                if ((c+i)<this.size && !Space.isMutable(board[r][c+i])) break;
                if ((c+i)<this.size && board[r][c+i]==Space.BULB) return true;
            }
            for (int i=1;i<this.size;i++){
                if ((c-i)>=0 && !Space.isMutable(board[r][c-i])) break;
                if ((c-i)>=0 && board[r][c-i]==Space.BULB) return true;
            }
        }else throw new IllegalArgumentException();
        return false;
    }
    
    /**
     * Returns an assessment of the state of the puzzle, either 
     * "Clashing bulb at r,c", 
     * "Unlit square at r,c", 
     * "Broken number at r,c", or
     * three ticks, as on the LMS page. 
     * r,c must be the coordinates of a square that has that kind of error. 
     * If there are multiple errors on the board, you may return any one of them. 
     */
    public String isSolution()
    {
        // TODO 16
        // Check each square for errors.
        for (int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                switch (board[i][j]){
                case EMPTY:
                    if (!canSeeBulb(i,j)) return "Unlit square at "+i+","+j;
                    break;
                case ZERO:
                    if (numberOfBulbs(i,j)!=0) return "Broken number at "+i+","+j;
                    break;
                case ONE:
                    if (numberOfBulbs(i,j)!=1) return "Broken number at "+i+","+j;
                    break;
                case TWO:
                    if (numberOfBulbs(i,j)!=2) return "Broken number at "+i+","+j;
                    break;
                case THREE:
                    if (numberOfBulbs(i,j)!=3) return "Broken number at "+i+","+j;
                    break;
                case FOUR:
                    if (numberOfBulbs(i,j)!=4) return "Broken number at "+i+","+j;
                    break;
                case BULB:
                    if (canSeeBulb(i,j)) return "Clashing bulb at "+i+","+j;
                }
            }
        }
        return "\u2713\u2713\u2713";
    }
}
