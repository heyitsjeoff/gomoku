package gomoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

public class LobbyController{
    
    private LobbyView theView;
    private DefaultListModel<String> dlm;
    private DefaultListModel<String> idlm;
    private DefaultListModel<String> odlm;
    private Connection theConnection;
    private LobbyModel theLobbyModel;
    private String welcome = "Welcome to the Lobby ";
    private String pendingRequest = "You have a pending request";
    private String swar = "Someone withdrew a request";
    private GameView theGameView;
    private PotentialView pView;
    private GameModel theGameModel;
    private GameHostController theGameController;
    private Connection connectionToGame;

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
        this.dlm = new DefaultListModel<String>();
        String[] usernames = onlineUsers.split("\\s+");
        for(int i = 0; i< usernames.length; i++){
            this.dlm.addElement(usernames[i]);
        }
        theView.updateOnlineList(dlm);
        return this.dlm;
    }
    
    public DefaultListModel updateIncomingList(String manyUsernames){
        this.idlm = new DefaultListModel<String>();
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
    
    public void removeFromIncomingList(String username){
        theLobbyModel.removeFromIncomingList(username);
        theView.updateIncomingList(updateIncomingList(theLobbyModel.updateIncomingList()));
    }
    
    public void addToOutgoingList(String username){
        theLobbyModel.addToOutgoingList(username);
        theView.updateOutgoingList(updateOutgoingList(theLobbyModel.updateOutgoingList()));
    }
    
    public void removeFromOutgoingList(String username){
        theLobbyModel.removeFromOutgoingList(username);
        theView.updateOutgoingList(updateOutgoingList(theLobbyModel.updateOutgoingList()));
    }
    
    public DefaultListModel updateOutgoingList(String manyUsernames){
        this.odlm = new DefaultListModel<String>();
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
        theConnection.write("DECLINETO "+ usernameDeclined+";");
        removeFromIncomingList(usernameDeclined);
    }
    
    public void withdrawRequest(String usernameWithdrawn){
        theConnection.write("WITHDRAWTO " + usernameWithdrawn + ";");
        removeFromOutgoingList(usernameWithdrawn);
    }
    
    public void startGame(){
        this.theView.setVisible(false);
        this.theGameModel = new GameModel();
        this.theGameModel.setPlayerHostName(theLobbyModel.getUsername());
        this.pView = new PotentialView();
        this.theGameController  = new GameHostController(this.theGameModel, this.pView);
        this.theGameController.listen();
    }
    
    public void connectToHost(String hostIP){
        connectionToGame = new Connection(hostIP);
        connectionToGame.setPort(8081);
        connectionToGame.connect();
        connectionToGame.startThread();
        this.theView.setVisible(false);
        this.theGameModel = new GameModel();
        this.theGameModel.setPlayerClientName(theLobbyModel.getUsername());
        this.pView = new PotentialView();
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
            if(!theView.getSelectedUsername().isEmpty()){
                if(!theView.getSelectedUsername().equals(theLobbyModel.getUsername())){
                    theConnection.write("INVITETO " + theView.getSelectedUsername() + ";");
                    addToOutgoingList(theView.getSelectedUsername());
                }
            }
        }//actionPerformed
    }//backListener
    
    
    class WithdrawListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            withdrawRequest(theView.getOutgoingUsername());
        }
    }
    
}
