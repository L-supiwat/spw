package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class SpaceShip extends Sprite{

	int step = 10;
	
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, width, height);
		
	}

	public void vmove(int direction){
		x += (step * direction);
		if(x < 0)
			x = 0;
		if(x > 380 - width)
			x = 380 - width;
	}
	public void hmove(int direction){
		y += (step * direction);
		if(y < 0)
			y = 0;
		if(y > 580 - height)
			y = 580 - height;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
}
