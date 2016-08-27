package Backend;

import java.util.ArrayList;

import processing.core.PApplet;

public class Levels {
	
	private static ArrayList<GameObject> level1, level2, level3, level4, level5;
	
	static SpawnPoint sp1;
	static SpawnPoint sp2;
	static SpawnPoint sp3;
	static SpawnPoint sp4;
	static SpawnPoint sp5;
	static SpawnPoint sp6;
	static SpawnPoint sp7;
	static SpawnPoint sp8;
	
	public static void loadLevel(PApplet p, Player player, ArrayList<GameObject> gameObjects, int level) {
		
		for (int i = 0; i < 2; i++) {
			float x = p.random(90, 900 - 90);
			float y = p.random(90, 900 - 90);
			float d = (float)(Math.hypot(x-player.getX(), y-player.getY()));
			if (d <= (player.getSize()/2 + 15))
				i--;
			else {
				Obstacle o = new Obstacle(p, x, y);
				gameObjects.add(o);
			}
		}
		sp1 = new SpawnPoint(p, 15, 15, level);
		sp2 = new SpawnPoint(p, 900 - 15, 15, level);
		sp3 = new SpawnPoint(p, 450, 900 - 15, level);
		
	}
}
