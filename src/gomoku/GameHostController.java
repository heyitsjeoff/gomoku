package gomoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class GameHostController implements Runnable{
    
    private GameModel theModel;
    private GameView theView;
    private LobbyController theLobbyController;
    
    //connection things
    private Thread worker;
    private Socket socket;
    private ServerSocket serverSocket;
    private GameConnection theGameConnection;
    
    public static final String makeMove = "Please make a valid move";
    public static final String gameOverWin =  "You have won!\n Returning to the lobby";
    public static final String gameOverLose =  "You have lost!\n Returning to the lobby";
    public static final String YOULOSE = "YOULOSE";
    private static final String REQUESTLIST = "REQUESTLIST;";
    public static final char MYTOKEN = '*';
    public static final char THEIRTOKEN = '#';

    public GameHostController(GameModel theModel, GameView theView, LobbyController theLobbyController){
        try {
            serverSocket = new ServerSocket(8081);
        } catch (IOException ex) {
            Logger.getLogger(GameHostController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.theModel = theModel;
        this.theView = theView;
        this.theView.setMyMove(true);
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
        theModel.setCell(row, col, THEIRTOKEN);
        theView.updateGridView();
        theView.enableBTN();
    }
    
    public void returnToLobby(){
        theView.dispose();
        LobbyView newView = new LobbyView();
        newView.setVisible(true);
        this.theLobbyController.setNewView(newView);
        this.theLobbyController.writeToConnection(REQUESTLIST);
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(GameHostController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void lose(){
        JOptionPane.showMessageDialog(null, gameOverLose);
        returnToLobby();
    }
    
    public void win(){
        JOptionPane.showMessageDialog(null, gameOverWin);
        returnToLobby();
    }

    class SendMoveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(theModel.getCount()==0){
                theView.appendMessage(makeMove);
            }
            else{
                if(theModel.gameOver(MYTOKEN)){
                    theGameConnection.write(YOULOSE + " " + theModel.getNextMove());
                    win();
                }
                else{
                    theGameConnection.write(theModel.getNextMove());
                    theView.disableBTN();
                }
            }
        }        
    }
    
}
