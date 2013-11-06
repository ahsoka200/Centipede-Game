import java.awt.Color;

import acm.graphics.*;
import acm.program.GraphicsProgram;

public class Ship extends GImage {

	private int wayrocketIsMovingLR; //move left is 0. move right is 2, and stay still is 1.

	private int wayrocketIsMovingUD; // 0 is down, 2 is up, and 1 is stay still.
	
	public Ship(){

		super("ship.png", CentipedeGame.APPLICATION_WIDTH/2 - 10, 600);
		
		

		wayrocketIsMovingLR = 1;

		wayrocketIsMovingUD = 1;
		
	}//Rocket brace

	public void setMovingLeft(){

		wayrocketIsMovingLR= 0;

	}

	public void setMovingRight(){

		wayrocketIsMovingLR = 2;

	}


	public void setNotMovingLR(){

		wayrocketIsMovingLR = 1;

	}


	public void setNotMovingUD(){

		wayrocketIsMovingUD = 1;

	}

	
	
	public void setMovingUp(){
		
		wayrocketIsMovingUD = 2;
		
	}
	
	
	public void setMovingDown(){
		
		wayrocketIsMovingUD = 0;
		
	}
	
	public void move(int rocketSpeed){

		//makes rocket move right
		if(wayrocketIsMovingLR == 2){
			move(rocketSpeed ,0);
			
			
		}
		
		
		//makes rocket move left
		if(wayrocketIsMovingLR == 0){
			move(-rocketSpeed ,0);


		}
		
		//makes rocket move down
		if(wayrocketIsMovingUD == 0){
			move(0, rocketSpeed);
			
		}
		
		//makes rocket move up
		if(wayrocketIsMovingUD == 2){
			move(0, -rocketSpeed);
			
		}
		

		//left side off screen prevent
		if(this.getX() <= 0){

			this.setLocation(0, getY());

			wayrocketIsMovingLR = 1;
		}
		
		//right side off screen prevent
		if(this.getX() >= CentipedeGame.APPLICATION_WIDTH - this.getWidth()){

			this.setLocation(CentipedeGame.APPLICATION_WIDTH - this.getWidth(), getY());

			wayrocketIsMovingLR = 1;
		}
		
		//off screen down prevent
		if(this.getY() >= CentipedeGame.APPLICATION_HEIGHT - this.getHeight()){
			
			this.setLocation(getX(), CentipedeGame.APPLICATION_HEIGHT - this.getHeight());
			
			wayrocketIsMovingUD = 1;
			
		}
		
		//off screen up prevent
		if(this.getY() <= 530){
			
			this.setLocation(getX(), 530);
			
			wayrocketIsMovingUD = 1;
			
		}

		
		
		

	}//move brace
	
}//end brace
