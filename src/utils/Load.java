package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Load {
	
	public static final String BACKGROUND = "bg.png";
	public static final String OCEAN = "ocean.png";
	public static final String BUTTONS = "buttons.png";
	public static final String PLAYER = "player.png";
	public static final String OPPONENT = "opponent.png";
	public static final String PLAYERWIN = "playerwins.png";
	public static final String OPPONENTWIN = "opponentwins.png";
	public static final String CARRIER = "carrier.png";
	public static final String CRUISER = "battleship.png";
	public static final String DESTROYER = "destroyer.png";
	public static final String SUBMARINE = "submarine.png";
	
	public static BufferedImage GetImage(String fileName) {
		BufferedImage img = null;
		InputStream is = Load.class.getResourceAsStream("/" + fileName);
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		return img;
	}

}
