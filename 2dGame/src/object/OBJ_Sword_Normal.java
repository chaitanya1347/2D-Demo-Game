package object;

import character.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {

	public OBJ_Sword_Normal(GamePanel gp) {
		super(gp);
		name = "sowrdNormal";
		image = setup("/Objects/sword_normal",gp.newTileSize,gp.newTileSize);
		attackValue =1;
	}

}
