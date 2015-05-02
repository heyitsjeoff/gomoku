package gomoku;

public class Cell {
    private int row;
    private int col;
    private char token;
    
    public Cell(int theRow, int theCol, char theToken){
        this.row = theRow;
        this.col = theCol;
        this.token = theToken;
    }
    
    public int getRow(){
        return this.row;
    }
    
    public int getCol(){
        return this.col;
    }
    
    public char geToken(){
        return this.token;
    }
}
