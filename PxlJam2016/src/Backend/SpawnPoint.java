package Backend;

import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;

public class SpawnPoint extends GameObject {

	private static ArrayList<SpawnPoint> spawnPoints = new ArrayList<SpawnPoint>();
	PApplet parent;
	int level;

	public SpawnPoint(PApplet p, float x, float y, int level) {
		this.x = x;
		this.y = y;
		parent = p;
		this.level = level;

		spawnPoints.add(this);
	}

	public static void spawn(PApplet p, ArrayList<GameObject> gameObjects) {
		Random r = new Random();

		SpawnPoint sp = spawnPoints.get(r.nextInt(spawnPoints.size()));

		if (r.nextFloat() <= 0.3) {
			SnakeMonster m = new SnakeMonster(p, sp.getX(), sp.getY(), (float) (2 + 0.1 * sp.level), 10);
			gameObjects.add(m);
		} else {
			Monster m = new Monster(p, sp.getX(), sp.getY(), (float) (2 + 0.1 * sp.level), 10);
			gameObjects.add(m);
		}
	}

	public static ArrayList<SpawnPoint> getSpawnPoints() {
		return spawnPoints;
	}

}
