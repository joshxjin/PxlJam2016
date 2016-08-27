package Backend;

import java.util.ArrayList;

import Frontend.Application;
import processing.core.PApplet;
import processing.core.PConstants;

public class Player extends GameObject {

	PApplet parent;
	float DX, DY;
	float angle;
	int maxHealth = 10;
	int speedTimer = 0;
	int tripleFireTimer = 0;
	int rapidFireTimer = 0;
	int powerTime = 100;
	
	float speedScale;
	double tripleAngle = Math.PI/6;
	int rapidDelay = 20;

	public Player(PApplet p, float xx, float yy) {
		parent = p;
		x = xx;
		y = yy;
		DX = 5;
		DY = 5;
		size = 30;
		angle = 0;
		health = 3;
		speedScale = 0;
	}

	public void setDir() {
		
		if (parent.key == PConstants.CODED) {
			if (parent.keyCode == PConstants.UP) {
					dy = -(DY + speedScale);
			} else if (parent.keyCode == PConstants.DOWN) {
					dy = DY + speedScale;
			} else if (parent.keyCode == PConstants.LEFT) {
				dx = -(DX + speedScale);
			} else if (parent.keyCode == PConstants.RIGHT) {
				dx = DX + speedScale;
			}
		} else {
			if (parent.key == 'w' || parent.key == 'W') {
				dy = -(DY + speedScale);
			} else if (parent.key == 's' || parent.key == 'S') {
				dy = DY + speedScale;
			} else if (parent.key == 'a' || parent.key == 'A') {
				dx = -(DX + speedScale);
			} else if (parent.key == 'd' || parent.key == 'D') {
				dx = DX + speedScale;
			}
		}
	}

	public void move() {

		
		//counting down the various timers
		if(speedTimer == 1) speedScale = 0;
		if(rapidFireTimer == 1) rapidDelay = 20;
		if(speedTimer > 0) speedTimer--;
		if(tripleFireTimer > 0) tripleFireTimer--;
		if(rapidFireTimer > 0) rapidFireTimer--;

		ArrayList<Obstacle> ob = Obstacle.getObstacles();

		if (x - size * 2 <= 0) {
			x = size * 2;
		}

		if (x + size * 2 >= parent.width) {
			x = parent.width - size * 2;
		}

		if (y - size * 2 <= 0) {
			y = size * 2;
		}

		if (y + size * 2 >= parent.height) {
			y = parent.height - size * 2;
		}

		x = x + dx;
		y = y + dy;

		// DC: undo move if player collides with an obstacle
		boolean collision = false;

		for (int i = 0; i < ob.size(); i++) {

			double xdiff = Math.abs(x - ob.get(i).getX());
			double ydiff = Math.abs(y - ob.get(i).getY());
			double obSize = ob.get(i).getSize();

			if (Math.hypot(xdiff, ydiff) > Math.sqrt(2) * obSize / 2 + size / 2)
				continue;

			// tests proximity to the edges
			if (xdiff <= (obSize / 2 + size / 2) && ydiff <= obSize / 2) {
				collision = true;
				break;
			}
			if (ydiff <= (obSize / 2 + size / 2) && xdiff <= obSize / 2) {
				collision = true;
				break;
			}

			// tests proximity to the corner
			double cornerDist = Math.hypot(xdiff - obSize / 2, ydiff - obSize / 2);
			// this is the distance from the circle centre to the rectangle
			// corner

			if (cornerDist < size / 2) {
				collision = true;
				break;
			}
		}

		if (collision) {
			x = x - dx;
			y = y - dy;
		}
		
		checkPowerUps();
	}
	
	private void checkPowerUps(){
		ArrayList<PowerUp> powerups = PowerUp.getPowerUps();
		ArrayList<PowerUp> removeList = new ArrayList<PowerUp>();
		
		for (PowerUp p : powerups){
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
	
	private void addLife(){
		if (health < maxHealth) {
			health++;
		}
	}
	
	private void speedUp(){
		speedTimer += powerTime;
		speedScale = 2;
	}
	
	private void tripleFire(){
		tripleFireTimer += powerTime;
	}
	
	private void rapidFire(){
		rapidFireTimer += powerTime;
		rapidDelay = 0;
	}
	
	public void resetPowerUps(){
		speedTimer = 0;
		speedScale = 0;
		tripleFireTimer = 0;
		rapidFireTimer = 0;
		rapidDelay = 20;
	}

	public void stopDir() {
		if (parent.key == PConstants.CODED) {
			if (parent.keyCode == PConstants.UP) {
				dy = 0;
			} else if (parent.keyCode == PConstants.DOWN) {
				dy = 0;
			} else if (parent.keyCode == PConstants.LEFT) {
				dx = 0;
			} else if (parent.keyCode == PConstants.RIGHT) {
				dx = 0;
			}
		} else {
			if (parent.key == 'w' || parent.key == 'W') {
				dy = 0;
			} else if (parent.key == 's' || parent.key == 'S') {
				dy = 0;
			} else if (parent.key == 'a' || parent.key == 'A') {
				dx = 0;
			} else if (parent.key == 'd' || parent.key == 'D') {
				dx = 0;
			}
		}
	}

	public void shoot(ArrayList<GameObject> gameObjects) {
		Bullet b = null;

		float xStart = (float) (x + (size / 2) * Math.sin(angle));
		float yStart = (float) (y - (size / 2) * Math.cos(angle));

		if (parent.mouseButton == PConstants.LEFT) {
			b = Bullet.addBullet(parent, xStart, yStart, parent.mouseX, parent.mouseY);
		} else if (parent.mouseButton == PConstants.RIGHT) {
			b = Bullet.addBullet(parent, xStart, yStart, parent.mouseX, parent.mouseY);
		}
		gameObjects.add(b);
		
		if(tripleFireTimer > 0){
			Bullet b1 = null, b2 = null;
			double xdiff = parent.mouseX - xStart;
			double ydiff = parent.mouseY - yStart;
			
			double xleft = xStart + xdiff*Math.cos(tripleAngle) - ydiff*Math.sin(tripleAngle);
			double yleft = yStart + xdiff*Math.sin(tripleAngle) + ydiff*Math.cos(tripleAngle);
			
			double xright = xStart + xdiff*Math.cos(-tripleAngle) - ydiff*Math.sin(-tripleAngle);
			double yright = yStart + xdiff*Math.sin(-tripleAngle) + ydiff*Math.cos(-tripleAngle);
			
			if (parent.mouseButton == PConstants.LEFT) {
				
				b1 = Bullet.addBullet(parent, xStart, yStart, (float)xleft, (float)yleft);
				b2 = Bullet.addBullet(parent, xStart, yStart, (float)xright, (float)yright);
			} else if (parent.mouseButton == PConstants.RIGHT) {
				b1 = Bullet.addBullet(parent, xStart, yStart, (float)xleft, (float)yleft);
				b2 = Bullet.addBullet(parent, xStart, yStart, (float)xright, (float)yright);
			}
			
			gameObjects.add(b1);
			gameObjects.add(b2);
		}
	}

	public void drawPlayerHealth() { // IMAGE RELEVANT
		for (int i = 0; i < this.health; i++) { // IMAGE RELEVANT
			parent.fill(259, 0, 0); // IMAGE RELEVANT
			parent.image(Application.heart, 45 + (30 * i), 15); // IMAGE
																// RELEVANT
		} // IMAGE RELEVANT
	} // IMAGE RELEVANT

	public void draw() {

		angle = -1 * PApplet.atan2(parent.mouseX - x, parent.mouseY - y) + (float) Math.PI;

		parent.fill(0, 200);

		parent.pushMatrix();
		parent.translate(x, y);
		parent.rotate(angle);
		// parent.ellipse(0, 0, size, size);
		parent.image(Application.shipPic, 0, 0); // IMAGE RELEVANT
		parent.popMatrix();
	}
	
	public int getDelay(){
		return rapidDelay;
	}

}
