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
	
	//method to check collision with Obstacles and recalculate movement if needed
	public void checkCollision(){
		ArrayList<Obstacle> ob = Obstacle.getObstacles();
		
		boolean collision = false;
		
		//this is the movement vector
		float x_move = 0;
		float y_move = 0;
		
		for (int i = 0; i < ob.size(); i++){
			
			double xdiff = Math.abs(x - ob.get(i).getX());
		    double ydiff = Math.abs(y - ob.get(i).getY());
		    double obSize = ob.get(i).getSize();
		    
		    if (Math.hypot(xdiff, ydiff) > Math.sqrt(2)*obSize/2 + size/2) continue;

		    //tests proximity to the edges
		    if (xdiff <= (obSize/2 + size/2) && ydiff <= obSize/2) {
		    	collision = true;
		    	continue;
		    	} 
		    if (ydiff <= (obSize/2 + size/2) && xdiff <= obSize/2) {
		    	collision = true;
		    	continue;
		    	}

		    //tests proximity to the corner
		    double cornerDist = Math.hypot(xdiff - obSize/2, ydiff - obSize/2);
		    	//this is the distance from the circle centre to the rectangle corner

		    if (cornerDist < size/2) {
		    	collision = true; 
		    	break;
		    	}
			}
		
		if (collision == false) return;
		
		
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
			o.move();
		}
	}
}