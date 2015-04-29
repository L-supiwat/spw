package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	public GamePanel() {
		bi = new BufferedImage(650, 400, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		big.setBackground(Color.WHITE);
	}

	public void updateGameUI(GameReporter reporter,boolean cop,boolean isAlive){
		big.clearRect(0, 0, 650, 400);
		
		big.setColor(Color.BLACK);		
		big.drawString(String.format("score %07d", reporter.getScore()), 500, 30);
		big.drawString(String.format("hp %02d", reporter.getheart()), 100, 30);
		for(Sprite s : sprites){
			s.draw(big);
		}
		if(cop){
			big.drawString(String.format("PAUSE"), 300, 200);
			for(Sprite s : sprites){
				s.draw(big);
			}
		}
		if(isAlive){
			big.drawString(String.format("GAME OVER"), 300, 200);
			for(Sprite s : sprites){
				s.draw(big);
			}
		}
		
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}

}
