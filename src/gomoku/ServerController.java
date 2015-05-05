package gomoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author heyitsjeoff
 */
public class ServerController implements Runnable{
    
    private Thread worker;
    private Socket socket;
    private ServerSocket serverSocket;
    private ServerModel theModel;
    private ServerGUI theView;
    private AuthController theAuthController;
    private Client theClient;
    
    /**
     * Creates a ServerController object 
     * @param theModel
     * @param theView
     */
    public ServerController(ServerModel theModel, ServerGUI theView){
        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }//try catch
        this.theModel = theModel;
        this.theView = theView;
    }//ServerController constructor
    
    /**
     * accepts incoming clients
     */
    @Override
    public void run() {
        boolean accepting = true;
         while(accepting){
            try {
                this.socket = serverSocket.accept();
                this.theClient = new Client(this.socket, this);
                theClient.start();
            } catch (IOException ex) {
                Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
            }  
         }//while accepting
    }//run
    
	/**
	 * Gets the server model
	 * @return the server model
	 */
	public ServerModel getModel(){
		return theModel;
	}
	
    /**
     * checks if a user's credentials are valid
     * @param check user in question
     * @return true if credentials are valid
     */
    public boolean authenticate(User check){
        if(theModel.authenticate(check)){
            theModel.addOnline(theClient);
            return true;
        }
        else{
            return false;
        }
    }//authenticate
    
    public void updateAllOnlineList(String message){
        theModel.updateAllOnlineList(message);
        //this.theClient.sendMessage("REQUESTLIST");
    }
    
    /**
     * checks to see if a user with a specific username exists
     * @param check user in question
     * @return true if username is being used
     */
    public boolean checkUsers(User check){
        return theModel.userExists(check);
	}
	
    /**
     * checks to see if a user with a specific username exists
     * @param check user in question
     * @return true if username is being used
     */
    public boolean checkUsers(String username){
        return theModel.userExists(username);
    }
    
    /**
     * adds a user to the model
     * @param nUser user being added
     */
    public void addUser(User nUser){
        theModel.addUser(nUser);
    }
    
    /**
     * removes a user from the model
     * @param oUser user being removed
     */
    public void removeUser(Client oUser){
        theModel.removeOnline(oUser);
        StringBuilder sb = new StringBuilder("LIST ");
        sb.append(theModel.getOnlineUsernames()+";");
        theModel.updateAllOnlineList(sb.toString());
        System.out.println("tried remove user");
    }
    
    /**
     * instantiates and starts a thread
     */
    public void listen(){
        worker = new Thread(this);
        worker.start();
    }//listen

}//ServerController
