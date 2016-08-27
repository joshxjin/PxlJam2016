package Backend;

import java.util.ArrayList;

import processing.core.PApplet;

public class Bullet extends GameObject {
	private PApplet parent;
	private float speed;
	private int damage;
	private static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	public Bullet(PApplet p, float playerX, float playerY, float mouseX, float mouseY) {
		parent = p;
		this.damage = 10;
		size = 5;
		x = playerX;
		y = playerY;
		speed = 10;
		
		// Calculate speed for the bullet based on player position and mouse position
		float tempHypot = (float)(Math.hypot((playerX - mouseX), (playerY - mouseY)));
		float ratio = speed / tempHypot;
		dx = (mouseX - playerX) * ratio;
		dy = (mouseY - playerY) * ratio;
		
		bullets.add(this);
	}
	
	public static Bullet addBullet(PApplet p, float playerX, float playerY, float mouseX, float mouseY) {
		Bullet b = new Bullet(p, playerX, playerY, mouseX, mouseY);
		return b;
	}
	
	public static void moveShowBullets(ArrayList<GameObject> gameObjects) {
		ArrayList<Bullet> deadBullets = new ArrayList<Bullet>();
		if (bullets.size() > 0) {
			for (Bullet b : bullets) {
				Bullet deadBullet = b.move();
				if (deadBullet == null)
					b.show();
				else 
					deadBullets.add(deadBullet);
			}
		}
		
		if (deadBullets.size() > 0) {
			bullets.removeAll(deadBullets);
			gameObjects.removeAll(deadBullets);
		}
	}
	
	public Bullet move() {
		this.x += this.dx;
		this.y += this.dy;
		
		if (x < 0 || x > parent.width || y < 0 || y > parent.height) {
			return this;
		}
		
		return null;
	}
	
	public void show() {
		parent.fill(255, 100, 100);
		parent.ellipse(x, y, size, size);
	}

	public static ArrayList<Bullet> getBullets() {
		return bullets;
	}
	
}
