package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class Shot extends Sprite{
	public static final int Y_TO_FADE = 30;
	public static final int Y_TO_DIE = 0;
	
	private int step = 10;
	private boolean alive = true;
	private boolean createOnPause;
	private boolean die = false;
	public Shot(int x, int y, boolean createOnPause) {
		super(x, y+5 , 20, 10);
		this.createOnPause = createOnPause;
	}

	@Override
	public void draw(Graphics2D g) {
		//if(!createOnPause){
		//////
		if(y > Y_TO_FADE)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		else if(die){
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
					(float)(y - Y_TO_DIE)/(Y_TO_FADE - Y_TO_DIE)));
		}
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);
		////////
	//	}
	}

	public void proceed(){
		x += step;
		if(die){
			alive = false;
		}
	}
	
	public void isDie(){
		die = true;
	}
	
		public boolean isAlive(){
		return alive;
	}
	
	public boolean isCreateOnPause(){
		return createOnPause;
	}
	public void initialshot(){
		step = 0;
	}
	public void shoting(){
		step = 10;
	}
}