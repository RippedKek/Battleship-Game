package main;

import static utils.Constants.Ships.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import utils.Load;

public class Ships {

	private Game game;
	private int type, x, y;
	private String ship;
	private Rectangle2D.Float shipBound;
	
	private boolean isActive = false;
	private boolean canPlace = false;
	private boolean rotated = false;
	
	private BufferedImage shipImg, rotatedImage;
	private int shipWidth, shipHeight;
	
	public Ships(Game game, int x, int y, int type) {
		this.game = game;
		this.type = type;
		this.x = x;
		this.y = y;
		determineSize();
		initShipBounds();
		loadImgs();
	}
	
	private void determineSize() {
		shipWidth = GetShipWidth(type);
		shipHeight = GetShipHeight(type);
	}

	private void loadImgs() {
		switch(type) {
		case CARRIER:
			shipImg = Load.GetImage(Load.CARRIER);
			break;
		case DESTROYER:
			shipImg = Load.GetImage(Load.DESTROYER);
			break;
		case CRUISER:
			shipImg = Load.GetImage(Load.CRUISER);
			break;
		case SUB:
			shipImg = Load.GetImage(Load.SUBMARINE);
			break;
		}
	}

	private void initShipBounds() {
		shipWidth = GetShipWidth(type);
		shipHeight = GetShipHeight(type);
		shipBound = new Rectangle2D.Float(x, y, shipWidth, shipHeight);
	}
	
	private void drawShipBound(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect((int)shipBound.x, (int)shipBound.y, (int)shipBound.width, (int)shipBound.height);
	}
	
	public void update(int x, int y) {
		changePos(x, y);
	}
	
	private void updateBounds(int x, int y) {
		shipBound.x = x;
		shipBound.y = y;
		if(!rotated) {	
			shipBound.width = shipWidth;
			shipBound.height = shipHeight;
		}
		else {
			shipBound.width = shipHeight;
			shipBound.height = shipWidth;
		}
	}

	public void draw(Graphics g) {
		if (rotated && rotatedImage != null) {
			g.drawImage(rotatedImage, x, y, shipHeight, shipWidth, null);
	    } else if (!rotated && shipImg != null) {
	        g.drawImage(shipImg, x, y, shipWidth, shipHeight, null);
	    }
	}
	
	public void changePos(int x, int y) {
		this.x = x;
		this.y = y;
		updateBounds(x, y);
	}
	
	public void rotateImage() {
		if (!rotated) {
            int width = shipImg.getWidth();
            int height = shipImg.getHeight();
            rotatedImage = new BufferedImage(height, width, shipImg.getType());

            Graphics2D g2d = rotatedImage.createGraphics();
            g2d.rotate(Math.PI / 2, height / 2, width / 2);
            g2d.drawImage(shipImg, (height - width) / 2, (width - height) / 2, null);
            g2d.dispose();

            rotated = true;
        } else {
            rotated = false;
        }
	}
	
	public void setActive(boolean active) {
		this.isActive = active;
	}
	
	public boolean isCanPlace() {
		return canPlace;
	}

	public void setCanPlace(boolean canPlace) {
		this.canPlace = canPlace;
	}

	public Rectangle2D.Float getBound(){
		return shipBound;
	}
	
	public boolean getActive() {
		return isActive;
	}
	
	public boolean getOrientation() {
		return rotated;
	}
	
}
