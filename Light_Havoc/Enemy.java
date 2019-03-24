import java.util.*;

public class Enemy extends Character{

	private double angle;

	public Enemy(double xin, double yin) {
		x = xin;
		y = yin;
	}

	public void update(Player player, ArrayList<util.Rectangle> walls) {
		double dx = player.getX() - x;
		double dy = player.getY() - y;
		angle = Math.min(4, Math.atan2(dy, dx));
		move(2.0*Math.cos(angle), 0);

		for (util.Rectangle w : walls) {
			if (w.intersects(x - 32, y - 32, 64, 64)){
				if (dx < 0) x = w.getX() + w.getWidth() + 32;
				else if (dx > 0) x = w.getX() - 32;
			}
		}

		move(0, 2.0*Math.sin(angle));
		for (util.Rectangle w : walls) {
			if (w.intersects(x - 32, y - 32, 64, 64)){
				if (dy < 0) y = w.getY() + w.getHeight() + 32;
				else if (dy > 0) y = w.getY() - 32;
			}
		}
	}
}