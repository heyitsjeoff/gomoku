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

public class AuthController{
    
    //Views
    private MainView theView;
    private CreateAccountView theCAView;
    private LobbyView theLobbyView;
    
    //controllers
    private CreateAccountController caController;
    private LobbyController theLobbyController;
    
    private Connection theConnection;
    private boolean streamsConnected;
    
    private static final String logIn = "y";
    private static final String notLogIn = "n";
    private static final String uae = "user already exists";
    private static final String initialIP = "127.0.0.1";
    
    //Label messages
    private static final String dc = "Disconnected";
    private static final String invalid = "Invalid Username or Password";
    private static final String nuc = "New user created";
    private static final String exception = "New exception found";
    private static final String dataDialog = "is the current data of login";
    private static final String cts = "Connected to server";
    private static final String pdnm = "Passwords do not match";
    private static final String ipChanged = "IP changed to ";
    
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

    @Override
    public void run(){
        boolean checking = true;
        while(checking){
            try {
                int count = this.inputStream.read(byteArray);
                if(count>0){
                    String login = new String(byteArray, 0, count);

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
    */
    
    public void login(){
        this.theView.dispose();
        this.theLobbyView = new LobbyView();
        this.theLobbyView.setVisible(true);
        this.theLobbyController = new LobbyController(theLobbyView, theConnection);
        
    }
    
    public void notLogin(){
        this.theView.appendMSG(invalid);
    }
    
    /**
     * writes the username and password for a possible 
     * new User to the outputstream
     * @param username username of new User
     * @param password password of new User
     */
    public void createAccount(String username, String password){
        String both = "!" + username + " " + password;
        this.theConnection.write(both);
    }//createAcccount
    
    /**
     * writes the username and password and checks to see if it is valid
     * @param username username of a User
     * @param password username of a User's password
     */
    public void authenticate(String username, String password){
        String both = username + " " + password;
        this.theConnection.write(both);
    }//authenticate
    
    /**
     * sets the IP for the server that the controller will connect to
     * @param number new IP
     */
    public void setIP(String number){
        checkConnection();
        theView.appendMSG(ipChanged + number);
        this.theConnection.setIP(number);
    }//setIP
    
    /**
     * sets the port number for the server that the controller will connect to
     * @param number new port
     */
    public void setPort(int number){
        this.theConnection.setPort(number);
    }//setPort
    
    public void checkConnection(){
        if(this.streamsConnected==false){
            theConnection = new Connection(initialIP);
            theConnection.setAuthController(this);
            theConnection.connect();
            theConnection.startThread();
            this.streamsConnected=true;
        }
    }
    
    public void tryLogin(){
        checkConnection();
        String enteredUsername = theView.getUsername();
        String enteredPassword = theView.getPassword();
        authenticate(enteredUsername, enteredPassword);
    }
    
    
    //---Listeners----------------------------------------------------
    
     /**
     * Listener for the login button
     * sets streams and attempts to login
     */
    class LoginListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            tryLogin();
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
            checkConnection();
            theView.setVisible(false);
            theCAView = new CreateAccountView();
            theCAView.setVisible(true);
            caController = new CreateAccountController(theCAView, theConnection, theView);
            theConnection.setCreateAccountController(caController);
        }//actionPerformed
    }//CAListener
    
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
                tryLogin();
            }//keyPressed
        }
        @Override
        public void keyReleased(KeyEvent e) {
            //NA
        }//keyReleased  
    }//EnterListener
    
}//AuthController
