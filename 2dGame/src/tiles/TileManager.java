package tiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import character.Player;
import main.GamePanel;
import main.UtilityTool;

public class TileManager extends Tile {
	public Tile[] tile = new Tile[10];
	GamePanel gp;
	Player p;

	public int mapData[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		mapData = new int[gp.worldRow][gp.worldCol];
		loadTiles();
		loadMap("/maps/world01.txt");
	}
	
	public void loadTiles() {
		setup(0,"grass",false);
		setup(1,"wall",true);
		setup(2,"water",true);
		setup(3,"earth",false);
		setup(4,"tree",true);
		setup(5,"sand",false);
	}
	
	public void setup(int index,String name,boolean collision) {
		try {
			
			UtilityTool ut = new UtilityTool();
			tile[index] =new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+name+".png"));
			tile[index].image =  ut.scaledImage(tile[index].image,gp.newTileSize,gp.newTileSize);
			tile[index].collision = collision;
			tile[index].name = name;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void loadMap(String filePath) {
		
		try {
			// just a general way to read file given the file path keep this in mind
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
		
			for(int row =0;row< gp.worldRow;row++) {
				String line = br.readLine();
				String numbers[] = line.split(" ");
				for(int col =0;col<gp.worldCol;col++) {
					int ind = Integer.parseInt(numbers[col]);
					mapData[row][col] = ind;
				}
			}
			br.close();
			
		}catch(Exception e) {

		}
	}
	
	public void draw(Graphics2D g2) {
 
		for(int row =0;row< gp.worldRow;row++) {
			for(int col =0;col<gp.worldCol;col++) {
				int ind = mapData[row][col];
				BufferedImage img = tile[ind].image;
				
				int actualMapX = col * gp.newTileSize; 
			    int actualMapY = row * gp.newTileSize;
			    int visibleX = actualMapX - gp.player.worldX + gp.player.screenX;
			    int visibleY = actualMapY - gp.player.worldY + gp.player.screenY;
			    
			    
			    //to improve efficiency not to render whole map at once
			    if(actualMapX+gp.newTileSize>=gp.player.worldX - gp.player.screenX && 
			    		actualMapX-gp.newTileSize<=gp.player.worldX +  gp.player.screenX && 
			    		actualMapY+gp.newTileSize>=gp.player.worldY - gp.player.screenY && 
			    		actualMapY - gp.newTileSize<=gp.player.worldY + gp.player.screenY) {
			    		g2.drawImage(img,visibleX,visibleY,null);
			    }
			}

		}
		
	}
}
