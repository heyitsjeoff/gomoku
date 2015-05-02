/*
Jeoff Villanueva
Project
CSCE 320 Spring
Date
Java used in Netbeans
Sources:

 */
package gomoku;


public class Board {

    private char[][] grid;
    private int rows;
    private int cols;
    
    public Board(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[this.rows][this.cols];
        for(int i = 0; i<this.rows; i++){
            for(int j = 0; j<this.cols; j++){
                grid[i][j]='-';
            }
        }
    }
    
    public void setCell(int row, int col, char token){
        this.grid[row][col] = token;
    }
    
    public char getCell(int row, int col){
        return this.grid[row][col];
    }
    
    public int getRows(){
        return this.rows;
    }
    
    public int getCols(){
        return this.cols;
    }
    
}
