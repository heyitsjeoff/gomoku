/*
Jeoff Villanueva
Project
CSCE 320 Spring
Date
Java used in Netbeans
Sources:

 */
package gomoku;


public class GameModel {
    
    private String playerHost;
    private String playerClient;
    
    public void setPlayerHostName(String name){
        this.playerHost = name;
    }
    
    public void setPlayerClientName(String name){
        this.playerClient = name;
    }

    public String getPlayerHostName() {
        return this.playerHost;
    }
    
    public String getPlayerClientName(){
        return this.playerClient;
    }

}
