/**
 * Program to play Gomoku
 * Usage: java Gomoku <ip of server> <port of server>
 * ip and port required for connecting to the server
 */

/*
Group Ultron
Chase Luplow
Junhao Zeng
John Lyon
Jeoff Villanueva
Final Submissions
CSCE 320 Spring
Date 20 May 2015
Java used in Netbeans
*/

package gomoku;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Gomoku {
    
    /**
     * starts the game controller
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Gomoku.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Gomoku.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Gomoku.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Gomoku.class.getName()).log(Level.SEVERE, null, ex);
        }
        MainView theMainView = new MainView();
        AuthController myAuthController = new AuthController(theMainView);
        if(args.length > 0){
            myAuthController.setIP(args[0]);
            myAuthController.setPort(Integer.parseInt(args[1]));
        }
        theMainView.setVisible(true);
    } 
}
