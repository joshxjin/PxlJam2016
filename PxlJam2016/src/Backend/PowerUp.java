package Backend;

import java.util.ArrayList;

import Frontend.Application;
import processing.core.PApplet;

public class PowerUp extends GameObject {
	
	private PApplet parent;
	private int type;
	
	float speed = (float)4.5;
	int bounceCount;
	int bounceMax = 20;
	double spawnRate = 0.2;
	
	/*The types of powerup:
	 * 1 = extra life
	 * 2 = speed boost
	 * 3 = triple fire
	 * 4 = rapid fire*/

	private static ArrayList<PowerUp> powerups = new ArrayList<PowerUp>();
	
	public PowerUp(PApplet p, float x, float y, int t) {
		this.parent = p;
		this.x = x;
		this.y = y;
		this.size = 30;
		this.type = t;

		powerups.add(this);
	}
	
	public int getType(){
		return type;
	}
	
	public static ArrayList<PowerUp> getPowerUps(){
		return powerups;
	}
	
	/* Additions for power up runaway glitch*/
	public void setMove(float playerX, float playerY) {		
		if(bounceCount <1){
			float tempHypot = (float) (Math.hypot((this.x - playerX), (this.y - playerY)));
			if (tempHypot < 60) {
			float ratio = this.speed / tempHypot;
			this.dx = (this.x - playerX) * ratio;
			this.dy = (this.y - playerY) * ratio;
			} else {
				this.dx = 0;
				this.dy = 0;
			}
		}
		
		if(bounceCount == bounceMax) bounceCount = 0;
		
		checkCollision();
	}
	
	public void move() {
		x += dx;
		y += dy;
		if (bounceCount >0 ) bounceCount++;
	}
	
	public static void movePowerUps(float playerX, float playerY) {
		for (PowerUp o : powerups) {
			o.setMove(playerX, playerY);
			o.move();
		}
	}
	
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
	/* power up glitch additions end here*/
	
	public static void showPowerUps(){
		for (PowerUp p : powerups){

			switch(p.type){
			case 1:
				p.parent.image(Application.lifePowerUp, p.x, p.y);
				break;
			case 2:
				p.parent.image(Application.speedPowerUp, p.x, p.y);
				break;
			case 3:
				p.parent.image(Application.tripleFirePowerUp, p.x, p.y);
				break;
			case 4:
				p.parent.image(Application.rapidFirePowerUp, p.x, p.y);
				break;
			}

		}
	}

}
