package character;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.CollisionChecker;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Shield_Normal;
import object.OBJ_Sword_Normal;

public class Player extends Entity {
	
	KeyHandler keyH;
	GamePanel gp;
	public int screenX,screenY;
	public int keyCount = 0;
	public int bootTimeCounter = 0;
	int totalBoots = 0;
	int invincibleBlinkCounter =0;
	
	int screenTempX;
	int screenTempY;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH; 
		this.gp = gp; 
		setValues();
		playerDefaultImage();
		playerAttackImage();
	}
	
//	CollisionChecker cChecker = new CollisionChecker(gp);
	
	public void playerDefaultImage() {
//		System.out.println("HELL");	
			up1 = setup("/player/boy_up_1",gp.newTileSize,gp.newTileSize);
			up2 = setup("/player/boy_up_2",gp.newTileSize,gp.newTileSize);
			left1 = setup("/player/boy_left_1",gp.newTileSize,gp.newTileSize);
			left2 =setup("/player/boy_left_2",gp.newTileSize,gp.newTileSize);
			down1 = setup("/player/boy_down_1",gp.newTileSize,gp.newTileSize);
			down2 = setup("/player/boy_down_2",gp.newTileSize,gp.newTileSize);
			right1 = setup("/player/boy_right_1",gp.newTileSize,gp.newTileSize);
			right2 = setup("/player/boy_right_2",gp.newTileSize,gp.newTileSize);

	}
	
	public void playerAttackImage() {
		attackUp1 = setup("/player/boy_attack_up_1",gp.newTileSize,2*gp.newTileSize);
		attackUp2 = setup("/player/boy_attack_up_2",gp.newTileSize,2*gp.newTileSize);
		attackLeft1= setup("/player/boy_attack_left_1",2*gp.newTileSize,gp.newTileSize);
		attackLeft2 =setup("/player/boy_attack_left_2",2*gp.newTileSize,gp.newTileSize);
		attackDown1 = setup("/player/boy_attack_down_1",gp.newTileSize,2*gp.newTileSize);
		attackDown2 = setup("/player/boy_attack_down_2",gp.newTileSize,2*gp.newTileSize);
		attackRight1 = setup("/player/boy_attack_right_1",2*gp.newTileSize,gp.newTileSize);
		attackRight2 = setup("/player/boy_attack_right_2",2*gp.newTileSize,gp.newTileSize);
	}
	
	public void setValues() {
		//set accordingly like where to instantiate the character....don't forget difference between screenX and WorldX;
		worldX = gp.newTileSize*23;
		worldY = gp.newTileSize*21;
		// set different params
		speed = 4; 
		direction ="down";
		
		//setting the solid boundary		
		SolidBody = new Rectangle();
		SolidBody.x = 8;
		SolidBody.y = 16;
		SolidBodyDefaultX = SolidBody.x;
		SolidBodyDefaultY = SolidBody.y;
		SolidBody.width = 32;
		SolidBody.height =32;
		
		SolidSword.width = 36;
		SolidSword.height = 36;
		
		screenX = gp.screenWidth/2 - gp.newTileSize/2;
		screenY = gp.screenHeight/2 - gp.newTileSize/2;
		
		//player health status
		maxLife = 6;
		life = maxLife;
		level = 1;
		strength = 1;
		dexterity = 1;
		coin = 0;
		exp =0 ;
		nextLevelExp = 5;
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Normal(gp);
		attack = strength *  currentWeapon.attackValue;
		defense = dexterity * currentShield.defenceValue; 
	}
	
	public void update() {

		if(attacking==true) {
			attackAction();
		}
		else if(keyH.upPressed==true||keyH.downPressed==true||keyH.leftPressed==true||keyH.rightPressed==true || gp.keyH.enterPressed == true) {
			if(keyH.upPressed==true) {
				direction ="up";
			}
			else if(keyH.downPressed==true) {
				direction ="down";
			}
			else if(keyH.leftPressed==true) {
				direction ="left";
			}
			else if(keyH.rightPressed==true) {
				direction = "right";
			}
			
			//Collision Checkersssssss
			collision = false;
			
			//collison of player with any tile(tree,wall..)
			gp.cChecker.updateCollision(this);
			
			
			//collision of player with the object
			int index = gp.cChecker.objectCollision(this,true);
			if(index!=-1) hitObject(index);
			
			
			//collision of the object with the NPCs
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			hitNpc(npcIndex);
			
			
			int monsterIndex  = gp.cChecker.checkEntity(this, gp.monsters);
			contactMonster(monsterIndex);
			//event
			gp.eh.checkEvent();
						
			
			if(keyH.enterPressed==true && attackCancel == false ) {
				attacking =true;
				gp.soundSE(7);
			}
			
			attackCancel = false;
			
			if(this.collision==false && gp.eh.pitFlag == false && gp.keyH.enterPressed == false) {
				if(direction == "up") {
					worldY-=speed;
//					System.out.println(worldY);
				}else if (direction == "down") {
					worldY+=speed;
				}else if (direction == "left") {
					worldX-=speed;
				}else if(direction == "right") {
					worldX+=speed;
				}
			}
			
			gp.keyH.enterPressed= false;
			
			spriteCounter++;
			if(spriteCounter>=10) {
				spriteValue = 1 - spriteValue;
				spriteCounter = 0;
			}
		}
		
		
		if(invincible==true) {
			invincibleCounter++;
			invincibleBlinkCounter++;
			if(invincibleCounter>120) {
				invincibleCounter = 0;
				invincible =false;
			}
		}
		
		// to decrease the speed automatically after 10 sec;
		bootTimeCounter++;
		if(totalBoots>0) {
			if(bootTimeCounter>=600) {
				speed-=2;
				totalBoots--;
			}
		}
		
	}
	
	public void attackAction() {
		spriteCounter++;
		if(spriteCounter<=5) {
			spriteValue = 0;
		}else if(spriteCounter<=25 && spriteCounter>=5) {
			spriteValue = 1;
			
			int currentWordlX = worldX;
			int currentWorldY = worldY;
			int currentWidth = SolidBody.width;
			int currentHeight = SolidBody.height;
			
			if(direction == "up") {
				worldY-= SolidSword.height;
			}else if (direction == "down") {
				worldY+= SolidSword.height;
			}else if (direction == "left") {
				worldX-= SolidSword.width;
			}else if(direction == "right") {
				worldX+= SolidSword.width;
			}
			
			this.SolidBody.height = this.SolidSword.height;
			this.SolidBody.width = this.SolidSword.width;
			
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monsters);
			damageMoster(monsterIndex);
			
			worldX = currentWordlX;
			worldY = currentWorldY;
			this.SolidBody.width = currentWidth;
			this.SolidBody.height = currentHeight;
			
			
		}else {
			spriteCounter = 0;
			spriteValue = 0;
			attacking = false;
		}
	}
	
	public void hitObject(int ind) {
			if(gp.obj[ind].name=="key") {
				keyCount++;
				gp.obj[ind]=null;
				gp.soundSE(1);
				gp.ui.showMessage("You got the key!");
			}
			else if(gp.obj[ind].name=="door") {
				if(keyCount>0) {
					gp.obj[ind]=null;
					keyCount--;
					gp.soundSE(2);
					gp.ui.showMessage("Door Opened!");
				}else gp.ui.showMessage("You need a key :/"); 
			}else if(gp.obj[ind].name == "boots") {
				gp.obj[ind]=null;
				speed+=2;
				bootTimeCounter = 0;
				totalBoots++;
				gp.soundSE(3);
				gp.ui.showMessage("Speed UP!");
			}else if(gp.obj[ind].name == "chest") {
//				gp.obj[ind]=null;
//				keyCount--;
				gp.ui.gameFinished = true;
				gp.stop();
				gp.soundSE(4);
				gp.ui.showMessage("You found the treasure!");
//				gp.ui.showMessage("Congratulations"); 
			}							
	}
	
	public void contactMonster(int i) {
		if(i!=-1) {
			if(invincible==false) {
				life-=1; 
				invincible =true;
				gp.soundSE(6);
			}
		}
	}
	
	public String turnOppsite(String direction) {
		if(direction=="up") return "down";
		if(direction=="down") return "up";
		if(direction=="right") return "left";
		if(direction=="left") return "right";
		return null;
	}
	public void hitNpc(int index) {
//		System.out.println("YOU HIT AN NPC");
		if(index!=-1) {
			if(gp.keyH.enterPressed==true) {
				attackCancel = true;
				gp.npc[index].direction = turnOppsite(this.direction);
				gp.gameState = gp.dialogueState; 
				gp.npc[index].speak();
			}
		}

	}
	public void damageMoster(int index) {
		if(index!=-1) {
			if(gp.monsters[index].invincible==false) {
				gp.monsters[index].life-=1;
				gp.monsters[index].invincible = true;
				gp.monsters[index].damageAction();
				gp.soundSE(5);
				gp.monsters[index].hpDisplay = true;
				gp.monsters[index].hpCounter = 0;
				if(gp.monsters[index].life==0) gp.monsters[index].dying =true; 
			}
		}
	}
	public void draw(Graphics2D g2) {
		
//		g2.setColor(Color.white);
//		g2.fillRect(x, y, gp.newTileSize, gp.newTileSize);
//		g2.dispose();
		
		BufferedImage image = null; 
		screenTempX = this.screenX;
		screenTempY = this.screenY;
		
		if(direction == "up") {
			if(attacking == false) {
				if(spriteValue==0) image = up1;
				else image =up2;  
			}
			if(attacking == true) {
				screenTempY -= gp.newTileSize;
				if(spriteValue==0) image = attackUp1;
				else image = attackUp2;  
			}  
		}else if (direction == "down") {

			if(attacking == false) {
				if(spriteValue==0) image = down1;
				else image =down2;
			}
			if(attacking == true) {
				if(spriteValue==0) image = attackDown1;
				else image =attackDown2;
			} 
		}else if (direction == "left") {
			if(attacking == false) {
				if(spriteValue==0) image = left1;
				else image =left2;
			}
			if(attacking == true) {
				screenTempX -= gp.newTileSize;
				if(spriteValue==0) image = attackLeft1;
				else image =attackLeft2;
			}

		}else if(direction == "right") {
			if(attacking == false) {
				if(spriteValue==0) image = right1;
				else image =right2;
			}
			if(attacking == true) {
				if(spriteValue==0) image = attackRight1;
				else image =attackRight2;
			}
		}
	
		if(this.invincible==true) {
			 g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
			 if(invincibleBlinkCounter>20) {
				 invincibleBlinkCounter = 0;
				 g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
			 }
		}

		g2.drawImage(image,screenTempX,screenTempY,null);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		
	}
	
}
