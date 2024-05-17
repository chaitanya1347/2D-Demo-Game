package object;

import character.Entity;
import main.GamePanel;

public class OBJ_DOOR extends Entity {
	GamePanel gp;
	
	public OBJ_DOOR(GamePanel gp) {
		super(gp);
		name = "door";
		collision = true;
		down1 = setup("/Objects/door",gp.newTileSize,gp.newTileSize);
	}
}
