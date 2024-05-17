package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import object.OBJ_HEART;
import object.OBJ_KEY;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	BufferedImage imgKey,heart_full,heart_half,heart_blank;
	Font arial_30,arial_80B,purisaB;
	String message;
	int messageOn = 0;
	int messageCounter = 0;
	double gameTimer = 0;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	public boolean gameFinished = false;
	public String currentDialogue;
	int commandNum = 0;
	public int maxCommandValue = 2;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		arial_30  = new Font("Arial",Font.PLAIN, 30);
		arial_80B = new Font("Arial",Font.BOLD,80);
		
		InputStream is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
		try {
			purisaB = Font.createFont(Font.TRUETYPE_FONT,is);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		OBJ_KEY key = new OBJ_KEY(gp);
		imgKey = key.down1;
		
		OBJ_HEART heart = new OBJ_HEART(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
	}
	
	public void drawAtCenter(String message, int x,int y,Graphics2D g2) {
		
		int textLength = (int)g2.getFontMetrics().getStringBounds(message,g2).getWidth();
		x-=textLength/2;
		g2.drawString(message, x, y);
	}
	
	public int alignAtCenter(String message, int x) {
		
		int textLength = (int)g2.getFontMetrics().getStringBounds(message,g2).getWidth();
		x-=textLength;
		return x;
	}
	
	public void generalFont(Graphics2D g2) {
		g2.setFont(purisaB);
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,25));
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		generalFont(g2);
		
		
		//Title Sttae
		if(gp.gameState == gp.titleState) {
			drawTitleState();
		}
		else {
			//Paused State
			if(gp.gameState==gp.pauseState) {
				g2.setFont(g2.getFont().deriveFont(Font.BOLD,80));
				g2.setColor(Color.yellow);
				drawAtCenter("Paused",gp.screenWidth/2,gp.screenHeight/2,g2);
			}
			
			//Dialogue State
			
			if(gp.gameState == gp.dialogueState) {
				displayDialogueBox();
			}
			
			if(gp.gameState == gp.characterState) {
				drawCharacterScreen();
			}
			
			if(gp.gameState == gp.playState) {
			if(gameFinished) {
				g2.setFont(arial_30);
				g2.setColor(Color.white);
				
				message = "You Found The Treasure";
				int x = gp.screenWidth/2;
				int y = gp.screenHeight/2 - 3*gp.newTileSize;
				drawAtCenter(message,x,y,g2);
				
				message = "Congratulations";
				g2.setFont(arial_80B);
				g2.setColor(Color.yellow);
				x = gp.screenWidth/2;
				y = gp.screenHeight/2 + gp.newTileSize; 
				drawAtCenter(message,x,y,g2);
				
				message = "Total Play Time = "+ dFormat.format(gameTimer);
				g2.setFont(arial_30);
				g2.setColor(Color.white);
				x = gp.screenWidth/2;
				y = gp.screenHeight/2 + (int)3.5*gp.newTileSize; 
				drawAtCenter(message,x,y,g2);
				
				gp.gameThread =null;
				
			}else {
				generalFont(g2);
				g2.setColor(Color.white);
				
				//draw the key image
				g2.drawImage(imgKey, gp.newTileSize/2, gp.newTileSize/2-gp.newTileSize/4, gp.newTileSize, gp.newTileSize, null);
				
				//draw health bar
				this.drawHealthBar();
				
				g2.drawString("Time = "+dFormat.format(gameTimer),(int)11.5*gp.newTileSize,40);
				g2.drawString("Key="+gp.player.keyCount, 75, 40);
				
				//if we hit the object then what to diplay
				if(messageCounter>100) {
					messageOn = 0;
					messageCounter = 0;
				}
				if(messageOn==1) {
					messageCounter++;
					g2.setFont(g2.getFont().deriveFont(30));
					g2.drawString(message, gp.newTileSize/2, 5*gp.newTileSize);
				}
				
				if(gp.pauseState!=gp.gameState) gameTimer+=(double) 1/60;
				
			}
		}
	}
		
}
	public void drawCharacterScreen() {
		Color color = new Color(0,0,0,210);
		int frameWidth = 5*gp.newTileSize;
		int frameHeight = 10*gp.newTileSize;
		int arcSize = 35;
		rectangleStrokeMethod(gp.newTileSize,gp.newTileSize,frameWidth,frameHeight,g2,arcSize,color);
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(19F));
		int x = gp.newTileSize + 20;
		int y = gp.newTileSize + 35;
		int space =42;
		 
		g2.drawString("Level", x, y);
		y+=space;
		g2.drawString("Life", x, y);
		y+=space;
		g2.drawString("Strength", x, y);
		y+=space;
		g2.drawString("Dexterity", x, y);
		y+=space;
		g2.drawString("Attack", x, y);
		y+=space;
		g2.drawString("Defence", x, y);
		y+=space;
		g2.drawString("Exp", x, y);
		y+=space;
		g2.drawString("Coin", x, y);
		y+=5*space/4 - 5;
		g2.drawString("Weapon", x, y);
		y+=5*space/4 + 10;
		g2.drawString("Sheild", x, y);
		
		
		g2.setFont(g2.getFont().deriveFont(22F));
		int tailX = gp.newTileSize + frameWidth -30;
		y = gp.newTileSize + 35;
		String value;
		
		value = String.valueOf(gp.player.level);
		x = alignAtCenter(value,tailX);
		g2.drawString(value, x, y);
		y+=space;
		
		value = String.valueOf(gp.player.life);
		value+="/" + gp.player.maxLife;
		x = alignAtCenter(value,tailX);
		g2.drawString(value, x, y);
		y+=space;
		
		value = String.valueOf(gp.player.strength);
		x = alignAtCenter(value,tailX);
		g2.drawString(value, x, y);
		y+=space;
		
		value = String.valueOf(gp.player.dexterity);
		x = alignAtCenter(value,tailX);
		g2.drawString(value, x, y);
		y+=space;
		
		value = String.valueOf(gp.player.attack);
		x = alignAtCenter(value,tailX);
		g2.drawString(value, x, y);
		y+=space;
		
		value = String.valueOf(gp.player.defense);
		x = alignAtCenter(value,tailX);
		g2.drawString(value, x, y);
		y+=space;
		
		value = String.valueOf(gp.player.exp);
		x = alignAtCenter(value,tailX);
		g2.drawString(value, x, y);
		y+=space;
		
		value = String.valueOf(gp.player.coin);
		x = alignAtCenter(value,tailX);
		g2.drawString(value, x, y);
		y+=space/2;
		
		g2.drawImage(gp.player.currentWeapon.image,tailX - gp.newTileSize, y,null);
		y+=5*space/4 + 6;
		g2.drawImage(gp.player.currentShield.image,tailX - gp.newTileSize, y,null);
		

	}
	public void drawHealthBar() {
		if(gp.player.life<=0) gp.player.life =0;
		if(gp.player.life>=gp.player.maxLife) gp.player.life =gp.player.maxLife;
		
		int full = gp.player.life/2;
		int blank = (gp.player.maxLife - gp.player.life)/2;
		int half = ((gp.player.maxLife)/2) - full - blank;
		int x = gp.newTileSize/2;
		//at what y to draw health
		int y = 5*gp.newTileSize/4;
		while(full!=0) {
			g2.drawImage(heart_full, x,y, null);
			full-=1;
			x+=gp.newTileSize;	
		}
		while(half!=0) {
			g2.drawImage(heart_half, x,y, null);
			half-=1;
			x+=gp.newTileSize;	
		}
		while(blank!=0) {
			g2.drawImage(heart_blank, x,y, null);
			blank-=1;
			x+=gp.newTileSize;	
		}
	}
	
	public void drawTitleState() {
		// Main line
		int x,y;
		String text = "MY FIRST GAME";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,50));
		x = gp.screenWidth/2;
		y = 3*gp.newTileSize;	
		g2.setColor(Color.yellow);
		drawAtCenter(text,x,y,g2);
	
		
		//player
		x = gp.screenWidth/2 - gp.newTileSize;
		y = gp.screenHeight/2-gp.newTileSize;
		g2.drawImage(gp.player.down1, x, y,2*gp.newTileSize,2*gp.newTileSize,null);
		
		//Remaining lines
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,30));	
		g2.setColor(Color.white);
		text = "NEW GAME";
		x = gp.screenWidth/2;
		y += 3.5*gp.newTileSize;
		drawAtCenter(text,x,y,g2);
		
		if(commandNum==0) {
			Color color = new Color(0,0,0,150);
			rectangleStrokeMethod(x - 5* gp.newTileSize/2,y-(3*gp.newTileSize/4),5*gp.newTileSize,gp.newTileSize,g2,20,color);
		}
		
		text = "LOAD GAME";
		x = gp.screenWidth/2;
		y += gp.newTileSize;
		drawAtCenter(text,x,y,g2);
		
		if(commandNum==1) {
			Color color = new Color(0,0,0,150);
			rectangleStrokeMethod(x - 3* gp.newTileSize,y-(3*gp.newTileSize/4),6*gp.newTileSize,gp.newTileSize,g2,20,color);
		}
		
		text = "QUIT";
		x = gp.screenWidth/2;
		y += gp.newTileSize;
		drawAtCenter(text,x,y,g2);
		
		if(commandNum==2) {
			Color color = new Color(0,0,0,150);
			rectangleStrokeMethod(x - 2* gp.newTileSize,y-(3*gp.newTileSize/4),4*gp.newTileSize,gp.newTileSize,g2,20,color);
		}
		
	}
	
	public void rectangleStrokeMethod(int x,int y,int width,int height,Graphics2D g2,int arcSize,Color color) {
		
		g2.setColor(color);
		g2.fillRoundRect(x, y, width, height, arcSize, arcSize);
		color = new Color(255,255,255);
		g2.setColor(color);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, arcSize, arcSize);
	}
	
	public void displayDialogueBox() {
		int x = gp.screenWidth/2-5*gp.newTileSize;
		int y = gp.newTileSize;
		int width = 12*gp.newTileSize;
		int height = 4*gp.newTileSize;
		Color color = new Color(0,0,0,210);
		
		rectangleStrokeMethod(x,y,width,height,g2,35,color);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20));
		g2.setColor(Color.yellow);
		x+=gp.newTileSize/2;
		y+=gp.newTileSize;
		for (String line: currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y+=35;
		}
	}

	public void showMessage(String text) {
		message = text;
		messageOn = 1;
	}
}
