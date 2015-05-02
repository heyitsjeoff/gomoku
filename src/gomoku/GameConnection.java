
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
    
    private GameHostController theGameController;
    private InputStream inputStream;
    private OutputStream outputStream;
    private int port;
    private Socket player2;
    private Thread theThread;
    private BufferedReader br;
    private byte[] byteArray = new byte[2000];
    private String connectionIP;
    
    public GameConnection(Socket player2, GameHostController theGameController) throws IOException{
        this.player2 = player2;
        this.theGameController = theGameController;
        this.inputStream = player2.getInputStream();
        this.outputStream = player2.getOutputStream();
        this.br = new BufferedReader(new InputStreamReader(inputStream));
        this.connectionIP = this.player2.getRemoteSocketAddress().toString().substring(1);
    }
    
    public void run(){
        boolean connected = true;
        while(connected){
            try {
                int count = this.inputStream.read(byteArray);
                if(count>0){
                    //do the client thing
                }
            } catch (IOException ex) {
                Logger.getLogger(GameConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void write(String message){
        byte[] buffOut;
        try {
            buffOut = message.getBytes();
            this.outputStream.write(buffOut, 0, message.length());
            this.outputStream.flush();
            System.out.println(message + " has been flushed by GameConnection");
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
