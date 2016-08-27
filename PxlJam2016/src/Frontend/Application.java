package Frontend;

import java.awt.Rectangle;
import java.util.ArrayList;

import Backend.Bullet;
import Backend.GameObject;
import Backend.Levels;
import Backend.Monster;
import Backend.Obstacle;
import Backend.Player;
import Backend.QuadTree;
import Backend.SnakeMonster;
import Backend.SpawnPoint;
import processing.core.PApplet;
import processing.core.PImage;//IMAGE RELEVANT

public class Application extends PApplet {

	public static PImage shipPic;// IMAGE RELEVANT
	public static PImage monsterPic;// IMAGE RELEVANT
	public static PImage snakeMonsterPic;// IMAGE RELEVANT
	public static PImage heart;// IMAGE RELEVANT
	public static PImage rock; // IMAGE RELEVANT
	Player player = new Player(this, 450, 450, 5, 5);
	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	QuadTree qt = new QuadTree(0, new Rectangle(0, 0, 900, 900));

	int level = 1;
	int levelFrame = 900;
	int spawnFrame = 100;
	boolean gameOver = false;

	public static void main(String[] args) {
		PApplet.main("Frontend.Application");

	}

	public void settings() {
		size(900, 900);
	}

	public void setup() {
		frameRate(60);

		// for (int i = 0; i < 10; i++) {
		// Monster m = new Monster(this, random(this.width),
		// random(this.height), 1, 10);
		// gameObjects.add(m);
		// }
		//
		// Monster m = new Monster(this, 100, 100, 2, 10);
		// Monster m0 = new SnakeMonster(this, random(this.width),
		// random(this.height), 1, 10);

		// gameObjects.add(m);
		// gameObjects.add(m0);

		gameObjects.add(player);

		// Obstacle o = new Obstacle(this, 300, 300);
		// gameObjects.add(o);

		Levels.loadLevel(this, player, gameObjects, level);

		monsterPic = loadImage("monster1.png");// IMAGE RELEVANT
		snakeMonsterPic = loadImage("monster2.png");// IMAGE RELEVANT
		shipPic = loadImage("AVerySillyShip2.png");// IMAGE RELEVANT
		heart = loadImage("heart.png");// IMAGE RELEVANT
		rock = loadImage("definitelyARock.png"); // IMAGE RELEVANT

	}

	public void mousePressed() {
		if (gameOver) {
			gameOver = false;
			player = new Player(this, 450, 450, 5, 5);
			gameObjects.add(player);
			level = 1;
			Levels.loadLevel(this, player, gameObjects, level);
			frameCount = 0;
			levelFrame = 900;
			spawnFrame = 100;
			return;
		}
		player.shoot(gameObjects);
	}

	public void keyPressed() {
		player.setDir();
	}

	public void keyReleased() {
		player.stopDir();
	}

	public void draw() {
		if (gameOver) {
			return;
		}
		background(255);
		fill(0, 0, 0);
		// noStroke();
		rect(0, 0, 900, 30);
		rect(0, 0, 30, 900);
		rect(900 - 30, 0, 30, 900);
		rect(0, 900 - 30, 900, 30);

		levelSpawn();

		textAlign(LEFT);
		textSize(10);
		text("Frame: " + frameCount, 800, 60);
		textSize(10);
		text("Level: " + level, 800, 70);

		imageMode(CENTER);// IMAGE RELEVANT

		player.drawPlayerHealth();

		Bullet.moveShowBullets(gameObjects);
		checkCollision();

		player.move();
		player.draw();

		// Monster.moveMonsters(player.getX(), player.getY());
		Monster.showMonsters();

		Obstacle.showObstacles();
	}

	public void checkCollision() {
		qt.clear();
		for (GameObject obj : gameObjects) {
			qt.insert(obj);
		}

		ArrayList<GameObject> returnList = new ArrayList<GameObject>();
		ArrayList<GameObject> removeList = new ArrayList<GameObject>();

		for (int i = 0; i < gameObjects.size(); i++) {

			returnList.clear();
			qt.retrieve(returnList, gameObjects.get(i));

			if (gameObjects.get(i) instanceof Bullet) {

				Bullet b = (Bullet) gameObjects.get(i);
				for (int j = 0; j < returnList.size(); j++) {
					float d = (float) (Math.hypot(b.getX() - returnList.get(j).getX(),
							b.getY() - returnList.get(j).getY()));

					if (d <= (b.getSize() + returnList.get(j).getSize()) / 2 && !(returnList.get(j) instanceof Player)
							&& !(returnList.get(j) instanceof Bullet)) {
						if (returnList.get(j) instanceof Monster) {
							removeList.add(b);
							removeList.add(returnList.get(j));
							Bullet.getBullets().remove(b);
							Monster.getMonsters().remove(returnList.get(j));
							break;
						}

						if (returnList.get(j) instanceof Obstacle) {
							removeList.add(b);
							Bullet.getBullets().remove(b);
							break;
						}
					}
				}
			}

			else if (gameObjects.get(i) instanceof Monster) {
				Monster m = (Monster) gameObjects.get(i);
				for (int j = 0; j < returnList.size(); j++) {
					float d = (float) (Math.hypot(m.getX() - returnList.get(j).getX(), m.getY() - returnList.get(j).getY()));
					if (d <= (m.getSize() + returnList.get(j).getSize()) / 2 && returnList.get(j) instanceof Player) {
						player.setHealth(player.getHealth() - 1);
						if (player.getHealth() == 0) {
							gameOver = true;
							textSize(30);
							textAlign(CENTER);
							text("Game Over!", 450, 450);
							Bullet.getBullets().clear();
							Monster.getMonsters().clear();
							Obstacle.getObstacles().clear();
							SpawnPoint.getSpawnPoints().clear();
							gameObjects.clear();
						} else {
							player.setX(450);
							player.setY(450);
							gameObjects.removeAll(Monster.getMonsters());
							gameObjects.removeAll(Bullet.getBullets());
							Monster.getMonsters().clear();
							Bullet.getBullets().clear();
							frameCount = levelFrame * (level - 1);
						}
						return;
					} else { // DC: removed Monster/Monster collision check
						m.setMove(player.getX(), player.getY());
					}
				}
				m.move();
			}

		}

		gameObjects.removeAll(removeList);

	}

	public void levelSpawn() {
		if (frameCount % levelFrame == 0) {
			// gameObjects.removeAll(Obstacle.getObstacles());
			SpawnPoint.getSpawnPoints().clear();
			// Obstacle.getObstacles().clear();
			level++;
			Levels.loadLevel(this, player, gameObjects, level);
			spawnFrame = spawnFrame - 5;
		}

		if (frameCount % spawnFrame == 0) {
			SpawnPoint.spawn(this, gameObjects);
		}
	}

}