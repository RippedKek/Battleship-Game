package gamestates;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.Board;
import main.Game;
import utils.Load;

public class Deploy extends State implements Statemethods{

	private Board playerBoard, opponentBoard;
	private boolean playerTurn = true;
	private Rectangle2D.Float deployButton;
	
	private BufferedImage bg;
	private BufferedImage[] buttons;
	private int index;
	private boolean mouseOver, mousePressed;
	
	private BufferedImage player, opponent;
	
	public Deploy(Game game) {
		super(game);
		playerBoard = new Board(game);
		opponentBoard = new Board(game);
		deployButton = new Rectangle2D.Float(165, 500, 150, 60);
		loadImgs();
	}

	private void loadImgs() {
		bg = Load.GetImage(Load.BACKGROUND);
		player = Load.GetImage(Load.PLAYER);
		opponent = Load.GetImage(Load.OPPONENT); 
		buttons = new BufferedImage[3];
		for(int i = 0; i < buttons.length; i++)
			buttons[i] = Load.GetImage(Load.BUTTONS).getSubimage(i * 140, 0, 140, 56);
	}

	@Override
	public void update() {
		index = 0;
		if(mouseOver)
			index = 1;
		if(mousePressed)
			index = 2;
		if(playerTurn)
			playerBoard.update();
		else
			opponentBoard.update();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(bg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		if(playerTurn) {
			playerBoard.draw(g);
			g.drawImage(player, Game.TILE_SIZE, Game.TILE_SIZE, null);
		}
		else {
			opponentBoard.draw(g);
			g.drawImage(opponent, Game.TILE_SIZE, Game.TILE_SIZE, null);
		}
		g.drawImage(buttons[index], 165, 500, 150, 60, null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(playerTurn)
			playerBoard.mouseClicked(e);
		else
			opponentBoard.mouseClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(playerTurn)
			playerBoard.mousePressed(e);
		else
			opponentBoard.mousePressed(e);
		index = 0;
		if(isIn(e, deployButton))
			mousePressed = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(playerTurn)
			playerBoard.mouseMoved(e);
		else
			opponentBoard.mouseMoved(e);
		mouseOver = false;
		if(isIn(e, deployButton))
			mouseOver = true;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(isIn(e, deployButton))
			if(mousePressed) {
				if(playerTurn)
					playerTurn = !playerTurn;
				else 
					setGameState(Gamestates.BATTLE);			
			}
		mouseOver = false;
		mousePressed = false;
	}
	
	public Board getPlayerBoard() {
		return playerBoard;
	}

	public Board getOpponentBoard() {
		return opponentBoard;
	}

}
