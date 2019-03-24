import java.awt.*;

public class Character {
	protected double x, y;
	protected double health, healthMax;

	public Character(double h) {
		health = healthMax = h;
	}

	public void move(double xmove, double ymove) {
		x += xmove;
		y += ymove;
	}

	public double getX() { return this.x; }
	public double getY() { return this.y; }

	public double getHealth() { return health; }
	public double getHealthMax() { return healthMax; }
	public double percentHealth() { return health/healthMax; }

	public void harm(double amount) { this.health -= Math.max(0, amount); }
	public boolean isDead() { return this.health <= 0; }

	public void drawHealthBar(Graphics g, Player player) {
		g.setColor(Color.GREEN);
		g.fillRect((int)(x - 36 - player.getX() + 400), (int)(y - 40 - player.getY() + 300), (int)(percentHealth() * 72), 4);
		g.setColor(Color.BLACK);
		g.drawRect((int)(x - 36 - player.getX() + 400), (int)(y - 40 - player.getY() + 300), 72, 4);
	}

}