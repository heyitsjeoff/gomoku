/*
Jeoff Villanueva
Project
CSCE 320 Spring
Date
Java used in Netbeans
Sources:

 */
package gomoku;

import java.net.ServerSocket;
import java.net.Socket;


public class GameController {
    
    private GameModel theModel;
    private GameView theView;
    
    //connection things
    private Thread worker;
    private Socket socket;
    private ServerSocket serverSocket;

    public GameController(GameModel theModel, GameView theView){
        this.theModel = theModel;
        this.theView = theView;
    }
    
}
