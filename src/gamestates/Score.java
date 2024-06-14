package gamestates;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import utils.Load;

public class Score extends State implements Statemethods{

	private BufferedImage bg, playerWin, opponentWin;
	
	public Score(Game game) {
		super(game);
		loadImgs();
	}
	
	private void loadImgs() {
		bg = Load.GetImage(Load.BACKGROUND);
		playerWin = Load.GetImage(Load.PLAYERWIN);
		opponentWin = Load.GetImage(Load.OPPONENTWIN);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(bg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		if(game.getBattle().getPlayerWin())
			g.drawImage(playerWin, 0, 0, null);
		else
			g.drawImage(opponentWin, 0, 0, null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
