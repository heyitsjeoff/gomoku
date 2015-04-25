/**
 * Program to play Gomoku
 * Usage: java Gomoku <ip of server>
 * ip not required
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

import javax.swing.JFrame;

/**
 *
 * @author heyitsjeoff
 */
public class Gomoku {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainView theMainView = new MainView();
        AuthController myAuthController = new AuthController(theMainView);
        if(args.length > 0){
            myAuthController.setIP(args[0]);
        }
        theMainView.setVisible(true);
    } 
}
