package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import character.Entity;
import character.NPC;
import character.Player;
import object.OBJ_KEY;
import tiles.TileManager;

public class GamePanel extends JPanel implements Runnable{
	//Screen features
	int FPS = 60;
	
	public final int windowTileSize = 16;
	public final int scaleFactor = 3;
	public final int newTileSize = windowTileSize * scaleFactor;
	
	public  final int tilesHorizontal = 16;
	public  final int tilesVertical = 12;
	
	public final int screenWidth = tilesHorizontal*newTileSize;
	public  final int screenHeight = tilesVertical*newTileSize;
	
	
	//WORLD

	
	public  final int worldCol = 50;
	public  final int worldRow = 50;
	
	public final int worldWidth = tilesHorizontal*newTileSize;
	public  final int worldHeight = tilesVertical*newTileSize;
	
	public int gameState;
	public int playState = 0;
	public int pauseState = 1;
	public int dialogueState = 2;
	public int titleState = 3;
	public int characterState = 4;
	
	
	Thread gameThread;
	public KeyHandler keyH = new KeyHandler(this);
	TileManager tm = new TileManager(this);
	AssignObjects assign = new AssignObjects(this);
	Sound music  = new Sound();
	Sound se = new Sound();
	public UI ui = new UI(this);
	public CollisionChecker cChecker = new CollisionChecker(this);
	public EventHandler eh = new EventHandler(this);
	
	
	//Plyaer, objects,NPC
	public Player player = new Player(this,keyH);
	public Entity obj[] = new Entity[10];
	public NPC npc[] = new NPC[10];
	public Entity monsters[] = new Entity[20];
	ArrayList<Entity> entityList = new ArrayList<>();

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.run();
	}
	
	@Override
	public void run() {
//		System.out.println("hello");
		
		double drawInterval = 1000000000/FPS;
		long lastTime = System.nanoTime();
		long currentTime;
		double delta = 0;
		
		long timer = 0;
		long showFps = 0;
		
		while(gameThread!=null) {
			
			currentTime = System.nanoTime();
			
			delta+=(currentTime - lastTime)/drawInterval;
			timer+=(currentTime - lastTime);
			
			lastTime = currentTime;
			
			if(delta>=1) {
				update();
				repaint();
				delta--;
				showFps++;
			}
			
			if(timer>=1000000000) {
				System.out.println("FPS:" + showFps);
				timer = 0;
				showFps =0;			
			}		
		}		
	}  
	public void setupGame() {
		gameState = titleState;
		assign.setObjects();
		assign.setNpc();
		assign.setMonsters();
	}
	
	public void update() {
//		System.out.println(gameState);
//		System.out.println(playState);
		if(gameState == playState) {
			player.update();
			
			for(int i=0;i<npc.length;i++) {
				if(npc[i]!=null) {
					npc[i].update();
				}
			}
			
			for(int i=0;i<monsters.length;i++) {
				if(monsters[i]!=null) {
					if(monsters[i].alive==true && monsters[i].dying == false)
					monsters[i].update();
					
					if(monsters[i].alive==false) monsters[i] = null;
				}
			}
			
		}else if(gameState == pauseState) {
			
		}else if(gameState == dialogueState) {
			
		}
		
	}
	//use to draw the updated frames
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g);
		
		if(gameState == titleState ) {
			this.ui.draw(g2);
		}else {
			//tiles
			tm.draw(g2);
			
			entityList.add(player);
			
			for(int i=0;i<obj.length;i++) {
				if(obj[i]!=null) {
					entityList.add(obj[i]);
				}
			}
			
			for(int i=0;i<npc.length;i++) {
				if(npc[i]!=null) {
					entityList.add(npc[i]);
				}
			}
			
			for(int i=0;i<monsters.length;i++) {
				if(monsters[i]!=null) {
					entityList.add(monsters[i]);
				}
			}
			
			Collections.sort(entityList,new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					int res = Integer.compare(e1.worldY, e2.worldY);
					return res;
				}
				
			});
			
			for(int i=0;i<entityList.size();i++) {
				entityList.get(i).draw(g2);
			}
			
			entityList.clear();
			//keys count, timeer
			ui.draw(g2);
		}
	}
	
	public void gameSound(int i) {
		 music.setFile(i);
		 music.play();
		 music.loop();
	}
	public void stop() {
		 music.stop();
	}
	public void soundSE(int i) {
		 se.setFile(i);
		 se.play();
	}
}
