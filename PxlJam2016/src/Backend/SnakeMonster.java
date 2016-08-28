package Backend;

import java.util.ArrayList;

import processing.core.PApplet;

public class SnakeMonster extends Monster{

	private float time;
	
	public SnakeMonster(PApplet p, float x, float y, float speed) {
		super(p, x, y, speed);
		// TODO Auto-generated constructor stub
		time = (float)Math.random()*59;
	}
	
	public void setMove(float playerX, float playerY) {
		
		if(bounceCount == 0){
			float xturn, yturn;

			float tempHypot = (float)(Math.hypot((this.x - playerX), (this.y - playerY)));
			float ratio = this.speed / tempHypot;

			double swingFreq = 7.5;

			xturn = (float)(-(playerY-this.y)*Math.sin(time/swingFreq)*5);
			yturn = (float)((playerX-this.x)*Math.sin(time/swingFreq)*5);

			if(tempHypot < 40) {
				xturn = 0;
				yturn = 0;
			}

			this.dx = (playerX - this.x + xturn) * ratio;
			this.dy = (playerY - this.y + yturn) * ratio;
		}
		
		if(bounceCount == bounceMax) bounceCount = 0;
		
		checkCollision();
	}
	
	public void move() {
		x += dx;
		y += dy;

		if (bounceCount >0 ) bounceCount++;
		this.time++;
		
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

}