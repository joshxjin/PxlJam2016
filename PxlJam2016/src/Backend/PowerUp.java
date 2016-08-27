package Backend;

import java.util.ArrayList;

import Frontend.Application;
import processing.core.PApplet;

public class PowerUp extends GameObject {
	
	private PApplet parent;
	private int type;
	
	/*The types of powerup:
	 * 1 = extra life
	 * 2 = speed boost
	 * 3 = triple fire
	 * 4 = rapid fire*/

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

			switch(p.type){
			case 1:
				p.parent.image(Application.lifePowerUp, p.x, p.y);
				break;
			case 2:
				p.parent.image(Application.speedPowerUp, p.x, p.y);
				break;
			case 3:
				p.parent.image(Application.tripleFirePowerUp, p.x, p.y);
				break;
			case 4:
				p.parent.image(Application.rapidFirePowerUp, p.x, p.y);
				break;
			}

		}
	}

}
