/*
 * old view
 */

package gomoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author PLUCSCE
 */
public class OfflineGameView extends JFrame{
    private JPanel panelCenter = new JPanel();
    private int row, col;
    private boolean myMove = true;
    private MyJButton[][] square;
    private JTextArea displayArea;
    private int count;
    private JButton move;
    private GameModel theModel;
    public static final char MYTOKEN = '*';
    public static final char THEIRTOKEN = '#';

    public OfflineGameView(GameModel theModel){
        this.theModel = theModel;
	this.row = theModel.getRows();
	this.col = theModel.getCols();
		
	this.setLayout(new BorderLayout());
		
	//set up north layout
	move = new JButton("Make Move");
	move.addActionListener(new ButtonListener());
	this.add(move,BorderLayout.NORTH);
		
	//set up grid in for the center panel
	panelCenter.setLayout(new GridLayout(row,col));
	square = new MyJButton[row][col];
	SquareListener listener = new SquareListener();
	for(int i =0; i<row; i++) {
            for (int j=0; j<col; j++){
		square[i][j]= new MyJButton(); 
		square[i][j].i=i;
		square[i][j].j=j;
		//square[i][j].setSize(20,20);
		square[i][j].setBackground(Color.white);
		square[i][j].addActionListener(listener);
		panelCenter.add(square[i][j]);
            }
	}
	this.add(panelCenter,BorderLayout.CENTER);
		
	//set up text area in the south
	//displayArea = new JTextArea( 8, 15 ); // set up JTextArea
	//displayArea.setEditable( false );
	//this.add( new JScrollPane( displayArea ), BorderLayout.SOUTH );

	//setup for display
	setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	setSize(400,500);
	setVisible(true);
	//this.pack();
		
	}
    
    public void setMyMove(boolean value){
        this.myMove = value;
    }
    
    public void updateCellView(int i, int j){
        if(theModel.getCell(i, j)==GomokuVariables.MYTOKEN){
            square[i][j].setBackground(Color.blue);
            square[i][j].setEnabled(false);
        }
        if(theModel.getCell(i, j)==GomokuVariables.THEIRTOKEN){
            square[i][j].setBackground(Color.red);
            square[i][j].setEnabled(false);
        }
    }
        
    public void updateGridView(){
        for(int i = 0; i<this.row; i++){
            for(int j = 0; j<this.col; j++){
                updateCellView(i,j);
            }
        }
        count = 0;
        this.theModel.resetCount();
        this.myMove = true;
    }
    
    public void appendMessage(String message){
        displayArea.append(message+"\n");
    }
	
    private class SquareListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            MyJButton button = (MyJButton) e.getSource();
            if(theModel.getCount()==0 && myMove == true) {
		button.setBackground(Color.blue);
                theModel.setCell(button.i, button.j, MYTOKEN);
                theModel.setNextMove(button.i, button.j, MYTOKEN);
                theModel.addToCount();
            }
            else if(theModel.getCount()==1 && button.getBackground().equals((Color.blue)) && myMove == true){
                button.setBackground(Color.white);
                theModel.setCell(button.i, button.j, ' ');
                theModel.subtractFromCount();
            }			 		
        }
    }
    
    public void moveListener(ActionListener listenerForSendBTN){
        move.addActionListener(listenerForSendBTN);
    }
	
    private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            
        }	
    }
	
	/**
	 * This is a private helper class the extends
	 * JButton to include its (i,j) location in the 
	 * center panel
	 *
	 */
	private static class MyJButton extends JButton{
		private int i;
		private int j;	
	}
}
