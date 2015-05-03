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
