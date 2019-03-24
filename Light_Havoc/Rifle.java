public class Rifle extends Weapon {

	private int tier;
	private final int[] DAMAGE = new int[] {0, 0, 0, 0, 0};
	private final int[] RADIUS = new int[] {0, 0, 0, 0, 0};

	public Rifle (int tin) {

		tier = tin;
	}

	public Projectile[] use(double ang, double x, double y) {
		return new Projectile[] {new Projectile (x, y, 1, ang, "bullet", 500, 4)};
	}


}