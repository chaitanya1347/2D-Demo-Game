package object;
import character.Entity;
import main.GamePanel;

public class OBJ_CHEST extends Entity {
	GamePanel gp;
	
	public OBJ_CHEST(GamePanel gp) {
		super(gp);
		name = "chest";
		collision = true;
		down1 = setup("/Objects/chest",gp.newTileSize,gp.newTileSize);
	}
	
}
