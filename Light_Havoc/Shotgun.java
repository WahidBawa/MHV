import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.util.*;

public class Shotgun extends Weapon{
	private final int[] DAMAGE = new int[] {1, 2, 3, 5, 7};
	private int damage;
	private Random rand = new Random();
	private int amountOfBullets;
	public Shotgun (int tin) {
		World.gunClass = "Shotgun";
		tier = tin;
		type = "Shotgun";
		damage = DAMAGE[tier];
		image = null;
		amountOfBullets = tier + 3;
		try {
			 image = ImageIO.read(new File("weapons/weapon00.png"));
		} catch (IOException e) {System.out.println("Image not found");}

	}

	public Projectile[] use(double ang, double x, double y) {
		Projectile[] projectiles = new Projectile[amountOfBullets];
		for (int i = 0; i < amountOfBullets; i++) projectiles[i] = new Projectile(x, y, 8, (ang - Math.toRadians(40)) + Math.toRadians(rand.nextInt(80)), "bullet", damage);
		return projectiles;
	}
}