package util;

import com.test.Game;

import java.awt.*;
import java.util.ArrayList;

public class QuadTree {
	private int capacity;
	private QuadTree[] nodes;
	private ArrayList<Rectangle> rectangles;
	private Rectangle bounds;

	public QuadTree() {
		this(0, 0, Game.WIDTH, Game.HEIGHT, 4);
	}

	public QuadTree(int cap) {
		this(0, 0, Game.WIDTH, Game.HEIGHT, cap);
	}

	public QuadTree(double x, double y, double width, double height, int cap) {
		rectangles = new ArrayList<Rectangle>();
		clear();
		this.capacity = cap;
		bounds = new Rectangle(x, y, width, height);
	}

	public void clear() {
		nodes = new QuadTree[4];
		rectangles.clear();
	}

	private void subdivide() {
		nodes[0] = new QuadTree(bounds.getX() + bounds.getWidth() / 2, bounds.getY(), bounds.getWidth() / 2, bounds.getHeight() / 2, capacity);
		nodes[1] = new QuadTree(bounds.getX(), bounds.getY(), bounds.getWidth() / 2, bounds.getHeight() / 2, capacity);
		nodes[2] = new QuadTree(bounds.getX(), bounds.getY() + bounds.getHeight() / 2, bounds.getWidth() / 2, bounds.getHeight() / 2, capacity);
		nodes[3] = new QuadTree(bounds.getX() + bounds.getWidth() / 2, bounds.getY() + bounds.getHeight() / 2, bounds.getWidth() / 2, bounds.getHeight() / 2, capacity);
		for (int i = rectangles.size() - 1; i >= 0; i--) {
			insert(rectangles.remove(i));
		}
	}

	public void insert(Rectangle r) {
		if (rectangles.size() >= capacity && nodes[0] == null) {
			subdivide();
		}

		if (nodes[0] == null) {
			rectangles.add(r);
		} else {
			boolean childFound = false;
			for (QuadTree n : nodes) {
				if (n.bounds.contains(r)) {
					n.insert(r);
					childFound = true;
					break;
				}
			}
			if (!childFound) {
				rectangles.add(r);
			}
		}
	}

	public void show(Graphics g) {
		Color prev = g.getColor();
		g.drawRect((int)bounds.getX(), (int)bounds.getY(), (int)bounds.getWidth(), (int)bounds.getHeight());
		g.setColor(prev);
		if (nodes[0] != null) {
			for (QuadTree n : nodes) {
				n.show(g);
			}
		}
	}

	private void query(ArrayList<Rectangle> rectangles, Rectangle range) {
		if (nodes[0] != null) {
			for (QuadTree n : nodes) {
				if (n.bounds.intersects(range)) {
					n.query(rectangles, range);
				}
			}
		}
		for (Rectangle r : this.rectangles) {
			if (range.intersects(r)) rectangles.add(r);
		}
	}

	public ArrayList<Rectangle> query(Rectangle range) {
		ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
		query(rectangles, range);
		return rectangles;
	}
}
