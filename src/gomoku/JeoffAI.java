/*
 * http://stackoverflow.com/questions/5887709/getting-random-numbers-in-java
 */

package gomoku;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author PLUCSCE
 */
public class JeoffAI {
    
    private String diff;
    private GameModel theModel;
    private int mediumCount = 0;
    
    public static final String EASY = "Easy";
    public static final String MEDIUM = "Medium";
    public static final String HARD = "Hard";
    
    public JeoffAI(String diff, GameModel theModel){
        this.theModel = theModel;
        this.diff = diff;
    }
    
    public String easyMove(){
        int row = (int)(Math.random() * theModel.getRows()-1);
        int col = (int)(Math.random() * theModel.getCols()-1);
        if(theModel.isMoveValid(row, col)){
            return row + " " + col;
        }
        else{
            boolean temp = false;
            while(!temp){
                System.out.println(row + " " + col + " is invalid");
                row = (int)(Math.random() * theModel.getRows()-1);
                col = (int)(Math.random() * theModel.getCols()-1);
                temp = theModel.isMoveValid(row, col);
            }
            return row + " " + col;
        }
    }
    
    public String mediumMove(){
        if(this.mediumCount%3==0){
            return easyMove();
        }
        else{
            this.mediumCount++;
            return hardMove();
        }
    }
    
    public String hardMove(){
        return null;
    }
    
    public String getNextMove(){
        if(this.diff.equals(EASY)){
            return easyMove();
        }
        else if(this.diff.equals(MEDIUM)){
            return mediumMove();
        }
        else{
            return hardMove();
        }
    }
    
}
