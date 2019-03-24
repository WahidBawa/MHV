public class Player extends Character{

	private Weapon weapon;
	private Secondary secondary;

	public Player(double xin, double yin) {
		x= xin;
		y = yin;
	}

	public Projectile[] useWeapon(double ang, double x, double y) {
		if (weapon instanceof Rifle) {
			return ((Rifle)weapon).use(ang, x, y);
		} else {
			return null;
		}
	}
}