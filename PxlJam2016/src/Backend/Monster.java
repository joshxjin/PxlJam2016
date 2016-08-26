package Backend;

import java.util.ArrayList;

import processing.core.PApplet;

public class Monster extends GameObject {

	private PApplet parent;

	private static ArrayList<Monster> monsters = new ArrayList<Monster>();

	float size;
	float speed;

	public Monster(PApplet p, float x, float y, float speed, int health) {
		this.parent = p;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.health = health;
		this.size = 30;
		
		float tempHypot = (float)(Math.hypot((this.x - Player.getXX()), (this.y - Player.getYY())));
		float ratio = speed / tempHypot;
		dx = (Player.getXX() - this.x) * ratio;
		dy = (Player.getYY() - this.y) * ratio;
		
		monsters.add(this);
	}

	public float getDX() {
		return dx;
	}

	public float getDY() {
		return this.dy;
	}
	
	public void setMove(float playerX, float playerY) {
		float tempHypot = (float)(Math.hypot((this.x - playerX), (this.y - playerY)));
		float ratio = this.speed / tempHypot;
		this.dx = (playerX - this.x) * ratio;
		this.dy = (playerY - this.y) * ratio;
	}

	public static ArrayList<Monster> getMonsters() {
		return monsters;
	}

	public static void showMonsters() {
		for (Monster o : monsters) {
			o.parent.fill(255);
			o.parent.ellipse(o.x, o.y, o.size, o.size);
		}
	}

	public static void moveMonsters(float playerX, float playerY) {
		for (Monster o : monsters) {
			o.setMove(playerX, playerY);
			o.x = o.x + o.dx;
			o.y = o.y + o.dy;
		}
	}

}