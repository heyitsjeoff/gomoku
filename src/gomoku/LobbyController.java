package gomoku;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

public class LobbyController{
    //Models
    private LobbyModel theLobbyModel;
    private GameModel theGameModel;
    
    //Views
    private LobbyView theView;
    private GameView2 pView;
    
    //Controllers
    private GameHostController theGameHostController;
    private GameClientController theGameClientController;
    
    //variables for class
    private DefaultListModel<String> dlm;
    private DefaultListModel<String> idlm;
    private DefaultListModel<String> odlm;
    private Connection theConnection;
    private int boardSize = GomokuVariables.boardSize;

    private ConnectionForGame connectionToGame;

    /**
     * creates the controller for the lobby
     * @param theView gui of the lobby
     * @param theLobbyModel model of the lobby
     * @param theConnection connection object to write to the stream
     */
    public LobbyController(LobbyView theView, LobbyModel theLobbyModel, Connection theConnection){
        this.theView = theView;
        this.theView.opponentStatsListener(new StatListener());
        this.theView.challengePlayerListener(new ChallengeListener());
        this.theView.acceptListener(new AcceptListener());
        this.theView.rejectListener(new RejectListener());
        this.theView.withdrawListener(new WithdrawListener());
        this.theConnection = theConnection;
        this.theConnection.setLobbyController(this);
        this.theLobbyModel = theLobbyModel;
        this.theView.setTitle(GomokuVariables.welcome);
        this.theConnection.write("STATS;");
    }
    
    /**
     * writes a connection to the output stream
     * @param message string of the message
     */
    public void writeToConnection(String message){
        this.theConnection.write(message);
    }
      
    /**
     * constructs the DefaultListModel for the online list
     * @param onlineUsers string representation of the online users
     * @return DefualtListModel version of the strings being passed in
     */
    public DefaultListModel<String> constructOnlineListDLM(String onlineUsers){
        this.dlm = new DefaultListModel<String>();
        String[] usernames = onlineUsers.split("\\s+");
        for(int i = 0; i< usernames.length; i++){
            this.dlm.addElement(usernames[i]);
        }
        theView.updateOnlineList(dlm);
        return this.dlm;
    }
    
    /**
     * constructs the DefualtLIstmodel for incoming invites
     * @param manyUsernames string representation of the incoming invites
     * @return DefaultLIstModel version of the string being passed in
     */
    public DefaultListModel<String> updateIncomingList(String manyUsernames){
        this.idlm = new DefaultListModel<String>();
        String[] usernames = manyUsernames.split("\\s+");
        for(int j = 0; j< usernames.length; j++){
            this.idlm.addElement(usernames[j]);
        }
        return this.idlm;
    }
    
    /**
     * empties the incoming and outgoing list
     */
    public void clearIncomingOutgoing(){
        theLobbyModel.clearIncomingOutgoing();
        theView.updateIncomingList(updateIncomingList(theLobbyModel.updateIncomingList()));
        theView.updateOutgoingList(updateOutgoingList(theLobbyModel.updateOutgoingList()));
    }
    
    /**
     * adds a user to the incoming list
     * @param username username of the a challenger
     */
    public void addToIncomingList(String username){
        theLobbyModel.addToIncomingList(username);
        theView.updateIncomingList(updateIncomingList(theLobbyModel.updateIncomingList()));
        this.theView.setTitle(GomokuVariables.pendingRequest);
    }
    
    /**
     * removes a player from the incoming list
     * @param username username of the player being removed
     */
    public void removeFromIncomingList(String username){
        theLobbyModel.removeFromIncomingList(username);
        theView.updateIncomingList(updateIncomingList(theLobbyModel.updateIncomingList()));
    }
    
    /**
     *  adds a user to the outgoing list
     * @param username username of the player being invites
     */
    public void addToOutgoingList(String username){
        theLobbyModel.addToOutgoingList(username);
        theView.updateOutgoingList(updateOutgoingList(theLobbyModel.updateOutgoingList()));
    }
    
    /**
     * removes a player from the outgoing list
     * @param username username of the player being removed
     */
    public void removeFromOutgoingList(String username){
        theLobbyModel.removeFromOutgoingList(username);
        theView.updateOutgoingList(updateOutgoingList(theLobbyModel.updateOutgoingList()));
    }
    
    /**
     * constructs the DefualtLIstmodel for outgoing invites
     * @param manyUsernames string representation of the outgoing invites
     * @return DefaultLIstModel version of the string being passed in
     * @return DefaultLIstModel version of the string being passed in
     */
    public DefaultListModel<String> updateOutgoingList(String manyUsernames){
        this.odlm = new DefaultListModel<String>();
        String[] usernames = manyUsernames.split("\\s+");
        for(int j = 0; j< usernames.length; j++){
            this.odlm.addElement(usernames[j]);
        }
        return this.odlm;
    }
    
    /**
     * accepts an invite
     * @param usernameAccepted username of the player being accepted
     */
    public void acceptRequest(String usernameAccepted){
        theConnection.write(GomokuVariables.ACCEPTTO+usernameAccepted+";");
        startGame();
    }
    
    /**
     * rejects an invite
     * @param usernameDeclined username of the player being declined
     */
    public void rejectRequest(String usernameDeclined){
        theConnection.write(GomokuVariables.DECLINETOSPACE+ usernameDeclined+";");
        removeFromIncomingList(usernameDeclined);
    }
    
    /**
     * withdraws an invite
     * @param usernameWithdrawn username of the player being withdrawn
     */
    public void withdrawRequest(String usernameWithdrawn){
        theConnection.write(GomokuVariables.WITHDRAWTO + usernameWithdrawn + ";");
        removeFromOutgoingList(usernameWithdrawn);
    }
    
    /**
     * enables or disables the view
     * @param value boolean value for the setVisible method
     */
       public void setView(boolean value){
       this.theView.setVisible(value);
   }
    
    /**
     * clears the incoming and outgoing list
     * instantiates the model and view
     * creates the host controller and tells it to start the thread
     */
    public void startGame(){
        clearIncomingOutgoing();
        this.theView.setVisible(false);
        this.theGameModel = new GameModel(this.boardSize, this.boardSize);
        this.theGameModel.setPlayerHostName(theLobbyModel.getUsername());
        this.pView = new GameView2();
        //this.pView.drawBoard(boardSize, boardSize);
        //this.pView.setVisible(true);
        this.theGameHostController  = new GameHostController(this.theGameModel, this.pView, this);
        this.theGameHostController.drawBoard();
        this.theGameHostController.listen();
        this.theGameModel.setPlayerHostName(theConnection.getUsername());
    }
    
    /**
     * clears the incoming and outgoing list
     * instantiates the model and view
     * connects to the host
     * @param hostIP ip of the host
     */
    public void connectToHost(String hostIP){
        clearIncomingOutgoing();
        connectionToGame = new ConnectionForGame(hostIP);
        connectionToGame.setPort(8081);
        connectionToGame.connect();
        connectionToGame.startThread();
        this.theView.setVisible(false);
        this.theGameModel = new GameModel(this.boardSize, this.boardSize);
        this.theGameModel.setPlayerClientName(theLobbyModel.getUsername());
        this.pView = new GameView2();
        //this.pView.drawBoard(boardSize, boardSize);
        this.pView.disableSend();
        this.theGameModel.setPlayerClientName(theConnection.getUsername());
        this.theGameClientController = new GameClientController(this.pView, this.theGameModel, this.connectionToGame, this);
        this.theGameClientController.drawBoard();
        connectionToGame.setGameClientController(this.theGameClientController);
    }
    
    /**
     * gets a reference to the client controller
     * @return client controller
     */
    public GameClientController getClientController(){
        return this.theGameClientController;
    }
    
    /**
     * stores the string of the scores to the model
     * @param fullList
     */
    public void storeStats(String fullList){
        this.theLobbyModel.storeInitStats(fullList);
        displayMyStats(this.theLobbyModel.getStats(this.theLobbyModel.getUsername()));
    }
    
    /**
     * displays this player's stas on the view
     * @param stats string representation of the score
     */
    public void displayMyStats(String stats){
        String[] temp = stats.split("\\s+");
        this.theView.displayMyStats("WINS: \t"+temp[0]);
        this.theView.displayMyStats("LOSSES: "+temp[1]);
        this.theView.displayMyStats("DRAWS: \t"+temp[2]);
    }
    
    /**
     * displays the oppenent's stats on the view
     * @param stats string representation of the score
     */
    public void displayOppStats(String stats){
        String[] temp = stats.split("\\s+");
        this.theView.clearOppStats();
        this.theView.displayOppStats("WINS: \t"+temp[0]);
        this.theView.displayOppStats("LOSSES: "+temp[1]);
        this.theView.displayOppStats("DRAWS: \t"+temp[2]);
    }
    
    //Listeners
    /**
     * ActionListener for the accept button
     */
    class AcceptListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            acceptRequest(theView.getIncomingUsername());
        }
    }
    
    /**
     * ActionListener for the reject button
     */
    class RejectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            rejectRequest(theView.getIncomingUsername());
        }
    }
    
    /**
     * ActionLIstener for the stats button
     */
    class StatListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String oppStats = theLobbyModel.getStats(theView.getSelectedUsername());
            displayOppStats(oppStats);
        }
    }
        
    /**
     * ActionListener for the challenge button
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
    
    /**
     * ActionListener for the withdraw button
     */
    class WithdrawListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            withdrawRequest(theView.getOutgoingUsername());
        }
    }
    
}
