package com.pedrinhojogos.world;

import java.awt.Graphics;

import java.awt.image.BufferedImage;

import com.pedrinhojogos.enums.TileType;
import com.pedrinhojogos.main.Game;

public class Tile {
	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, 16, 16);
	public static BufferedImage TILE_SUBFLOOR = Game.spritesheet.getSprite(0, 16, 16, 16);
	public static BufferedImage TILE_NOTHING = Game.spritesheet.getSprite(0, 80, 16, 16);
	
	private BufferedImage sprite;
	private double x, y;
	private final double speed = 0.5;
	private TileType type;
	private boolean penetrable;
	
	public Tile(double x, double y, TileType type, boolean penet) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.penetrable = penet;

		switch (type) {
			case FLOOR:
				this.sprite = TILE_FLOOR;
				break;

			case DIRT:
				this.sprite = TILE_SUBFLOOR;
				break;
		
			default:
				this.sprite = TILE_NOTHING;
				break;
		}
	}
	
	public boolean tick () {
		x--;
		if(x + 112 < 0)
			return true;
		return false;
		
	}
	
	public double getX() {
		return x;
	}

	public TileType getType(){
		return type;
	}

	public boolean getPenetrable(){
		return !penetrable;
	}
	
	public void render (Graphics g) {
		g.drawImage(sprite, (int) (x - Camera.x), (int) (y - Camera.y), null);
		
	}
}
