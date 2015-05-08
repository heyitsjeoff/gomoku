/*
 * http://stackoverflow.com/questions/5887709/getting-random-numbers-in-java
 */

package gomoku;

import java.util.Random;

/**
 *
 * @author PLUCSCE
 */
public class JeoffAI {
    
    private String diff;
    private GameModel theModel;
    
    public static final String EASY = "Easy";
    public static final String MEDIUM = "Medium";
    public static final String HARD = "Hard";
    
    public JeoffAI(String diff, GameModel theModel){
        this.theModel = theModel;
        this.diff = diff;
    }
    
    public String easyMove(){
        int row = (int)(Math.random() * theModel.getRows()-1);
        System.out.println(row);
        int col = (int)(Math.random() * theModel.getCols()-1);
        System.out.println(col);
        StringBuilder sb = new StringBuilder();
        if(theModel.getCell(row, col)==' '){
            sb.append(row+ " " + col);
            return sb.toString();
        }
        else{
            easyMove();
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
    
    public String getNextMove(){
        if(this.diff.equals(EASY)){
            return easyMove();
        }
        else{
            return "hello";
        }
    }
    
}
