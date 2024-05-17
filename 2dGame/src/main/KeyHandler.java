package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	public boolean upPressed,downPressed,leftPressed,rightPressed,enterPressed,cPressed,f1pressed;
	boolean pause;
	GamePanel gp;;
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	//this got activates when key is pressed
	public void keyPressed(KeyEvent e) {
		int code  = e.getKeyCode();
		if(gp.gameState==gp.titleState) {
			if(gp.ui.commandNum==0) {
				if(code==KeyEvent.VK_ENTER) {
					gp.gameState = gp.playState;
					gp.gameSound(0);
				}
			}else if(gp.ui.commandNum==2) {
				if(code==KeyEvent.VK_ENTER) {
					System.exit(0);
				}
				
			}
			if(code==KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum<0) gp.ui.commandNum = gp.ui.maxCommandValue;
			}else if(code==KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum>gp.ui.maxCommandValue) gp.ui.commandNum = 0;
			}
		}
		else if(gp.gameState == gp.playState) {
			if(code==KeyEvent.VK_W) {
				upPressed = true;
			}
			else if(code==KeyEvent.VK_S) {
				downPressed = true;
			}
			else if(code==KeyEvent.VK_D) {
				rightPressed = true;
			}
			else if(code==KeyEvent.VK_A) {
				leftPressed = true;
			}
			if(code==KeyEvent.VK_P) {
				pause = true;
				gp.gameState = gp.pauseState;

			}
			if(code==KeyEvent.VK_ENTER) {
				enterPressed = true;
			}
			if(code==KeyEvent.VK_C) {
				cPressed = true;
			}
			
			if(code==KeyEvent.VK_F1) {
				gp.gameState = gp.characterState;
			}
			
		}else if(gp.gameState == gp.pauseState) {
			if(code==KeyEvent.VK_P) {
				pause = false;
				gp.gameState = gp.playState;	
			}
		}else if(gp.gameState == gp.dialogueState) {
			if(code == KeyEvent.VK_ENTER) {
				gp.gameState = gp.playState;
			}
		}else if(gp.gameState == gp.characterState) {
			if(code==KeyEvent.VK_F1) {
				gp.gameState = gp.playState;
			}
		}
	}			
	

	@Override
	public void keyReleased(KeyEvent e) {
		int code  = e.getKeyCode();
		if(code==KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code==KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code==KeyEvent.VK_D) {
			rightPressed = false;
		}
		if(code==KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code==KeyEvent.VK_ENTER) {
			enterPressed = false;
		}

	}

}
