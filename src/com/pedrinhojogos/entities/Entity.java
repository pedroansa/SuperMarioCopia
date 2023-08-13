package com.pedrinhojogos.entities;

import java.awt.Graphics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;

import com.pedrinhojogos.main.Game;
import com.pedrinhojogos.world.Camera;


public class Entity {
	protected int deadFallback = 0;
	protected boolean dead = false;
	protected double x;
	protected double y;
	public double speed = 0;
	private int width;
	private int height;
	protected int dir = 1;

	private BufferedImage rightsprite;	
	protected BufferedImage deadsprite;	

	public Entity(int x, int y, int width, int height, BufferedImage spriter) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rightsprite = spriter;
	}

	void Atacar(Entity en) {
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public void setX(int x) {
		this.x = x; 
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void tick(){
		if(this.dead){
			Game.entities.remove(this);
		}
	}

	public void render(Graphics g) {
		if(this.dead)
			g.drawImage(deadsprite, this.getX() - Camera.x , this.getY() - Camera.y , null);
		else
			g.drawImage(rightsprite, this.getX() - Camera.x , this.getY() - Camera.y , null);
	}

	public Rectangle createCollisionBox(int x, int y) {
		Rectangle collisionBox = new Rectangle(x , y , width, height);
		return collisionBox;
	}
	
	public Rectangle createCollisionBox(int x, int y, int width, int height) {
		Rectangle collisionBox = new Rectangle(x , y , width, height);
		return collisionBox;
	}
	

	public static double Distance(Entity e1, Entity e2) {
		int x1 = e1.getX();
		int x2 = e2.getX();
		int y1 = e1.getY();
		int y2 = e2.getY();
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}

	public Rectangle createCollisionBox() {
		Rectangle collisionBox = new Rectangle(getX(), getY(), width, height);
		return collisionBox;
	}

	public static boolean isColidding(Entity e1,Entity e2){
		Rectangle e1Mask = new Rectangle(e1.getX(),e1.getY(),e1.getWidth(),e1.getHeight());
		Rectangle e2Mask = new Rectangle(e2.getX(),e2.getY(),e2.getWidth(),e2.getHeight());
		
		return e1Mask.intersects(e2Mask);
	}

	public void Dies() {
		this.dead = true;
	}
	
}
