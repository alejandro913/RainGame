//UIUC CS125 FALL 2014 MP. File: RainGame.java, CS125 Project: PairProgramming, Version: 2015-02-23T21:30:19-0600.400781264
/**
 * @author replace-this-with-your-netids-on-this-line-here-with-a-comma-between-them
 */
public class RainGame {

	public static void main(String[] args) {
		// To get points type your netids above (CORRECTLY-Please double check your partner correctly spells your netid correctly!!)
		// Your netid is the unique part of your @illinois email address
		// Do not put your name or your UIN. 
		// REMEMBER TO COMMIT this file...
	
		int x=0, y=0, dx=0, dy=0, score = 0;
		String text = "";
		long startTime =System.currentTimeMillis();
		long abs_startTime=System.currentTimeMillis();
		int level=0;
		
		
		Zen.setFont("Helvetica-64");
		while (Zen.isRunning()) {

			if (text.length() == 0) {
				y = 2;
				x = Zen.getZenHeight() / 2;
				dx = 0;
				dy = 2+(int)((System.currentTimeMillis()-abs_startTime)/8000);
				
				text = "" + (int) (Math.random() * 999);
				long elapsed = System.currentTimeMillis() - startTime;
				startTime = System.currentTimeMillis();
				score += 5000-elapsed;
				level= dy-2;
			}
			Zen.setColor(255, 0, 255);
			Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());

			Zen.setColor(0, 255, 0);
			Zen.drawText(text, x, y);
			
			Zen.drawText("Level: "+ level,10,30);
			Zen.drawText("Score: "+score,10,60);
			
			x += dx;
			y += dy;
			
			// Find out what keys the user has been pressing.
			String user = Zen.getEditText();
			// Reset the keyboard input to an empty string
			// So next iteration we will only get the most recently pressed keys.
			Zen.setEditText("");
			
			for(int i=0;i < user.length();i++) {
				char c = user.charAt(i);
				if(c == text.charAt(0))
					text = text.substring(1,text.length()); // all except first character
			}
			
			Zen.sleep(90);// sleep for 90 milliseconds

		}
	}

}
