package gomoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

public class LobbyController implements Runnable{
    
    private LobbyView theView;
    private DefaultListModel dlm;
    private Client theClient;

    public LobbyController(LobbyView theView, Client theClient){
        this.theView = theView;
        this.theView.challengePlayerListener(new ChallengeListener());
        this.theClient = theClient;
    }
    
    @Override
    public void run() {
        boolean waitingInLobby = true;
        while(waitingInLobby){
            
        }
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
            theClient.sendMessage("INVITETO " + theView.getSelectedUsername());
        }//actionPerformed
    }//backListener
    
    
}
