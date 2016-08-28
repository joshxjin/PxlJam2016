package Backend;
  
import java.util.ArrayList;

import Frontend.Application;//IMAGE RELEVANT
import Backend.SnakeMonster;//IMAGE RELEVANT
import processing.core.PApplet;

public class Monster extends GameObject {

	private PApplet parent;

	private static ArrayList<Monster> monsters = new ArrayList<Monster>();
	private static ArrayList<Monster> deadMonsters = new ArrayList<Monster>();
	
	private static ArrayList<Monster> extraMonsters = new ArrayList<Monster>();
	//DC: a list of extra monsters to add in the case of power-up thief glitch being on
	//and a monster running over an extra life power-up
	
	private static ArrayList<Bullet> extraBullets = new ArrayList<Bullet>();
	//DC: a list of bullets created by all monsters if they are currently shooting things
	//in case of power-thief glitch being on

	int bounceCount;
	int bounceMax = 20;
	static double spawnRateDefault = 0.2;
	
	int deathTime = 10;
	
	//DC: added timers for power-ups
	int speedTimer = 0;
	int tripleFireTimer = 0;
	int rapidFireTimer = 0;
	int powerTimer = 100;
	
	float speedScale = (float)1.3;
	float speedDefault;
	int shootRate = 15;
	int rapidShootRate = 2;
	int shootTimer = 0;
	double tripleAngle = Math.PI/6;
	//end changes

	public Monster(PApplet p, float x, float y, float speed) {
		this.parent = p;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.size = 30;
		this.dx = 0;
		this.dy = 0;
		bounceCount = 0;
		
		speedDefault = speed;	//DC: added

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

	//DC: changed this method to take the list of game objects as an argument
	public void move() {
		x += dx;
		y += dy;
		if (bounceCount >0 ) bounceCount++;
		
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

	
	//method to check collision with Obstacles/Power-Ups and recalculate movement if needed
	public void checkCollision(){
		
		//DC: adding collisions with power-ups in case of glitch
		if(Application.powerUpThiefGlitch){
			ArrayList<PowerUp> powerups = PowerUp.getPowerUps();
			ArrayList<PowerUp> removeList = new ArrayList<PowerUp>();
			
			for(PowerUp p: powerups){
				double dist = Math.hypot(p.getX() - x, p.getY() - y);
				
				if(dist < size/2 + p.getSize()/2){
					switch(p.getType()) {
					case 1:
						addLife();
						break;
					case 2:
						speedUp();
						break;
					case 3:
						tripleFire();
						break;
					case 4:
						rapidFire();
						break;
					}
					removeList.add(p);
				}
			}
			
			powerups.removeAll(removeList);
		}
		//end of change
		
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
	
	public void kill(){
		deadMonsters.add(this);
	}
	
	//DC: added power-up related methods below
	
	//if monster gets an extra life power-up
	//it adds an extra monster in the same spot, with a slower speed
	private void addLife(){
		Monster m = new Monster(parent, x,y,(float)(speed*0.9));
		extraMonsters.add(m);
	}
	
	//speeds up by a factor of 1.5 if it gets a speed power-up
	private void speedUp(){
		speedTimer += powerTimer;
		speed = speedDefault*speedScale;
	}
	
	private void tripleFire(){
		tripleFireTimer += powerTimer;
		shootTimer = shootRate;
	}
	
	private void rapidFire(){
		rapidFireTimer += powerTimer;
		shootTimer = rapidShootRate;
	}
	
	protected void shoot(){
		Bullet b;
		
		double angle = Math.random()*Math.PI*2;
		float xStart = (float)((size/2 + 3)*Math.sin(angle) + x);
		float yStart = (float)((size/2 + 3)*Math.cos(angle) + y);
		float xEnd = (float)((size)*Math.sin(angle) + x);
		float yEnd = (float)((size)*Math.cos(angle) + y); 
		
		b = Bullet.addBullet(parent, xStart, yStart, xEnd, yEnd);
		extraBullets.add(b);
		
		if(tripleFireTimer > 0){
			double xdiff = xEnd - xStart;
			double ydiff = yEnd - yStart;
			
			double xleft = xStart + xdiff*Math.cos(tripleAngle) - ydiff*Math.sin(tripleAngle);
			double yleft = yStart + xdiff*Math.sin(tripleAngle) + ydiff*Math.cos(tripleAngle);
			
			double xright = xStart + xdiff*Math.cos(-tripleAngle) - ydiff*Math.sin(-tripleAngle);
			double yright = yStart + xdiff*Math.sin(-tripleAngle) + ydiff*Math.cos(-tripleAngle);
			
			b = Bullet.addBullet(parent, xStart, yStart, (float)xleft, (float)yleft);
			extraBullets.add(b);
			b = Bullet.addBullet(parent, xStart, yStart, (float)xright, (float)yright);	
			extraBullets.add(b);
		}
		
		if(rapidFireTimer > 0) shootTimer = rapidShootRate;
		else shootTimer = shootRate;
	}

	public static ArrayList<Monster> getExtraMonsters(){
		return extraMonsters;
	}
	
	public static ArrayList<Bullet> getExtraBullets(){
		return extraBullets;
	}
	
	//end of changes

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
		
		//DC: shows dead monsters and removes them from the list when appropriate
		ArrayList<Monster> removeList = new ArrayList<Monster>();
		PowerUp p;
		
		double spawnRate;
		if(Application.manyPowerUpsGlitch){
			spawnRate = 1;
		}
		else spawnRate = spawnRateDefault;
		
		for (Monster o: deadMonsters){

			if (o.deathTime == 0){
				removeList.add(o);
				if(Math.random() < spawnRate){
					switch((int)(Math.random()*4 + 1)){
					case 1:
						p = new PowerUp(o.parent,o.x,o.y,1);
						break;
					case 2:
						p = new PowerUp(o.parent,o.x,o.y,2);
						break;
					case 3:
						p = new PowerUp(o.parent,o.x,o.y,3);
						break;
					case 4:
						p = new PowerUp(o.parent,o.x,o.y,4);
						break;
					}
				}
			}
			else{
				if (o.getClass() == SnakeMonster.class){
					o.parent.image(Application.deadSnakeMonsterPic, o.x, o.y);
					} 
				else{
					o.parent.image(Application.deadMonsterPic, o.x, o.y);
				}
				o.deathTime--;
			}
		}
		
		deadMonsters.removeAll(removeList);
	}

	public static void moveMonsters(float playerX, float playerY) {
		for (Monster o : monsters) {
			o.setMove(playerX, playerY);
			o.move();
		}
	}

}