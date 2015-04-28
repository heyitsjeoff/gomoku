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
    private DefaultListModel idlm;
    private Connection theConnection;
    private LobbyModel theLobbyModel;

    public LobbyController(LobbyView theView, LobbyModel theLobbyModel, Connection theConnection){
        this.theView = theView;
        this.theView.challengePlayerListener(new ChallengeListener());
        this.theView.acceptListener(new AcceptListener());
        this.theConnection = theConnection;
        this.theConnection.setLobbyController(this);
        this.theLobbyModel = theLobbyModel;
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
    
    public DefaultListModel updateIncomingList(String manyUsernames){
        this.idlm = new DefaultListModel();
        String[] usernames = manyUsernames.split("\\s+");
        for(int j = 0; j< usernames.length; j++){
            this.idlm.addElement(usernames[j]);
        }
        return this.idlm;
    }
    
    public void addToIncomingList(String username){
        theLobbyModel.addToIncomingList(username);
        theView.updateIncomingList(updateIncomingList(theLobbyModel.updateIncomingList()));
    }
    
    public void acceptRequest(String usernameAccepted){
        theConnection.write(usernameAccepted);
    }

    class AcceptListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            acceptRequest(theView.getIncomingUsername());
        }
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
