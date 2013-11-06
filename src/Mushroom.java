import java.awt.Image;

import acm.graphics.GImage;



public class Mushroom extends GImage {
	
	private int damage = 0;
	private int mushroomGridX;
	private int mushroomGridY;
	

	public Mushroom(int gridX, int gridY) {
		super("mushroom1.png", gridX * 16, gridY * 16 + 30);
		
		mushroomGridX = gridX;
		mushroomGridY = gridY;
		
	}

	public void mushroomRemade(){
		
		setImage("mushroom1.png");	
		
		damage = 0;
		
	}
	
	
	public void mushroomHit(){

		damage = damage + 1;
		if(damage == 1){
			
			setImage("mushroom2.png");	
		}
		
		if(damage == 2){
			
			setImage("mushroom3.png");	
		}
		

		if(damage == 3){
			
			setImage("mushroom4.png");	
		}
		
		
		
		
	}//mushroomHit brace
	
	public boolean isDestriod(){
		
		if(damage == 4){
			
			return true;
		}else{
			
			return false;
		}
		
		
	}
	
	public int getGridX(){
		
		return mushroomGridX;
		
		
	}


	public int getGridY(){
			
		return mushroomGridY;
			
		}
		
	
	
}//end
