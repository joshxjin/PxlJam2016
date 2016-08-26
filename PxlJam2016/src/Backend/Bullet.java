package Backend;

import processing.core.PApplet;

public class Bullet extends GameObject {
	private PApplet parent;
	private float speed;
	private int damage;
	
	public Bullet(PApplet p, float playerX, float playerY, float mouseX, float mouseY) {
		parent = p;
		damage = 10;
		size = 5;
		x = playerX;
		y = playerY;
		speed = 5;
		
		float tempHypot = (float)(Math.hypot((playerX - mouseX), (playerY - mouseY)));
		float ratio = speed / tempHypot;
		dx = (mouseX - playerX) * ratio;
		dy = (mouseY - playerY) * ratio;
		
	}
	
	public void move() {
		this.x += dx;
		this.y += dy;
	}
	
	public void show() {
		parent.fill(0);
		parent.ellipse(x, y, size, size);
	}
	
	
	
}
