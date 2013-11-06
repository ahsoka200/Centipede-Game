import java.applet.AudioClip;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.MediaTools;
import acm.util.RandomGenerator;



public class CentipedeGame extends GraphicsProgram {

	public static final int APPLICATION_WIDTH = 480;
	public static final int APPLICATION_HEIGHT = 670;
	private Ship ship;

	private RandomGenerator rgen = RandomGenerator.getInstance();


	private GRect bullet;
	private double bulletSpeed = 19;
	private boolean canShoot = true; 
	private boolean spaceHeld = false;
	private GLabel scoreLabel;
	private GLabel livesLabel;
	private int score = 0;
	public Object[][] grid = new Object[30][40];
	private ArrayList<CentipedePart> centipede = new ArrayList<CentipedePart>();
	private int counter = 0;
	static public CentipedeGame game;
	private int LIVES = 3;
	private Spider spider;
	private FallSnail fallSnail;
	private GLabel youLoseLabel;
	private int spiderSpawnRate = 501;
	private double spiderSpeed = 3;
	private int fallSnailSpawnRate = 800;
	private double newLevelFreaquencyIncreace = 1.2;
	
	private AudioClip youShootSound = MediaTools.loadAudioClip("snap.wav");
	private AudioClip youDieSound = MediaTools.loadAudioClip("fall and splat.wav");
	private AudioClip fallSnailSound = MediaTools.loadAudioClip("Cave in.wav");
	private AudioClip CpDieSound = MediaTools.loadAudioClip("bow fired.wav");

	
	
	public void run(){

		setup();
		GameLoop();



	}// run brace



	public void setup(){

		game = this;
		ship();
		addKeyListeners();

		setBackground(Color.black);

		mushroomCreator();

		scoreLabel();

		livesLabel();


		createCentipedes();




	}//setup brace


	public void GameLoop(){

		while (true){

			ship.move(4);

			pause(20);

			moveAllBullets();

			bulletColitionDitection();
			shipColitionDetection();
			counter++;

			if(counter == 3 || counter == 1){

				tryCreateSnailMushroom();
				tryMoveFallSnail();
			}
			
			
			if(counter == 3){

				moveAllCentipedes();
				counter = 0;
			}

			tryCreateSpider();

			tryCreatFallSnail();
			
			
			
			
			

			if(LIVES <= 0){		

				youLoseLabel = new GLabel("YOU LOSE :] ",  200, 600);
				youLoseLabel.setColor(Color.white);
				add(youLoseLabel);

				break;

			}

			tryMoveSpider();

			youWin();
			
			
			if(canShoot == true && spaceHeld == true){
				
				shootBullet();
				
				youShootSound.play();
				
			}
			

		}//while true brace







	}



	public void keyPressed(KeyEvent a) {


		// if(e.getKeyChar()=='a' || e.getKeyChar()=='A')
		if(a.getKeyChar() == 'a' || a.getKeyChar() == 'A'){

			ship.setMovingLeft();

		}
		// if(e.getKeyChar()=='d' || e.getKeyChar()=='D')
		if(a.getKeyChar() == 'd' || a.getKeyChar() == 'D'){

			ship.setMovingRight();

		}



		// if(e.getKeyChar()=='w' || e.getKeyChar()=='W'){
		if(a.getKeyChar() == 'w' || a.getKeyChar() == 'W'){

			ship.setMovingUp();
		}


		// if(e.getKeyChar()=='s' || e.getKeyChar()=='S'){
		if(a.getKeyChar() == 's' || a.getKeyChar() == 'S'){

			ship.setMovingDown();
		}


		
		//if space bar is pushed
		if(a.getKeyCode() == 32 && canShoot){
			
			spaceHeld = true;
			
			

			
			

		}

		

	}//keyPressed "a" brace




	public void keyReleased(KeyEvent d) {

		println("key released");
		
		
		
		//if space bar is pushed
		if(d.getKeyCode() == 32){
			


			spaceHeld = false;
			

		}
		
		



		if(d.getKeyChar() == 'a' || d.getKeyChar() == 'A' 
			||d.getKeyChar() == 'd' || d.getKeyChar() == 'D'){

			ship.setNotMovingLR();
		}

		if(d.getKeyChar() == 'w' || d.getKeyChar() == 'W' 
			||d.getKeyChar() == 's' || d.getKeyChar() == 'S'){

			ship.setNotMovingUD();
		}

	}//keyReleased brace





	public void moveAllBullets(){

		if(bullet != null){
			bullet.move(0, -bulletSpeed);


			//remove bullet if goes off screen
			if(bullet.getY() < -10){

				canShoot = true;

				remove(bullet);
			}


		}

	}







	public void ship(){

		ship = new Ship();
		add(ship);



	}// ship brace

	public void mushroomCreator(){



		for(int i = 0; i < 50; i ++){


			int mushroom1X = rgen.nextInt(30);
			int mushroom1Y = rgen.nextInt(29)+1;

			while(grid[mushroom1X][mushroom1Y] != null){
				mushroom1X = rgen.nextInt(30);
				mushroom1Y = rgen.nextInt(29)+1;
			}

			createMushroom(mushroom1X, mushroom1Y);


		}//for brace


	}//mushroomCreator brace


	public void bulletColitionDitection(){
		if(bullet != null){
			GObject object = getElementAt(bullet.getX() + bullet.getWidth()/2,bullet.getY() -1);

			if(object == null){
				
			object = getElementAt(bullet.getX() + bullet.getWidth()/2,bullet.getY() + bullet.getHeight() + 1);
				
			}
			
			if(object != null && object instanceof Mushroom){

				removeBullet();

				((Mushroom) object).mushroomHit();

				changeScore(10);


				if(((Mushroom) object).isDestriod() == true){

					remove(object);
					grid[((Mushroom) object).getGridX()][((Mushroom) object).getGridY()] = null;

				}

			}

			if(object != null && object instanceof CentipedePart){

				removeBullet();

				changeScore(50);

				remove(object);

				CpDieSound.play();
				
				centipede.remove(object);


				int CentipedeDieSpotX = (int) object.getX();
				int CentipedeDieSpotY = (int) object.getY();


				createMushroom(CentipedeDieSpotX/16, (CentipedeDieSpotY-30)/16);



			}

			if(object != null && object instanceof Spider){

				removeBullet();

				changeScore(250);

				CpDieSound.play();
				
				remove(object);


				spider = null;


			}

			//if you hit this, (look down :) 
			if(object != null && object instanceof FallSnail){

				removeBullet();

				changeScore(500);

				
				CpDieSound.play();
				
				remove(object);


				fallSnail = null;


			}
			

		}




	}//bulletColitionDitection


	private void shipColitionDetection(){

		GObject object = getElementAt(ship.getX() +2,ship.getY() + ship.getHeight()/2);
		if(object == null){
			object = getElementAt(ship.getX() +ship.getWidth() -2,ship.getY() + ship.getHeight()/2);
		}

		
		//detecs if any of the objects in the "if" below have hit YOU!!!
		if(object != null && (object instanceof CentipedePart || object instanceof Spider || object instanceof FallSnail)){

			LIVES = LIVES - 1;
			livesLabel.setLabel("lives: "+ LIVES);

			youDieSound.play();
			
			youDead();





		}


		
		
		
	}	//shipColitionDetectio brace

	public void youDead(){


		for(int i = 0; i < centipede.size(); i++){

			remove(centipede.get(i));

		}
		centipede = new ArrayList<CentipedePart>();

		
		if(spider != null){
			
			remove(spider);
			spider = null;
		}
		
		if(fallSnail != null){
			
			remove(fallSnail);
			fallSnail = null;
			
		}

		


		//create again
		createCentipedes();

		regrowMushrooms();


	}//youDead brace



	public void createCentipedes(){

		for(int i = 0; i < 12; i ++){

			CentipedePart cp = new CentipedePart(1*(i), 0);
			add(cp);
			centipede.add(cp);


		}


	}


	private void createMushroom(int x, int y){
		Mushroom mushroom2 = new Mushroom(x, y);

		add(mushroom2);

		grid[x][y] = mushroom2;
	}

	private void removeBullet(){
		remove(bullet);
		bullet = null;
		canShoot = true;
	}


	public void scoreLabel(){

		scoreLabel = new GLabel("Score: " + score, 20, 20);
		scoreLabel.setColor(Color.white);
		add(scoreLabel);


	}

	public void livesLabel(){

		livesLabel = new GLabel("Lives: " + LIVES, 415, 20);
		livesLabel.setColor(Color.white);
		add(livesLabel);


	}


	private void changeScore(int increase){

		score = score + increase;	
		scoreLabel.setLabel("score: "+ score);


	}


	private void moveAllCentipedes(){

		for(int i = 0; i < centipede.size(); i++){

			CentipedePart currnetPart = centipede.get(i);	

			currnetPart.move();



		}
	}


	private void regrowMushrooms(){

		for(int x = 0; x < 30; x ++){

			for(int y = 0; y < 40; y ++){

				Object object = grid[x][y];

				if(object != null && object instanceof  Mushroom){

					((Mushroom) object).mushroomRemade();

				}



			}//y brace

		}//x brace

	}


	public void tryCreateSpider(){


		if(spider == null){

			int number = rgen.nextInt(spiderSpawnRate);

			if(number == 1){

				spider = new Spider();
				add(spider);

			}

		}


	}


	public void tryMoveSpider(){

		if(spider != null){

			spider.move(spiderSpeed);


			if(spider.isOffScreen() == true){

				remove(spider);

				spider = null;
			}


		}
			
	}//try move spider brace


	public void youWin(){


		//maybe CentipedePart?
		if(centipede.size() == 0){

			//create again
			createCentipedes();
			//regrows mushrooms
			regrowMushrooms();  
			
			spiderSpawnRate = (int) (spiderSpawnRate/newLevelFreaquencyIncreace);
			fallSnailSpawnRate = (int) (fallSnailSpawnRate/newLevelFreaquencyIncreace);
			spiderSpeed = spiderSpeed * newLevelFreaquencyIncreace;
			
			if(spider != null){

				remove(spider);
				spider = null;
			}
			
			if(fallSnail != null){
				
				remove(fallSnail);
				fallSnail = null;
				
			}




		}


	}


	public void tryCreatFallSnail(){

		if(fallSnail == null && spiderSpeed != 3){

			int number = rgen.nextInt(fallSnailSpawnRate);
			if(number == 1){

				fallSnail = new FallSnail();
				add(fallSnail);
				
				fallSnailSound.play();
				

			}

		}




	}// tryCreatFallSnail brace

	public void tryMoveFallSnail(){

		if(fallSnail != null){

			fallSnail.move(0, 16);


			if(fallSnail.isOffScreen() == true){

				remove(fallSnail);

				fallSnail = null;
			}


		}
			
	}//try move spider brace
	
	
	
	
	public void tryCreateSnailMushroom(){
		
		if(fallSnail != null ){
			
			int gridx = fallSnail.getGridX();
			int gridy = fallSnail.getGridY();
			
			if(grid[gridx][gridy] == null){
				
				int randomMushroomCreator = rgen.nextInt(101);
				
				if(randomMushroomCreator <= 30 && gridy <= 37){
					
					createMushroom(gridx, gridy);
					
				}
				
				
				
				
			}
			
			
			
		}
		
		
	
		
		
	}
	
	
	public void shootBullet(){
		
		bullet = new GRect(ship.getX() + ship.getWidth()/2 - 1, ship.getY(),2,10);
		bullet.setColor(Color.yellow);
		bullet.setFilled(true);
		bullet.setFillColor(Color.yellow);
		add(bullet);

		canShoot = false;
		
	}
	





}//end brace