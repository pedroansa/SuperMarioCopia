package com.pedrinhojogos.main;

import java.awt.Canvas;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import javax.swing.JFrame;

import com.pedrinhojogos.entities.Enemy;
import com.pedrinhojogos.entities.Entity;
import com.pedrinhojogos.entities.Player;
import com.pedrinhojogos.graficos.SpriteSheet;
import com.pedrinhojogos.graficos.UI;
import com.pedrinhojogos.world.Tile;
import com.pedrinhojogos.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning;
	public static final int WIDGHT = 500;
	public static final int HEIGTH = 160;
	public static final int SCALE = 2;

	private BufferedImage image;

	public static Player player;
	
	public static List<Entity> entities;
	
	public UI ui;

	public static String gameState = "NORMAL";

	public static SpriteSheet spritesheet;

	public static Random rand;
	
	public static final double GRAVITYTOTAL = 10;
	
	public static int score = 0;

	public static int lives = 3;
	
	private boolean gameOverEnter = false;

	public static World world;	

	
	public Game() {
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDGHT * SCALE, HEIGTH * SCALE));
		initFrame();

		image = new BufferedImage(WIDGHT, HEIGTH, BufferedImage.TYPE_INT_RGB);
		spritesheet = new SpriteSheet("/spritesheet.png");
		player = new Player(0, 0, 12, 12);
		entities = new ArrayList<Entity>();
		world = new World("/map.png");
		ui = new UI();
	}

	public static void main(String arg[]) {
		Game game = new Game();
		game.start();
	}

	public void initFrame() {

		frame = new JFrame();
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.run();
	}

	public synchronized void stop() {

	}

	public static void RestartGame() {
		gameState = "NORMAL";
		player = new Player(0, 0, 12, 12);
		entities = new ArrayList<Entity>();
		world = new World("/map.png");
		Game.lives--;
		if(Game.lives < 0)
			Game.lives = 3;

		score = 0;
		return;
	}

	public void tick() {
		if (gameState == "NORMAL") {
			player.tick();

			for (int i = 0; i < entities.size(); i++){
				entities.get(i).tick();
			}
		
			}
		else if(gameState == "GAMEOVER") {
			player.tick();
			if(gameOverEnter) {
				gameOverEnter = false;
				RestartGame();
			}
		}
		
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDGHT, HEIGTH);
		world.render(g);
		player.render(g);
		for (int i = 0; i < entities.size(); i++){
			entities.get(i).render(g);
		}
		ui.render(g);

		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, SCALE * WIDGHT, SCALE * HEIGTH, null);
		if (gameState == "GAMEOVER") {
			
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.white);
			g2.setFont(new Font("arial", Font.BOLD, 90));
			g2.drawString("GAMEOVER", (WIDGHT * SCALE) / 2 - 280 , (HEIGTH * SCALE) / 2);
			g2.setColor(Color.white);
			g2.setFont(new Font("arial", Font.BOLD, 20));
			
			g2.drawString("Clique Espaço  pra continuar", (WIDGHT * SCALE) / 2 - 150, (HEIGTH * SCALE) / 2 + 40);

		}
		
		
		bs.show();
		

	}

	@Override
	public void run() {
		requestFocus();
		long lastTime = System.nanoTime();
		double amountOfticks = 60.0;
		double ns = 1000000000 / amountOfticks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				delta--;
				tick();
				render();
				frames++;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP){
			player.jump = true;
		}

		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			player.moved = 1;
		}

		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			player.moved = 2;

		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			if(gameState == "GAMEOVER"){
				gameOverEnter = true;
			}

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP){
			player.jump = false;
		}

		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT){
			player.moved = 0;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
