import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.util.*;

public class Shotgun extends Weapon{
	private final int[] DAMAGE = new int[] {1, 2, 3, 5, 7};
	private int damage;
	private Random rand = new Random();

	public Shotgun (int tin) {

		tier = tin;
		type = "Shotgun";
		damage = DAMAGE[tier];
		image = null;
		try {
			 image = ImageIO.read(new File("weapons/weapon00.png"));
		} catch (IOException e) {System.out.println("Image not found");}

	}

	public Projectile[] use(double ang, double x, double y) {
		Projectile[] projectiles = new Projectile[tier + 3];
		for (int i = 0; i < tier + 3; i++) projectiles[i] = new Projectile(x, y, 8, (ang - Math.toRadians(40)) + Math.toRadians(rand.nextInt(80)), "bullet", damage);
		return projectiles;
	}
}