public class Character {
	double x, y;

	public Character() {}

	public void move(double xmove, double ymove) {
		x += xmove;
		y += ymove;
	}

	public double getX() { return this.x; }
	public double getY() { return this.y; }

}