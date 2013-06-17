package com.circuitsofimagination.tictactoe;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.circuitsofimagination.tictactoe.TicTacToeBoard.*;

import java.util.*;
import java.util.concurrent.*;

public class TicTacToeHumanPlayer implements TicTacToePlayer {

	TicTacToeGame game;
	Cell team;
	MoveMouseListener moveListener = new MoveMouseListener();
	LinkedList<Point> clickBuffer;
	
	Semaphore bufferLock;
	Semaphore emptyLock;
	
	@Override
	public void initialize(Cell team, TicTacToeGame game) {
		this.game = game;
		this.team = team;
	}

	@Override
	public Location makeMove() throws TicTacToeException { 
		// makeMove is called by the game thread
		// But the event listener is called from Swing's
		// event dispatch thread
		// so we have to do a little cross-thread syncronization
		clickBuffer = new LinkedList<Point>(); //Clear any previous
		bufferLock = new Semaphore(1); // The buffer is ready for use
		emptyLock = new Semaphore(0); // And there are 0 items in the buffer
		
		// Add the listener when we start 
		// waiting for the human to make a move
		game.boardView.addMouseListener(moveListener);
		Location move = null;
		
		// Keep trying until the user click on a valid location
		while((move == null) || (game.board.cellAt(move) != Cell.EMPTY)) {
			move = getMove();
		}
		
		// And remove the listener once were found a valid move
		game.boardView.removeMouseListener(moveListener);
		return move;
	}
	
	private Location getMove() {
		Location l = null;
		try {
			// It is necessary to acquire the emptyLock
			// before the bufferLock so we don't end up with a 
			// deadlock
			emptyLock.acquire();
			bufferLock.acquire();
			Point thePoint = clickBuffer.poll();
			if(thePoint != null) {
				l = game.boardView.translatePoint(thePoint);
			}
		} catch (InterruptedException exc) {
			exc.printStackTrace();
		} finally {
			bufferLock.release();
		}
		return l;
	}
	
	private class MoveMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent evt) {
			Point clickPoint = evt.getPoint();
			try {
				bufferLock.acquire();
				clickBuffer.add(clickPoint);
				emptyLock.release();
			} catch (InterruptedException exc) {
				exc.printStackTrace();
			} finally {
				bufferLock.release();
			}
		}
	}

}
