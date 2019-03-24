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
}