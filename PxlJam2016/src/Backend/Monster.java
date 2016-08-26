package Backend;

import java.util.ArrayList;

import processing.core.PApplet;

public class Monster extends GameObject {

	private PApplet parent;

	private static ArrayList<Monster> monsters = new ArrayList<Monster>();

	float size;

	public Monster(PApplet p, float x, float y, float dx, float dy) {
		this.parent = p;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.health = 3;
		this.size = 30;
		monsters.add(this);
	}

	public float getDX() {
		return dx;
	}

	public float getDY() {
		return this.dy;
	}

	public void setMove(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
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

	public static void moveMonsters() {
		for (Monster o : monsters) {
			o.x = o.x + o.dx;
			o.y = o.y + o.dy;
		}
	}

}