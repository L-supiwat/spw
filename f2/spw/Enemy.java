package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	private int step = 12;
	private boolean alive = true;
	private boolean createOnPause;
	private boolean die = false;
	public Enemy(int x, int y, boolean createOnPause) {
		super(x, y, 20, 20);
		this.createOnPause = createOnPause;
	}

	@Override
	public void draw(Graphics2D g) {
		if(!createOnPause){
		//////
		if(y < Y_TO_FADE)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		else if(y >= Y_TO_FADE || !die){
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
					(float)(Y_TO_DIE - y)/(Y_TO_DIE - Y_TO_FADE)));
		}
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width, height);
		////////
		}
	}

	public void proceed(){
		y += step;
		if(y > Y_TO_DIE){
			alive = false;
		}
	}
	
	public boolean isAlive(){
			return alive;
		
	}
	public boolean isCreateOnPause(){
		return createOnPause;
	}
	
	public void isDie(){
		die = true;
	}
	
	public void enemystop(){
		step = 0;
	}
	public void enemyresume(){
		step = 12;
	}
}