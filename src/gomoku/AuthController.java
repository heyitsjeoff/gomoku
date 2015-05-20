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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class AuthController{
    
    //Views
    private MainView theView;
    private CreateAccountView theCAView;
    private LobbyView theLobbyView;
    private LobbyModel theLobbyModel;
    private OfflineDifficultyView theOfflineDiff;
    
    //controllers
    private CreateAccountController caController;
    private LobbyController theLobbyController;
    private OfflineController theOfflineController;
    
    private GameModel theOfflineGameModel;
    
    private Connection theConnection;
    private boolean streamsConnected;
    private boolean ipChanged = false;
    private String ip = GomokuVariables.initialIP;
    private int port = GomokuVariables.initialPort;
    
    /**
     * Creates the Authorization controller for Gomoku
     * @param theView view for the controller
     */
    public AuthController (MainView theView){
        this.theView = theView;
        this.theView.loginListener(new LoginListener());
        this.theView.createAccountListener(new CAListener());
        this.theView.passwordPFListener(new EnterListener());
        this.theView.changeIPListener(new IPListener());
        this.theView.anonListener(new AnonListener());
        this.theView.offlineListener(new OfflineListener());
        this.theView.changePortListener(new PortListener());
        theConnection = new Connection(ip, port);
    }
    
    /**
     * Logs the user in by instantiating the variables 
     * for the lobby and setting the views
     */
    public void login(){
        this.theLobbyModel = new LobbyModel();
        this.theLobbyModel.setUsername(theView.getUsername());
        this.theView.dispose();
        this.theLobbyView = new LobbyView();
        this.theLobbyView.setVisible(true);
        this.theLobbyController = new LobbyController(theLobbyView, this.theLobbyModel, theConnection);
    }
    
    /**
     * Displays in the view that the user has not logged in
     */
    public void notLogin(){
        this.theView.appendMSG(GomokuVariables.invalid);
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
        if(number==null){
            theView.appendMSG(GomokuVariables.PCI);
        }
        else{
            theView.appendMSG(GomokuVariables.ipChange + number);
            this.ipChanged = true;
            this.theConnection.setIP(number);
        }
    }//setIP
    
    /**
     * sets the port number for the server that the controller will connect to
     * @param number new port
     */
    public void setPort(int number){
        this.theConnection.setPort(number);
    }//setPort
    
    /**
     * establishes the streams to the server, if they have not been already
     * @return true if streams are created
     */
    public boolean checkConnection(){
        if(this.streamsConnected==false){
            if(ipChanged){
                theConnection.setAuthController(this);
                theConnection.connect();
                theConnection.startThread();
                this.streamsConnected=true;
                return true;
            }
            else{
                theView.appendMSG(GomokuVariables.PCI);
            }
        }
        return false;
    }
    
    /**
     * attempts a login a from the main view
     */
    public void tryLogin(){
        if(!theView.getUsername().isEmpty() && !theView.getPassword().isEmpty()){
            String enteredUsername = theView.getUsername();
            String enteredPassword = theView.getPassword();
            authenticate(enteredUsername, enteredPassword);
            this.theConnection.setUsername(enteredUsername);
        }
        else{
            theView.appendMSG(GomokuVariables.complete);
        }
    }
    
    /**
     * logs in using an anon account
     */
    public void anon(){
        theCAView = new CreateAccountView();
        caController = new CreateAccountController(theCAView, theConnection, theView);
        theConnection.setCreateAccountController(caController);
        Random rand = new Random();
        int num = rand.nextInt(9000000) + 1000000;
        String uAndP = GomokuVariables.anonString+Integer.toString(num);
        this.theConnection.setUsername(uAndP);
        caController.createAnon(uAndP);
    }
    
    //---Listeners----------------------------------------------------
    
    /**
     * ActionListener for the anon button
     */
    class AnonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            checkConnection();
            anon();
        }
    }
    
    /**
     * ActionListener for the offline button
     */
    class OfflineListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            theOfflineDiff = new OfflineDifficultyView();
            theView.setVisible(false);
            theOfflineDiff.setVisible(true);
            theOfflineGameModel = new GameModel(30, 30);
            theOfflineController = new OfflineController(theOfflineDiff, theOfflineGameModel);
        }
    }
    
     /**
     * Listener for the login button
     */
    class LoginListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            checkConnection();
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
            String newIP = JOptionPane.showInputDialog(null, GomokuVariables.serverIP);
            setIP(newIP);
            ip = newIP;
        }//actionPerformed
    }//IPListener
    
    /**
     * ActionListener for the change port menu item
     * prompts the user for a new server port
     */
    class PortListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String newPort = JOptionPane.showInputDialog(null, GomokuVariables.portNumber);
            if(newPort!=null){
                setPort(Integer.parseInt(newPort));
                port = Integer.parseInt(newPort);
            }
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
                if(checkConnection()){
                    tryLogin();
                }  
            }//keyPressed
        }
        @Override
        public void keyReleased(KeyEvent e) {
            //NA
        }//keyReleased  
    }//EnterListener
    
}//AuthController
