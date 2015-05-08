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
 * @author jeffthechef14
 */
public class GameClientController {
    
    private GameView theView;
    private GameModel theModel;
    private LobbyController theLobbyController;
    private ConnectionForGame theConnection;
    public static final char MYTOKEN = '*';
    public static final char THEIRTOKEN = '#';
    
    public static final String makeMove = "Please make a valid move";
    public static final String gameOverWin =  "You have won!\n Returning to the lobby";
    public static final String gameOverLose =  "You have lost!\n Returning to the lobby";
    public static final String YOULOSE = "YOULOSE";
    
    public GameClientController(GameView theView, GameModel theModel, ConnectionForGame theConnection, LobbyController theLobbyController){
        this.theView=theView;
        this.theView.setMyMove(false);
        this.theModel=theModel;
        this.theConnection=theConnection;
        this.theView.sendMoveListener(new SendMoveListener());
        this.theLobbyController = theLobbyController;
    }
    
    public void updateBoard(String move){
        String[] split = move.split("\\s+");
        int row = Integer.parseInt(split[0]);
        int col = Integer.parseInt(split[1]);
        theModel.setCell(row, col, THEIRTOKEN);
        theView.updateGridView();
        theView.enableBTN();
        System.out.println("before here");
        System.out.println("Their token:" + theModel.gameOver(THEIRTOKEN));
    }
    
    public void lose(){
        JOptionPane.showMessageDialog(null, gameOverLose);
        returnToLobby();
    }
    
    public void win(){
        JOptionPane.showMessageDialog(null, gameOverWin);
        returnToLobby();
    }
    
    public void returnToLobby(){
        theView.dispose();
        this.theLobbyController.showLobbyView();
        
    }
    
    class SendMoveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(theModel.getCount()==0){
                theView.appendMessage(makeMove);
            }
            else{
                if(theModel.gameOver(MYTOKEN)){
                    theConnection.write(YOULOSE + " " + theModel.getNextMove());
                    win();
                }
                else{
                    theConnection.write(theModel.getNextMove());
                    theView.disableBTN();
                }
            }
        }        
    }
    
}
