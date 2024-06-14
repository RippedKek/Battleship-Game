package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Gamestates;
import utils.Load;

import static utils.Constants.Ships.*;

public class Board {

	private Game game;
	private Ships ships[];
	private final int BOARD_SIZE = 10;
	
	private Rectangle2D.Float[][] bounds = new Rectangle2D.Float[BOARD_SIZE][BOARD_SIZE];
	private Point p;
	private boolean mouseOver = false;
	
	private int mouseX, mouseY;
	private int shipPlaceX, shipPlaceY;
	
	private BufferedImage ocean;
	
	private ArrayList<Point> hitpoints = new ArrayList<>();
	private ArrayList<Point> alreadyHit = new ArrayList<>();
	private boolean isHit = false;
	
	public Board(Game game) {
		this.game = game;
		initShips();
		initBounds();
		ocean = Load.GetImage(Load.OCEAN);
	}

	private void initShips() {
		ships = new Ships[7];
		ships[0] = new Ships(game, 560, 80, CARRIER);
		ships[1] = new Ships(game, 560, 140, CRUISER);
		ships[2] = new Ships(game, 560, 200, CRUISER);
		ships[3] = new Ships(game, 560, 260, DESTROYER);
		ships[4] = new Ships(game, 560, 320, DESTROYER);
		ships[5] = new Ships(game, 560, 380, SUB);
		ships[6] = new Ships(game, 560, 440, SUB);
	}

	private void initBounds() {
		for(int j = 0; j < BOARD_SIZE; j++) 
			for(int i = 0; i < BOARD_SIZE; i++) 
				bounds[j][i] = new Rectangle2D.Float(Game.TILE_SIZE + Game.TILE_SIZE * i, 2 * Game.TILE_SIZE + Game.TILE_SIZE * j, Game.TILE_SIZE, Game.TILE_SIZE);
	}
	
	public void update() {
		for(Ships s : ships)
			if(s.getActive())
				s.update(mouseX, mouseY);
	}

	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g2.drawImage(ocean, Game.TILE_SIZE, 2 * Game.TILE_SIZE, BOARD_SIZE * Game.TILE_SIZE, BOARD_SIZE * Game.TILE_SIZE, null);
		if(mouseOver)
			drawBlur(g2);
		if(hitpoints.size() != 0)
			drawHit(g2);
		if(alreadyHit.size() != 0)
			drawAlreadyHit(g2);
		for(int j = 0; j < BOARD_SIZE; j++) 
			for(int i = 0; i < BOARD_SIZE; i++) {
				g2.setColor(new Color(0, 0, 0));
				g2.drawRect(Game.TILE_SIZE + Game.TILE_SIZE * i, 2 * Game.TILE_SIZE + Game.TILE_SIZE * j, Game.TILE_SIZE, Game.TILE_SIZE);
			}
		if(Gamestates.state == Gamestates.DEPLOY)
			for(Ships s : ships)
				s.draw(g2);
	}

	private void drawBlur(Graphics g) {
		g.setColor(new Color(0, 0, 0, 50));
		g.fillRect(p.x, p.y, Game.TILE_SIZE, Game.TILE_SIZE);
	}
	
	private void drawHit(Graphics g) {
		g.setColor(new Color(255, 0, 0));
		for(Point p : hitpoints)
			g.fillRect(p.x, p.y, Game.TILE_SIZE, Game.TILE_SIZE);
	}
	
	private void drawAlreadyHit(Graphics g) {
		g.setColor(new Color(0, 0, 0));
		for(Point p : alreadyHit)
			g.fillRect(p.x, p.y, Game.TILE_SIZE, Game.TILE_SIZE);
	}

	public void mouseMoved(MouseEvent e) {
		mouseOver = false;
		for(int j = 0; j < BOARD_SIZE; j++) 
			for(int i = 0; i < BOARD_SIZE; i++) 
				if(isIn(e, bounds[j][i])) {
					mouseOver = true;
					p = mouseInBound((int)bounds[j][i].getX(), (int)bounds[j][i].getY());
					break;
				}
		if(Gamestates.state == Gamestates.DEPLOY)
			for(Ships s : ships)
				if(s.getActive()) {
					mouseX = e.getX();
					mouseY = e.getY();
					if(canPlaceShip(e.getPoint(), s.getBound(), s.getOrientation())) {
						s.setCanPlace(true);
						break;
					}
					else
						s.setCanPlace(false);
				}
	}
	
	public void mouseClicked(MouseEvent e) {
		if(Gamestates.state == Gamestates.DEPLOY) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				for(Ships s : ships)
					if(isIn(e, s.getBound())) {
						if(!s.getActive()) {
							s.setActive(true);
							break;
						}
						if(s.getActive()) {
							if(s.isCanPlace()) {
								s.setActive(false);
								s.changePos(shipPlaceX + Game.TILE_SIZE, shipPlaceY + 2 * Game.TILE_SIZE);
								break;
							}
						}
					}
			}
			else if(e.getButton() == MouseEvent.BUTTON3) {
				for(Ships s : ships)
					if(s.getActive()) {
						s.rotateImage();
						break;
					}
			}
		}
		else if(Gamestates.state == Gamestates.BATTLE) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				for(int j = 0; j < BOARD_SIZE; j++) 
					for(int i = 0; i < BOARD_SIZE; i++) 
						if(isIn(e, bounds[j][i])) {
							if(checkIfHit(j, i)) 
								hitpoints.add(mouseInBound((int)bounds[j][i].getX(), (int)bounds[j][i].getY()));
							else
								alreadyHit.add(mouseInBound((int)bounds[j][i].getX(), (int)bounds[j][i].getY()));
						}
			}
		}
	}
	
	public void mousePressed(MouseEvent e) {
		
	}
	
	private boolean canPlaceShip(Point p, Rectangle2D.Float shipBound, boolean orientation) {
		boolean flag = true;
		for(int j = 0; j < BOARD_SIZE; j++) {
			for(int i = 0; i < BOARD_SIZE; i++) 
				if(bounds[j][i].contains(p)) {
					shipPlaceX = i * Game.TILE_SIZE;
					shipPlaceY = j * Game.TILE_SIZE;
					flag = false;
					break;
				}
			if(!flag)
				break;
		}
		if(flag)
			return false;
		if(!orientation) {
			for(int j = shipPlaceY / Game.TILE_SIZE; j < 1 + shipPlaceY / Game.TILE_SIZE; j++) {
				for(int i = shipPlaceX / Game.TILE_SIZE; i < (shipPlaceX / Game.TILE_SIZE) + (shipBound.width / Game.TILE_SIZE) ; i++) {
					if(i >= BOARD_SIZE) 
						return false;
				}
			}
			return true;
		}
		else {
			for(int j = shipPlaceX / Game.TILE_SIZE; j < 1 + shipPlaceX / Game.TILE_SIZE; j++) {
				for(int i = shipPlaceY / Game.TILE_SIZE; i < (shipBound.height / Game.TILE_SIZE) + (shipPlaceY / Game.TILE_SIZE); i++) {
					if(i >= BOARD_SIZE) 
						return false;
				}
			}
			return true;
		}
	}
	
	private boolean checkIfHit(int y, int x) {
		x = (x * Game.TILE_SIZE) + Game.TILE_SIZE;
		y = (y * Game.TILE_SIZE) + (2 * Game.TILE_SIZE);
		for(Ships s : ships)
			if(s.getBound().contains(x,y)) {
				isHit = true;
				return true;
			}
		return false;
	}
	
	private Point mouseInBound(int x, int y) {
		return new Point(x, y);
	}

	private boolean isIn(MouseEvent e, Rectangle2D.Float b) {
		if(b.contains(e.getX(), e.getY()))
			return true;
		return false;
	}
	
	public int getHitCount() {
		return hitpoints.size();
	}
	
	public boolean getIsHit() {
		boolean temp = isHit;
		isHit = false;
		return temp;
	}
	
}
