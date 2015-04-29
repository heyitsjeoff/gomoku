package gomoku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Connection object
 */
public class Client extends Thread implements Comparable<String>{
    private InputStream inputStream;
    private OutputStream outputStream;
    private int port;
    private Socket socket;
    private Thread worker;
    private BufferedReader br;
    private byte[] byteArray = new byte[2000];
    private ServerController theServerController;
    private String connectionIP;
    private String clientUsername;//string rep of clinet object in the form of user's username
	private ServerModel theServerModel;
	private boolean loggedIn;

	/**
	 * Creates a client object and sets the streams
	 *
	 * @param clientSocket the socket of the client connecting to the server
	 * @param theServerController the ServerController object
	 * @throws IOException
	 */
	public Client(Socket clientSocket, ServerController theServerController)
			throws IOException {
		this.socket = clientSocket;
		this.theServerController = theServerController;
		this.inputStream = clientSocket.getInputStream();
		this.outputStream = clientSocket.getOutputStream();
		this.br = new BufferedReader(new InputStreamReader(inputStream));
		this.connectionIP = this.socket.
				getRemoteSocketAddress().toString().substring(1);
		this.loggedIn = false;
		theServerModel = theServerController.getModel();
	}//Connection

	/**
	 * Sends network message to client
	 *
	 * @param message Message to be sent
	 */
	public void sendMessage(String message){
		try {
			byte[] buffOut;
			buffOut = message.getBytes();
			this.outputStream.write(buffOut, 0, message.length());
			this.outputStream.flush();
		} catch (IOException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	/**
	 * Interprets and executes a command received over the network from a Gomoku client program
	 * @param command The command received from the network. Each command starts with a keyword
	 *					identifying the command, contains a space-deliniated list of arguments,
	 *					and terminates with a semicolon.
	 */
	private void interpretCommand(String command) {
		Scanner scan = new Scanner(command);
		String cmdKeyword = scan.next();
		switch (cmdKeyword) {
			case "INVITETO":
				if(!theServerModel.invite(clientUsername, scan.next()))
					sendMessage("FAIL The targeted user does not exist or is offline;");
				break;
			case "ACCEPTTO":
				if(!theServerModel.accept(scan.next(), clientUsername, connectionIP))
					sendMessage("FAIL The targeted user does not exist or is offline;");
				break;
			case "WITHDRAWTO":
				if(!theServerModel.withdraw(clientUsername, scan.next()))
					sendMessage("FAIL The targeted user does not exist or is offline;");
				break;
			case "DECLINETO":
				if(!theServerModel.decline(clientUsername, scan.next()))
					sendMessage("FAIL The targeted user does not exist or is offline;");
				break;
			case "REQUESTLIST":
				StringBuilder sb = new StringBuilder("LIST ");
				for(Client c : theServerModel.getOnlineList()){
					sb.append(c.clientUsername);
					sb.append(" ");
				}
				sb.append(";");
				//sendMessage(sb.toString());
                                theServerModel.updateAllOnlineList(sb.toString());
				break;
			default:
				//This block allows later addition of special handling
				//for invalid network commands.
				//As of now they are ignored.
				break;
		}
	}
	
	/**
	 * Processes an incoming invitation. Called when another user has invited
	 *		this user to a game, this method generates and sends a network
	 *		message informing the client program of the invitation
	 * @param fromUsername The user sending the invitation
	 */
	public void invite(String fromUsername) {
		sendMessage("INVITEFROM " + fromUsername + ";");
	}

	/**
	 * Processes an incoming acceptance. Called when this user has previously
	 *		invited another user to a game, and the other user has accepted.
	 *		This method generates and sends a network message informing
	 *		the client program of the acceptance.
	 * @param fromUsername The user accepting the invitation
	 * @param fromIP The IP address of the user accepting the invitation
	 */
	public void accept(String fromUsername, String fromIP) {
		sendMessage("ACCEPTFROM " + fromUsername + " "
			+ fromIP + ";");
	}
	
	/**
	 * Processes an incoming acceptance. Called when another user has previously
	 *		invited this user to a game, but withdraws the invitation.
	 *		This method generates and sends a network message informing
	 *		the client program of the withdrawl.
	 * @param fromUsername The user accepting the invitation
	 */
	public void withdraw(String fromUsername) {
		sendMessage("WITHDRAWFROM " + fromUsername + ";");
	}

	/**
	 * Processes an incoming declination. Called when this user has previously
	 *		invited another user to a game, and the other user has declined.
	 *		This method generates and sends a network message informing
	 *		the client program of the declination.
	 * @param fromUsername The user accepting the invitation
	 */
	public void decline(String fromUsername) {
		sendMessage("DECLINEFROM " + fromUsername + ";");
	}

	/**
	 * creates a user object with a given username and password and checks if it
	 * is valid
	 *
	 * @param u username
	 * @param p password
	 * @return
	 */
	public boolean authenticate(String u, String p) {
		User check = new User(u, p);
		if (this.theServerController.authenticate(check)) {
			loggedIn = true;
			return true;
		} else {
			return false;
		}
	}//authenticate

	/**
	 * attempts to create a new user object
	 *
	 * @param username of new user
	 * @param password of new user
	 * @return
	 */
	public String createAccount(String username, String password) {
		User newUser = new User(username, password);
		if (theServerController.checkUsers(newUser)) {
			return "user already exists";
		} else {
			theServerController.addUser(newUser);
			return "user created";
		}
	}//createAccount
        
        public String getUsername(){
            return this.clientUsername;
        }
	
	/**
	 * Compares to a string representing a username, to determine if this Client object
	 * is associated with that user.
	 * 
	 * @param username the name of a user
	 * @return	0 if this Client object is associated with user username
	 *			1 if this Client object is associated with a different user
	 *			-1 if this CLient object is not associated with any user
	 *				because it is not yet logged in
	 */
	@Override
	public int compareTo(String username) {
		if(loggedIn){
			if(username.equals(clientUsername)){
				return 0;
			} else return 1;
		} else return -1;
	}

	/**
	 * reads in, interprets, and acts upon network messages
	 */
	public void run() {
            boolean connected = true;
            String message = "";
            while (connected) {
		try {
                    int count = this.inputStream.read(byteArray);
                    if (count > 0) {

                        //authentication mode
                        if (!loggedIn) {
                            message = new String(byteArray, 0, count);
                            //connected = false;
                            String[] split = message.split("\\s+");
                            String uName = split[0];
                            String uPass = split[1];
                            this.clientUsername = uName;
                            if (message.charAt(0) == '!') {
                                sendMessage(createAccount(uName.substring(1), uPass));
                            }//called when creating new account
                            else {
                                if (authenticate(uName, uPass)) {
                                    loggedIn = true; 
                                    sendMessage("y");
                                } else {
                                    sendMessage("n");
                                }
                            }//else authenticate
                            message = "";
                        }
                        //lobby mode
                        else {
                            //extract semicolon-separated commands from input stream
                            String inRaw = new String(byteArray, 0, count);
                            message = message + inRaw;
                            int deliniate = message.indexOf(";");
                            while (deliniate >= 0) {
                                String command = message.substring(0, deliniate);
                                message = message.substring(deliniate + 1);
                                interpretCommand(command);
                                deliniate = message.indexOf(";");
                            }
			}//else authenticate
			message = "";
                    

				}
                    else if(count==-1){
                        this.inputStream.close();
                        this.outputStream.close();
                        this.socket.close();
                        this.theServerController.removeUser(this);
                        connected = false;
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(Client.class.
                            getName()).log(Level.SEVERE, null, ex);
			}
            }//while
	}//run    
}//Client class
