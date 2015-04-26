/*
Jeoff Villanueva
Project
CSCE 320 Spring
Date
Java used in Netbeans
Sources:

 */
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


public class Connection implements Runnable{
    
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
    
    private static final String logIn = "y";
    private static final String notLogIn = "n";
    private static final String uae = "user already exists";
    private static final String created = "user created";
    
    private AuthController aController;
    private CreateAccountController theCreateAccountController;
    
    public Connection(String ip){
        this.ip = ip;
    }

    @Override
    public void run() {
        boolean connected = true;
        while(connected){
            try {
                int count = this.inputStream.read(byteArray);
                if(count>0){
                    String messageFromInput = new String(byteArray, 0, count);
                    if(messageFromInput.equals(logIn)){
                            aController.login();
                            signedIn=true;
                            inLobby=true;
                    }
                    else if(messageFromInput.equals(notLogIn)){
                        aController.notLogin();
                    }
                    else if(messageFromInput.equals(uae)){
                        theCreateAccountController.userAlreadyExists();
                    }
                    else if(messageFromInput.equals("user created")){
                        theCreateAccountController.accountCreated();
                    }
                }//connected
                else{
                    connected = false;
                }//disconnected
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void setAuthController(AuthController aController){
        this.aController = aController;
    }
    
    public void setCreateAccountController(CreateAccountController theCreateAccountController){
        this.theCreateAccountController = theCreateAccountController;
    }
    
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
