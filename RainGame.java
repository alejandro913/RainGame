//UIUC CS125 FALL 2014 MP. File: RainGame.java, CS125 Project: PairProgramming, Version: 2015-02-23T21:30:19-0600.400781264
/**
 * @author afolta2, aizqurd2
 */


public class RainGame {

	public static void main(String[] args) {
	
		int gposx = 0;
		int x=0, y=0, dx=0, dy=0, score=1000;
		String text = "";																	
		int level=-1;																				//the level increase once per turn
		boolean gameover = false;
		boolean gamestarted = false;
		long abs_startTime =System.currentTimeMillis();	
		level = startscreen();
		gamestarted = true;
		score = -4;
		long startTime =System.currentTimeMillis();	

		Zen.setFont("Helvetica-40");
		while (Zen.isRunning() && gamestarted) {

			if (text.length() == 0) {
				y = (int)(Math.random()*(7*Zen.getZenHeight()/8-35) + Zen.getZenHeight()/8+35);		//random starting y point													
				x = (int)(Math.random()*(Zen.getZenWidth()-70));									//more visible when starting
				dx = (int)((Math.random()*2-1)*level);  											//random velocity, to increase difficulty (+/-)
				dy = (int)((Math.random()*2-1)*level);   											//velocity increases with level increase, starts at 0
				text = "" + (int) (Math.random() * 999);
				long elapsed = System.currentTimeMillis() - startTime;
				startTime = System.currentTimeMillis();
				score += (5000-elapsed)/1000;														//score based on how quickly answered, can be negative
				gameover = score<0;
				level++;																			//level increases with time
			}
			Zen.setColor(92, 148, 252);																//background for score and level
			Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());
			
			Zen.drawImage("bg2.png", 0, 50);
			
			if (gposx > Zen.getZenWidth()-100)
				gposx=0;
			else
				gposx += 4;
			animations(abs_startTime, gposx);

			Zen.setColor(255, 255, 255);
			Zen.drawText(text, x, y);
			
			Zen.drawText("Level: "+level,10,50);													//better visibility
			Zen.drawText("Score: "+score,400,50);													//score and level take up less space
			if (gameover){
				gameoverscreen(level,score);														//game finishes
				break;
			}
			
			x += dx;
			if (x+dx<0) 																			//the following makes the numbers bounce around the screen
				dx = -dx;
			else if (x+dx>(Zen.getZenWidth()-70))
				dx = -dx;
			y += dy;
			if (y+dy<Zen.getZenHeight()/8+35)
				dy = -dy;
			else if (y+dy>(7*Zen.getZenHeight()/8-35) + Zen.getZenHeight()/8+35) 
				dy = -dy;
			
			// Find out what keys the user has been pressing.
			String user = Zen.getEditText();
			// Reset the keyboard input to an empty string
			// So next iteration we will only get the most recently pressed keys.
			Zen.setEditText("");
			
			if (user.length() != 0) {
				if(user.charAt(0) == text.charAt(0))
					text = text.substring(1,text.length()); 										// all except first character
				else if (user.charAt(0) == 'q')
					gameover = true;
			}
			Zen.flipBuffer();																		//fixes flicker
			Zen.sleep(90);																			// sleep for 90 milliseconds
		}
	}

	//bottom animations
	public static void animations(long abs_startTime, int gposx) {
		int mposx = Zen.getZenWidth()/3;
		int remainder =((int)(System.currentTimeMillis()-abs_startTime)/1000)%2;
		if (gposx < mposx+120 && gposx > mposx+20){
			Zen.drawImage("mario3.gif", mposx, Zen.getZenHeight()-110);
			mposx = -200;
		}
		switch (remainder){
			case 0: Zen.drawImage("goomba1.gif", gposx, Zen.getZenHeight()-45);
					Zen.drawImage("plant1.gif", 7*Zen.getZenWidth()/8, Zen.getZenHeight()-160);
					Zen.drawImage("mario1.gif", mposx, Zen.getZenHeight()-110);
					break;
			case 1: Zen.drawImage("goomba2.gif", gposx, Zen.getZenHeight()-45);
					Zen.drawImage("plant2.gif", 7*Zen.getZenWidth()/8, Zen.getZenHeight()-160);
					Zen.drawImage("mario2.gif", mposx, Zen.getZenHeight()-110);
					break;


		}
	}
	
	//start screen
	public static int startscreen () {
		int level = 0;
		Zen.setColor(0, 0, 0);																		//background for start screen
		Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());									
		Zen.setColor(0, 255, 0);
		Zen.drawText("ENTER STARTING LEVEL (00-99): __ ",Zen.getZenWidth()/4,Zen.getZenHeight()/3);//write instructions, q to quit? enter starting level?
		Zen.drawText("DIRECTIONS",4*Zen.getZenWidth()/10,Zen.getZenHeight()/3 + 45);
		Zen.drawText("Type the numbers on the screen as fast as you can. Your score will ",Zen.getZenWidth()/12,Zen.getZenHeight()/3 + 70);
		Zen.drawText("decrease if you answer in less than 5 seconds. The level and speed",Zen.getZenWidth()/12,Zen.getZenHeight()/3 + 90);
		Zen.drawText("will increase with each correct answer",Zen.getZenWidth()/12,Zen.getZenHeight()/3 + 110);
		Zen.drawText("While game is running, press Q to quit",Zen.getZenWidth()/12,Zen.getZenHeight()/3 + 130);
		while (level == 0) {
			String lvltext = Zen.getEditText();
			Zen.drawText(lvltext, 94*Zen.getZenHeight()/100,Zen.getZenHeight()/3);
			if (lvltext.length()>1){
				level = Integer.parseInt(lvltext)-1;												//for some reason entering 01 doesnt work
				Zen.sleep(1000);
			}
		}
		return level;
	}
	
	//gameover screen
	public static void gameoverscreen (int level, int score) {
		boolean flag = true;
		Zen.setEditText("");
		Zen.flipBuffer();
		Zen.drawText("GAMEOVER",Zen.getZenHeight()/3 ,Zen.getZenHeight()/2 );				//gameover if score falls below zero
		Zen.drawText("Level: "+level,10,50);												//better visibility
		Zen.drawText("Score: "+score,400,50);												//score and level take up less space
		Zen.drawText("Play Again? (y/n)",Zen.getZenHeight()/3,6*Zen.getZenHeight()/10);	
		Zen.flipBuffer();

		while (flag) {
			String restart = Zen.getEditText();
			if (restart.length() == 1) {
				if (restart.charAt(0) == 'y'){
					flag = false;
					Zen.closeWindow();
					String[] arguments = new String[] {"123"};
				    RainGame.main(arguments);
				}
				else {
					flag = false;
					System.exit(0);
				}

			}

		}
	}
	
}
