package character;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	public int worldX;
	public int worldY;
	public int speed;
	GamePanel gp;
	public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
	public BufferedImage attackUp1,attackUp2,attackDown1,attackDown2,attackLeft1,attackLeft2,attackRight1,attackRight2;
	public BufferedImage image,image2,image3;
	public boolean up,down,left,right;
	public String direction = "down";
	public boolean collision = false;
	public boolean collisionNpcToPlayer;
	public boolean attacking = false;
	public Rectangle SolidBody = new Rectangle(0,0,48,48);
	public Rectangle SolidSword = new Rectangle(0,0,0,0);
	public int SolidBodyDefaultX = 0;
	public int SolidBodyDefaultY = 0;
	public int spriteCounter =0;
	public int spriteValue = 0;
	public String name;
	public int maxLife;
	public int life;
	public int strength;
	public int dexterity;
	public int level;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public int attackValue;
	public int defenceValue;
	
	public Entity currentWeapon;
	public Entity currentShield;
	public boolean invincible = false;
	public int invincibleCounter = 0;
	public int invincibleBlinkCounter =0;
	public boolean dying  = false;
	public boolean alive  = true;
	public int dyingCounter = 0;
	public int hpCounter = 0;
	public boolean hpDisplay = false;
	public int monsterBlinkDuration = 40;
	public boolean attackCancel = false;
	
	String setDirection = "down";
	public String[] dialogue = new String[10];
	int dialogueIndex = 0;
	public int type; //0: player, 1:NPC, 2: Monster
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public BufferedImage setup(String filePath,int width,int height) {
		UtilityTool ut = new UtilityTool();
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResourceAsStream(filePath+".png"));
			image = ut.scaledImage(image, width, height);
		}catch(IOException e) {
			e.getStackTrace();
		}
		return image;
	}
	
	public void setAction() {}
	public void damageAction(){}
	
	public void dialogueBox(NPC npc) {
		if(npc.collisionNpcToPlayer==true) {
			if(gp.keyH.enterPressed==true) {
				gp.gameState = gp.dialogueState; 
				npc.speak();
			}
		}
	}
	public void update() {
		this.setAction();
		collision = false;
		collisionNpcToPlayer =false;
		
		gp.cChecker.updateCollision(this);
		gp.cChecker.objectCollision(this, false);
		gp.cChecker.checkEntity(this,gp.npc);
		gp.cChecker.checkEntity(this,gp.monsters);
		boolean contactPlayer = gp.cChecker.npcToPlayerCollision(this);		
		
		//damge when moster touches player
		if(contactPlayer==true && this.type == 2) {
			if(gp.player.invincible==false) {
				gp.player.life-=1;
				gp.player.invincible=true;
				gp.soundSE(6);
			}
		}
		
		if(invincible==true) {
			invincibleCounter++;
			invincibleBlinkCounter++;
			if(invincibleCounter>40) {
				invincibleCounter = 0;
				invincible =false;
			}
		}
		
//		if(collision ==true  ) System.out.println("You HIT the player");
		if(collision == false) {
			if(direction == "up") {
				worldY-=speed;
			}else if (direction == "down") {
				worldY+=speed;
			}else if (direction == "left") {
				worldX-=speed;
			}else if(direction == "right") {
				worldX+=speed;
			}
		}
		
		spriteCounter++;
		if(spriteCounter>=10) {
			spriteValue = 1 - spriteValue;
			spriteCounter = 0;
		}
	}
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
	    int visibleX = worldX - gp.player.worldX + gp.player.screenX;
	    int visibleY = worldY - gp.player.worldY + gp.player.screenY;
	    
		if(direction == "up") {
			if(spriteValue==0) image = up1;
			else image =up2;
		}else if (direction == "down") {
			if(spriteValue==0) image = down1;
			else image =down2;
		}else if (direction == "left") {
			if(spriteValue==0) image = left1;
			else image =left2;
		}else if(direction == "right") {
			if(spriteValue==0) image = right1;
			else image =right2;
		}
	    
	    //to improve efficiency not to render whole map at once
	    if(worldX+gp.newTileSize>=gp.player.worldX - gp.player.screenX && 
	    		worldX-gp.newTileSize<=gp.player.worldX +  gp.player.screenX && 
	    		worldY+gp.newTileSize>=gp.player.worldY - gp.player.screenY && 
	    		worldY - gp.newTileSize<=gp.player.worldY + gp.player.screenY) {
	    	
			
	    		if(type==2 && hpDisplay == true) {
	    			hpCounter++;
	    			
					double scale = (double)gp.newTileSize/maxLife;
					double currentHp = scale*life;
					
					g2.setColor(new Color(35,35,35));
					g2.fillRect(visibleX-1, visibleY,gp.newTileSize + 2 , 8);
					g2.setColor(new Color(255,0,30));
					g2.fillRect(visibleX, visibleY+1,(int)currentHp , 6 );
					
					if(hpCounter>=300) {
						hpDisplay = false;
						hpCounter = 0; 
					}
	    		}

			if(this.invincible==true) {
				 g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f));
				 if(invincibleBlinkCounter>20) {
					 invincibleBlinkCounter = 0;
					 g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
				 }
			}
			
			if(this.dying == true) {
				dyingAnimation(g2);
			}
			
	    		g2.drawImage(image,visibleX,visibleY,null);
	    		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
	    }
	}
	
	public void dyingAnimation(Graphics2D g2) {
		dyingCounter++;
		if(((dyingCounter)/5)%2==0) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0f));
		}else {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		}
		
		if(dyingCounter>=monsterBlinkDuration) {
			dying = false;
			alive = false;
		}
	}
	
	
}
	