package Backend;

import processing.core.PApplet;

public class Bullet extends GameObject {
	private PApplet parent;
	private int damage;
	
	public Bullet(PApplet p, float x, float y) {
		parent = p;
		damage = 10;
		size = 5;
		this.x = x;
		this.y = y;
		this.dx = 5;
		this.dy = 5;
	}
	
	public void move() {
		this.x += dx;
		this.y += dy;
	}
	
	public void show() {
		parent.ellipse(x, y, size, size);
	}
	
	
	
}
