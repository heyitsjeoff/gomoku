/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

import java.util.Random;

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
	
	public static final int DIFFICULTY_EASY = 0;
	public static final int DIFFICULTY_MEDIUM = 1;
	public static final int DIFFICULTY_HARD = 2;
	
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
	private Random gen;
	private int width;
	private int height;
	private int difficulty;
	
	private char[][] board;
	private Move[][] moves;
	
	//constructor
	public AI(int boardWidth, int boardHeight, int difficulty){
		this.width = boardWidth;
		this.height = boardHeight;
		this.difficulty = difficulty;
		gen = new Random();
	}
	
	//public methods
	public int getDifficulty(){
		return difficulty;
	}
	
	public void setDifficulty(int difficulty){
		this.difficulty = difficulty;
	}
	
	public int[] makeMove(char[][] board){
		//todo
		return null;
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
	
	private class Row{
		Cell[] cells;
		int direction;
		boolean valid;
		
	}
	
	private class Move implements Comparable<Move>{
		Cell location;
		int weight;
		public Move(int r, int c){
			location = new Cell(r,c,board[r][c]);
			weight = 0;
		}
		
		@Override
		public int compareTo(Move other){
			return this.weight - other.weight;
		}
	}
	
	//helper methods
	private Cell getNextCell(Cell cur, int dir, char[][] board){
		int r = cur.row;
		int c = cur.col;
		Cell cel = null;
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
}
