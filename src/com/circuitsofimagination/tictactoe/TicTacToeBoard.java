package com.circuitsofimagination.tictactoe;

import java.util.*;

public class TicTacToeBoard {
	
	public enum Cell {
		EMPTY, X, O
	}
	
	public static class Location {
		public int x;
		public int y;
		
		public Location(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public String toString() {
			return "("+x+", "+y+")";
		}
	}
	
	public static final int WIDTH = 3;
	public static final int HEIGHT=3;
	private static final int NUM_CELLS=WIDTH*HEIGHT;
	
	private Cell[] board;
	
	public TicTacToeBoard() {
		board = new Cell[NUM_CELLS];
		for(int i=0; i<NUM_CELLS; i++) {
			board[i] = Cell.EMPTY;
		}
	}
	
	/*
	 * Private constructor used for copying (aka. cloning) boards
	 */
	private TicTacToeBoard(Cell[] cells){
		board = new Cell[NUM_CELLS];
		for(int i=0; i<NUM_CELLS; i++) {
			board[i] = cells[i];
		}
	}
	
	/*
	 * Helper function to flip the players
	 */
	public static Cell oppositePlayer(Cell team) {
		if(team == Cell.EMPTY){
			return Cell.EMPTY;
		} else if(team == Cell.X){
			return Cell.O;
		} else {
			return Cell.X;
		}
	}
	
	/*
	 * Helper function for creating location instances
	 */
	public static Location newLocation(int x, int y) {
		return new Location(x, y);
	}

	/*
	 * Find the correct index in the board array for a given
	 * x and y 
	 */
	private int indexFor(Location l) {
		return l.x+(l.y*WIDTH);
	}
	
	/*
	 * Make a clone of the board. Used by AI
	 */
	public TicTacToeBoard copy() {
		return new TicTacToeBoard(board);
	}
	
	/*
	 * Apply a move by a team to the board
	 */
	public void makeMove(Cell c, Location l) throws TicTacToeException {
		try {
			if(cellAt(l) != Cell.EMPTY) {
				throw new TicTacToeException("Cell is already take");
			} else {
				board[indexFor(l)] = c;
			}
		}catch(IndexOutOfBoundsException exc){
			throw new TicTacToeException("Invalid cell");
		}
	}
	
	/*
	 * Returns the value of a given cell
	 */
	public Cell cellAt(Location l) {
		return board[indexFor(l)];
	}
	
	/*
	 * Check if there are any winners
	 * return values:
	 * 	X = Player X has won
	 * 	O = Player O has won
	 *  EMPTY = Game is a Draw
	 *  null = Neither player has won yet
	 * 
	 */
	public Cell winner() {
		//TODO: Get rid of magic numbers
		int[][] winning_indexes = new int[][]{
				// Horizontal lines
				{0, 1, 2},
				{3, 4, 5},
				{6, 7, 8},
				
				// Vertical lines
				{0, 3, 6},
				{1, 4, 7},
				{2, 5, 8},
				
				// Diagonals
				{0, 4, 8},
				{2, 4, 6}
		};
		
		// Return the winner if there is one
		for(int i=0; i<winning_indexes.length; i++){
			if(board[winning_indexes[i][0]] != Cell.EMPTY
					&& (board[winning_indexes[i][0]] == board[winning_indexes[i][1]]) 
					&& (board[winning_indexes[i][1]] == board[winning_indexes[i][2]])){
				return board[winning_indexes[i][0]];
			}
		}
		
		// If there is no winner and the board in full than the game is a draw
		// TODO: We can check if it is impossible for any one to win
		// But that would be too much work for now
		if(boardFull()) {
			return Cell.EMPTY;
		}
		
		// Otherwise there is no winner and the game can go on
		return null;
	}
	
	/*
	 * Check to see if the board has been filled
	 */
	
	public boolean boardFull() {
		for(int i=0; i<NUM_CELLS; i++) {
			if(board[i] == Cell.EMPTY) {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * Return the coordinates for empty slots
	 */
	public List<Location> emptySlots() {
		ArrayList<Location> slots = new ArrayList<Location>();
		for(int y=0; y<HEIGHT; y++) {
			for(int x=0; x<WIDTH; x++) {
				Location l = new Location(x, y);
				Cell c = cellAt(l);
				if(c == Cell.EMPTY) {
					slots.add(l);
				}
			}
		}
		return slots;
	}
}
