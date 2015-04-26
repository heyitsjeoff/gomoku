package gomoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

public class LobbyController{
    
    private LobbyView theView;
    private DefaultListModel dlm;
    private Connection theConnection;

    public LobbyController(LobbyView theView, Connection theConnection){
        this.theView = theView;
        this.theView.challengePlayerListener(new ChallengeListener());
        this.theConnection = theConnection;
        this.theConnection.setLobbyController(this);
        //this.theConnection.write("REQUESTLIST ;");
    }
      
    public DefaultListModel updateOnlineList(String onlineUsers){
        this.dlm = new DefaultListModel();
        String[] usernames = onlineUsers.split("\\s+");
        for(int i = 0; i< usernames.length; i++){
            this.dlm.addElement(usernames[i]);
        }
        return this.dlm;
    }
    
    //Listeners
    /**
     * Listener for the back button of Create Account view
     * hides the Create Account View and displays the main view
     */
    class ChallengeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("LobbyController.challengeListener: "+"INVITETO " + theView.getSelectedUsername() + ";");
            theConnection.write("INVITETO " + theView.getSelectedUsername() + ";");
        }//actionPerformed
    }//backListener
    
    
}
