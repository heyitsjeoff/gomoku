package gomoku;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class OfflineController {
    
    private OfflineDifficultyView theDiffView;
    private AI theAI;
    private GameView2 theView;
    private GameModel theModel;
    private AuthController theAuthController;
    private int row = GomokuVariables.row;
    private int col = GomokuVariables.col;
    
    /**
     * creates the controller for offline
     * @param theDiffView difficulty view
     * @param theModel game model
     */
    public OfflineController(OfflineDifficultyView theDiffView, GameModel theModel){
        this.theDiffView = theDiffView;
        this.theModel = theModel;
        this.theDiffView.startListener(new StartListener());
    }
    
    /**
     * ActionListener for the start button
     */
    class StartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            theModel = new GameModel(row, col);
            theAI = new AI(row, col, theDiffView.getDiff());
            theDiffView.dispose();
            theView = new GameView2();
            CellListener theCellListener = new CellListener();
            theView.drawBoard(row, col, theCellListener);
            theView.setVisible(true);
            theView.sendMoveListener(new MoveListener());
        }
    }
    
    /**
     * win popup and returns to the main view
     */
    public void win(){
        JOptionPane.showMessageDialog(null, GomokuVariables.oGameOverWin);
        returnToMainView();
    }
    
    /**
     * lose popup and returns to the main view
     */
    public void lose(){
        JOptionPane.showMessageDialog(null, GomokuVariables.oGameOverLose);
        returnToMainView();
    }
    
    /**
     * tie popup and returns to the main view
     */
    public void tie(){
        JOptionPane.showMessageDialog(null, GomokuVariables.oGameOverTie);
        returnToMainView();
    }
    
    /**
     * returns to the main view gui
     */
    public void returnToMainView(){
        MainView theMainView = new MainView();
        AuthController theAuthController = new AuthController(theMainView);
        this.theView.dispose();
        theMainView.setVisible(true);
    }
    
    /**
     * disables the move made by the user
     * @param move locatiton of the cell
     */
    public void disableMyMoveCell(String move){
        String[] split = move.split("\\s+");
        int row = Integer.parseInt(split[1]);
        int col = Integer.parseInt(split[2]);
        theView.disableCell(row, col);
    }
    
    /**
     * ActionListener for the cell
     * calls the AI to make a move
     */
    class MoveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(theModel.gameOver(GomokuVariables.MYTOKEN)){
                win();
            }
            else if(theModel.boardFull()){
                tie();
            }
            else{
                if(theModel.getCount()==0){
                    theView.append(GomokuVariables.makeMove);
                }
                else{
                    disableMyMoveCell(theModel.getNextMove());
                    int[] theMoveCoordinates = theAI.makeMove(theModel.getGrid());
                    int row = theMoveCoordinates[0];
                    int col = theMoveCoordinates[1];
                    theModel.setCell(row, col, GomokuVariables.THEIRTOKEN);
                    theView.updateCell(row, col, GomokuVariables.enemyColor);
                    theModel.addToTokenCount();
                    if(theModel.gameOver(GomokuVariables.THEIRTOKEN)){
                        lose();
                    }
                    else if(theModel.boardFull()){
                        tie();
                    }
                    else
                        theModel.resetCount();
                }
            }
        }
    }
    
    /**
     * ActionListener for the cells
     */
    class CellListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Cell cell  = (Cell) e.getSource();
            if(theModel.getCount()==0){
                cell.click();
                theModel.setCell(cell.getRow(), cell.getCol(), GomokuVariables.MYTOKEN);
                theModel.setNextMove(cell.getRow(), cell.getCol(), GomokuVariables.MYTOKEN);
                theModel.addToCount();
                theModel.addToTokenCount();
            }
            else if(theModel.getCount()==1 && cell.getBackground().equals((Color.blue))){
                cell.click();
                theModel.setCell(cell.getRow(), cell.getCol(), ' ');
                theModel.subtractFromCount();
                theModel.subtractFromTokenCount();
            }
        }        
    }
    
}
