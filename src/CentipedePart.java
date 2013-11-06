import java.awt.Color;
import java.awt.Point;

import acm.graphics.GImage;
import acm.graphics.GOval;


public class CentipedePart extends GImage {
	
	private int wayIsMoving; //1 is right, 2 is left, 3 is down, and 4 is up.
	private Point gridPoint;
	private boolean headedDown;
	
	
	public CentipedePart(int gridX, int  gridY){
	
		
		super("body centipede left.png", gridX * 16, gridY * 16 + 30);
		
		
		
		
		
		gridPoint = new Point(gridX, gridY);
		
		wayIsMoving = 1;
		headedDown = true;
		
	}
	
	public void move(){
		//calculate next grid location
		Point nextSpot = calculateNextSpot();
		
		if(isSpotValid(nextSpot) == true){
			
			gridPoint = nextSpot;
			setLocation(gridPoint.x*16, gridPoint.y*16+30);
			
		}else{
			//remember which way it was moving
			int previousDir = wayIsMoving;
			//switch to either down or up
			if(headedDown == true){
				wayIsMoving = 3;
			}else{
				wayIsMoving = 4;
			}
			//call move once to move down/up
			forceMove();
			//change direction to left or right
			//WILL CHANGE LATER FOR MUSHROOMS
			if(previousDir == 1){
				wayIsMoving = 2;
			}
			if(previousDir == 2){
				wayIsMoving = 1;
			}
			
		}	
			
	
		
	}
	
	private void forceMove(){
		
		Point nextSpot = calculateNextSpot();
			
		gridPoint = nextSpot;
		setLocation(gridPoint.x*16, gridPoint.y*16+30);
		
		
	}
	
	private boolean isSpotValid(Point point){
		
		if(point.x < 0){
			
			return false;
		}
		
		
		if(point.x >= 30){
			
			return false;
			
		}
		
		if(point.y < 0){
			headedDown = true;
			return false;
			
		}
		
		if(point.y >= 40){
			headedDown = false;
			return false;
		}
		
		if(CentipedeGame.game.grid[point.x] [point.y] instanceof Mushroom){
			
			return false;
		}
		
		
		//determine if spot is valid
/*		if(CentipedePart.getLocation != null){
			
			gridY = gridY + 1; 
			
			wayIsMoving = 2;
			
		}
*/		
		
		return true;
		
	}

	private Point calculateNextSpot(){
		
		int newGridX = gridPoint.x;
		
		int newGridY = gridPoint.y;
		
		if(wayIsMoving == 1){
			//right
			newGridX++;
			
		}
		
		
		if(wayIsMoving == 2){
			//left
			newGridX--;
			
		}
		
		if(wayIsMoving == 3){
			//down
			newGridY++;
			
		}
		
		if(wayIsMoving == 4){
			//up
			newGridY--;
		}
		
		return new Point(newGridX, newGridY);
		
	}//nextSpot brace
	

}
