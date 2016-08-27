package Backend;
  
import java.util.ArrayList;

import Frontend.Application;//IMAGE RELEVANT
import Backend.SnakeMonster;//IMAGE RELEVANT
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
		this.dx = 0;
		this.dy = 0;

		monsters.add(this);
	}

	public float getDX() {
		return dx;
	}

	public float getDY() {
		return this.dy;
	}

	public void setMove(float playerX, float playerY) {
		float tempHypot = (float) (Math.hypot((this.x - playerX), (this.y - playerY)));
		float ratio = this.speed / tempHypot;
		this.dx = (playerX - this.x) * ratio;
		this.dy = (playerY - this.y) * ratio;
	}

	public void move() {
		x += dx;
		y += dy;
	}

	public static ArrayList<Monster> getMonsters() {
		return monsters;
	}

	public static void showMonsters() {
		for (Monster o : monsters) {
			o.parent.fill(255);
			//o.parent.ellipse(o.x, o.y, o.size, o.size); //IMAGE RELEVANT
			if (o.getClass() == SnakeMonster.class){//IMAGE RELEVANT
				o.parent.image(Application.snakeMonsterPic, o.x, o.y);//IMAGE RELEVANT
			} else{//IMAGE RELEVANT
				o.parent.image(Application.monsterPic, o.x, o.y);//IMAGE RELEVANT	
			}//IMAGE RELEVANT
			
		}
	}

	public static void moveMonsters(float playerX, float playerY) {
		for (Monster o : monsters) {
			o.setMove(playerX, playerY);
			o.move();
		}
	}
}