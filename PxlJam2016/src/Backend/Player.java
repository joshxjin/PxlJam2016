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
		    	dy = -DY;
		    } 
		    else if (parent.keyCode == PConstants.DOWN) {
		    	dy = DY;
		    }
		    else if (parent.keyCode == PConstants.LEFT){
		    	dx = -DX;
		    }
		    else if (parent.keyCode == PConstants.RIGHT){
		    	dx = DX;
		    }
		  }
		
		else {
			if(parent.key == 'w'){
		    	dy = -DY;				
			}
		    else if (parent.key == 's') {
		    	dy = DY;
		    }
		    else if (parent.key == 'a'){
		    	dx = -DX;
		    }
		    else if (parent.key == 'd'){
		    	dx = DX;
		    }
		}
	}
	
	public void move(){
		x = x + dx;
		y = y + dy;
	}
	
	public void stopDir(){
		if (parent.key == PConstants.CODED) { 
		    if (parent.keyCode == PConstants.UP) {
		    	dy = 0;
		    } 
		    else if (parent.keyCode == PConstants.DOWN) {
		    	dy = 0;
		    }
		    else if (parent.keyCode == PConstants.LEFT){
		    	dx = 0;
		    }
		    else if (parent.keyCode == PConstants.RIGHT){
		    	dx = 0;
		    }
		  }
		else{
		    if (parent.key == 'w') {
		    	dy = 0;
		    } 
		    else if (parent.key == 's') {
		    	dy = 0;
		    }
		    else if (parent.key == 'a'){
		    	dx = 0;
		    }
		    else if (parent.key == 'd'){
		    	dx = 0;
		    }
		}
	}
	
	public void draw(){
		parent.fill(0);
		parent.ellipse(x,y,size,size);
	}
	
}
