package object;

import character.Entity;
import main.GamePanel;

public class OBJ_Shield_Normal extends Entity{

	public OBJ_Shield_Normal(GamePanel gp) {
		super(gp);
		name = "shieldWood";
		image = setup("/Objects/shield_wood",gp.newTileSize,gp.newTileSize);
		defenceValue = 1;
	}

}
