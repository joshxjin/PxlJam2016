package Frontend;

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
		
	}
	
	public void keyPressed() {
		player.setDir();
		
	}
	
	public void keyReleased(){
		
	}
	
	public void draw() {
		background(200);
		player.move();
		player.draw();
	}

}