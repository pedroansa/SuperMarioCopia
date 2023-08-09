package com.pedrinhojogos.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.pedrinhojogos.entities.Enemy;
import com.pedrinhojogos.entities.Player;
import com.pedrinhojogos.enums.TileType;
import com.pedrinhojogos.main.Game;

public class World {

    private static Tile[] tiles;
    public static int WIDTH, HEIGTH;
    public int player_x;
    public int player_y;
    public static final int TILE_SIZE = 16;

    public World(String path){
        try {
            BufferedImage imageMap = ImageIO.read(getClass().getResource(path));
            WIDTH = imageMap.getWidth();
            HEIGTH = imageMap.getHeight();

            int[] pixels = new int[WIDTH * HEIGTH];
            tiles = new Tile[WIDTH * HEIGTH];
            imageMap.getRGB(0, 0, WIDTH, HEIGTH, pixels, 0, WIDTH);

            for (int x = 0; x < WIDTH; x++){
                for (int y = 0; y < HEIGTH; y++){


                    if(pixels[x + (y * WIDTH)] == 0xFFFFFFFF){
                        // Tile Chao
                        if(y > 0 && pixels[x + ((y-1) * WIDTH)] == 0xFFFFFFFF)
                            tiles[x + (y * WIDTH)] = new Tile(x*16, y*16, TileType.DIRT, false);
                        else{
                            tiles[x + (y * WIDTH)] = new Tile(x*16, y*16, TileType.FLOOR, false);
                        }
                    }

                    else if(pixels[x + (y * WIDTH)] == 0xFFFF0000){
                        Game.player.setX(x*16);
                        Game.player.setY(y*16);
                        tiles[x + (y * WIDTH)] = new Tile(x*16, y*16, TileType.NOTHING, true);
                    }

                    else if(pixels[x + (y * WIDTH)] == 0xFF0009FF){
                        Enemy enemy = new Enemy(x*16, y*16, 16, 16);
                        Game.entities.add(enemy);
                        tiles[x + (y * WIDTH)] = new Tile(x*16, y*16, TileType.NOTHING, true);
                    }
                    

                    else{
                        tiles[x + (y * WIDTH)] = new Tile(x*16, y*16, TileType.NOTHING, true);
                    }

                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean isFree(int xnext,int ynext){
		
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-5) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-5) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*WIDTH)].getPenetrable()) ||
				(tiles[x2 + (y2*WIDTH)].getPenetrable()) ||
				(tiles[x3 + (y3*WIDTH)].getPenetrable()) ||
				(tiles[x4 + (y4*WIDTH)].getPenetrable()) 
                );
	}

    public void render(Graphics g){
        int xstart = Camera.x/16;
        int ystart = Camera.y/16;

        int xfinal = xstart + (Game.WIDGHT/16);
        int yfinal = ystart + (Game.HEIGTH/16);

        for (int x = xstart; x <= xfinal; x++){
            for (int y = ystart; y < yfinal; y++){
                if(x < 0 || y < 0 || x >= WIDTH || y >= HEIGTH)
                    continue;
                    
                Tile tile = tiles[x + (y * WIDTH)];
                tile.render(g);
            }
        }
    }
}
