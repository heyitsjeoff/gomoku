package gomoku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameConnection extends Thread{
    
    private GameHostController theGameHostController;
    private InputStream inputStream;
    private OutputStream outputStream;
    private int port;
    private Socket player2;
    private Thread theThread;
    private BufferedReader br;
    private byte[] byteArray = new byte[2000];
    private String connectionIP;
    
    /**
     * creates a gameConnection object for the client player
     * @param player2 socket of player2
     * @param theGameHostController the controller of the host
     * @throws IOException
     */
    public GameConnection(Socket player2, GameHostController theGameHostController) throws IOException{
        this.player2 = player2;
        this.theGameHostController = theGameHostController;
        this.inputStream = player2.getInputStream();
        this.outputStream = player2.getOutputStream();
        this.br = new BufferedReader(new InputStreamReader(inputStream));
        this.connectionIP = this.player2.getRemoteSocketAddress().toString().substring(1);
    }
    
    /**
     * handles the messages sent in and acts on the controller
     */
    public void run(){
        boolean connected = true;
        String message = "";
        while(connected){
            try {
                int count = this.inputStream.read(byteArray);
                if(count>0){
                    message = new String(byteArray, 0, count);
                    String[] split = message.split("\\s+");
                    String code = split[0];
                    if(code.equals(GomokuVariables.nm)){
                        String move = split[1] + " " + split[2];
                        theGameHostController.updateBoard(split[1] +" "+ split[2]);
                    }
                    else if(code.equals(GomokuVariables.YOULOSE)){
                        theGameHostController.updateBoard(split[2] +" "+ split[3]);
                        this.theGameHostController.lose();
                    }
                    else if(code.equals(GomokuVariables.TIE)){
                        this.theGameHostController.updateBoard(split[2] +" "+ split[3]);
                        this.theGameHostController.tie();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(GameConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * sends a byte array of a string to the output stream
     * @param message string being sent
     */
    public void write(String message){
        byte[] buffOut;
        try {
            buffOut = message.getBytes();
            this.outputStream.write(buffOut, 0, message.length());
            this.outputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
