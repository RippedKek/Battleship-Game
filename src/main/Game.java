package main;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

import gamestates.Battle;
import gamestates.Deploy;
import gamestates.Gamestates;
import gamestates.Score;

public class Game implements Runnable{
	
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	
	private Deploy deploy;
	private Battle battle;
	private Score score;
	
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	public static final int TILE_SIZE = 40;
	
	public Game() {
		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();
		startGameLoop();
	}
	
	private void initClasses() {
		deploy = new Deploy(this);
		battle = new Battle(this);
		score = new Score(this);
	}
	
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;
		long previousTime = System.nanoTime();
		int frames = 0;
		int updates = 0;
		double deltaU = 0;
		double deltaF = 0;
		long lastCheck = System.currentTimeMillis();
		
		while(true) {
			long currentTime = System.nanoTime();
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			if(deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}
			if(deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}
			if(System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
//				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}
	
	public void update() {
		switch(Gamestates.state) {
		case DEPLOY:
			deploy.update();
			break;
		case BATTLE:
			battle.update();
			break;
		case SCORE:
			score.update();
			break;
		}
	}
	
	public void render(Graphics g) {
		switch(Gamestates.state) {
		case DEPLOY:
			deploy.draw(g);
			break;
		case BATTLE:
			battle.draw(g);
			break;
		case SCORE:
			score.draw(g);
			break;
		}
	}
	
	public Deploy getDeploy() {
		return deploy;
	}
	
	public Battle getBattle() {
		return battle;
	}
	
	public Score getScore() {
		return score;
	}
	
}
