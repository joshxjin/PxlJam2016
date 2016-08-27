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
	
	public static void loadLevel(PApplet p, ArrayList<GameObject> gameObjects, int level) {
		
		switch(level) {
		case 1:
			for (int i = 0; i < 5; i++) {
				Obstacle o = new Obstacle(p, p.random(30, 900 - 30), p.random(30, 900 - 60));
				gameObjects.add(o);
			}
			sp1 = new SpawnPoint(p, 15, 15, level);
			sp2 = new SpawnPoint(p, 900 - 15, 15, level);
			sp3 = new SpawnPoint(p, 450, 900 - 15, level);
			break;
		case 2:
			for (int i = 0; i < 10; i++) {
				Obstacle o = new Obstacle(p, p.random(30, 900 - 30), p.random(30, 900 - 60));
				gameObjects.add(o);
			}
			sp1 = new SpawnPoint(p, 15, 900 - 15, level);
			sp2 = new SpawnPoint(p, 900 - 15, 900 - 15, level);
			sp3 = new SpawnPoint(p, 450, 15, level);
			break;
		case 3:
			for (int i = 0; i < 15; i++) {
				Obstacle o = new Obstacle(p, p.random(30, 900 - 30), p.random(30, 900 - 60));
				gameObjects.add(o);
			}
			sp1 = new SpawnPoint(p, 15, 15, level);
			sp2 = new SpawnPoint(p, 900 - 15, 15, level);
			sp3 = new SpawnPoint(p, 900 - 15, 900 - 15, level);
			sp4 = new SpawnPoint(p, 15, 900 - 15, level);
			break;
		case 4:
			for (int i = 0; i < 20; i++) {
				Obstacle o = new Obstacle(p, p.random(30, 900 - 30), p.random(30, 900 - 60));
				gameObjects.add(o);
			}
			sp1 = new SpawnPoint(p, 450, 15, level);
			sp2 = new SpawnPoint(p, 900 - 15, 450, level);
			sp3 = new SpawnPoint(p, 450, 900 - 15, level);
			sp4 = new SpawnPoint(p, 15, 450, level);
			break;
		case 5:
			for (int i = 0; i < 30; i++) {
				Obstacle o = new Obstacle(p, p.random(30, 900 - 30), p.random(30, 900 - 60));
				gameObjects.add(o);
			}
			sp1 = new SpawnPoint(p, 450, 15, level);
			sp2 = new SpawnPoint(p, 900 - 15, 450, level);
			sp3 = new SpawnPoint(p, 450, 900 - 15, level);
			sp4 = new SpawnPoint(p, 15, 450, level);
			sp5 = new SpawnPoint(p, 15, 15, level);
			break;
		default:
			for (int i = 0; i < 40; i++) {
				Obstacle o = new Obstacle(p, p.random(30, 900 - 30), p.random(30, 900 - 60));
				gameObjects.add(o);
			}
			sp1 = new SpawnPoint(p, 450, 15, level);
			sp2 = new SpawnPoint(p, 900 - 15, 450, level);
			sp3 = new SpawnPoint(p, 450, 900 - 15, level);
			sp4 = new SpawnPoint(p, 15, 450, level);
			sp5 = new SpawnPoint(p, 900 - 15, 900 - 15, level);
			break;
		}
	}
}
