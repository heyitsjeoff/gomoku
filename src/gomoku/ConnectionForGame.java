/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

import static gomoku.Connection.nm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeffthechef14
 */
public class ConnectionForGame implements Runnable {
    
    private String ip = "127.0.0.1";
    private int port = 8080; //port of ip
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
    
    public static final String nm = "NEXTMOVE";
    
    private AuthController aController;
    private CreateAccountController theCreateAccountController;
    private LobbyController theLobbyController;
    private GameHostController theGameController;
    private GameClientController theGameClientController;
    
    
    public ConnectionForGame(String ip){
        this.ip = ip;
    }

    @Override
    public void run() {
        boolean connected = true;
        while(connected){
            try {
                int count = this.inputStream.read(byteArray);
                if(count>0){
                    String message = new String(byteArray, 0, count);
                    System.out.println(message + " received");
                    String[] split = message.split("\\s+");
                    String code = split[0];
                    if(code.equals(nm)){
                        this.theGameClientController.updateBoard(split[1] +" "+ split[2]);
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
    
    public void write(String message){
        byte[] buffOut;
        try {
            buffOut = message.getBytes();
            this.outputStream.write(buffOut, 0, message.length());
            this.outputStream.flush();
            System.out.println(message + " has been flushed");
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setGameClientController(GameClientController theGameClientController){
        this.theGameClientController = theGameClientController;
    }
    
    public void startThread(){
        this.worker = new Thread(this);
        worker.start();
    }
    
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
    
    public void setIP(String number){
        this.ip = number;
    }
    
    public void setPort(int number){
        this.port = number;
    }
    
}
