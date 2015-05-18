package gomoku;

import java.awt.Color;

public class GomokuVariables {
    
    //Strings for AuthController
    public static final String dc = "Disconnected";
    public static final String invalid = "Invalid Username or Password";
    public static final String nuc = "New user created";
    public static final String exception = "New exception found";
    public static final String dataDialog = "is the current data of login";
    public static final String cts = "Connected to server";
    public static final String pdnm = "Passwords do not match";
    public static final String ipChange = "IP changed to ";
    public static final String PCI = "Please Configure IP";
    public static final String complete = "Please complete both fields";
    public static final String serverIP = "Server IP:";
    public static final String portNumber = "Port Number:";
    public static final String anonString = "anon";
    public static final String initialIP = "127.0.0.1";
    public static final int initialPort = 8080;
    
    //Strings for CreateAccountController
    public static final String uaeL = "User already exists";
    public static final String created = "user created";
    public static final String ucs = "Username cannot contain spaces";
    public static final String pcs = "Password cannot contain spaces";
    public static final String pts = "Password too short \nMust be at least 6 characters long";
    
    //Strings for Client/Connection
    public static final String logIn = "y";
    public static final String notLogIn = "n";
    public static final String uae = "user already exists";
    public static final String LIST = "LIST";
    public static final String INVITETO = "INVITETO";
    public static final String INVITEFROM = "INVITEFROM";
    public static final String REQUESTLIST = "REQUESTLIST;";
    public static final String ACCEPTFROM = "ACCEPTFROM";
    public static final String WITHDRAWFROM = "WITHDRAWFROM";
    public static final String DECLINETO = "DECLINETO";
    public static final String DECLINEFROM = "DECLINEFROM";
    public static final String STATSRETURN = "STATSRETURN";
    
    //lobby vars
    public static final String welcome = "Welcome to the Lobby ";
    public static final String pendingRequest = "You have a pending request";
    public static final String swar = "Someone withdrew a request";
    public static final int boardSize = 30;
    
    //Colors for cell
    public static final Color myColor = Color.blue;
    public static final Color enemyColor = Color.orange;
    public static final Color blank = Color.white;
    
    //vars for Gameplay
    public static final int row = 30;
    public static final int col = 30;
    public static final String nm = "NEXTMOVE";
    public static final String YOULOSE = "YOULOSE";
    public static final String makeMove = "Please make a valid move";
    public static final String gameOverWin =  "You have won!\n Returning to the lobby";
    public static final String gameOverLose =  "You have lost!\n Returning to the lobby";
    public static final String oGameOverWin =  "You have defeated Ultron!\n Returning to the main menu";
    public static final String oGameOverLose =  "You have been defeated by Ultron!\n Returning to the main menu";
    public static final char MYTOKEN = '*';
    public static final char THEIRTOKEN = '#';
    public static final String IMADEAMOVEAT = "I made a move at row ";
    public static final String ANDCOL = " and column ";
    public static final String THEYMADEAMOVEAT = "They made a move at row ";
    public static final String MYTURN = "My Turn";
    public static final String THEIRTURN = "Their Turn";
    
    //Strings for OfflineDiff
    public static final String EASY = "Easy";
    public static final String MEDIUM = "Medium";
    public static final String HARD = "HARD";
    
    //vars for Server
    public static final String fileName = "data";
    public static final String scoreFileName = "scoreData";
    public static final String ude = "User does not exist";    
}
