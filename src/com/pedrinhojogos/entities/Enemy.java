package com.pedrinhojogos.entities;

import java.awt.image.BufferedImage;

import com.pedrinhojogos.main.Game;
import com.pedrinhojogos.world.Camera;
import com.pedrinhojogos.world.World;

public class Enemy extends Entity{

    public static BufferedImage ENEMY_FRONT = Game.spritesheet.getSprite(48, 16, 16, 16);

    public Enemy(int x, int y, int width, int height) {
        super(x, y, width, height, ENEMY_FRONT);
        this.speed = 1;
        this.deadsprite = Game.spritesheet.getSprite(48, 32, 16, 16);
    }

    @Override
    public void tick(){
        if(this.dead){
            deadFallback++;
            if(deadFallback > 20){
                Game.entities.remove(this);
            }
            return;
        }
        this.x += dir * speed;

        if(World.isFree((int)(x + 10*dir), (int)(y)) ){
            dir *= -1;
        }

        
        if(!World.isFree((int)(x + dir), (int)(y-10)) ){
            dir *= -1;
        }
    }
    
}
