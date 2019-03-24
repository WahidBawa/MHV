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

	public World() {
		player = new Player(worldWidth * 64 / 2, worldHeight * 64 / 2);
		projectiles = new ArrayList<Projectile>();
		enemies = new ArrayList<Enemy>();
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
}