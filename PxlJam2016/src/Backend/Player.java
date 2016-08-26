package Backend;

import processing.core.PApplet;
import processing.core.PConstants;

public class Player extends GameObject {
	
	PApplet parent;
	float DX, DY;
	
	public Player(PApplet p, float xx, float yy, float speedx, float speedy){
		parent = p;
		x = xx;
		y = yy;
		DX = speedx;
		DY = speedy;
		size = 30;
	}
	
	public void setDir(){
		if (parent.key == PConstants.CODED) { 
		    if (parent.keyCode == PConstants.UP) {
		    	dx = 0;
		    	dy = -DY;
		    } 
		    else if (parent.keyCode == PConstants.DOWN) {
		    	dx = 0;
		    	dy = DY;
		    }
		    else if (parent.keyCode == PConstants.LEFT){
		    	dx = -DX;
		    	dy = 0;
		    }
		    else if (parent.keyCode == PConstants.RIGHT){
		    	dx = DX;
		    	dy = 0;
		    }
		  }
	}
	
	public void move(){
		x = x + dx;
		y = y + dy;
	}
	
	public void draw(){
		parent.fill(0);
		parent.ellipse(x,y,size,size);
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
