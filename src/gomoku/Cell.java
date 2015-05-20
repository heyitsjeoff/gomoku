package gomoku;

import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Cell extends JButton{
    private int row, col;
    private boolean locked;
    private Color theColor;
    private boolean clicked;
    
    /**
     * Constructs a cell object with a reference to the location of the
     * cell within a 2d array
     * @param row the row of the cell
     * @param col the col of the cell
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.locked = false;
        this.theColor = GomokuVariables.blank;
        this.clicked = false;
        //actual button
        this.setSize(28, 28);
        this.setBackground(GomokuVariables.blank);
    }
    
    /**
     * gets the row of the cell
     * @return Cell row
     */
    public int getRow(){
        return this.row;
    }
    
    /**
     * gets the col of the cell
     * @return Cell col
     */
    public int getCol(){
        return this.col;
    }
    
    /**
     * sets the boolean locked to true
     */
    public void lockCell(){
        this.locked = true;
    }
    
    /**
     * checks to see if the cell is locked
     * @return true if cell is locked, else false
     */
    public boolean isLocked(){
        return this.locked;
    }
    
    /**
     * checks to see if the cell has been clicked
     * @return true if clicked, else false
     */
    public boolean isClicked(){
        return this.clicked;
    }
        
    /**
     * clicks the cell ? if not clicked: if clicked
     */
    public void click(){
        if(this.clicked){
            this.setBackground(GomokuVariables.blank);
            this.clicked = false;
        }
        else{
            this.setBackground(GomokuVariables.myColor);
            this.clicked = true;
        }
    }
        
    /**
     * adds a listener to the cell object
     * @param listenerForCell listener being added
     */
    public void cellListener(ActionListener listenerForCell){
        this.addActionListener(listenerForCell);
    }
}