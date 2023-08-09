package com.pedrinhojogos.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.pedrinhojogos.main.Game;
import com.pedrinhojogos.world.Camera;
import com.pedrinhojogos.world.World;

public class Player extends Entity {

	public boolean morto = false;
	public int moved = 0;
	public boolean jump = false;
	public boolean blockjump = false;
	private double force = 0.1;
	private double forcejump = 0.5;
	private double speedjump = 0.05;
	private double upaceleration = 0;
	
	private static double gravity = 1.5;
	private static final double maxspeed = 2;
	int timing = 0;

	private int run_frame = 0;
	private int change_frame = 0;
	
	public static BufferedImage[] PLAYER_FRONT = new BufferedImage[4];
	public static BufferedImage[] PLAYER_BACK = new BufferedImage[4];

	
	
	public Player(int x, int y, int width, int height) {
		super(x, y, width, height, Game.spritesheet.getSprite(16, 0, 16, 16));
		PLAYER_FRONT[0] = Game.spritesheet.getSprite(16, 0, 16, 16);
		PLAYER_FRONT[1] = Game.spritesheet.getSprite(16, 16, 16, 16);
		PLAYER_FRONT[2] = Game.spritesheet.getSprite(16, 32, 16, 16);
		PLAYER_FRONT[3] = Game.spritesheet.getSprite(16, 48, 16, 16);
		PLAYER_BACK[0] = Game.spritesheet.getSprite(32, 0, 16, 16);
		PLAYER_BACK[1] = Game.spritesheet.getSprite(32, 16, 16, 16);
		PLAYER_BACK[2] = Game.spritesheet.getSprite(32, 32, 16, 16);
		PLAYER_BACK[3] = Game.spritesheet.getSprite(32, 48 , 16, 16);

		
	}

	@Override
	public void tick() {

		if (moved == 0){
			run_frame = 0;
			if(speed < 0){
				speed += force/2;
			}

			else if(speed > 0){
				speed -= force/2;
			}

			if(Math.abs(speed) < 0.2){
				speed = 0;
			}
		}
		else if(moved == 1){
			dir = 1;
			
			// Here we change the frame of running
			change_frame++;
			if(change_frame > 10){
				change_frame = 0;

				if(run_frame == 2)
					run_frame = 1;
				else
					run_frame++;
			}
			if(this.speed < maxspeed){
				this.speed += force;
			}
		}
		else if(moved == 2){
			dir = -1;
			// Here we change the frame of running
			change_frame++;
			if(change_frame > 10){
				change_frame = 0;

				if(run_frame == 2)
					run_frame = 1;
				else
					run_frame++;
			}
			if(this.speed > -maxspeed){
				this.speed -= force;
			}
		}

		if(World.isFree((int)(x+speed), (int)y)){
			x += speed;
		}

		// Gravity commands
		if(jump && !blockjump){
			if(speedjump > -3){
				speedjump -= 0.2;
			}
			// Here we block the jump
			else{
				blockjump = true;
				timing = 0;
			}
				
			
		}
		// It's not jumping
		else{
			speedjump += 0.2;
		}

		// Not trying to jump
		if(speedjump > 0){
			// The ground don't let it
			if(!World.isFree((int)(x), (int)(y+speedjump))){
				speedjump = 0;
				timing++;
				if(timing > 10){
					blockjump = false;
					timing = 0;
				}
					
			}
		}
		//Trying to jump
		else{
			if(!World.isFree((int)(x), (int)(y+speedjump))){
				speedjump = 0;
				blockjump = true;
			}
		}

		// Moving y axis command
		y += speedjump;

		// Update Camera
		Camera.x = Camera.clamp(this.getX() - (Game.WIDGHT/2), 0, World.WIDTH*16 - Game.WIDGHT);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGTH / 2), 0, World.HEIGTH * 16 - Game.HEIGTH);

	}

	// private void CheckCollisionCano() {
	// 	// TODO Auto-generated method stub
	// 		if(!morto && isColidding(this,Game.cano)) {
	// 			Game.gameState = "GAMEOVER";
	// 			force = 0;
	// 			this.morto = true;
	// 		}
		
	// }
	
	// private static boolean isColidding(Entity e1, Cano e2) {
	// 	Rectangle c1 = e1.createCollisionBox();
	// 	Rectangle c2 = e2.createCollisionBox(false);
	// 	Rectangle c3 = e2.createCollisionBox(true);

	// 	return c1.intersects(c2) || c1.intersects(c3);
	// }

	@Override
	public void render(Graphics g) {
		if (jump && !blockjump){
			if(dir == 1)
			g.drawImage(PLAYER_FRONT[3],(int) x - Camera.x ,(int) y - Camera.y , null);
		else 
			g.drawImage(PLAYER_BACK[3],(int) x - Camera.x ,(int) y - Camera.y, null);
		}
		else{
			if(dir == 1)
				g.drawImage(PLAYER_FRONT[run_frame],(int) x - Camera.x ,(int) y - Camera.y , null);
			else if(dir == -1)
				g.drawImage(PLAYER_BACK[run_frame],(int) x - Camera.x ,(int) y - Camera.y, null);
			
		}
	}
	
	

}
