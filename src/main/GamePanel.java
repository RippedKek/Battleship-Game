package main;


import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import inputs.MouseInputs;

import static main.Game.GAME_WIDTH;
import static main.Game.GAME_HEIGHT;


public class GamePanel extends JPanel{
	
	private MouseInputs mouseInputs;
	private Game game;
	
	public GamePanel(Game game) {
		setPanelSize();
		mouseInputs = new MouseInputs(game);
		this.game = game;
		super.addMouseListener(mouseInputs);
		super.addMouseMotionListener(mouseInputs);
	}
	
	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
		//setMinimumSize(size);
		setPreferredSize(size);
		//setMaximumSize(size);
	}
	
	public Game getGame() {
		return game;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
	}
}
