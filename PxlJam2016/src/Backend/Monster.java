package Backend;
  
import java.util.ArrayList;

import Frontend.Application;//IMAGE RELEVANT
import processing.core.PApplet;

public class Monster extends GameObject {

	private PApplet parent;

	private static ArrayList<Monster> monsters = new ArrayList<Monster>();

	float size;
	float speed;
	
	int bounceCount;
	int bounceMax = 20;

	public Monster(PApplet p, float x, float y, float speed, int health) {
		this.parent = p;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.health = health;
		this.size = 30;
		this.dx = 0;
		this.dy = 0;
		bounceCount = 0;

		monsters.add(this);
	}

	public float getDX() {
		return dx;
	}

	public float getDY() {
		return this.dy;
	}

	public void setMove(float playerX, float playerY) {		
		if(bounceCount <1){
			float tempHypot = (float) (Math.hypot((this.x - playerX), (this.y - playerY)));
			float ratio = this.speed / tempHypot;
			this.dx = (playerX - this.x) * ratio;
			this.dy = (playerY - this.y) * ratio;
		}
		
		if(bounceCount == bounceMax) bounceCount = 0;
		
		checkCollision();
	}

	public void move() {
		x += dx;
		y += dy;
		if (bounceCount >0 ) bounceCount++;
	}

	public void bounce(double x_move, double y_move){
		
	}
	
	//method to check collision with Obstacles and recalculate movement if needed
	public void checkCollision(){
		ArrayList<Obstacle> ob = Obstacle.getObstacles();
		
		int collision = 0;
		
		//this is the movement vector
		float x_move = 0;
		float y_move = 0;
		
		for (int i = 0; i < ob.size(); i++){
			
			double xdiff = Math.abs(x - ob.get(i).getX());
		    double ydiff = Math.abs(y - ob.get(i).getY());
		    double dist = Math.hypot(xdiff, ydiff);
		    double obSize = ob.get(i).getSize();
		    
		    if (dist > Math.sqrt(2)*obSize/2 + size/2) continue;

		    //tests proximity to the edges
		    if (xdiff <= (obSize/2 + size/2) && ydiff <= obSize/2) {
		    	collision++;
		    	x_move += (x - ob.get(i).getX())/dist;
		    	y_move += (y - ob.get(i).getY())/dist;
		    	continue;
		    	} 
		    if (ydiff <= (obSize/2 + size/2) && xdiff <= obSize/2) {
		    	collision++;
		    	x_move += (x - ob.get(i).getX())/dist;
		    	y_move += (y - ob.get(i).getY())/dist;
		    	continue;
		    	}

		    //tests proximity to the corner
		    double cornerDist = Math.hypot(xdiff - obSize/2, ydiff - obSize/2);
		    	//this is the distance from the circle centre to the rectangle corner

		    if (cornerDist < size/2) {
		    	collision++; 
		    	x_move += (x - ob.get(i).getX())/dist;
		    	y_move += (y - ob.get(i).getY())/dist;
		    	}
			}
		
		if (collision == 0) return;
		
		bounceCount = 1;
		dx = x_move/((float)Math.hypot(x_move, y_move))*speed;
		dy = y_move/((float)Math.hypot(x_move, y_move))*speed;
		
		if(collision == 1){
			dx = (float) (dx*((float)Math.random()+0.5));
			dy = (float) (dy*((float)Math.random()+0.5));
		}
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