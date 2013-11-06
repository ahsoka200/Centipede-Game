import java.awt.Image;

import acm.graphics.GImage;
import acm.util.RandomGenerator;


public class FallSnail extends GImage {

	private RandomGenerator rgen = RandomGenerator.getInstance();
	

	public FallSnail() {

										//tempotary X and Y
		super("Centipede bug thing?.png", 0, 30);

		scale(1.6);
		
		
		int randomMove = rgen.nextInt(30);
		this.setLocation(randomMove * 16, 46);
		
		
	}//FallSnail
	
	public boolean isOffScreen(){
		
		
		if(this.getY() >= 670){
			
			return true;
			
		}
		return false;
		
	}
	
	
	public int getGridX(){
			
		return (int) (this.getX() / 16);
		
	}
	
	
	
	public int getGridY(){
		
		return (int) ((this.getY() - 30) / 16);
		
	}
	
	
	
}//end
