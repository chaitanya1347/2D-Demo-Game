package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import character.Entity;
import main.GamePanel;

public class OBJ_BOOTS extends Entity {
	GamePanel gp;
	
	public OBJ_BOOTS(GamePanel gp) {
		super(gp);
		name = "boots";
		down1 = setup("/Objects/boots",gp.newTileSize,gp.newTileSize);
	}
	
	
}
