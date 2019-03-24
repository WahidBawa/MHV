public class Player extends Character{

	private Weapon weapon;
	private Secondary secondary;

	public Player(double xin, double yin) {
		x= xin;
		y = yin;

		weapon = new Rifle(0);
	}

	public double getX() { return x; }
	public double getY() { return y; }
	public int getIntX() { return (int)x; }
	public int getIntY() { return (int)y; }

	public Projectile[] useWeapon(double ang) {
		if (weapon instanceof Rifle) {
			return ((Rifle)weapon).use(ang, x, y);
		} else {
			return null;
		}
	}
}