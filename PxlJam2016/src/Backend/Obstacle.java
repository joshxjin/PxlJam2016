package Backend;

import java.util.ArrayList;

import processing.core.PApplet;

public class Obstacle extends GameObject {

	private PApplet parent;
	private static ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();

	public Obstacle(PApplet p, float x, float y) {
		this.parent = p;
		this.x = x;
		this.y = y;
		this.dx = 0;
		this.dy = 0;
		this.size = 30;
		obstacles.add(this);
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public static ArrayList<Obstacle> getObstacles() {
		return obstacles;
	}

	public static void showObstacles() {
		for (Obstacle o : obstacles) {
			o.parent.fill(0, 0, 255);
			o.parent.rect(o.x, o.y, o.size, o.size);
		}
	}

}