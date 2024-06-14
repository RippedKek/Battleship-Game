package gamestates;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import main.Game;

public class State {

	protected Game game;
	
	public State(Game game) {
		this.game = game;
	}
	
	public Game getGame() {
		return game;
	}
	
	public boolean isIn(MouseEvent e, Rectangle2D.Float eb) {
		return eb.getBounds().contains(e.getX(), e.getY());
	}
	
	public void setGameState(Gamestates state) {
		Gamestates.state = state;
	}
	
}
