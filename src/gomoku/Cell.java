/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gomoku;

/**
 *
 * @author PLUCSCE
 */
public class Cell {
    
    private int row;
    private int col;
    private Cell one;
    private Cell two;
    private Cell three;
    private Cell four;
    private Cell six;
    private Cell seven;
    private Cell eight;
    private Cell nine;
    private Cell topLeft;
    
    private Cell[][] board = new Cell[30][30];
    
    public Cell(int r, int c){
        this.row = r;
        this.col = c;
    }
    
    public void makeBoard(){
        topLeft = new Cell(0,0);
        board[0][0] = topLeft;
        for(int i=0; i<30; i++){
            for(int j=0; j<30; j++){
                
            }
        }
    }
    
    public int getRow(){
        return this.row;
    }
    
    public int getCol(){
        return this.col;
    }
    
}
