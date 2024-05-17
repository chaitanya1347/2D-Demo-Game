package main;

import character.NPC;
import monsters.GreenSlime;
import object.OBJ_BOOTS;
import object.OBJ_CHEST;
import object.OBJ_DOOR;
import object.OBJ_KEY;

public class AssignObjects {
	GamePanel gp;
	
	public AssignObjects(GamePanel gp) {
		this.gp = gp;
		
	}
	
	public void setObjects() {
		gp.obj[0] = new OBJ_KEY(gp);
		gp.obj[0].worldX = 23*gp.newTileSize;
		gp.obj[0].worldY = 7*gp.newTileSize;
		
		gp.obj[1] =new OBJ_KEY(gp);
		gp.obj[1].worldX = 23*gp.newTileSize;
		gp.obj[1].worldY = 40*gp.newTileSize;
		
		gp.obj[2] =new OBJ_DOOR(gp);
		gp.obj[2].worldX = 10*gp.newTileSize;
		gp.obj[2].worldY = 11*gp.newTileSize;

		gp.obj[3] =new OBJ_DOOR(gp);
		gp.obj[3].worldX = 8*gp.newTileSize;
		gp.obj[3].worldY = 28*gp.newTileSize;
		
		gp.obj[4] =new OBJ_DOOR(gp);
		gp.obj[4].worldX = 12*gp.newTileSize;
		gp.obj[4].worldY = 22*gp.newTileSize;
		
		gp.obj[5] =new OBJ_CHEST(gp);
		gp.obj[5].worldX = 10*gp.newTileSize;
		gp.obj[5].worldY = 7*gp.newTileSize;
		
		gp.obj[6] =new OBJ_BOOTS(gp);
		gp.obj[6].worldX = 37*gp.newTileSize;
		gp.obj[6].worldY = 42*gp.newTileSize;
		
		
		gp.obj[7] =new OBJ_KEY(gp);
		gp.obj[7].worldX = 38*gp.newTileSize;
		gp.obj[7].worldY = 9*gp.newTileSize;
		
		gp.obj[8] =new OBJ_BOOTS(gp);
		gp.obj[8].worldX = 38*gp.newTileSize;
		gp.obj[8].worldY = 42*gp.newTileSize;
	}
	public void setNpc() {
		gp.npc[0] = new NPC(gp);
		gp.npc[0].speed = 1;
		gp.npc[0].worldX = 20*gp.newTileSize;
		gp.npc[0].worldY = 21*gp.newTileSize;
		
		gp.npc[2] = new NPC(gp);
		gp.npc[2].speed = 1;
		gp.npc[2].worldX = 25*gp.newTileSize;
		gp.npc[2].worldY = 21*gp.newTileSize;

		gp.npc[1] = new NPC(gp);
		gp.npc[1].speed = 1;
		gp.npc[1].worldX = 23*gp.newTileSize;
		gp.npc[1].worldY = 22*gp.newTileSize;
	}
	
	public void setMonsters() {
		gp.monsters[0] = new GreenSlime(gp);
//		gp.monsters[0].speed = 1;
		gp.monsters[0].worldX = 23*gp.newTileSize;
		gp.monsters[0].worldY = 37*gp.newTileSize;
		
		gp.monsters[1] = new GreenSlime(gp);
//		gp.monsters[0].speed = 1;
		gp.monsters[1].worldX = 23*gp.newTileSize;
		gp.monsters[1].worldY = 17*gp.newTileSize;
	}
}
