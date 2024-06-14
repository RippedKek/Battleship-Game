package gamestates;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Board;
import main.Game;
import utils.Load;

public class Battle extends State implements Statemethods{

	private Board playerBoard, opponentBoard;
	private boolean playerTurn = false;
	private boolean playerWin;
	
	private BufferedImage bg, player, opponent;
	
	public Battle(Game game) {
		super(game);
		initBoards();
		loadImgs();
	}

	private void loadImgs() {
		bg = Load.GetImage(Load.BACKGROUND);
		player = Load.GetImage(Load.PLAYER);
		opponent = Load.GetImage(Load.OPPONENT);
	}

	private void initBoards() {
		playerBoard = game.getDeploy().getPlayerBoard();
		opponentBoard = game.getDeploy().getOpponentBoard();
	}

	@Override
	public void update() {
		if(playerTurn) {
			opponentBoard.update();
			if(opponentBoard.getHitCount() == 16) {
				playerWin = true;
				System.out.println("player wins");
				setGameState(Gamestates.SCORE);
			}		
		}
		else {
			playerBoard.update();
			if(playerBoard.getHitCount() == 16) {
				playerWin = false;
				System.out.println("opponent wins");
				setGameState(Gamestates.SCORE);
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(bg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		if(playerTurn) {
			opponentBoard.draw(g);
			g.drawImage(player, Game.TILE_SIZE, Game.TILE_SIZE, null);
		}
		else {
			playerBoard.draw(g);
			g.drawImage(opponent, Game.TILE_SIZE, Game.TILE_SIZE, null);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(playerTurn) {
			opponentBoard.mouseClicked(e);
			if(opponentBoard.getIsHit()) 
				System.out.println("opponent ship hit");
			else
				playerTurn = !playerTurn;
		}
		else {
			playerBoard.mouseClicked(e);
			if(playerBoard.getIsHit()) 
				System.out.println("player ship hit");
			else
				playerTurn = !playerTurn;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(playerTurn)
			opponentBoard.mouseMoved(e);
		else
			playerBoard.mouseMoved(e);
	}
	
	public boolean getPlayerWin() {
		return playerWin;
	}

}
