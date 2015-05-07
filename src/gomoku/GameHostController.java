package gomoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameHostController implements Runnable{
    
    private GameModel theModel;
    private GameView theView;
    
    
    //connection things
    private Thread worker;
    private Socket socket;
    private ServerSocket serverSocket;
    private GameConnection theGameConnection;
    
    public static final String makeMove = "Please make a valid move";
    public static final char THEIRTOKEN = '#';

    public GameHostController(GameModel theModel, GameView theView){
        try {
            serverSocket = new ServerSocket(8081);
        } catch (IOException ex) {
            Logger.getLogger(GameHostController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.theModel = theModel;
        this.theView = theView;
        this.theView.setMyMove(true);
        this.theView.sendMoveListener(new SendMoveListener());
    }
    
    public void listen(){
        worker = new Thread(this);
        worker.start();
    }

    @Override
    public void run() {
         boolean accepting = true;
         System.out.println("running run in GameHostController");
         while(accepting){
            try {
            this.socket = serverSocket.accept();
            this.theGameConnection = new GameConnection(this.socket, this);
            theGameConnection.start();
            System.out.println("GameController.run has accepted a new gameConnection");
            accepting = false;
        } catch (IOException ex) {
            Logger.getLogger(GameHostController.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
        //play
    }
    
    public void opponentNextMove(String move){
        String[] split = move.split("\\s+");
        int row = Integer.parseInt(split[0]);
        int col = Integer.parseInt(split[1]);
        char token = split[2].charAt(0);
        theModel.setCell(row, col, token);
    }
    
    public void updateBoard(String move){
        String[] split = move.split("\\s+");
        int row = Integer.parseInt(split[0]);
        int col = Integer.parseInt(split[1]);
        theModel.setCell(row, col, THEIRTOKEN);
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
                theGameConnection.write(theModel.getNextMove());
                theView.disableBTN();
                if(theModel.gameOver()){
                    System.out.println("The game has ended");
                    theView.appendMessage("The game has ended");
                }
            }
        }        
    }
    
}
