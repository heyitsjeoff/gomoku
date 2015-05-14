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
    
    private LobbyController theLobbyController;
    private ConnectionForGame theConnection;
    
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
        theModel.setCell(row, col, GomokuVariables.THEIRTOKEN);
        theView.updateCell(row, col, GomokuVariables.enemyColor);
        this.theModel.resetCount();
        theView.enableSend();
        theView.append(GomokuVariables.THEYMADEAMOVEAT + row + GomokuVariables.ANDCOL + col);
        this.myMove = true;
    }
    
    public void lose(){
        JOptionPane.showMessageDialog(null, GomokuVariables.gameOverLose);
        returnToLobby();
    }
    
    public void win(){
        JOptionPane.showMessageDialog(null, GomokuVariables.gameOverWin);
        returnToLobby();
    }
    
    public void disableMyMoveCell(String move){
        String[] split = move.split("\\s+");
        int row = Integer.parseInt(split[1]);
        int col = Integer.parseInt(split[2]);
        theView.disableCell(row, col);
    }
    
    public void returnToLobby(){
        theView.dispose();
        this.theLobbyController.setView(true);
        this.theLobbyController.writeToConnection(GomokuVariables.REQUESTLIST);
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
                theView.append(GomokuVariables.makeMove);
            }
            else{
                if(theModel.gameOver(GomokuVariables.MYTOKEN)){
                    theConnection.write(GomokuVariables.YOULOSE + " " + theModel.getNextMove());
                    myMove = false;
                    win();
                }
                else{
                    theConnection.write(theModel.getNextMove());
                    myMove = false;
                    theView.disableSend();
                    theView.append(theModel.myMoveToString());
                    disableMyMoveCell(theModel.getNextMove());
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
                theModel.setCell(cell.getRow(), cell.getCol(), GomokuVariables.MYTOKEN);
                theModel.setNextMove(cell.getRow(), cell.getCol(), GomokuVariables.MYTOKEN);
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
