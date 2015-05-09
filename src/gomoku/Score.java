package gomoku;

public class Score {

    private String username;
    private int win, lose, tie;
    
    public Score(){
        this.win = 0;
        this.lose = 0;
        this.tie = 0;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public Score(String scoreString){
        String[] split = scoreString.split("\\s+");
        this.win = Integer.parseInt(split[0]);
        this.lose = Integer.parseInt(split[1]);
        this.tie = Integer.parseInt(split[2]);
    }
    
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
    
    public void setScore(int win, int lose, int tie){
        this.tie = tie;
        this.win = win;
        this.lose = lose;
    }
    
    public String toString(){
        return this.username + " " + this.win + " " + this.lose + " " + this.tie;
    }
    
}
