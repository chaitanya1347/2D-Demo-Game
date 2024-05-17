package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import character.Entity;
import main.GamePanel;

public class OBJ_KEY extends Entity {
	GamePanel gp;
	
	public OBJ_KEY(GamePanel gp) {
		super(gp);
		name = "key";
		down1 = setup("/Objects/key",gp.newTileSize,gp.newTileSize);
	}
	
}
