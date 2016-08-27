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
			float x = p.random(90, 900 - 45);
			float y = p.random(90, 900 - 45);
			float d = (float) (Math.hypot(x - player.getX(), y - player.getY()));
			float dCenter = (float) (Math.hypot(x - 450, y - 450));
			if (d <= (player.getSize() / 2 + 15) || dCenter <= (player.getSize() / 2 + 15)) {
				i--;
			} else {
				Obstacle o = new Obstacle(p, x, y);
				gameObjects.add(o);
			}
		}
		sp1 = new SpawnPoint(p, 15, 15, level);
		sp2 = new SpawnPoint(p, 450, 15, level);
		sp3 = new SpawnPoint(p, 900 - 15, 15, level);
		sp4 = new SpawnPoint(p, 15, 450, level);
		sp5 = new SpawnPoint(p, 900 - 15, 450, level);
		sp6 = new SpawnPoint(p, 15, 900 - 15, level);
		sp7 = new SpawnPoint(p, 450, 900 - 15, level);
		sp8 = new SpawnPoint(p, 900 - 15, 900 - 15, level);

	}
}
