public class World {

	private static final int worldWidth = 96;
	private static final int worldHeight = 80;

	private Player player;
	private ArrayList<Projectile> projectiles;
	// private ArrayList<Enemy> enemies;

	public World() {
		player = new Player(worldWidth * 64 / 2, worldHeight * 64 / 2);
		projectiles = new ArrayList<Projectile>();
		enemies = new ArrayList<Enemy>();
	}

	//doEnemies here

	public void doProjectiles() {
		for (int i = projectiles.size() - 1; i >= 0; i--) {
			Projectile proj = projectiles.get(i);

			proj.move();
		}
	}
}