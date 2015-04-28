package gomoku;

import java.util.ArrayList;


public class LobbyModel {
    
    private ArrayList<String> incomingList;
    private ArrayList<String> outgoingList;
    
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
    
    public String updateIncomingList(){
        StringBuilder sb = new StringBuilder();
        for(int i =0; i<this.incomingList.size(); i++){
            sb.append(this.incomingList.get(i) + " ");
        }
        return sb.toString();
    }

}
