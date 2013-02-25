package com.circuitsofimagination.tictactoe.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import com.circuitsofimagination.tictactoe.*;
import com.circuitsofimagination.tictactoe.TicTacToeBoard.Cell;
import com.circuitsofimagination.tictactoe.TicTacToeBoard.Location;

public class TicTacToeBoardView extends JPanel {
	private TicTacToeBoard theBoard;

	/*
	 * Variables used for drawing the board
	 * and translating between locations on the screen
	 * to cells on the board
	 */
	private double padding_height;
	private double padding_width;
	
	private double board_width;
	private double board_height;
	
	private double cell_width;
	private double cell_height;
	
	private double cell_padding_width;
	private double cell_padding_height;
	
	public TicTacToeBoardView(TicTacToeBoard board) {
		super();
		theBoard = board;
		
		setBackground(Color.white);
		setDoubleBuffered(true);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(rh);
		
		Dimension size = getSize();
		double width = size.getWidth();
		double height = size.getHeight();
		
		g2.setBackground(Color.white);
		
		padding_height = height/8;
		padding_width = width/8;
		
		board_width = width - (2*padding_width);
		board_height = height - (2*padding_height);
		
		cell_width = board_width/TicTacToeBoard.WIDTH;
		cell_height = board_height/TicTacToeBoard.HEIGHT;
		
		cell_padding_height = cell_height/8;
		cell_padding_width = cell_width/8;
		
		// Temp variables used for drawing the board
		int i;
		Line2D line;
		
		// Draw the vertical lines....
		for(i=1; i<TicTacToeBoard.WIDTH; i++) {
			double x = padding_width+(i*cell_width);
			line = new Line2D.Double(x, padding_height, x, height-padding_height);
			g2.draw(line);
		}
		// ... and the horizontal ones
		for(i=1; i<TicTacToeBoard.HEIGHT; i++) {
			double y = padding_height+(i*cell_height);
			line = new Line2D.Double(padding_width, y, width-padding_width, y);
			g2.draw(line);
		}
		
		for(int x=0; x<TicTacToeBoard.WIDTH; x++) {
			for(int y=0; y<TicTacToeBoard.HEIGHT; y++) {
				Location l = new Location(x, y);
				if(theBoard.cellAt(l) == Cell.X){
					drawX(g2, l);
				}
				if(theBoard.cellAt(l) == Cell.O) {
					drawO(g2, l);
				}
			}
		}
	}
	
	/*
	 * Translate a point from a location on the screen to a location on the game board
	 * Returns null if the point is not on the board
	 */
	public Location translatePoint(Point p){
		double x = p.getX();
		double y = p.getY();
		if(x < padding_width
				|| x > (padding_width + board_width)
				|| y < padding_height
				|| y > (padding_height + board_height)
		) {
			return null;
		} 
		
		x = x - padding_width;
		y = y - padding_height;
		x = Math.floor(x/cell_width);
		y = Math.floor(y/cell_height);
		return TicTacToeBoard.newLocation((new Double(x)).intValue(), (new Double(y)).intValue());
	}
	
	/*
	 * Translate a location to the upper left corder of the cell on the screen it belongs to
	 */
	public Point translateLocation(Location l) {
		double x = padding_width + (cell_width * l.x);
		double y = padding_height + (cell_height * l.y);
		return new Point((new Double(x)).intValue(), (new Double(y)).intValue());
	}
	
	private void drawX(Graphics2D g, Location l) {
		Point where = translateLocation(l);
		Line2D l1 = new Line2D.Double(
				where.getX() + cell_padding_width, 
				where.getY() + cell_padding_height, 
				cell_width+where.getX() - cell_padding_width, 
				cell_height+where.getY() - cell_padding_height);
		Line2D l2 = new Line2D.Double(
				where.getX() + cell_width - cell_padding_width, 
				where.getY() + cell_padding_height, 
				where.getX() + cell_padding_width, 
				where.getY() + cell_height - cell_padding_height);
		g.draw(l1);
		g.draw(l2);
	}
	
	private void drawO(Graphics2D g, Location l) {
		Point where = translateLocation(l);
		Ellipse2D circle = new Ellipse2D.Double(
				where.getX() + cell_padding_width, 
				where.getY() + cell_padding_height, 
				cell_width - (2*cell_padding_width), 
				cell_height - (2*cell_padding_height));
		g.draw(circle);
	}
}
