/*
 * Source: Modified code of Jeoff Villanueva's submission for Lab 1 of CSCE 270 in Spring 2014
http://stackoverflow.com/questions/6835682/how-to-disable-gui-button-in-java
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


public class GameView extends JFrame{
    private JPanel panelCenter = new JPanel();
    private int row, col;
    private boolean myMove = false;
    private MyJButton[][] square;
    private JTextArea displayArea;
    private int count;
    private JButton send;
    private GameModel theModel;
    public static final char MYTOKEN = '*';
    public static final char THEIRTOKEN = '#';

    public GameView(GameModel theModel){
        this.theModel = theModel;
	this.row = theModel.getRows();
	this.col = theModel.getCols();
		
	this.setLayout(new BorderLayout());
		
	//set up north layout
	send = new JButton("Send Move");
	send.addActionListener(new ButtonListener());
	this.add(send,BorderLayout.NORTH);
		
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
	displayArea = new JTextArea( 8, 15 ); // set up JTextArea
	displayArea.setEditable( false );
	this.add( new JScrollPane( displayArea ), BorderLayout.SOUTH );

	//setup for display
	setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	setSize(400,500);
	setVisible(true);
	//this.pack();
		
	}
    
    public void setMyMove(boolean value){
        this.myMove = value;
    }
    
    public void enableBTN(){
        send.setEnabled(true);
        this.myMove = true;
    }
    
    public void disableBTN(){
        send.setEnabled(false);
        this.myMove = false;
    }
    
    public void updateCellView(int i, int j){
        if(theModel.getCell(i, j)==GameModel.MYTOKEN){
            square[i][j].setBackground(Color.blue);
            square[i][j].setEnabled(false);
        }
        if(theModel.getCell(i, j)==GameModel.THEIRTOKEN){
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
        System.out.println("updateGridView called and myMove=" + this.myMove);
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
    
    public void sendMoveListener(ActionListener listenerForSendBTN){
        send.addActionListener(listenerForSendBTN);
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
