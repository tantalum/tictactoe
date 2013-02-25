package com.circuitsofimagination.tictactoe;

public interface TicTacToePlayer {
	public void initialize(TicTacToeBoard.Cell team, TicTacToeGame game);
	public TicTacToeBoard.Location makeMove() throws TicTacToeException;
}
