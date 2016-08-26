package Frontend;

import Backend.Bullet;
import Backend.Player;
import processing.core.PApplet;

public class Application extends PApplet {
	
	Player player = new Player(this, 50, 50, 5, 5);

	public static void main(String[] args) {
		PApplet.main("Frontend.Application");

	}
	
	public void settings() {
		size(900, 900);
	}
	
	public void setup() {
		frameRate(60);
	}
	
	public void mousePressed() {
		Bullet.addBullet(this, player.getX(), player.getY(), mouseX, mouseY);
	}
	
	public void keyPressed() {
		player.setDir();
	}
	
	public void keyReleased(){
		player.stopDir();
	}
	
	public void draw() {
		background(200);
		player.move();
		player.draw();
		Bullet.moveShowBullets();
	}

}