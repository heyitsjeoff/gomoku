package gomoku;

public class Score {

    private String username;
    private int win, lose, tie;
    
    /**
     * instantiates the scores to zero
     */
    public Score(){
        this.win = 0;
        this.lose = 0;
        this.tie = 0;
    }
    
    /**
     * converts a string to a score
     * @param scoreString string representation of the score
     */
    public Score(String scoreString){
        String[] split = scoreString.split("\\s+");
        this.win = Integer.parseInt(split[0]);
        this.lose = Integer.parseInt(split[1]);
        this.tie = Integer.parseInt(split[2]);
    }
    
    /**
     * sets the username of the score objects
     * @param username
     */
    public void setUsername(String username){
        this.username = username;
    }
    
    /**
     * gets the username of the score object
     * @return
     */
    public String getUsername(){
        return this.username;
    }
    
    /**
     * increments the wins, losses, or ties
     * @param option int of the score being modified
     */
    public void manipulateScore(int option){
        if(option==1){
            this.win++;
        }
        else if(option==2){
            this.lose++;
        }
        else{
            this.tie++;
        }
    }
    
    /**
     * sets the score of the score object
     * @param win
     * @param lose
     * @param tie
     */
    public void setScore(int win, int lose, int tie){
        this.tie = tie;
        this.win = win;
        this.lose = lose;
    }
    
    /**
     * converst a score to a string
     * @return string representation of the score
     */
    public String toString(){
        return this.username + " " + this.win + " " + this.lose + " " + this.tie;
    }
    
}
