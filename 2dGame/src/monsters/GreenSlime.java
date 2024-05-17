package monsters;

import java.util.Random;

import character.Entity;
import main.GamePanel;

public class GreenSlime extends Entity {
	int Counter  = 0;
	GamePanel gp;
	public GreenSlime(GamePanel gp) {
		super(gp);
		this.gp = gp;
		type = 2;
		name = "Green_Slime";
		speed = 1;
		maxLife  = 4;
		life = maxLife;
		
		SolidBody.x = 3;
		SolidBody.y = 18;
		SolidBody.width = 42;
		SolidBody.height = 30;
		SolidBodyDefaultX = SolidBody.x;
		SolidBodyDefaultY = SolidBody.y;
		
		loadImages();
		setAction();
	}
	public void loadImages() {
//		System.out.println("HELL");	
			up1 = setup("/monsters/greenslime_down_1",gp.newTileSize,gp.newTileSize);
			up2 = setup("/monsters/greenslime_down_2",gp.newTileSize,gp.newTileSize);
			left1 = setup("/monsters/greenslime_down_1",gp.newTileSize,gp.newTileSize);
			left2 =setup("/monsters/greenslime_down_2",gp.newTileSize,gp.newTileSize);
			down1 = setup("/monsters/greenslime_down_1",gp.newTileSize,gp.newTileSize);
			down2 = setup("/monsters/greenslime_down_2",gp.newTileSize,gp.newTileSize);
			right1 = setup("/monsters/greenslime_down_1",gp.newTileSize,gp.newTileSize);
			right2 = setup("/monsters/greenslime_down_2",gp.newTileSize,gp.newTileSize);
	}
	
	public void setAction() {
//		if(this.collision==true) Counter = 181;
		Counter++;
		if(Counter > 120) {
		Random random = new Random();
		int i = random.nextInt(100) + 1;
		if(i>=1 && i<=25) direction = "down";
		if(i>25 && i<=50) direction = "left";
		if(i>50 && i<=75) direction = "right";
		if(i>75 && i<=100) direction = "up";
		Counter  = 0;
		}
	}
	
	public void damageAction() {
		direction  = gp.player.direction;
	}
}
	
