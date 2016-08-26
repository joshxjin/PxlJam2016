package Backend;

import processing.core.PApplet;
import processing.core.PConstants;

public class Player extends GameObject {

	PApplet parent;
	float DX, DY;

	public Player(PApplet p, float xx, float yy, float speedx, float speedy) {
		parent = p;
		x = xx;
		y = yy;
		DX = speedx;
		DY = speedy;
		size = 30;
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
		if (x - size <= 0) {
			x = size + 1;
		}
		
		if (x + size >= parent.width) {
			x = parent.width - size - 1;
		}
		
		if (y - size <= 0) {
			y = size + 1;
		}
		
		if (y + size >= parent.height) {
			y = parent.height - size - 1;
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

	public void draw() {
		parent.fill(0);
		parent.ellipse(x, y, size, size);
	}

}
