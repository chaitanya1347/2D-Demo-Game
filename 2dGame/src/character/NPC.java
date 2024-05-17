package character;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class NPC extends Entity {
	GamePanel gp;
	int npcCounter =0;
	public NPC(GamePanel gp) {
		super(gp);
		this.gp =gp;
		NpcDefaultImage();
		SetDialogue();
	}
	public void NpcDefaultImage() {
//		System.out.println("HELL");	
			up1 = setup("/npc/oldman_up_1",gp.newTileSize,gp.newTileSize);
			up2 = setup("/npc/oldman_up_2",gp.newTileSize,gp.newTileSize);
			left1 = setup("/npc/oldman_left_1",gp.newTileSize,gp.newTileSize);
			left2 =setup("/npc/oldman_left_2",gp.newTileSize,gp.newTileSize);
			down1 = setup("/npc/oldman_down_1",gp.newTileSize,gp.newTileSize);
			down2 = setup("/npc/oldman_down_2",gp.newTileSize,gp.newTileSize);
			right1 = setup("/npc/oldman_right_1",gp.newTileSize,gp.newTileSize);
			right2 = setup("/npc/oldman_right_2",gp.newTileSize,gp.newTileSize);
	}
	
	public void setAction() {
		npcCounter++;
		
		dialogueBox(this);
//		if(this.collision==true) npcCounter = 181;
		
		if(npcCounter > 120) {
		Random random = new Random();
		int i = random.nextInt(100) + 1;
		if(i>=1 && i<=25) direction = "down";
		if(i>25 && i<=50) direction = "left";
		if(i>50 && i<=75) direction = "right";
		if(i>75 && i<=100) direction = "up";
		npcCounter  = 0;
		}
	}
	public void SetDialogue() {
		this.dialogue[0] = "Hello Chaitanya!";
		this.dialogue[1] = "Have you to came to this island in search\nof treasure ?";
		this.dialogue[2] = "Please, let me know if I could help you\nwith any of your difficulties.";
		this.dialogue[3] = "Good luck with your adventure :)";
		this.dialogue[4] = "and yeah, one thing to remember!";
		this.dialogue[5] = "There are total of 3 golden Keys that you\nneed to find to reach the treasure.";
				
	}
	
	public void speak() {
		gp.ui.currentDialogue = dialogue[dialogueIndex]; 
		dialogueIndex++;
		if(dialogue[dialogueIndex]==null) dialogueIndex = 0;
	}
}
