package gomoku;
//john test commit
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
public class Client extends Thread{
    private InputStream inputStream;
    private OutputStream outputStream;
    private int port;
    private Socket socket;
    private Thread worker;
    private BufferedReader br;
    private byte[] byteArray = new byte[2000];
    private ServerController theServerController;
    private String connectionIP;
    private String clientUsername;  //string rep of clinet object in the form of user's username

	private boolean loggedIn;
	private User thisUser;

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
	}//Connection

	/**
	 * Sends network message to client
	 *
	 * @param message Message to be sent
	 * @throws IOException
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
						if (message.charAt(0) == '!') {
							sendMessage(createAccount(uName.substring(1), uPass));
						}//called when creating new account
						else {
							if (authenticate(uName, uPass)) {
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
						}
					}
				}
			} catch (IOException ex) {
				Logger.getLogger(Client.class.
						getName()).log(Level.SEVERE, null, ex);
			}
		}//while
	}//run    

	private void interpretCommand(String command) {
		Scanner scan = new Scanner(command);
		String cmdKeyword = scan.next();
		if (cmdKeyword.equals("INVITETO")) {
			//todo invite
		} else if (cmdKeyword.equals("ACCEPTTO")) {
			//todo accept
		} else if (cmdKeyword.equals("WITHDRAWTO")) {
			//todo withdraw
		} else if (cmdKeyword.equals("DECLINETO")) {
			//todo decline
		} else if (cmdKeyword.equals("REQUESTLIST")) {
			//todo request list
		} else {
			//This block allows later addition of special handling
			//for invalid network commands.
			//As of now they are ignored.
		}
	}

	public void invite(String fromUsername) {
		sendMessage("INVITEFROM " + fromUsername + ";");
	}

	public void accept(String fromUsername, String fromIP) {
		sendMessage("ACCEPTFROM " + fromUsername + " "
			+ fromIP + ";");
	}

	public void withdraw(String fromUsername) {
		sendMessage("WITHDRAWFROM " + fromUsername + ";");
	}

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
			thisUser = check;
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

}//Client class
