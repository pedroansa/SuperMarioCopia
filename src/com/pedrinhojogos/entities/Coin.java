package com.pedrinhojogos.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.pedrinhojogos.main.Game;
import com.pedrinhojogos.world.Camera;

public class Coin extends Entity{
    
    public static BufferedImage[] COIN_FRONT = new BufferedImage[3];
    public int sprite_n = 0;
    private int change_sprite = 0;
    
    public Coin(int x, int y, int width, int height) {
        super(x, y, width, height, COIN_FRONT[0]);
        COIN_FRONT[0] = Game.spritesheet.getSprite(48, 48, 16, 16);
        COIN_FRONT[1] = Game.spritesheet.getSprite(48+16, 48, 16, 16);
        COIN_FRONT[2] = Game.spritesheet.getSprite(48+32, 48, 16, 16);
    }

    @Override
    public void tick(){
        if(this.dead){
			Game.entities.remove(this);
		}
        
        change_sprite++;
        if(change_sprite > 10){
            sprite_n++;
            change_sprite = 0;
        }
        
        if(sprite_n > 3)
            sprite_n = 0;
    }
    @Override
    public void render(Graphics g){
        if(sprite_n == 3)
            g.drawImage(COIN_FRONT[1], this.getX() - Camera.x , this.getY() - Camera.y , null);
        else    
            g.drawImage(COIN_FRONT[sprite_n], this.getX() - Camera.x , this.getY() - Camera.y , null);    

    }

}
