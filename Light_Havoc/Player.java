import java.awt.*;

public class Player extends Character{

	private Weapon weapon;
	private Secondary secondary;

	public Player(double xin, double yin) {
		super(100);
		x= xin;
		y = yin;

		weapon = new Rifle(0);
	}

	public void heal() { health = Math.min(healthMax, health+0.01); }

	public double getX() { return x; }
	public double getY() { return y; }
	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	public int getIntX() { return (int)x; }
	public int getIntY() { return (int)y; }
	public Weapon getWeapon() {return weapon;}

	public Projectile[] useWeapon(double ang) {
		if (weapon instanceof Rifle) {
			return ((Rifle)weapon).use(ang, x + (int)(Math.cos(ang + Math.PI / 4) * 32 * Math.sqrt(2)), y + (int)(Math.sin(ang + Math.PI / 4) * 32 * Math.sqrt(2)));
		} else {
			return null;
		}
	}
}