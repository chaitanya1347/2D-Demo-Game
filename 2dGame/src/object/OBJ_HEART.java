package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import character.Entity;
import main.GamePanel;

public class OBJ_HEART extends Entity {
	GamePanel gp;
	
	public OBJ_HEART(GamePanel gp) {
		super(gp);
		name = "heart";
		image = setup("/Objects/heart_full",gp.newTileSize,gp.newTileSize);
		image2 = setup("/Objects/heart_half",gp.newTileSize,gp.newTileSize);
		image3 = setup("/Objects/heart_blank",gp.newTileSize,gp.newTileSize);
	}
	
}
