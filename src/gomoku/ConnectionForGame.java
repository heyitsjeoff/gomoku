package gomoku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionForGame implements Runnable {
    private String ip = GomokuVariables.initialIP;
    private int port = GomokuVariables.initialPort;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private BufferedReader br;
    private Thread worker;
    private byte[] byteArray = new byte[2000];
    private boolean streamsConnected = false;
    private boolean signedIn = false;
    private boolean inLobby = false;
    private boolean inGame = false;
    private String username;
    
    //Controllers
    private AuthController aController;
    private CreateAccountController theCreateAccountController;
    private LobbyController theLobbyController;
    private GameHostController theGameController;
    private GameClientController theGameClientController;
    
    /**
     * creates a connectionForGame object for the client to the host
     * @param ip ip of host
     */
    public ConnectionForGame(String ip){
        this.ip = ip;
    }

    /**
     * handles the messages sent in and acts on the controller
     */
    @Override
    public void run() {
        boolean connected = true;
        while(connected){
            try {
                int count = this.inputStream.read(byteArray);
                if(count>0){
                    String message = new String(byteArray, 0, count);
                    String[] split = message.split("\\s+");
                    String code = split[0];
                    if(code.equals(GomokuVariables.nm)){
                        this.theGameClientController.updateBoard(split[1] +" "+ split[2]);
                    }
                    else if(code.equals(GomokuVariables.YOULOSE)){
                        this.theGameClientController.updateBoard(split[2] +" "+ split[3]);
                        this.theGameClientController.lose();
                    }
                    else if(code.equals(GomokuVariables.TIE)){
                        this.theGameClientController.updateBoard(split[2] +" "+ split[3]);
                        this.theGameClientController.tie();
                    }
                }//connected
                else {
                    connected = false;
                }//disconnected
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
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
    
    /**
     * 
     * @param theGameClientController
     */
    public void setGameClientController(GameClientController theGameClientController){
        this.theGameClientController = theGameClientController;
    }
    
    /**
     * starts the thread
     */
    public void startThread(){
        this.worker = new Thread(this);
        worker.start();
    }
    
    /**
     * sets the streams for the host
     */
    public void connect(){
        try {
            this.socket = new Socket(InetAddress.getByName(ip), port);
            this.inputStream = socket.getInputStream();
            this.outputStream = socket.getOutputStream();
            this.br = new BufferedReader(new InputStreamReader(this.inputStream));
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * disconnects from the host
     */
    public void disconnect(){
        try {
            this.inputStream.close();
            this.outputStream.close();
            this.socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionForGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * sets the ip to connect to the hose
     * @param number the ip number
     */
    public void setIP(String number){
        this.ip = number;
    }
    
    /**
     * sets the port number to connect to the host
     * @param number the port number
     */
    public void setPort(int number){
        this.port = number;
    }
    
}
