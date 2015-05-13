/**
 * Program to play Gomoku
 * Usage: java Gomoku <ip of server>
 * ip not required
 * http://www.quora.com/How-do-I-generate-a-positive-7-digit-random-number-in-Java
 */

/*
Group Ultron
Chase Luplow
Junhao Zeng
John Lyon
Jeoff Villanueva
Authentication
CSCE 320 Spring
Date 6 April 2015
Java used in Netbeans
Sources: cipherspeak.tk - for encryption
*/

package gomoku;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import static javax.swing.UIManager.getSystemLookAndFeelClassName;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author heyitsjeoff
 */
public class Gomoku {
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
        }
        theMainView.setVisible(true);
    } 
}
