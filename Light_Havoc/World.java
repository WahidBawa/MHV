import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import javax.imageio.*;

import java.awt.image.*;
import util.*;

public class World {

	private static final int worldWidth = 96;
	private static final int worldHeight = 80;

	private Player player;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Enemy> enemies;

	private ArrayList<String> imageFiles;
	private BufferedImage[] tiles;
	private int[][] map;

	public World() {
		player = new Player(worldWidth * 64 / 2, worldHeight * 64 / 2);
		projectiles = new ArrayList<Projectile>();
		enemies = new ArrayList<Enemy>();
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
					map[j][i] = line.charAt(j);
				}
			}
			
		}  catch (IOException e) {
			System.out.println("File error");
		}
	}

	public void render(Graphics g) {
		

		for (int i = projectiles.size() - 1; i >= 0; i--) {
			Projectile proj = projectiles.get(i);
			Color c = null;
			
			if (proj.getType().equals("bullet")) {
				g.setColor(Color.BLACK);
				g.fillOval(proj.getIntX() - player.getIntX() + 400 - proj.getRadius() / 2, proj.getIntY() - player.getIntY() + 300 - proj.getRadius() / 2, proj.getRadius(), proj.getRadius());
			}
		}

		for (int i = (player.getIntY() - 300) / 64; i < (player.getIntY() - 300) / 64 + 11; i++) {
			for (int j = (player.getIntX() - 400) / 64; j < (player.getIntX() - 400) / 64 + 17; j++) {
				if (i < 0 || j < 0 || j > 16 * 6 || i > 5 * 10) {
					g.setColor(Color.BLACK);
					g.fillRect(j * 64 - player.getIntX() + 400, i * 64 - player.getIntY() + 300, 64, 64);
				} else {
					g.drawImage(tiles[map[j][i] - 32], j * 64 - player.getIntX() + 400, i * 64 - player.getIntY() + 300, null);
				}
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
	}

	public boolean collidesRooms(int x, int y) {
		double[][] roomStarts = new double[][] {{0, 0.5}, {0, 3.5}, {1, 0}, {1, 1}, {1, 3}, 
														{1, 4}, {1.5, 2}, {2, 0.5}, {2, 3.5}, {2.5, 1.5}, 
														{2.5, 2.5}, {3, 0.5}, {3, 3.5}, {3.5, 2}, {4, 0}, 
														{4, 1}, {4, 3}, {4, 4}, {5, 0.5}, {5, 3.5}};

		for (int i = 0; i < 20; i++) {
			if (roomStarts[i][0] * 16 * 64 <= x && x <= roomStarts[i][0] * 16 * 64 + 15 * 64 && roomStarts[i][1] * 10 * 64 <= y && y <= roomStarts[i][1] * 10 * 64 + 9 * 64) {
				return true;
			}
		}

		return false;
	}
}