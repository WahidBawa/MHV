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
	}

	public void render(Graphics g) {
		BufferedReader get = null;
		try {
			get = new BufferedReader(new FileReader("rooms/arena.txt"));
		

			for (int i = 0; i < (player.getIntY() - 300) / 64 - 1; i++) {
				get.readLine();
			}
			String line;
			for (int i = 0; i < 12; i++) {
				line = get.readLine();
				for (int j = (player.getIntX() - 400) / 64; j < (player.getIntX() - 400) / 64 + 16; j++) {
					g.drawImage(tiles[(int)(line.charAt(j) - 32)], j * 64 - player.getIntX() + 400, (((player.getIntY() - 300) / 64 - 1 + i) * 64 - player.getIntY() + 300), null);
					//System.out.println((j * 64 - player.getIntX() + 400) + " " + (((player.getIntY() - 300) / 64 - 1 + i) * 64 - player.getIntY() + 300));
				}
			}
		}  catch (IOException e) {
			System.out.println("File error");
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
}