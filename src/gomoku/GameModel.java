package gomoku;

public class GameModel {
    
    private String playerHost;
    private String playerClient;
    
    private char[][] grid;
    private int rows;
    private int cols;
    private String nextMove;
    private int count = 0;
    private int tokenCount = 0;
    private int maxCount;
    
    /**
     * Constructs the game model, which will be a 2d array of Cells
     * @param rows number of rows in the array
     * @param cols number of cols in the array
     */
    public GameModel(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[this.rows][this.cols];
        for(int i = 0; i<this.rows; i++){
            for(int j = 0; j<this.cols; j++){
                grid[i][j]='-';
            }
        }
        maxCount = rows*cols;
    }
    
    /**
     * creates a copy of a gameModel
     * @param other
     */
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
    
    /**
     * gets the grid of chars
     * @return grid
     */
    public char[][] getGrid(){
        return this.grid;
    }
    
    /**
     * checks to see if the game is over
     * @param theToken char being checked on
     * @return true if that token won, false otherwise
     */
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
    
    /**
     * adds to the tokenCount
     */
    public void addToTokenCount(){
        tokenCount++;
        System.out.println(tokenCount);
    }
    
    /**
     * subtracts from the token count
     */
    public void subtractFromTokenCount(){
        tokenCount--;
    }
    
    /**
     * checks to see if the board is full
     * @return true if the board is full, else false
     */
    public boolean boardFull(){
        if(tokenCount==maxCount){
            return true;
        }
        return false;
    }
    
    /**
     * checks a column to see if there is a win there
     * @param theToken token being checked on
     * @return true if a win, false otherwise
     */
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
    
    /**
     * checks a row to see if there is a win there
     * @param theToken token being checked on
     * @return true if a win, false otherwise
     */
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
    
    /**
     * checks to see if there is a win in the upwards diagnol
     * @param theToken token being checked on
     * @return true if a win, false otherwise
     */
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
    
    /**
     * checks to see if there is a win in the downwards diagnol
     * @param theToken token being checked on
     * @return true if a win, false otherwise
     */
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
    
    /**
     * increments count
     */
    public void addToCount(){
        this.count++;
    }
    
    /**
     * decrements count
     */
    public void subtractFromCount(){
        this.count--;
    }
    
    /**
     * sets count to 0
     */
    public void resetCount(){
        this.count = 0;
    }
    
    /**
     * gets the count
     * @return count
     */
    public int getCount(){
        return this.count;
    }
    
    /**
     * sets the cell of grid to a token
     * @param row row of the cell
     * @param col col of the cell
     * @param token char being set for the token
     */
    public void setCell(int row, int col, char token){
        this.grid[row][col] = token;
    }
    
    /**
     * gets the cell of the grid at that location
     * @param row row location
     * @param col col location
     * @return
     */
    public char getCell(int row, int col){
        return this.grid[row][col];
    }
    
    /**
     * sets the next move as a string
     * @param row row of the next move
     * @param col col of the next move
     * @param token char for the token
     */
    public void setNextMove(int row, int col, char token){
        StringBuilder sb = new StringBuilder();
        sb.append(GomokuVariables.nm + " " + row + " " + col + " " + token);
        this.nextMove = sb.toString();
    }
    
    /**
     * returns the string representation of the next move
     * @return
     */
    public String getNextMove(){
        return this.nextMove;
    }
    
    /**
     * toString for a move
     * @return
     */
    public String myMoveToString(){
        String[] split = this.nextMove.split("\\s+");
        return GomokuVariables.IMADEAMOVEAT + split[1] + GomokuVariables.ANDCOL + split[2]; 
    }
    
    /**
     * gets the amount of rows
     * @return rows
     */
    public int getRows(){
        return this.rows;
    }
    
    /**
     * gets the amount of cols
     * @return cols
     */
    public int getCols(){
        return this.cols;
    }
    
    /**
     * sets the player host name
     * @param name name of the host
     */
    public void setPlayerHostName(String name){
        this.playerHost = name;
    }
    
    /**
     * sets the player client name
     * @param name name of the client
     */
    public void setPlayerClientName(String name){
        this.playerClient = name;
    }

    /**
     * returns the host name
     * @return client name
     */
    public String getPlayerHostName() {
        return this.playerHost;
    }
    
    /**
     * returns the client name
     * @return client name
     */
    public String getPlayerClientName(){
        return this.playerClient;
    }
    
    /**
     * checks to see if a move is valid
     * @param row row of the move
     * @param col col of the move
     * @return true if the move is valid, false otherwise
     */
    public boolean isMoveValid(int row, int col){
        if(getCell(row, col)=='-'){
            return true;
        }
        else
            return false;
    }

}
