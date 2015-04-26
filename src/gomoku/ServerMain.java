package gomoku;

import java.io.IOException;
import javax.swing.JFrame;

public class ServerMain {

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{
        ServerModel theModel = new ServerModel();
        ServerGUI theView = new ServerGUI();
        ServerController myServer = new ServerController(theModel, theView);
        myServer.listen();
        JFrame app = new JFrame("Gomoku Online Users");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setContentPane(theView);
        app.pack();
        app.setVisible(true);
        myServer.displayIP();
    }//psvm
}
