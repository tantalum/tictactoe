package com.circuitsofimagination.tictactoe;

import javax.swing.JOptionPane;

import com.circuitsofimagination.tictactoe.ui.*;
import com.circuitsofimagination.tictactoe.TicTacToeBoard.*;

public class TicTacToeGame implements Runnable {
	public TicTacToeGameView gameView;
	public TicTacToeBoardView boardView;
	
	public TicTacToeBoard board;
	
	public TicTacToePlayer xPlayer;
	public TicTacToePlayer oPlayer;
	
	public TicTacToeGame(String xPlayerName, String oPlayerName) {
		try {
			this.xPlayer = TicTacToePlayerFactory.createPlayer(xPlayerName);
			xPlayer.initialize(Cell.X, this);
			this.oPlayer = TicTacToePlayerFactory.createPlayer(oPlayerName);
			oPlayer.initialize(Cell.O,  this);
		} catch (TicTacToeException exc) {
			//This shouldn't happen since were using a combo box
		}
		board = new TicTacToeBoard();
		boardView = new TicTacToeBoardView(board);
		gameView = new TicTacToeGameView(boardView);		
	}
	
	@Override
	public void run() {
		boolean playing = true;
		Location move;

		while(playing) {
			try {
				move = xPlayer.makeMove();
				board.makeMove(Cell.X, move);
				boardView.repaint();
				if(board.winner() != null) {
					playing = false;
					break;
				}
				
				
				move = oPlayer.makeMove();
				board.makeMove(Cell.O, move);
				boardView.repaint();
				if(board.winner() != null) {
					playing = false;
				}
			} catch(TicTacToeException exc) {
				JOptionPane.showMessageDialog(gameView, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		String message = null;
		Cell winner  = board.winner();
		if (winner ==  Cell.X) {
			message = "X wins!!";
		} else if(winner ==  Cell.O) {
			message = "O wins!!";
		}else {
			message = "Game is a draw";
		}
		
		JOptionPane.showMessageDialog(gameView, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void startGame(String xPlayerName, String oPlayerName) {
		TicTacToeGame theGame = new TicTacToeGame(xPlayerName, oPlayerName);
		// Run the game in a seperate thread so that blocking calls
		// by the game (e.g. makeMove) won't block the GUI events
		Thread gameThread = new Thread(theGame);
		gameThread.setDaemon(true);
		gameThread.start();
	}
	
	public static void main(String [] args) {
		TicTacToePlayerSelectionView selector = new TicTacToePlayerSelectionView(null, "Select Players", true);
		selector.execute();
	}
	
}
