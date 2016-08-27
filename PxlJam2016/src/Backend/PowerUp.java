package Backend;

import java.util.ArrayList;

import processing.core.PApplet;

public class PowerUp extends GameObject {
	
	private PApplet parent;
	private int type;
	
	/*The types of powerup:
	 * 1 = extra life
	 * 2 = speed boost
	 * 3 = triple fire*/

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
	
	public static void showPowerUps(){
		for (PowerUp p : powerups){
			p.parent.fill(p.type*50);
			p.parent.ellipse(p.x, p.y, p.size, p.size);
		}
	}

}
