package main;

import java.awt.Rectangle;

import character.Entity;

public class EventHandler {
	GamePanel gp;
	
	EventRect eventRect[][];
	int prevEventX = 0,prevEventY = 0;
	boolean canTouchEvent = true;
	public boolean pitFlag = false;
//	public boolean nearWater = false;
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		eventRect = new EventRect[gp.worldRow][gp.worldCol];
		
		for(int i=0;i<gp.worldRow;i++) {
			for(int j=0;j<gp.worldCol;j++) {
				eventRect[i][j] = new EventRect();
				eventRect[i][j].x = 23;
				eventRect[i][j].x = 23;
				eventRect[i][j].width = 2;
				eventRect[i][j].height = 2;
				eventRect[i][j].eventRectDefaultX = eventRect[i][j].x;
				eventRect[i][j].eventRectDefaultY = eventRect[i][j].y;
				
			}
		}		
	}
	
	public void checkEvent() {
		int xDist = Math.abs(gp.player.worldX - prevEventX);
		int yDist = Math.abs(gp.player.worldY - prevEventY);
		int dist = Math.max(xDist,yDist);
		if(dist>=gp.newTileSize) {
			canTouchEvent =  true;
		}
		
		performPitAction();
		
		if(canTouchEvent) {
			if(hit(30,17,"right")==true) {
				performHit(30,17);
			}
		}
		
		if(gp.keyH.enterPressed==true) {
			if(healthUp(gp.player)) {
				gp.player.attackCancel = true;
				gp.player.life+=1;
			}
		}
	}
	
	public void performPitAction() {
		if(gp.keyH.cPressed == true) {
			pitFlag = false;
			gp.keyH.cPressed = false;
		}else if(pitFlag == true && gp.keyH.cPressed == false) {
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "You fell into the pit!\nPress 'C' to climb back up";
		}
	}
	
	public void performHit(int i,int j) {
		pitFlag = true;
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "You fell into the pit!\nPress 'C' to climb back up";
		gp.player.life-=1;
		canTouchEvent = false;
	}
	
	public boolean hit(int i,int j,String direction) {
		boolean flag = false;
		
		eventRect[i][j].x +=i*gp.newTileSize;
		eventRect[i][j].y +=j*gp.newTileSize;
		gp.player.SolidBody.x = gp.player.worldX + gp.player.SolidBody.x ;
		gp.player.SolidBody.y = gp.player.worldY + gp.player.SolidBody.y; 
		
		if(gp.player.SolidBody.intersects(eventRect[i][j]) ) {
			if(gp.player.direction==direction ) {
				flag = true;
				prevEventX = gp.player.worldX;
				prevEventY = gp.player.worldY;
			}
		}
		
		eventRect[i][j].x = eventRect[i][j].eventRectDefaultX;
		eventRect[i][j].y = eventRect[i][j].eventRectDefaultY;
		gp.player.SolidBody.x = gp.player.SolidBodyDefaultX;
		gp.player.SolidBody.y = gp.player.SolidBodyDefaultY;
		return flag;
	}
	
	public boolean healthUp(Entity entity) {
//		System.out.println("HELLO");
		int worldLeftX = entity.worldX + entity.SolidBody.x;
		int worldRightX = entity.worldX + entity.SolidBody.x + entity.SolidBody.width;
		int worldTopY = entity.worldY + entity.SolidBody.y;	
		int worldBottomY = entity.worldY + entity.SolidBody.y + entity.SolidBody.height;
		
		int topRow = worldTopY/gp.newTileSize;
		int bottomRow = worldBottomY/gp.newTileSize;
		int leftCol = worldLeftX/gp.newTileSize;
		int rightCol = worldRightX/gp.newTileSize;
		
		String direction = entity.direction;
		int num1=0,num2=0;
		

		if(direction == "up") {
			topRow = (worldTopY - 2*gp.newTileSize)/gp.newTileSize;
			num1 = gp.tm.mapData[topRow][leftCol];
			num2 = gp.tm.mapData[topRow][rightCol];
		}else if (direction == "down") {
			bottomRow = (worldBottomY + entity.speed)/gp.newTileSize;
			num1 = gp.tm.mapData[bottomRow][leftCol];
			num2 = gp.tm.mapData[bottomRow][rightCol];
			
		}else if (direction == "left") {
			leftCol = (worldLeftX - entity.speed)/gp.newTileSize;
			num1 = gp.tm.mapData[topRow][leftCol];
			num2 = gp.tm.mapData[bottomRow][leftCol];
		
		}else if(direction == "right") {
			rightCol = (worldRightX + entity.speed)/gp.newTileSize;
			num1 = gp.tm.mapData[topRow][rightCol];
			num2 = gp.tm.mapData[bottomRow][rightCol];

		}

		if(gp.tm.tile[num1].name=="water" || gp.tm.tile[num2].name=="water" ) return  true;
		return  false;
	}
}
