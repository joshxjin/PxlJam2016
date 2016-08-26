package Backend;

import processing.core.PApplet;
import processing.core.PConstants;

public class Player extends GameObject {
	
	PApplet parent;
	
	public Player(PApplet p, float xx, float yy, float speedx, float speedy){
		parent = p;
		x = xx;
		y = yy;
		dx = speedx;
		dy = speedy;
	}
	
	public void move(){
		if (parent.key == PConstants.CODED) { 
		    if (parent.keyCode == PConstants.UP) {
		      y = y - dy;
		    } 
		    else if (parent.keyCode == PConstants.DOWN) {
		      y = y + dy;
		    }
		    else if (parent.keyCode == PConstants.LEFT){
		    	x = x - dx;
		    }
		    else if (parent.keyCode == PConstants.RIGHT){
		    	x = x + dx;
		    }
		  }
	}
	
	public void setX(float xx){
		x = xx;
	}
	
	public void setY(float yy){
		y = yy;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
}
