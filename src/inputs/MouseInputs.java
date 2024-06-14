package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import gamestates.Battle;
import gamestates.Deploy;
import gamestates.Gamestates;
import main.Board;
import main.Game;
import main.GamePanel;
import main.Ships;

public class MouseInputs implements MouseListener,MouseMotionListener{
	
	private Game game;
	private Deploy deploy;
	private Battle battle;
	
	public MouseInputs(Game game) {
		this.game = game;
		deploy = game.getDeploy();
		battle = game.getBattle();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch(Gamestates.state) {
		case DEPLOY:
			deploy.mouseMoved(e);
			break;
		case BATTLE:
			battle.mouseMoved(e);
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch(Gamestates.state) {
		case DEPLOY:
			deploy.mouseClicked(e);
			break;
		case BATTLE:
			battle.mouseClicked(e);
			break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		deploy.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		deploy.mouseReleased(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
