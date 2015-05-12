/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


/**
 *
 * @author jeffthechef14
 */
public class GameClientController {
    
    private GameView2 theView;
    private GameModel theModel;
    private boolean myMove;
    
    private Color myColor = Color.blue;
    private Color enemyColor = Color.orange;
    private Color blank = Color.white;
    
    private LobbyController theLobbyController;
    private ConnectionForGame theConnection;
    public static final char MYTOKEN = '*';
    public static final char THEIRTOKEN = '#';
    private static final String REQUESTLIST = "REQUESTLIST;";
    public static final String makeMove = "Please make a valid move";
    public static final String gameOverWin =  "You have won!\n Returning to the lobby";
    public static final String gameOverLose =  "You have lost!\n Returning to the lobby";
    public static final String YOULOSE = "YOULOSE";
    
    public GameClientController(GameView2 theView, GameModel theModel, ConnectionForGame theConnection, LobbyController theLobbyController){
        this.theView=theView;
        //this.theView.setMyMove(false);
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
        theView.updateCell(row, col, enemyColor);
        this.myMove = true;
        this.theModel.resetCount();
        //theView.updateGridView();
        theView.enableSend();
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
        LobbyView newView = new LobbyView();
        newView.setVisible(true);
        this.theLobbyController.setNewView(newView);
        this.theLobbyController.writeToConnection(REQUESTLIST);
    }
    
    public void drawBoard(){
        CellListener listener = new CellListener();
        this.theView.drawBoard(this.theModel.getRows(), this.theModel.getCols(), listener);
        this.theView.setVisible(true);
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
                    myMove = false;
                    win();
                }
                else{
                    theConnection.write(theModel.getNextMove());
                    theView.disableSend();
                    myMove = false;
                }
            }
        }        
    }
    
    class CellListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Cell cell  = (Cell) e.getSource();
            if(theModel.getCount()==0 && myMove==true){
                cell.click();
                theModel.setCell(cell.getRow(), cell.getCol(), MYTOKEN);
                theModel.setNextMove(cell.getRow(), cell.getCol(), MYTOKEN);
                theModel.addToCount();
            }
            else if(theModel.getCount()==1 && cell.getBackground().equals((Color.blue)) && myMove == true){
                cell.click();
                theModel.setCell(cell.getRow(), cell.getCol(), ' ');
                theModel.subtractFromCount();
            }
        }        
    }
    
}
