import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import javax.imageio.*;

import java.awt.image.*;

public class World {

	private static final int worldWidth = 96;
	private static final int worldHeight = 80;

	// 6 - 22 WALLTILES

	private Player player;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Enemy> enemies;
	private ArrayList<util.Rectangle> walls;

	private ArrayList<String> imageFiles;
	private BufferedImage[] tiles;
	private int[][] map;

	public World() {
		player = new Player(3106, 1610);
		projectiles = new ArrayList<Projectile>();
		enemies = new ArrayList<Enemy>();
		walls = new ArrayList<util.Rectangle>();
	}

	public void initTiles() {
		Filefetcher imageGetter = new Filefetcher();
		imageFiles = imageGetter.showFiles(System.getProperty("user.dir") + "/environment");
		tiles = new BufferedImage[imageFiles.size()];
		for (int i = 0; i < imageFiles.size(); i++) {
			try {
			    tiles[i] = ImageIO.read(new File("environment/" + imageFiles.get(i)));

			} catch (IOException e) {System.out.println("Image not found");}
		}

		BufferedReader get = null;
		map = new int[6 * 16][5 * 10];
		try {
			get = new BufferedReader(new FileReader("rooms/arena.txt"));

			for (int i = 0; i < 5 * 10; i++) {
				String line = get.readLine();
				for (int j = 0; j < 6 * 16; j++) {
					int n = line.charAt(j) - 32;
					map[j][i] = n;
					if (6 <= n && n <= 22) {
						walls.add(new util.Rectangle(j*64, i*64, 64, 64));
					}
				}
			}
			
		}  catch (IOException e) {
			System.out.println("File error");
		}
	}

	public void render(Graphics g) {

		for (int i = (player.getIntY() - 300) / 64; i < (player.getIntY() - 300) / 64 + 11; i++) {
			for (int j = (player.getIntX() - 400) / 64; j < (player.getIntX() - 400) / 64 + 16; j++) {
				if (i < 0 || j < 0 || j >= 16 * 6 || i >= 5 * 10) {
					g.setColor(Color.BLACK);
					g.fillRect(j * 64 - player.getIntX() + 400, i * 64 - player.getIntY() + 300, 64, 64);
				} else {
					g.drawImage(tiles[map[j][i]], j * 64 - player.getIntX() + 400, i * 64 - player.getIntY() + 300, null);
				}
			}
		}

		for (int i = projectiles.size() - 1; i >= 0; i--) {
			Projectile proj = projectiles.get(i);
			Color c = null;
			
			if (proj.getType().equals("bullet")) {
				g.setColor(Color.BLACK);
				g.fillOval(proj.getIntX() - player.getIntX() + 400 - proj.getRadius() / 2, proj.getIntY() - player.getIntY() + 300 - proj.getRadius() / 2, proj.getRadius(), proj.getRadius());
			}
		}
	}

	public void moveEnemies() {
		for (int i = enemies.size() - 1; i >= 0; i--) {
			Enemy enemy = enemies.get(i);
			enemy.move(0, 0);
		}
	}

	public void moveProjectiles() {
		for (int i = projectiles.size() - 1; i >= 0; i--) {
			Projectile proj = projectiles.get(i);
			proj.move();
			for (util.Rectangle w : walls) {
				if (w.intersects(proj.getX() - proj.getRadius(), proj.getY() - proj.getRadius(), proj.getRadius(), proj.getRadius())) {
					projectiles.remove(i);
				}
			}
		}
	}

	public void useWeapon(double ang) {
		Projectile[] toAdd = player.useWeapon(ang);
		for (Projectile proj : toAdd) {
			projectiles.add(proj);
		}
	}

	public void movePlayer(double dx, double dy) {
		player.move(dx, dy);
		for (util.Rectangle w : walls) {
			if (w.intersects(player.getX() - 32, player.getY() - 32, 64, 64)){
				if (dx < 0) player.setX(w.getX() + w.getWidth() + 32);
				else if (dx > 0) player.setX(w.getX() - 32);
				
				if (dy < 0) player.setY(w.getY() + w.getHeight() + 32);
				else if (dy > 0) player.setY(w.getY() - 32);
			}
		}
	}
}