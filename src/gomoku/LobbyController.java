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
    private DefaultListModel odlm;
    private Connection theConnection;
    private LobbyModel theLobbyModel;
    private String welcome = "Welcome to the Lobby ";
    private String pendingRequest = "You have a pending request";
    private GameView theGameView;
    private GameModel theGameModel;
    private GameHostController theGameController;

    public LobbyController(LobbyView theView, LobbyModel theLobbyModel, Connection theConnection){
        this.theView = theView;
        this.theView.challengePlayerListener(new ChallengeListener());
        this.theView.acceptListener(new AcceptListener());
        this.theView.rejectListener(new RejectListener());
        this.theView.withdrawListener(new WithdrawListener());
        this.theConnection = theConnection;
        this.theConnection.setLobbyController(this);
        this.theLobbyModel = theLobbyModel;
        this.theView.setTitle(welcome);
        //this.theConnection.write("REQUESTLIST ;");
    }
      
    public DefaultListModel updateOnlineList(String onlineUsers){
        this.dlm = new DefaultListModel();
        String[] usernames = onlineUsers.split("\\s+");
        for(int i = 0; i< usernames.length; i++){
            this.dlm.addElement(usernames[i]);
        }
        theView.updateOnlineList(dlm);
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
        this.theView.setTitle(pendingRequest);
    }
    
    public void addToOutgoingList(String username){
        theLobbyModel.addToOutgoingList(username);
        theView.updateOutgoingList(updateOutgoingList(theLobbyModel.updateOutgoingList()));
    }
    
    public DefaultListModel updateOutgoingList(String manyUsernames){
        this.odlm = new DefaultListModel();
        String[] usernames = manyUsernames.split("\\s+");
        for(int j = 0; j< usernames.length; j++){
            this.odlm.addElement(usernames[j]);
        }
        return this.odlm;
    }
    
    public void acceptRequest(String usernameAccepted){
        theConnection.write("ACCEPTTO "+usernameAccepted+";");
        startGame();
    }
    
    public void rejectRequest(String usernameDeclined){
        theConnection.write("DECLINEFROM "+ usernameDeclined+";");
    }
    
    public void withdrawRequest(String usernameWithdrawn){
        theConnection.write("WITHDRAWFROM " + usernameWithdrawn + ";");
    }
    
    public void startGame(){
        this.theView.setVisible(false);
        this.theGameModel = new GameModel();
        this.theGameModel.setPlayerHostName(theLobbyModel.getUsername());
        this.theGameView = new GameView();
        this.theGameView.setVisible(true);
        this.theGameView.setHostLabel(theGameModel.getPlayerHostName());
        this.theGameController  = new GameHostController(this.theGameModel, this.theGameView);
    }
    
    //Listeners
    class AcceptListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            acceptRequest(theView.getIncomingUsername());
        }
    }
    
    class RejectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            rejectRequest(theView.getIncomingUsername());
        }
    }
    
    /**
     * Listener for the back button of Create Account view
     * hides the Create Account View and displays the main view
     */
    class ChallengeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("LobbyController.challengeListener: "+"INVITETO " + theView.getSelectedUsername() + ";");
            theConnection.write("INVITETO " + theView.getSelectedUsername() + ";");
            addToOutgoingList(theView.getSelectedUsername());
        }//actionPerformed
    }//backListener
    
    
    class WithdrawListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            withdrawRequest(theView.getOutgoingUsername());
        }
    }
    
}
