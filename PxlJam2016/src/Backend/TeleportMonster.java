package Backend;

import java.util.ArrayList;

import processing.core.PApplet;

public class TeleportMonster extends Monster {
	
	private int time;
	private float pX, pY;
	
	public TeleportMonster(PApplet p, float x, float y, float speed) {
		super(p, x, y, speed);
		// TODO Auto-generated constructor stub
		time = (int)(Math.random()*180 + 60);			//DC: change time settings
	}
	
	public void setMove(float playerX, float playerY){
		super.setMove(playerX, playerY);
		pX = playerX;
		pY = playerY;
		
	}
	
	public void move() {
		if (time == 0){
			teleport();
			if(bounceCount > 0) bounceCount = 0;
			return;
		}
		x += dx;
		y += dy;
		if (bounceCount >0 ) bounceCount++;
		time--;
		
		//DC: checking/resetting possible power-ups
		if (speedTimer == 1) speed = speedDefault;
		if (speedTimer > 0 ) speedTimer--;		
		if(tripleFireTimer > 0) tripleFireTimer--;
		if(rapidFireTimer > 0) rapidFireTimer--;
		
		//shooting every so many frames (determined by shootRate)
		if(rapidFireTimer > 0 || tripleFireTimer > 0){
			if (shootTimer == 0){
				shoot();
			}
			else shootTimer--;
		}
	}
	
	private void teleport(){
		double currentDist = Math.hypot(pX - x, pY - y);
		ArrayList<Obstacle> obstacles = Obstacle.getObstacles();
		
		float xNew = x;
		float yNew = y;
		
		while(true){
			
			xNew = (float) (Math.random()*840 + 30);
			yNew = (float) (Math.random()*840 + 30);
			
			//check that it isn't too far away
			if (Math.hypot(xNew - pX, yNew - pY) > currentDist * 1.5) continue;
			
			//check that it isn't too close
			if (Math.hypot(xNew - pX, yNew - pY) < Math.min(150,currentDist*0.8)) continue;
			
			//check for collision with obstacles
			boolean collision = false;
			
			for(Obstacle o: obstacles){
				
				double xdiff = Math.abs(x - o.getX());
			    double ydiff = Math.abs(y - o.getY());
			    double dist = Math.hypot(xdiff, ydiff);
			    double obSize = o.getSize();
			    
			    if (dist > Math.sqrt(2)*obSize/2 + size/2) continue;

			    //tests proximity to the edges
			    if (xdiff <= (obSize/2 + size/2) && ydiff <= obSize/2) {
			    	collision = true;
			    	break;
			    	} 
			    if (ydiff <= (obSize/2 + size/2) && xdiff <= obSize/2) {
			    	collision = true;
			    	break;
			    	}

			    //tests proximity to the corner
			    double cornerDist = Math.hypot(xdiff - obSize/2, ydiff - obSize/2);
			    	//this is the distance from the circle centre to the rectangle corner

			    if (cornerDist < size/2) {
			    	collision = true;
			    	break;
			    	}
				}
			if(collision) continue;
			
			break;
			}
		
		x = xNew;
		y = yNew;
		
		time = (int)(Math.random() * 180 + 60);		//DC: changed time settings
		}
}

