public class Player extends Character{

	private Weapon weapon;
	private Secondary secondary;

	public Player(double xin, double yin) {
		x= xin;
		y = yin;
	}

	public Projectile[] useWeapon() {
		if (weapon instanceof Rifle) {
			return ((Rifle)weapon).fire();
		}
	}
}