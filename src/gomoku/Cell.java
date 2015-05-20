package gomoku;

import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author heyitsjeoff
 */
public class Cell extends JButton{
    private int row, col;
    private boolean locked;
    private Color theColor;
    private boolean clicked;
    
    /**
     *
     * @param row
     * @param col
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
     *
     * @return
     */
    public int getRow(){
        return this.row;
    }
    
    /**
     *
     * @return
     */
    public int getCol(){
        return this.col;
    }
    
    /**
     *
     */
    public void lockCell(){
        this.locked = true;
    }
    
    /**
     *
     * @return
     */
    public boolean isLocked(){
        return this.locked;
    }
    
    /**
     *
     * @return
     */
    public boolean isClicked(){
        return this.clicked;
    }
        
    /**
     *
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
     *
     * @param listenerForCell
     */
    public void cellListener(ActionListener listenerForCell){
        this.addActionListener(listenerForCell);
    }
}