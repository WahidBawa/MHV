public class Projectile {

	private int range;
	private double vel, traj, x, y, ox, oy, sx, sy;
	private String type;

	public Projectile(double xin, double yin, double vin, double trin, String tyin, int damage) {
		x = xin;
		y = yin;
		sx = xin;
		sy = yin;
		ox = 99999;
		oy = 99999;

		vel = vin;
		traj = trin;

		type = tyin;
	}

	public double getX() { return x; }
	public double getY() { return y; }
	public double getSX() {return sx;}
	public double getSY() {return sy;}
	public int getIntX() { return (int)x; }
	public int getIntY() { return (int)y; }
	public String getType() {return type; }

	public void move() {
		ox = x;
		oy = y;
		x += Math.cos(traj) * vel;
		y += Math.sin(traj) * vel;
	}
}