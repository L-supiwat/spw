package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class Shot extends Sprite{
	public static final int Y_TO_FADE = 30;
	public static final int Y_TO_DIE = 0;
	
	private int step = 12;
	private boolean alive = true;
	private boolean createOnPause;
	
	public Shot(int x, int y, boolean createOnPause) {
		super(x, y, 20, 20);
		this.createOnPause = createOnPause;
	}

	@Override
	public void draw(Graphics2D g) {
		if(!createOnPause){
		//////
		if(y < Y_TO_FADE)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		else{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
					(float)(Y_TO_DIE - y)/(Y_TO_DIE - Y_TO_FADE)));
		}
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);
		////////
		}
	}

	public void proceed(){
		y -= step;
		if(y < Y_TO_DIE){
			//alive = false;
		}
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
		step = 12;
	}
}