package main;

import character.Entity;

public class CollisionChecker {
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public void updateCollision(Entity entity) {
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
			topRow = (worldTopY - entity.speed)/gp.newTileSize;
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
		
		if(gp.tm.tile[num1].collision==true || gp.tm.tile[num2].collision==true ) entity.collision = true;
		else entity.collision = false;
	}
	
	public int objectCollision(Entity entity,boolean flag) {
		int ind = -1;
		for(int i=0;i<gp.obj.length;i++) {
			if(gp.obj[i]!=null) {
				entity.SolidBody.x = entity.worldX + entity.SolidBody.x;
				entity.SolidBody.y = entity.worldY + entity.SolidBody.y;
				
				int num1,num2;
				num1 = gp.obj[i].SolidBody.x;
				num2 = gp.obj[i].SolidBody.y;
				gp.obj[i].SolidBody.x = gp.obj[i].worldX;
				gp.obj[i].SolidBody.y = gp.obj[i].worldY;
				String direction = entity.direction;
				
				if(direction == "up") {
					entity.SolidBody.y -=entity.speed;
				}else if (direction == "down") {
					entity.SolidBody.y +=entity.speed;
				}else if (direction == "left") {
					entity.SolidBody.x -=entity.speed;
				}else if(direction == "right") {
					entity.SolidBody.x +=entity.speed;
				}
				
				if(entity.SolidBody.intersects(gp.obj[i].SolidBody)) {
					if(gp.obj[i].collision) {
						entity.collision = true;
					}
					if(flag) ind = i;
				}
				entity.SolidBody.x = entity.SolidBodyDefaultX;
				entity.SolidBody.y = entity.SolidBodyDefaultY;
				gp.obj[i].SolidBody.x = num1;
				gp.obj[i].SolidBody.y = num2;
				if(ind!=-1) return ind;
			}
			
		}
		return ind;
	}
	
	public int checkEntity(Entity entity,Entity target[]) {
		int ind = -1;
		for(int i=0;i<target.length;i++) {
			if(target[i]!=null) {
				entity.SolidBody.x = entity.worldX + entity.SolidBody.x;
				entity.SolidBody.y = entity.worldY + entity.SolidBody.y;
				
				target[i].SolidBody.x = target[i].worldX + target[i].SolidBody.x;
				target[i].SolidBody.y = target[i].worldY + target[i].SolidBody.y;
				

				String direction = entity.direction;
				
				if(direction == "up") {
					entity.SolidBody.y -=entity.speed;
				}else if (direction == "down") {
					entity.SolidBody.y +=entity.speed;
				}else if (direction == "left") {
					entity.SolidBody.x -=entity.speed;
				}else if(direction == "right") {
					entity.SolidBody.x +=entity.speed;
				}
				
				if(entity.SolidBody.intersects(target[i].SolidBody)) {
					if(target[i]!=entity) {
						entity.collision = true;
						ind = i;
					}
				}
				
				entity.SolidBody.x = entity.SolidBodyDefaultX;
				entity.SolidBody.y = entity.SolidBodyDefaultY;
				target[i].SolidBody.x = target[i].SolidBodyDefaultX;
				target[i].SolidBody.y = target[i].SolidBodyDefaultY;
				
				if(ind!=-1) return ind;
			}
			
		}
		return ind;
	}
	
	public boolean npcToPlayerCollision(Entity entity) {
				boolean contactPlayer =false;
				entity.SolidBody.x = entity.worldX + entity.SolidBody.x;
				entity.SolidBody.y = entity.worldY + entity.SolidBody.y;

				gp.player.SolidBody.x = gp.player.worldX + gp.player.SolidBody.x ;
				gp.player.SolidBody.y = gp.player.worldY + gp.player.SolidBody.y; 
				
				String direction = entity.direction;
				 	
				if(direction == "up") {
					entity.SolidBody.y -= 10*entity.speed;
//					System.out.println("DIRECTION IS UP");
				}else if (direction == "down") {
					entity.SolidBody.y += entity.speed;
				}else if (direction == "left") {
					entity.SolidBody.x -= entity.speed;
				}else if(direction == "right") {
					entity.SolidBody.x += entity.speed;
				}
				if(entity.SolidBody.intersects(gp.player.SolidBody)) {
					entity.collision = true;
					contactPlayer = true;
				}
				
				entity.SolidBody.x = entity.SolidBodyDefaultX;
				entity.SolidBody.y = entity.SolidBodyDefaultY;	
				gp.player.SolidBody.x = gp.player.SolidBodyDefaultX;
				gp.player.SolidBody.y = gp.player.SolidBodyDefaultX;
				
				return contactPlayer;
			}
			
}

