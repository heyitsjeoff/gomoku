package gomoku;


public class GameModel {
    
    private String playerHost;
    private String playerClient;
    
    private char[][] grid;
    private int rows;
    private int cols;
    private String nextMove;
    private int count = 0;
    
    public static final String nm = "NEXTMOVE";
    
    public static final char MYTOKEN = '*';
    public static final char THEIRTOKEN = '#';
    
    public GameModel(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[this.rows][this.cols];
        for(int i = 0; i<this.rows; i++){
            for(int j = 0; j<this.cols; j++){
                grid[i][j]='-';
            }
        }
    }
    
    
    public GameModel(GameModel other){
        this.rows = other.rows;
        this.cols = other.cols;
        this.grid = new char[this.rows][this.cols];
        for(int i=0; i<this.rows; i++){
            for(int j=0; j<this.cols; j++){
                this.grid[i][j]=other.grid[i][j];
            }
        }
    }
    
    public char[][] getGrid(){
        return this.grid;
    }
    
    public boolean gameOver(char theToken){
        if(horizontal(theToken)){
            return true;
        }
        else if(vertical(theToken)){
            return true;
        }
        else if(upDiag(theToken)){
            return true;
        }
        else if(downDiag(theToken)){
            return true;
        }
        return false;
    }
    
    public boolean vertical(char theToken){
        for(int i = 0; i<this.rows-4; i++){
            for(int j = 0; j<this.cols; j++){
                if(getCell(i,j)==theToken){
                    if(getCell(i+1,j)==theToken && getCell(i+2,j)==theToken && getCell(i+3,j)==theToken && getCell(i+4,j)==theToken){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean horizontal(char theToken){
        for(int i = 0; i<this.rows; i++){
            for(int j = 0; j<this.cols-4; j++){
                if(getCell(i,j)==theToken){
                    if(getCell(i,j+1)==theToken && getCell(i,j+2)==theToken && getCell(i,j+3)==theToken && getCell(i,j+4)==theToken){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean upDiag(char theToken){
        for(int i = 0; i<this.rows-4; i++){
            for(int j = 0; j<this.cols-4; j++){
                if(getCell(i,j)==theToken){
                    if(getCell(i+1,j+1)==theToken && getCell(i+2,j+2)==theToken && getCell(i+3,j+3)==theToken && getCell(i+4,j+4)==theToken){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean downDiag(char theToken){
        for(int i = 4; i<this.rows; i++){
            for(int j = 0; j<this.cols-4; j++){
                if(getCell(i,j)==theToken){
                    if(getCell(i-1,j+1)==theToken && getCell(i-2,j+2)==theToken && getCell(i-3,j+3)==theToken && getCell(i-4,j+4)==theToken){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void addToCount(){
        this.count++;
    }
    
    public void subtractFromCount(){
        this.count--;
    }
    
    public void resetCount(){
        this.count = 0;
    }
    
    public int getCount(){
        return this.count;
    }
    
    public void setCell(int row, int col, char token){
        this.grid[row][col] = token;
    }
    
    public char getCell(int row, int col){
        return this.grid[row][col];
    }
    
    public void setNextMove(int row, int col, char token){
        StringBuilder sb = new StringBuilder();
        sb.append(nm + " " + row + " " + col + " " + token);
        this.nextMove = sb.toString();
    }
    
    public String getNextMove(){
        return this.nextMove;
    }
    
    public int getRows(){
        return this.rows;
    }
    
    public int getCols(){
        return this.cols;
    }
    
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
    
    public boolean isMoveValid(int row, int col){
        if(getCell(row, col)=='-'){
            return true;
        }
        else
            return false;
    }

}
