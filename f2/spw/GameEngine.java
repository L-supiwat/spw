package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private ArrayList<Item> items = new ArrayList<Item>();	
	private SpaceShip v;	
	
	private Timer timer;
	
	private long score = 0;
	private double difficulty = 0.1;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}
	private void generateItem(){
		Item i = new Item((int)(Math.random()*390), 30);
		gp.sprites.add(i);
		items.add(i);
	}
	
	private void process(){
	
		if(Math.random() < difficulty){
			generateEnemy();
			generateItem();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
		
		
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
			}
		
		}
		Iterator<Item> i_iter = items.iterator();
		while(i_iter.hasNext()){
			Item i = i_iter.next();
			i.proceed();
		
		
			if(!i.isAlive()){
				i_iter.remove();
				gp.sprites.remove(i);
				score += 100;
			}
		
		}
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double ir;
		for(Item i : items){
			ir = i.getRectangle();
			if(ir.intersects(vr)){
				score += 500;
			}
		}
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				die();
				return;
			}
		}
	}
	
	public void die(){
		timer.stop();
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_NUMPAD1:
			v.move(-2,2);
			break;	
		case KeyEvent.VK_NUMPAD3:
			v.move(2,2);
			break;	
		case KeyEvent.VK_NUMPAD7:
			v.move(-2,-2);
			break;	
		case KeyEvent.VK_NUMPAD9:
			v.move(2,-2);
			break;	
		case KeyEvent.VK_NUMPAD2:
			v.hmove(2);
			break;	
		case KeyEvent.VK_NUMPAD8:
			v.hmove(-2);
			break;			
		case KeyEvent.VK_NUMPAD4:
			v.vmove(-2);
			break;
		case KeyEvent.VK_NUMPAD6:
			v.vmove(2);
			break;
		case KeyEvent.VK_D:
			difficulty += 0.2;
			break;
		}
	}

	public long getScore(){
		return score;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
