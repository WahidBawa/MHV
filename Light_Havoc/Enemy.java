public class Enemy extends Character{

	private double angle;

	public Enemy(double xin, double yin) {
		x = xin;
		y = yin;
	}

	public void update(Player player) {
		double dx = player.getX() - x;
		double dy = player.getY() - y;
		angle = Math.min(1, Math.atan2(dy, dx));
		move(2*Math.cos(angle), 2*Math.sin(angle));
	}
}