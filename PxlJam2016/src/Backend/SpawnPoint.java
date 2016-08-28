package Backend;

import java.util.ArrayList;
import java.util.Random;

import Frontend.Application;
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

	public static void spawn(PApplet p, ArrayList<GameObject> gameObjects) {		//DC: modified spawn method to include Teleporters
		Random r = new Random();

		SpawnPoint sp = spawnPoints.get(r.nextInt(spawnPoints.size()));

		if (sp.level >= 3) {
			
			float teleportChance = 0;
			if (Application.teleportGlitch) {
				teleportChance = (float)0.2;
			}
			float z = r.nextFloat();
			
			if(z < teleportChance){
				TeleportMonster m = new TeleportMonster(p, sp.getX(), sp.getY(), (float) (2 + 0.1 * sp.level));
				gameObjects.add(m);
			}			
			else if (z <= 0.3 + teleportChance) {
				SnakeMonster m = new SnakeMonster(p, sp.getX(), sp.getY(), (float) (2 + 0.1 * sp.level));
				gameObjects.add(m);
			} else {
				Monster m = new Monster(p, sp.getX(), sp.getY(), (float) (2 + 0.2 * sp.level));
				gameObjects.add(m);
			}
			
		} else {
			float teleportChance = 0;
			if (Application.teleportGlitch) {
				teleportChance = (float)0.2;
			}
			if (r.nextFloat() < teleportChance){
				TeleportMonster m = new TeleportMonster(p, sp.getX(), sp.getY(), (float) (2 + 0.1 * sp.level));
				gameObjects.add(m);
			}
			else{			
				Monster m = new Monster(p, sp.getX(), sp.getY(), (float) (2 + 0.2 * sp.level));
				gameObjects.add(m);
			}
		}
	}

	public static ArrayList<SpawnPoint> getSpawnPoints() {
		return spawnPoints;
	}

}