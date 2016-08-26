package Frontend;

import java.awt.Rectangle;
import java.util.ArrayList;

import Backend.Bullet;
import Backend.GameObject;
import Backend.Monster;
import Backend.Player;
import Backend.QuadTree;
import processing.core.PApplet;

public class Application extends PApplet {

	Player player = new Player(this, 450, 450, 5, 5);
	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	QuadTree qt = new QuadTree(0, new Rectangle(0, 0, 900, 900));

	public static void main(String[] args) {
		PApplet.main("Frontend.Application");

	}

	public void settings() {
		size(900, 900);
	}

	public void setup() {
		frameRate(60);

		for (int i = 0; i < 10; i++) {
			Monster m = new Monster(this, random(this.width), random(this.height), 2, 10);
			gameObjects.add(m);
		}

		gameObjects.add(player);

	}

	public void mousePressed() {
		Bullet b = null;

		if (mouseButton == LEFT) {
			b = Bullet.addBullet(this, player.getX(), player.getY(), mouseX, mouseY);
		} else if (mouseButton == RIGHT) {
			b = Bullet.addBullet(this, player.getX(), player.getY(), mouseX, mouseY);
		}

		gameObjects.add(b);
	}

	public void keyPressed() {
		player.setDir();
	}

	public void keyReleased() {
		player.stopDir();
	}

	public void draw() {
		background(200);

		checkCollision();

		player.move();
		player.draw();
		Bullet.moveShowBullets();
		//Monster.moveMonsters(player.getX(), player.getY());
		Monster.showMonsters();
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
					float d = (float) (Math.hypot(b.getX() - returnList.get(j).getX(), b.getY() - returnList.get(j).getY()));
					if (d <= b.getSize() + 10 + returnList.size() && !(returnList.get(j) instanceof Player)) {
						if (returnList.get(j) instanceof Monster) {
							removeList.add(b);
							removeList.add(returnList.get(j));
							Bullet.getBullets().remove(b);
							Monster.getMonsters().remove(returnList.get(j));
							System.out.println("Collision!");
						}
					}
				}
			} else if (gameObjects.get(i) instanceof Monster) {
				Monster m = (Monster) gameObjects.get(i);
				for (int j = 0; j < returnList.size(); j++) {
					float d = (float) (Math.hypot(m.getX() - returnList.get(j).getX(), m.getY() - returnList.get(j).getY()));
					if (d <= m.getSize() + 3 + returnList.size() && returnList.get(j) instanceof Player) {
						System.out.println("Game Over!");
					} else if (d <= m.getSize() + 3 + returnList.size() && returnList.get(j) instanceof Bullet) {
						if (returnList.get(j) instanceof Monster) {
							removeList.add(m);
							removeList.add(returnList.get(j));
							Bullet.getBullets().remove(m);
							Monster.getMonsters().remove(returnList.get(j));
							System.out.println("Collision!");
						}
					} else if (d <= m.getSize() + 3 + returnList.size() && returnList.get(j) instanceof Monster && returnList.get(j) != m) {
						break;
					} else {
						m.setMove(player.getX(), player.getY());
					}
				}
				m.move();
			}
			
		}

		gameObjects.removeAll(removeList);

	}

}