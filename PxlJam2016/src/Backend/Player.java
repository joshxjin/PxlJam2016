package Backend;

import java.util.ArrayList;

import Frontend.Application;
import processing.core.PApplet;
import processing.core.PConstants;

public class Player extends GameObject {

	PApplet parent;
	float DX, DY;
	float angle;

	public Player(PApplet p, float xx, float yy, float speedx, float speedy) {
		parent = p;
		x = xx;
		y = yy;
		DX = speedx;
		DY = speedy;
		size = 30;
		angle = 0;
	}

	public void setDir() {
		if (parent.key == PConstants.CODED) {
			if (parent.keyCode == PConstants.UP) {
					dy = -DY;
			} else if (parent.keyCode == PConstants.DOWN) {
					dy = DY;
			} else if (parent.keyCode == PConstants.LEFT) {
				dx = -DX;
			} else if (parent.keyCode == PConstants.RIGHT) {
				dx = DX;
			}
		} else {
			if (parent.key == 'w' || parent.key == 'W') {
				dy = -DY;
			} else if (parent.key == 's' || parent.key == 'S') {
				dy = DY;
			} else if (parent.key == 'a' || parent.key == 'A') {
				dx = -DX;
			} else if (parent.key == 'd' || parent.key == 'D') {
				dx = DX;
			}
		}
	}

	public void move() {
		if (x - size * 2 <= 0) {
			x = size * 2;
		}
		
		if (x + size * 2 >= parent.width) {
			x = parent.width - size  * 2;
		}
		
		if (y - size * 2 <= 0) {
			y = size * 2;
		}
		
		if (y + size * 2 >= parent.height) {
			y = parent.height - size * 2;
		}
		
		x = x + dx;
		y = y + dy;
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
	
	public void shoot(ArrayList<GameObject> gameObjects){
		Bullet b = null;
		
		float xStart = (float)(x + (size/2)*Math.sin(angle));
		float yStart = (float)(y - (size/2)*Math.cos(angle));

		if (parent.mouseButton == PConstants.LEFT) {
			b = Bullet.addBullet(parent, xStart, yStart, parent.mouseX, parent.mouseY);
		} else if (parent.mouseButton == PConstants.RIGHT) {
			b = Bullet.addBullet(parent, xStart, yStart, parent.mouseX, parent.mouseY);
		}
		gameObjects.add(b);
	}

	public void draw() {
		
		
		angle = -1*PApplet.atan2(parent.mouseX-x, parent.mouseY-y) + (float)Math.PI;
		
		parent.fill(0, 200);
		
		parent.pushMatrix();
		parent.translate(x,y);
		parent.rotate(angle);
//		parent.ellipse(0, 0, size, size);
		parent.image(Application.shipPic, 0, 0); //IMAGE RELEVANT
		parent.popMatrix();
	}

}
