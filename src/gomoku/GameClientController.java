/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author jeffthechef14
 */
public class GameClientController {
    
    private GameView theView;
    private GameModel theModel;
    private Connection theConnection;
    
    public static final String makeMove = "Please make a valid move";
    
    public GameClientController(GameView theView, GameModel theModel, Connection theConnection){
        this.theView=theView;
        this.theModel=theModel;
        this.theConnection=theConnection;
        this.theView.sendMoveListener(new SendMoveListener());
    }
    
    public void updateBoard(String move){
        String[] split = move.split("\\s+");
        int row = Integer.parseInt(split[0]);
        int col = Integer.parseInt(split[1]);
        char token = split[2].charAt(0);
        theModel.setCell(row, col, token);
        theView.updateGridView();
        theView.enableBTN();
    }
    
    class SendMoveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(theModel.getCount()==0){
                theView.appendMessage(makeMove);
            }
            else{
                theConnection.write(theModel.getNextMove());
                theView.disableBTN();
            }
        }        
    }
    
}