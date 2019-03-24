import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

public class Rifle extends Weapon {

	private final int[] DAMAGE = new int[] {0, 0, 0, 0, 0};
	private final int[] RADIUS = new int[] {0, 0, 0, 0, 0};

	public Rifle (int tin) {

		tier = tin;
		type = "Rifle";
		image = null;
		try {
			 image = ImageIO.read(new File("weapons/weapon0" + (tier + 5) + ".png"));
		} catch (IOException e) {System.out.println("Image not found");}

	}

	public Projectile[] use(double ang, double x, double y) {
		return new Projectile[] {new Projectile (x, y, 8, ang, "bullet", 500)};
	}


}