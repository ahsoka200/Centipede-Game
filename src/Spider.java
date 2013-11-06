import java.awt.Color;
import java.awt.Point;

import acm.graphics.GImage;
import acm.util.RandomGenerator;




public class Spider extends GImage {

	private RandomGenerator rgen = RandomGenerator.getInstance();
	private  int vx = 1;
	private int vy = 1;
	
	



	public Spider() {

		super("Spider1.png", 0, 500);

	
		scale(1.75);
		
		
		
		int spiderRight_or_Left = rgen.nextInt(2);

		if(spiderRight_or_Left == 0){

			setLocation(480 - getWidth() ,500);
			
			vx = -1;
			
			
		}

	}//spider
	
	
	
	public void move(double speed){
	
		int randomMove = rgen.nextInt(30);
		
		if(randomMove == 1){
			
			changeDirection();
			
		}
		
		
		move(vx * speed, vy * speed);
	
	}
	
	
	public void changeDirection(){
		
		int randomVX = rgen.nextInt(3) - 1;
		
		/*int randomVY = rgen.nextInt(2);

		
		if(randomVY == 0){
			
			randomVY --;
			
		}*/
		
		vx = randomVX;
		vy = -vy; 
		
	}
	
	
	public boolean isOffScreen(){
		
		
		if(getX() <= 0- getWidth() || getX() >= 480 || getY() <= 30 || getY() >= 670 + getHeight()){
			
			return true;
			
			
		}
		
		return false;
		
	}
	
	
	
	
	
	
}//end
