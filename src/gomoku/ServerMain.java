package gomoku;

import java.io.IOException;
import javax.swing.JFrame;

public class ServerMain {
    
    private static int initialPort = 8080;

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{
        ServerModel theModel = new ServerModel();
        ServerGUI theView = new ServerGUI();
        ServerController myServer;
        if(args.length>0){
            initialPort = Integer.parseInt(args[0]);
        }
        myServer = new ServerController(theModel, theView, initialPort);
        myServer.listen();
        theView.append("Server listening on port: " + initialPort);
        JFrame app = new JFrame("Gomoku Server Feed");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setContentPane(theView);
        app.pack();
        app.setVisible(true);
    }//psvm
}
