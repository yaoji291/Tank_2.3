package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import model.Tank.Direction;
import client.TankClient;

public class Missile {

	//定义两个常量，表示运动的速度
	private static final int XSPEED = 10;
	private static final int YSPEED = 10;
	
	//子弹所在的位置
	private int x;
	private int y;
	
	//坦克的高度和宽度
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	//子弹的运行方向
	private Direction dir;
	
	private boolean live = true;
	
	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	public void draw(Graphics g){
		//如果该子弹不是存活的，则不进行绘图
		if(!live){
			return ;
		}
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		move();
	}

	private void move() {
		if(dir==Direction.L){//L,LU,U,RU,R,RD,D,LD,STOP
			x -= XSPEED;
		}
		else if(dir==Direction.LU){
			x -= XSPEED;
			y -= YSPEED;
		}
		else if(dir==Direction.U){
			y -= YSPEED;
		}
		else if(dir==Direction.RU){
			x += XSPEED;
			y -= YSPEED;
		}
		else if(dir==Direction.R){
			x += XSPEED;
		}
		else if(dir==Direction.RD){
			x += XSPEED;
			y += YSPEED;
		}
		else if(dir==Direction.D){
			y += YSPEED;
		}
		else if(dir==Direction.LD){
			x -= XSPEED;
			y += YSPEED;
		}
		
		//根据子弹所在的位置x,y来判断子弹是否还存活在
		if(x<0||x>TankClient.GAME_WIDTH||y<0||y>TankClient.GAME_HEIGHT){
			live = false;
		}
	}
	public boolean isLive() {	
		return live;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public boolean hitTank(Tank t){
		//首先判断此坦克是否是存活的，如果是死的，就不打了
		if(!t.isLive()){
			return false;
		}
		if(this.getRect().intersects(t.getRect())){//判断是否有碰撞
			//碰撞之后，子弹和该坦克就应该都死了
			this.live = false;//子弹死了
			t.setLive(false);//坦克死了
			return true;
		}
		else{
			return false;
		}
	}
}
