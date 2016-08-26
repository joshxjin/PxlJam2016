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
	
	public static void addBullet(PApplet p, float playerX, float playerY, float mouseX, float mouseY) {
		Bullet b = new Bullet(p, playerX, playerY, mouseX, mouseY);
	}
	
	public static void moveShowBullets() {
		if (bullets.size() > 0) {
			for (Bullet b : bullets) {
				b.x += b.dx;
				b.y += b.dy;
				b.show();
			}
		}
	}
	
	public void show() {
		parent.fill(255, 100, 100);
		parent.ellipse(x, y, size, size);
	}
	
}
