package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// Create a window
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setTitle("Treasure Hunt");
		
		//adding GamePanel to the window
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		//necessary to add the prefered size into our game
		window.pack();
		
		//Position the window on the center of the screen
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		gamePanel.setupGame();
		gamePanel.startGameThread();
	}

}
