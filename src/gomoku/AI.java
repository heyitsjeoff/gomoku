/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author John
 */
public class AI {
	//constants
	private static final int DIRECTION_UP_RIGHT = 0;
	private static final int DIRECTION_RIGHT = 1;
	private static final int DIRECTION_DOWN_RIGHT = 2;
	private static final int DIRECTION_DOWN = 3;
	private static final int[] DIRECTIONS = {
		DIRECTION_UP_RIGHT,
		DIRECTION_RIGHT,
		DIRECTION_DOWN_RIGHT,
		DIRECTION_DOWN
	};
	
	public static final double DIFFICULTY_EASY = 0.3;
	public static final double DIFFICULTY_MEDIUM = 1;
	public static final double DIFFICULTY_HARD = 2;
	
	private static final int WEIGHT_OWN_4 = 100;
	private static final int WEIGHT_OPPONENT_4 = 70;
	private static final int WEIGHT_OWN_3 = 30;
	private static final int WEIGHT_OPPONENT_3 = 25;
	private static final int WEIGHT_OWN_2 = 10;
	private static final int WEIGHT_OPPONENT_2 = 7;
	private static final int WEIGHT_OWN_1 = 3;
	private static final int WEIGHT_OPPONENT_1 = 3;
	private static final int WEIGHT_OPEN = 1;
	private static final int WEIGHT_DISPUTED = 0;
	
	//instance variables
	private int width;
	private int height;
	private double difficulty;
	
	private char[][] board;
	private Move[][] moves;
	
	//constructor
	public AI(int boardWidth, int boardHeight, double difficulty){
		this.width = boardWidth;
		this.height = boardHeight;
		this.difficulty = difficulty;
	}
	
	//public methods
	public double getDifficulty(){
		return difficulty;
	}
	
	public void setDifficulty(double difficulty){
		this.difficulty = difficulty;
	}
	
	public int[] makeMove(char[][] gameBoard){
		board = gameBoard;
		
		//populate moves array
		moves = new Move[height][width];
		for(int r = 0; r < height; r++){
			for(int c = 0; c < width; c++){
				moves[r][c] = new Move(r,c);
			}
		}
		
		//analyze rows
		for(int r = 0; r < height; r++){
			for(int c = 0; c < width; c++){
				for(int d : DIRECTIONS){
					analyzeRow(r,c,d);
				}
			}
		}
		
		//export to 1-D arraylist for sorting
		ArrayList<Move> rankedMoves = new ArrayList<Move>();
		for(Move[] row : moves){
			for(Move m : row){
				if(m.location.value == ' '){
					rankedMoves.add(m);
				}
			}
		}
		
		//apply difficulty adjustment
		//Idea comes from conversation with Jim Lyon
		rankedMoves.sort(null);
		double totalWeight = 0;
		for(Move m : rankedMoves){
			m.weight = Math.pow(m.weight, difficulty);
			totalWeight += m.weight;
		}
		
		double moveChoice = Math.random() * totalWeight;
		Move chosenMove = rankedMoves.get(0);
		for(Move m : rankedMoves){
			moveChoice -= m.weight;
			if(moveChoice <= 0.0){
				chosenMove = m;
				break;
			}
		}
		
		int[] toReturn = new int[2];
		toReturn[0] = chosenMove.location.row;
		toReturn[1] = chosenMove.location.col;
		
		return toReturn;
	}
	
	//helper classes
	private class Cell{
		int row;
		int col;
		char value;
		public Cell(int r, int c, char v){
			row = r;
			col = c;
			value = v;
		}
	}
	
	private class Move implements Comparable<Move>{
		Cell location;
		double weight;
		public Move(int r, int c){
			location = new Cell(r,c,board[r][c]);
			weight = 0;
		}
		
		@Override
		public int compareTo(Move other){
			if(this.weight > other.weight)
				return 1;
			else if(this.weight < other.weight)
				return -1;
			else return 0;
		}
	}
	
	//helper methods
	private Cell getNextCell(Cell cur, int dir){
		int r = cur.row;
		int c = cur.col;
		Cell cel;
		try{
			switch(dir){
				case DIRECTION_UP_RIGHT:
					r--;
					c++;
					break;
				case DIRECTION_RIGHT:
					c++;
					break;
				case DIRECTION_DOWN_RIGHT:
					r++;
					c++;
					break;
				case DIRECTION_DOWN:
					r++;
					break;
				default:
					return null;
			}
			cel = new Cell(r,c,board[r][c]);
		} catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
		return cel;
	}

public void analyzeRow(int row, int col, int dir){
		Cell[] cells = new Cell[5];
		int myCells = 0;
		int opponentCells = 0;
		int weight = 0;
		cells[1] = new Cell(row,col,board[row][col]);
		for(int i = 1; i < 5; i++){
			cells[i] = getNextCell(cells[i-1],dir);
			if(cells[i] == null){
				return;
			}
		}
		
		for(Cell c : cells){
			if(c.value == '#')
				myCells++;
			else if (c.value == '*')
				opponentCells++;
		}
		
		if(myCells == 0){
			switch(opponentCells){
				case 4:
					weight = WEIGHT_OPPONENT_4;
					break;
				case 3:
					weight = WEIGHT_OPPONENT_3;
					break;
				case 2:
					weight = WEIGHT_OPPONENT_2;
					break;
				case 1:
					weight = WEIGHT_OPPONENT_1;
					break;
				case 0:
					weight = WEIGHT_OPEN;
					break;
			}
		} else if(opponentCells == 0){
			switch(myCells){
				case 4:
					weight = WEIGHT_OWN_4;
					break;
				case 3:
					weight = WEIGHT_OWN_3;
					break;
				case 2:
					weight = WEIGHT_OWN_2;
					break;
				case 1:
					weight = WEIGHT_OWN_1;
					break;
			}
		} else {
			weight = WEIGHT_DISPUTED;
		}
		
		for(Cell c : cells){
			moves[c.row][c.col].weight += weight;
		}
	}
}