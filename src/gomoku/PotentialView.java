/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author jeffthechef14
 */
public class PotentialView extends JFrame{
    
    //GUI components of the PandemicView
	//The BorderLayout is used with the Center panel
	//containing the "grid" of people. 
	//North panel contains the "step" button
	//South panel is a JTextArea for instructions and results
	private JPanel panelCenter = new JPanel();
	private int row, col;
	private MyJButton[][] square;
	private JTextArea displayArea;
	private int count;
	private final int MAX_COUNT=4;
	private JButton step;
	
	/**
	 * The constructor is called by PandemicController and is passed initial
	 * state of the program using parameter cur
	 * @param cur  PandemicModel with the current view 
	 * @param inoc PandemicModel that stores the inoculations for
	 *             the current round (4 GREEN cells)
	 * @param con  handle to the controller
	 */
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
	

	

	

	/**
	 * The actionPerformed method is called each time a button
	 * in the center panel is selected. It allows the user to 
	 * select MAX_COUNT	WHITE cells to inoculate (set to GREEN)
	 * 
	 */
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
	
	/**
	 * The actionPerformed method is called each time the step
	 * button in the north panel is selected. The user must have
	 * select MAX_COUNT	WHITE cells to inoculate (set to GREEN)
	 *
	 */
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
