package gomoku;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class GameHostController implements Runnable{
    
    private GameModel theModel;
    private GameView2 theView;
    private LobbyController theLobbyController;
    
    //connection things
    private Thread worker;
    private Socket socket;
    private ServerSocket serverSocket;
    private GameConnection theGameConnection;
    
    private boolean myMove;
    

    public GameHostController(GameModel theModel, GameView2 theView, LobbyController theLobbyController){
        try {
            serverSocket = new ServerSocket(8081);
        } catch (IOException ex) {
            Logger.getLogger(GameHostController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.theModel = theModel;
        this.theView = theView;
        this.myMove = true;
        this.theView.sendMoveListener(new SendMoveListener());
        this.theLobbyController = theLobbyController;
    }
    
    public void listen(){
        worker = new Thread(this);
        worker.start();
    }

    @Override
    public void run() {
         boolean accepting = true;
         while(accepting){
            try {
            this.socket = serverSocket.accept();
            this.theGameConnection = new GameConnection(this.socket, this);
            theGameConnection.start();
            accepting = false;
        } catch (IOException ex) {
            Logger.getLogger(GameHostController.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
        //play
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
    
    public void returnToLobby(){
        theView.dispose();
        this.theLobbyController.setView(true);
        this.theLobbyController.writeToConnection(GomokuVariables.REQUESTLIST);
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(GameHostController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                    theGameConnection.write(GomokuVariables.YOULOSE + " " + theModel.getNextMove());
                    myMove = false;
                    win();
                }
                else{
                    theGameConnection.write(theModel.getNextMove());
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
