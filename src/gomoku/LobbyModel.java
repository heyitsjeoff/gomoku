package gomoku;

import java.util.ArrayList;

public class LobbyModel {
    
    private ArrayList<String> incomingList;
    private ArrayList<String> outgoingList;
    private ArrayList<String> listOfStats;
    private String username;
    
    /**
     * instanties the lists
     */
    public LobbyModel(){
        this.incomingList = new ArrayList<String>();
        this.outgoingList = new ArrayList<String>();
        this.listOfStats = new ArrayList<String>();
    }
    
    /**
     * adds a user to the incoming list
     * @param requestFrom username of the player
     */
    public void addToIncomingList(String requestFrom){
        if(!this.incomingList.contains(requestFrom)){
            this.incomingList.add(requestFrom);
        }
    }
    
    /**
     * removes a user from the incoming list
     * @param requestFrom username of the player
     */
    public void removeFromIncomingList(String requestFrom){
        if(this.incomingList.contains(requestFrom)){
            this.incomingList.remove(requestFrom);
        }
    }
    
    /**
     * clears the incoming and outgoing lists
     */
    public void clearIncomingOutgoing(){
        this.incomingList.clear();
        this.outgoingList.clear();
    }
    
    /**
     * returns a string of the incoming list
     * @return incoming list string
     */
    public String updateIncomingList(){
        StringBuilder sb = new StringBuilder();
        for(int i =0; i<this.incomingList.size(); i++){
            sb.append(this.incomingList.get(i) + " ");
        }
        return sb.toString();
    }
    
    /**
     * adds a player to the outgoing list
     * @param requestTo username of the player
     */
    public void addToOutgoingList(String requestTo){
        if(!this.outgoingList.contains(requestTo)){
            this.outgoingList.add(requestTo);
        }
    }
    
    /**
     * removes a player from the outgoinglist
     * @param requestTo username of the player
     */
    public void removeFromOutgoingList(String requestTo){
        if(this.outgoingList.contains(requestTo)){
            this.outgoingList.remove(requestTo);
        }
    }
    
    /**
     * returns a string of the outgoing list
     * @return outgoing list string
     */
    public String updateOutgoingList(){
        StringBuilder sb = new StringBuilder();
        for(int i =0; i<this.outgoingList.size(); i++){
            sb.append(this.outgoingList.get(i) + " ");
        }
        return sb.toString();
    }

    /**
     * sets the username of the lobby
     * @param username username of the player
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * gets the username of the lobby
     * @return username of the lobby
     */
    public String getUsername(){
        return this.username;
    }
    
    /**
     * adds a score the list of stats
     * @param list string of the scores
     */
    public void storeInitStats(String list){
        String[] temp = list.split(",");
        for(String line:temp){
            listOfStats.add(line);
        }
    }
    
    /**
     * returns the score of a user
     * @param userid username of the user
     * @return score of username
     */
    public String getStats(String userid){
        String wld = "";    //winslossesdraws
        int usernameLen = userid.length();
        for(String id:listOfStats){
            String temp = id.substring(0,usernameLen);
            if(temp.equals(userid)&&id.substring(usernameLen, usernameLen+1).equals(" ")){
                wld=id.substring(usernameLen+1,id.length());
            }
        }
        return wld;  
    }
}
