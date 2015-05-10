/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author jeffthechef14
 */
public class Cell extends JButton{
    private int row, col;
        private boolean locked;
        private Color theColor;
        private boolean clicked;
    
        private Color myColor = Color.blue;
        private Color enemyColor = Color.orange;
        private Color blank = Color.white;
    
        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
            this.locked = false;
            this.theColor = Color.white;
            this.clicked = false;
            //actual button
            this.setSize(28, 28);
            this.setBackground(blank);
        }
    
        public int getRow(){
            return this.row;
        }
    
        public int getCol(){
            return this.col;
        }
    
        public void lockCell(){
            this.locked = true;
        }
    
        public boolean isLocked(){
            return this.locked;
        }
    
        public boolean isClicked(){
            return this.clicked;
        }
        
        public void click(){
            if(this.clicked){
                this.setBackground(blank);
                this.clicked = false;
            }
            else{
                this.setBackground(myColor);
                this.clicked = true;
            }
        }
        
        public void cellListener(ActionListener listenerForCell){
            this.addActionListener(listenerForCell);
        }
}
