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
	private ArrayList<Shot> shots = new ArrayList<Shot>();
	private SpaceShip v;	
	
	public int tmpx = 180;
	public int tmpy = 450;
	private int step = 2;
	private Timer timer;
	private long score = 0;
	private double difficulty = 0.1;
	public boolean status = false;
	public boolean createOnPause = false;
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
	private void generateShot(){
		Shot s = new Shot(tmpx, tmpy , this.createOnPause);
		gp.sprites.add(s);
		shots.add(s);
	}
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30, this.createOnPause);
		gp.sprites.add(e);
		enemies.add(e);
	}
	private void generateItem(){
		Item i = new Item((int)(Math.random()*390), 30, this.createOnPause);
		gp.sprites.add(i);
		items.add(i);
	}
	
	private void process(){
		
		if(getScore() % 1000 == 100){
			generateItem();
		}
				
		if(Math.random() < difficulty){
			generateEnemy();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive() || e.isCreateOnPause()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
			}
		}
		
		Iterator<Item> i_iter = items.iterator();
		while(i_iter.hasNext()){
			Item i = i_iter.next();
			i.proceed();
		
			if(!i.isAlive() || i.isCreateOnPause()){
				i_iter.remove();
				gp.sprites.remove(i);
			}
		
		}
		
		Iterator<Shot> s_iter = shots.iterator();
		while(s_iter.hasNext()){
			Shot s = s_iter.next();
			s.proceed();
		
		
			if(!s.isAlive() || s.isCreateOnPause()){
				s_iter.remove();
				gp.sprites.remove(s);
			}
		
		}
		
		gp.updateGameUI(this,false);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double sr;
		Rectangle2D.Double ir;
		
		for(Shot s : shots ){
			sr = s.getRectangle();
			for(Enemy e : enemies){
				er = e.getRectangle();
				if(er.intersects(sr)){
					gp.sprites.remove(e);
					gp.sprites.remove(s);
					e.isAlive();
					e.isDie();
					s.isDie();
					s.isAlive();
					score += 1000;
				/*if(er.intersects(vr)){
					die();
					return;
				}*/	
				}
			}
		}
		
		for(Item i : items){
			ir = i.getRectangle();
			if(ir.intersects(vr)){
				gp.sprites.remove(i);
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
	
	void control(KeyEvent e){
		
		switch(e.getKeyCode()){
			case KeyEvent.VK_P:{
				if(!status){
					stop();
					die();
					status = true;
					createOnPause = true;
					for(Enemy en : enemies){
						en.enemystop();
						gp.updateGameUI(this,status);
					}
					break;				
				}
				if(createOnPause){
					resume();
					status = false;
					createOnPause = false;
					start();
					for(Enemy en : enemies){
						gp.updateGameUI(this,status);
						en.enemyresume();
					}break;
				}
			}
		}
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			v.hmove(step);
			tmpy += 8*step;
			break;	
		case KeyEvent.VK_UP:
			v.hmove(-step);
			tmpy -= 8*step;
			break;			
		case KeyEvent.VK_LEFT:
			v.vmove(-step);
			tmpx -= 8*step;
			break;
		case KeyEvent.VK_RIGHT:
			v.vmove(step);
			tmpx += 8*step;
			break;
		case KeyEvent.VK_D:
			difficulty += 0.2;
			break;
		case KeyEvent.VK_SPACE:
			generateShot();
			break;		
		}
	}

	public long getScore(){
		return score;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		control(e);
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
	
	public void stop(){
		step = 0;
	}
	
	public void resume(){
		step = 2;
	}
}
