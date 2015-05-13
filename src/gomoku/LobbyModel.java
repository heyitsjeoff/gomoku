package gomoku;

import java.util.ArrayList;


public class LobbyModel {
    
    private ArrayList<String> incomingList;
    private ArrayList<String> outgoingList;
    private ArrayList<String> listOfStats;
    private String username;
    
    public LobbyModel(){
        this.incomingList = new ArrayList<String>();
        this.outgoingList = new ArrayList<String>();
    }
    
    public void addToIncomingList(String requestFrom){
        if(!this.incomingList.contains(requestFrom)){
            this.incomingList.add(requestFrom);
        }
    }
    
    public void removeFromIncomingList(String requestFrom){
        if(this.incomingList.contains(requestFrom)){
            this.incomingList.remove(requestFrom);
        }
    }
    
    public void clearIncomingOutgoing(){
        this.incomingList.clear();
        this.outgoingList.clear();
    }
    
    public String updateIncomingList(){
        StringBuilder sb = new StringBuilder();
        for(int i =0; i<this.incomingList.size(); i++){
            sb.append(this.incomingList.get(i) + " ");
        }
        return sb.toString();
    }
    
    public void addToOutgoingList(String requestTo){
        if(!this.outgoingList.contains(requestTo)){
            this.outgoingList.add(requestTo);
        }
    }
    
    public void removeFromOutgoingList(String requestTo){
        if(this.outgoingList.contains(requestTo)){
            this.outgoingList.remove(requestTo);
        }
    }
    
    public String updateOutgoingList(){
        StringBuilder sb = new StringBuilder();
        for(int i =0; i<this.outgoingList.size(); i++){
            sb.append(this.outgoingList.get(i) + " ");
        }
        return sb.toString();
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public void storeInitStats(String list){
        
    }
}
