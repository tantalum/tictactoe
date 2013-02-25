package com.circuitsofimagination.tictactoe;

public class TicTacToePlayerFactory {
	public static final String AI_PLAYER_NAME = "Positronic Brain";
	public static final String HUMAN_PLAYER_NAME = "Human Brain";
	
	public static final String[] PLAYER_NAMES = {
		AI_PLAYER_NAME,
		HUMAN_PLAYER_NAME
	};
	
	public static TicTacToePlayer createPlayer(String name) throws TicTacToeException {
		TicTacToePlayer thePlayer = null;
		switch(name) {
		case AI_PLAYER_NAME:
			thePlayer = new TicTacToeAIPlayer();
			break;
		case HUMAN_PLAYER_NAME:
			thePlayer = new TicTacToeHumanPlayer();
			break;
		default:
			throw new TicTacToeException("Invalid player type");
		}
		return thePlayer;
	}

}
