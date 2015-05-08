/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gomoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author PLUCSCE
 */
public class OfflineController {
    
    private offlineDiff theDiffView;
    private JeoffAI theAI;
    private OGameView theView;
    private GameModel theModel;
    private AuthController theAuthController;
    
    public static final char ENEMY = '#';
    public static final String makeMove = "Please make a valid move";
    public static final String gameOverWin =  "You have won!\n Returning to the lobby";
    public static final String gameOverLose =  "You have lost!\n Returning to the lobby";
    
    public OfflineController(offlineDiff theDiffView){
        this.theDiffView = theDiffView;
        this.theDiffView.startListener(new StartListener());
    }
    
    class StartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            theModel = new GameModel(30, 30);
            theAI = new JeoffAI(theDiffView.getDiff(), theModel);
            theDiffView.dispose();
            theView = new OGameView(theModel);
            theView.moveListener(new MoveListener());
        }
    }
    
    public void win(){
        JOptionPane.showMessageDialog(null, gameOverWin);
        returnToMainView();
    }
    
    public void lose(){
        JOptionPane.showMessageDialog(null, gameOverLose);
        returnToMainView();
    }
    
    public void returnToMainView(){
        MainView theMainView = new MainView();
        AuthController theAuthController = new AuthController(theMainView);
        this.theView.dispose();
        theMainView.setVisible(true);
    }
    
    class MoveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(theModel.gameOver('*')){
                win();
            }
            else{
                if(theModel.getCount()==0){
                    theView.appendMessage(makeMove);
                }
                else{
                    String[] move = theAI.getNextMove().split("\\s+");
                    int row = Integer.parseInt(move[0]);
                    int col = Integer.parseInt(move[1]);
                    theModel.setCell(row, col, ENEMY);
                    theView.updateGridView();
                    if(theModel.gameOver(ENEMY)){
                        lose();
                    }
                    else
                        theModel.resetCount();
                }
            }
        }
    }
    
}
