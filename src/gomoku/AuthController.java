package gomoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *this is a test comment from chase
 *this is a test comment from jun
 * @author heyitsjeoff
 */
public class AuthController implements Runnable{
    
    //Views
    private MainView theView;
    private CreateAccountView theCAView;
    private LobbyView theLobbyView;
    
    //controllers
    private CreateAccount caController;
    private LobbyController theLobbyController;
    
    private String ip = "127.0.0.1";
    private int port = 8080; //port of ip
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private BufferedReader br;
    private Thread worker;
    private byte[] byteArray = new byte[2000];
    private boolean streamsConnected = false;
    
    private static final String logIn = "y";
    private static final String notLogIn = "n";
    private static final String uae = "user already exists";
    private static final String created = "user created";
    
    //Label messages
    private static final String dc = "Disconnected";
    private static final String invalid = "Invalid Username or Password";
    private static final String uaeL = "User already exists";
    private static final String nuc = "New user created";
    private static final String exception = "New exception found";
    private static final String dataDialog = "is the current data of login";
    private static final String cts = "Connected to server";
    private static final String pdnm = "Passwords do not match";
    private static final String wnct = "Will connect to: ";
    
    /**
     * Creates the main controller for Gomoku
     * class name is deceiving 
     * @param theView view for the controller
     */
    public AuthController (MainView theView){
        this.theView = theView;
        this.theView.loginListener(new LoginListener());
        this.theView.createAccountListener(new CAListener());
        this.theView.passwordPFListener(new EnterListener());
        this.theView.changeIPListener(new IPListener());
    }
    
    /**
     * reads in a string from the server and acts accordingly
     */
    @Override
    public void run(){
        boolean checking = true;
        while(checking){
            try {
                int count = this.inputStream.read(byteArray);
                if(count>0){
                    String login = new String(byteArray, 0, count);
                    if(login.equals(logIn)){
                        this.theView.dispose();
                        this.theLobbyView = new LobbyView();
                        this.theLobbyView.setVisible(true);
                        this.theLobbyController = new LobbyController(theLobbyView, this);
                        
                    }//login passed
                    else if(login.equals(notLogIn)){
                        this.theView.appendMSG(invalid);
                    }//login failed
                    else if(login.equals(uae)){
                        theCAView.message(uaeL);
                    }//user already exists
                    else if(login.equals(created)){
                        theCAView.setVisible(false);
                        theView.setVisible(true);
                        theView.appendMSG(nuc);
                    }//user created
                    else{
                        System.out.println(exception);
                        System.out.println(login + dataDialog);
                    }
                }
                else{
                    checking = false;
                    theView.appendMSG(dc);
                }
            } catch (IOException ex) {
                Logger.getLogger(AuthController.class.
                        getName()).log(Level.SEVERE, null, ex);
            }        
        }//while
    }//run
    
    public void sendMessageToClient(String theMessage){
        byte[] buffOut;
        buffOut = theMessage.getBytes();
        try {  
            this.outputStream.write(buffOut, 0, theMessage.length());
            this.outputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Creates a new thread and starts it
     */
    public void doIt(){
        this.worker = new Thread(this);
        worker.start();
    }//doIt
    
    /**
     * writes the username and password for a possible 
     * new User to the outputstream
     * @param username username of new User
     * @param password password of new User
     */
    public void createAccount(String username, String password){
        byte[] buffOut;
        String both = "!" + username + " " + password;
        buffOut = both.getBytes();
        try {
            this.outputStream.write(buffOut, 0, both.length());
            this.outputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(AuthController.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
    }//createAcccount
    
    /**
     * writes the username and password and checks to see if it is valid
     * @param username username of a User
     * @param password username of a User's password
     */
    public void authenticate(String username, String password){
        byte[] buffOut;
        String both = username + " " + password;
        buffOut = both.getBytes();
        try {
            this.outputStream.write(buffOut, 0, both.length());
            this.outputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(AuthController.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
    }//authenticate
    
    /**
     * instantiates a new socket to the ip and port
     * @throws IOException
     */
    public final void connect() throws IOException{
        this.socket = new Socket(InetAddress.getByName(ip), port);
        theView.appendMSG(cts);
    }//connect
    
    /**
     * sets the inputStream and outputStream
     * @throws IOException
     */
    public void streams() throws IOException{
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
        this.br = new BufferedReader(new InputStreamReader(this.inputStream));
    }//streams
    
    /**
     * sets the IP for the server that the controller will connect to
     * @param number new IP
     */
    public void setIP(String number){
        this.ip = number;
        theView.appendMSG(wnct + number);
    }//setIP
    
    /**
     * sets the port number for the server that the controller will connect to
     * @param number new port
     */
    public void setPort(int number){
        this.port = number;
    }//setPort
    
    
    //---Listeners----------------------------------------------------
    
     /**
     * Listener for the login button
     * sets streams and attempts to login
     */
    class LoginListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(streamsConnected==false){
            try {     
                connect();
                streams();
                doIt();
            } catch (IOException ex) {
                Logger.getLogger(AuthController.
                        class.getName()).log(Level.SEVERE, null, ex);
                }
            }//sets streams if it hasn't already
            String enteredUsername = theView.getUsername();
            String enteredPassword = theView.getPassword();
            authenticate(enteredUsername, enteredPassword);
        }//actionPerformed
    }//LoginListener
    
    /**
     * Listener for the change IP menu item
     * prompts the user for a new server IP
     */
    class IPListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String newIP = JOptionPane.showInputDialog(null, "Server IP:");
            setIP(newIP);
        }//actionPerformed
    }//IPListener
    
    /**
     * Listener for the create account button
     * hides the main view, and instantiates a Create Account view
     */
    class CAListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(streamsConnected==false){
            try {     
                connect();
                streams();
                doIt();
            } catch (IOException ex) {
                Logger.getLogger(AuthController.class.getName()).
                        log(Level.SEVERE, null, ex);
                }
            }//sets streams if it hasn't already
            
            theView.setVisible(false);
            theCAView = new CreateAccountView();
            theCAView.setVisible(true);
            theCAView.backListener(new backListener());
            theCAView.createListener(new CreateListener());
            theCAView.enterListener(new CreateEnterListener());
        }//actionPerformed
    }//CAListener
    
    /**
     * Listener for the Create Account button of the Create Account view
     * attempts to create an account
     */
    class CreateListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!theCAView.getPassword1().equals(theCAView.getPassword2())){
                theCAView.message(pdnm);
            }
            else{
                createAccount(theCAView.getUsername(),
                        theCAView.getPassword1());
            }
        }//actionPerformed
    }//CreateListener for create account view
    
     /**
     * Listener for a KeyEvent (enter) that attempts to create an account
     */
    class CreateEnterListener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {
            //NA
        }//keyTyped
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode()==KeyEvent.VK_ENTER){
                if(!theCAView.getPassword1().equals(theCAView.getPassword2())){
                theCAView.message(pdnm);
                }
                else{
                    createAccount(theCAView.getUsername(),
                            theCAView.getPassword1());
                }
            }//keyPressed
        }
        @Override
        public void keyReleased(KeyEvent e) {
            //NA
        }//keyReleased  
    }//EnterListener for CreateAccount
    
    /**
     * Listener for the lback button of Create Account view
     * hides the Create Account View and displays the main view
     */
    class backListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            theView.setVisible(true);
            theCAView.setVisible(false);
        }//actionPerformed
    }//backListener
    
    /**
     * Listener for a KeyEvent (enter) that sets streams and attempts
     * to login
     */
    class EnterListener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {
            //NA
        }//keyTyped
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode()==KeyEvent.VK_ENTER){
                if(streamsConnected==false){
                try {     
                    connect();
                    streams();
                    doIt();
                } catch (IOException ex) {
                    Logger.getLogger(AuthController.class.
                            getName()).log(Level.SEVERE, null, ex);
                    }
                }//sets streams if it hasn't already
                String enteredUsername = theView.getUsername();
                String enteredPassword = theView.getPassword();
                authenticate(enteredUsername, enteredPassword);
            }//keyPressed
        }
        @Override
        public void keyReleased(KeyEvent e) {
            //NA
        }//keyReleased  
    }//EnterListener
    
}//AuthController
