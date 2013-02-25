package com.circuitsofimagination.tictactoe.ui;

import javax.swing.JFrame;

public class TicTacToeGameView extends JFrame {
	private TicTacToeBoardView board;
	
	public TicTacToeGameView(TicTacToeBoardView b) {
		board = b;
		add(board);
		setTitle("Tic Tac Toe");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300, 280);
		setLocationRelativeTo(null);
		setVisible(true); 
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
