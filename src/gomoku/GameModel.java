package gomoku;


public class GameModel {
    
    private String playerHost;
    private String playerClient;
    
    private char[][] grid;
    private int rows;
    private int cols;
    
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
    
    public void setCell(int row, int col, char token){
        this.grid[row][col] = token;
    }
    
    public char getCell(int row, int col){
        return this.grid[row][col];
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

}
