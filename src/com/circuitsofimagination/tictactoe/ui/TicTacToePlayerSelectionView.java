package com.circuitsofimagination.tictactoe.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Container;
import java.awt.Dialog;

import com.circuitsofimagination.tictactoe.*;

public class TicTacToePlayerSelectionView extends JDialog implements ActionListener {
	
	private JComboBox<String> xPlayerCombo;
	private JComboBox<String> oPlayerCombo;
	private JButton playButton;
	private JButton quitButton;
	
	public TicTacToePlayerSelectionView(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
	}

	public void execute() {
		addComponents();
		setVisible(true);
	}
	
	private void addComponents() {
		
		Container contentPane = this.getContentPane();
		xPlayerCombo = new JComboBox<String>(TicTacToePlayerFactory.PLAYER_NAMES);
		oPlayerCombo = new JComboBox<String>(TicTacToePlayerFactory.PLAYER_NAMES);
	
		playButton = new JButton("Play!");
		playButton.addActionListener(this);
		
		quitButton = new JButton("Quit :-(");
		quitButton.addActionListener(this);
	
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel xPlayerPane = new JPanel();
		xPlayerPane.setLayout(new BoxLayout(xPlayerPane, BoxLayout.X_AXIS));
		xPlayerPane.add(new JLabel("Player X:"));
		xPlayerPane.add(xPlayerCombo);
		contentPane.add(xPlayerPane);
		contentPane.add(Box.createVerticalGlue());
		
		JPanel oPlayerPane = new JPanel();
		oPlayerPane.setLayout(new BoxLayout(oPlayerPane, BoxLayout.X_AXIS));
		oPlayerPane.add(new JLabel("Player O:"));
		oPlayerPane.add(oPlayerCombo);
		contentPane.add(oPlayerPane);
		contentPane.add(Box.createVerticalGlue());
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
		buttonPane.add(playButton);
		buttonPane.add(quitButton);
		contentPane.add(buttonPane);
		
		setSize(300, 200);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == playButton) {
			this.setVisible(false);

			String xPlayerName = (String)xPlayerCombo.getSelectedItem();
			String oPlayerName = (String)oPlayerCombo.getSelectedItem();

			TicTacToeGame.startGame(xPlayerName, oPlayerName);
		}
		
		if(evt.getSource() == quitButton) {
			this.setVisible(false);
		}
	}
}
