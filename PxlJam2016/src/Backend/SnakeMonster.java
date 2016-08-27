package Backend;

import processing.core.PApplet;

public class SnakeMonster extends Monster{

	private float time;
	
	public SnakeMonster(PApplet p, float x, float y, float speed, int health) {
		super(p, x, y, speed, health);
		// TODO Auto-generated constructor stub
		time = (float)Math.random()*59;
	}
	
	public void setMove(float playerX, float playerY) {
		
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
	
	public void move() {
		x += dx;
		y += dy;
		this.time++;
	}

}