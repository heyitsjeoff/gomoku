package gomoku;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class GameClientController {
    
    private GameView2 theView;
    private GameModel theModel;
    private boolean myMove;
    
    private LobbyController theLobbyController;
    private ConnectionForGame theConnection;
    
    /**
     * creates the controler for the client of the game
     * @param theView
     * @param theModel
     * @param theConnection
     * @param theLobbyController
     */
    public GameClientController(GameView2 theView, GameModel theModel, ConnectionForGame theConnection, LobbyController theLobbyController){
        this.theView=theView;
        this.theModel=theModel;
        this.theConnection=theConnection;
        this.theView.sendMoveListener(new SendMoveListener());
        this.theLobbyController = theLobbyController;
    }
    
    /**
     * updates the board from the opponent move
     * @param move opponent move in string form
     */
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
    
    /**
     * lose pop up and returns to the lobby
     */
    public void lose(){
        JOptionPane.showMessageDialog(null, GomokuVariables.gameOverLose);
        returnToLobby();
    }
    
    /**
     * win pop up and returns to the lobby
     */
    public void win(){
        JOptionPane.showMessageDialog(null, GomokuVariables.gameOverWin);
        returnToLobby();
    }
    
    /**
     * tie pop up and returns to the lobby
     */
    public void tie(){
        JOptionPane.showMessageDialog(null, GomokuVariables.gameOverTie);
        returnToLobby();
    }
    
    /**
     *  disables the cell move made by the player
     * @param move string representation of the move
     */
    public void disableMyMoveCell(String move){
        String[] split = move.split("\\s+");
        int row = Integer.parseInt(split[1]);
        int col = Integer.parseInt(split[2]);
        theView.disableCell(row, col);
    }
    
    /**
     * returns to the lobby view
     */
    public void returnToLobby(){
        theView.dispose();
        this.theLobbyController.setView(true);
        this.theLobbyController.writeToConnection(GomokuVariables.REQUESTLIST);
    }
    
    /**
     * initially draws the board
     */
    public void drawBoard(){
        CellListener listener = new CellListener();
        this.theView.drawBoard(this.theModel.getRows(), this.theModel.getCols(), listener);
        this.theView.setVisible(true);
    }
    
    /**
     * ActionListener for the send move button
     */
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
                else if(theModel.boardFull()){
                    theConnection.write(GomokuVariables.TIE + " " + theModel.getNextMove());
                    myMove = false;
                    tie();
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
    
    /**
     * ActionListener for the cells of the board
     */
    class CellListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Cell cell  = (Cell) e.getSource();
            if(theModel.getCount()==0 && myMove==true){
                cell.click();
                theModel.setCell(cell.getRow(), cell.getCol(), GomokuVariables.MYTOKEN);
                theModel.setNextMove(cell.getRow(), cell.getCol(), GomokuVariables.MYTOKEN);
                theModel.addToCount();
                theModel.addToTokenCount();
            }
            else if(theModel.getCount()==1 && cell.getBackground().equals((Color.blue)) && myMove == true){
                cell.click();
                theModel.setCell(cell.getRow(), cell.getCol(), ' ');
                theModel.subtractFromCount();
                theModel.subtractFromTokenCount();
            }
        }        
    }
    
}
