/*
 * Source: Modified code of Jeoff Villanueva's submission for Lab 1 of CSCE 270 in Spring 2014
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


public class PotentialView extends JFrame{
    

	private JPanel panelCenter = new JPanel();
	private int row, col;
	private MyJButton[][] square;
	private JTextArea displayArea;
	private int count;
	private final int MAX_COUNT=4;
	private JButton step;

	public PotentialView(){
		row = 30;
		col = 30;
		
		this.setLayout(new BorderLayout());
		
		//set up north layout
		step = new JButton("Send Move");
		step.addActionListener(new ButtonListener());
		this.add(step,BorderLayout.NORTH);
		
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
		displayArea = new JTextArea( 8, 30 ); // set up JTextArea
	    displayArea.setEditable( false );
	    //this.add( new JScrollPane( displayArea ), BorderLayout.SOUTH );
		
	    
		//setup for display
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setSize(400,500);
		setVisible(true);
		//this.pack();
		
	}
	
	private class SquareListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
                    MyJButton button = (MyJButton) e.getSource();
			//System.out.println("this is button:"+button.i + " " +button.j);
			if(count<1 && button.getBackground().equals(Color.white)) {
					button.setBackground(Color.green);
                                        count++;
			}
                        else if(button.getBackground().equals(Color.green)){
                            button.setBackground(Color.white);
                            count--;
                        }			 		
		}
		
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
