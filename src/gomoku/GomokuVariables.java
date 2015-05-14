package gomoku;

import java.awt.Color;

public class GomokuVariables {
    
    //Strings for Client/Connection
    public static final String logIn = "y";
    public static final String notLogIn = "n";
    public static final String uae = "user already exists";
    public static final String created = "user created";
    public static final String LIST = "LIST";
    public static final String INVITETO = "INVITETO";
    public static final String INVITEFROM = "INVITEFROM";
    public static final String REQUESTLIST = "REQUESTLIST;";
    public static final String ACCEPTFROM = "ACCEPTFROM";
    public static final String WITHDRAWFROM = "WITHDRAWFROM";
    public static final String DECLINETO = "DECLINETO";
    public static final String DECLINEFROM = "DECLINEFROM";
    public static final String STATSRETURN = "STATSRETURN";
    
    //Colors for cell
    public static final Color myColor = Color.blue;
    public static final Color enemyColor = Color.orange;
    public static final Color blank = Color.white;
    
    //Strings for Gameplay
    public static final String makeMove = "Please make a valid move";
    public static final String gameOverWin =  "You have won!\n Returning to the lobby";
    public static final String gameOverLose =  "You have lost!\n Returning to the lobby";
    public static final String YOULOSE = "YOULOSE";
    public static final char MYTOKEN = '*';
    public static final char THEIRTOKEN = '#';
}
