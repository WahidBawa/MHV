import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import javax.imageio.*;

import java.awt.image.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class World {

	private static final int worldWidth = 96;
	private static final int worldHeight = 80;

	// 6 - 22 WALLTILES

	private Player player;
	private BufferedImage playerPic;
	private String gunClass;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Enemy> enemies;
	private ArrayList<util.Rectangle> walls;

	private ArrayList<String> imageFiles;
	private BufferedImage[] tiles;
	private int[][] map;
	private int kills;

	private ArrayList<int[]> sources;

	public World(String gClass) {

		player = new Player(3106, 1610);
		gunClass = gClass;
		projectiles = new ArrayList<Projectile>();
		enemies = new ArrayList<Enemy>();
		walls = new ArrayList<util.Rectangle>();

		try {
			 playerPic = ImageIO.read(new File("playerPic.png"));
		} catch (IOException e) {System.out.println("Image not found");}

		sources = new ArrayList<int[]>();
		sources.add(new int []{player.getIntX(), player.getIntY(), 100, 999999});
	}

	public void initTiles() {
		Filefetcher imageGetter = new Filefetcher();
		imageFiles = imageGetter.showFiles(System.getProperty("user.dir") + "/environment");
		tiles = new BufferedImage[imageFiles.size()];
		for (int i = 0; i < imageFiles.size(); i++) {
			try {
			    tiles[i] = ImageIO.read(new File("environment/" + imageFiles.get(i)));

			} catch (IOException e) {System.out.println("Image not found");}
		}

		BufferedReader get = null;
		map = new int[6 * 16][5 * 10];
		try {
			get = new BufferedReader(new FileReader("rooms/arena.txt"));

			for (int i = 0; i < 5 * 10; i++) {
				String line = get.readLine();
				for (int j = 0; j < 6 * 16; j++) {
					int n = line.charAt(j) - 32;
					map[j][i] = n;
					if (6 <= n && n <= 22) {
						walls.add(new util.Rectangle(j*64, i*64, 64, 64));
					}
				}
			}
			
		}  catch (IOException e) {
			System.out.println("File error");
		}
	}

	public void render(Graphics g, double ang) {

		for (int i = (player.getIntY() - 300) / 64; i < (player.getIntY() - 300) / 64 + 11; i++) {
			for (int j = (player.getIntX() - 400) / 64; j < (player.getIntX() - 400) / 64 + 16; j++) {
				if (i < 0 || j < 0 || j >= 16 * 6 || i >= 5 * 10) {
					g.setColor(Color.BLACK);
					g.fillRect(j * 64 - player.getIntX() + 400, i * 64 - player.getIntY() + 300, 64, 64);
				} else {
					g.drawImage(tiles[map[j][i]], j * 64 - player.getIntX() + 400, i * 64 - player.getIntY() + 300, null);
				}
			}
		}

		for (int i = projectiles.size() - 1; i >= 0; i--) {
			Projectile proj = projectiles.get(i);
			Color c = null;
			
			if (proj.getType().equals("bullet")) {
				g.setColor(Color.BLACK);
				g.fillOval(proj.getIntX() - player.getIntX() + 400 - proj.getRadius() / 2, proj.getIntY() - player.getIntY() + 300 - proj.getRadius() / 2, proj.getRadius(), proj.getRadius());
			}
		}

		for (int i = enemies.size() - 1; i >= 0; i--) {
			Enemy enemy = enemies.get(i);
			g.setColor(Color.BLUE);
			g.fillOval((int)(enemy.getX() - player.getX() + 400f - 32), (int)(enemy.getY() - player.getY() + 300f - 32), 64, 64);
		}

		g.drawImage(rotateBuffered(player.getWeapon().getImage(), ang + Math.PI / 2, 32, 32), 400 - 32 + (int)(Math.cos(ang + Math.PI / 4) * 32 * Math.sqrt(2)), 300 - 32 + (int)(Math.sin(ang + Math.PI / 4) * 32 * Math.sqrt(2)), null);
		g.drawImage(rotateBuffered(playerPic, ang + Math.PI / 2, 32, 32), 400 - 32, 300 - 32, null);

		g.drawImage(getLightMask(), 0, 0, null);
	}

	public void drawUI(Graphics g) {

    	g.setColor(new Color(60, 0, 60));
    	g.fillRect(800, 0, 200, 800);
    	g.fillRect(0, 600, 1000, 200);

    	g.setColor(Color.RED);
    	g.drawRect(500 - 50, 600, 100, 200);

    	g.setColor(Color.BLACK);
    	g.fillRect(818 - 5, 42, 200 -36, 3 * 64 + 24);
    	g.setColor(new Color(60, 0, 60));
    	g.fillRect(818 - 5 + 5, 42 + 5, 200 - 36 - 10, 3 * 64 + 24 - 10);
    	g.setColor(Color.BLACK);
    	g.fillRect(810 - 5, 54, 180, 3 * 64);
    	g.fillRect(830 - 5, 34, 140, 3 * 64 + 40);
    	g.drawImage(scaleBuffered(player.getWeapon().getImage(), 3), 800 + (200 - 3 * 64) / 2, 50 + (200 - 3 * 64) / 2, null);

    	g.setColor(Color.BLACK);
    	g.fillRect(810 - 5 + 5, 275, 180 - 10, 30);
    	g.fillRect(810 - 5, 275 + 5, 180, 30 - 10);
    	g.setColor(Color.WHITE);
    	g.setFont(new Font("TimesRoman", Font.BOLD, 20)); 
    	g.drawString("Gun Type: " + gunClass, 834 - 5, 297);

    	g.setColor(Color.BLACK);
    	g.fillRect(810 - 5 + 5, 275 + 50, 180 - 10, 30);
    	g.fillRect(810 - 5, 275 + 5 + 50, 180, 30 - 10);
    	g.setColor(Color.WHITE);
    	g.setFont(new Font("TimesRoman", Font.BOLD, 20)); 
    	g.drawString("Gun Tier: " + player.getWeapon().getTierName() + 1, 844 - 5, 297 + 50);
    }


	public void moveEnemies() {
		double spawnChance = Math.max(20, 100 - kills*80);
		if ((int)(Math.random()*spawnChance) == 0) {
			double x, y;
			while (true) {
				// x = player.getX() + (Math.random()*2000 - 1000);
				// y = player.getY() + (Math.random()*2000 - 1000);
				x = Math.random()*6*16*64 - 1;
				y = Math.random()*5*10*64 - 1;
				// if (0 <= x && x <= 6*16*64 && 0 <= y && y <= 5*10*64) {
					int t = map[(int)(x/64)][(int)(y/64)];
					if (!(6 <= t && t <= 22 && t == 0)) {
						System.out.println("SPAWN");
						break;
					}
				// }
			}
			enemies.add(new Enemy(x, y));
		}
		for (int i = enemies.size() - 1; i >= 0; i--) {
			Enemy enemy = enemies.get(i);
			enemy.update(player, walls);
		}
	}

	public void moveProjectiles() {
		for (int i = projectiles.size() - 1; i >= 0; i--) {
			Projectile proj = projectiles.get(i);
			proj.move();
			for (util.Rectangle w : walls) {
				if (w.intersects(proj.getX() - proj.getRadius(), proj.getY() - proj.getRadius(), proj.getRadius(), proj.getRadius())) {
					projectiles.remove(i);
					break;
				}
			}
			for (int en = enemies.size() - 1; en >= 0; en--) {
				Enemy e = enemies.get(en);
				if ((new util.Rectangle(e.getX()-32, e.getY()-32, 64,64)).intersects(proj.getX() - proj.getRadius(), proj.getY() - proj.getRadius(), proj.getRadius(), proj.getRadius())) {
					projectiles.remove(i);
					enemies.remove(en);
					break;
				}
			}
		}
	}

	public void useWeapon(double ang) {
		Projectile[] toAdd = player.useWeapon(ang);
		for (Projectile proj : toAdd) {
			projectiles.add(proj);
		}
	}

	public void movePlayer(double dx, double dy) {
		player.move(dx, 0);
		for (util.Rectangle w : walls) {
			if (w.intersects(player.getX() - 32, player.getY() - 32, 64, 64)){
				if (dx < 0) player.setX(w.getX() + w.getWidth() + 32);
				else if (dx > 0) player.setX(w.getX() - 32);
			}
		}

		player.move(0, dy);
		for (util.Rectangle w : walls) {
			if (w.intersects(player.getX() - 32, player.getY() - 32, 64, 64)){
				if (dy < 0) player.setY(w.getY() + w.getHeight() + 32);
				else if (dy > 0) player.setY(w.getY() - 32);
			}
		}
	}

	public BufferedImage getLightMask() {
		BufferedImage im = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = im.createGraphics();
		g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 800, 600);
        
		for (int i = sources.size() - 1; i >= 0; i--) {
			int[] source = sources.get(i);
			source[3] -= 1;
			if (source[3]> 0) {
				g2.setColor(new Color(254, 254, 254));
				for (int j = source[0] - (int)player.getX() + 400 - source[2]; j < source[0] - (int)player.getX() + 400 + source[2]; j++) {
					for (int k = source[1] - (int)player.getY() + 300 - source[2]; k < source[1] - (int)player.getY() + 300 + source[2]; k++) {
						if(Math.hypot(source[0] - player.getX() + 400 - j, source[1] - player.getY() + 300 - k) <= source[2]) {
							if (j >= 0 && k >= 0 && j < 800 && k < 600) {
								im.setRGB(j, k, 16711422);
							}
						}
					}
				}
			} else {
				sources.remove(i);
			}
		}
		g2.dispose();
		return im;
	}

	public BufferedImage makeTransparent(BufferedImage source) {

        Image image = makeColorTransparent(source, new Color(254, 254, 254));
        BufferedImage transparent = imageToBufferedImage(image);
        //ImageIO.write(transparent, "PNG", "test.png");
        return transparent;

    }

    private static BufferedImage imageToBufferedImage(Image image) {

        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();

        return bufferedImage;

    }

    public static Image makeColorTransparent(BufferedImage im, final Color color) {
        ImageFilter filter = new RGBImageFilter() {

            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = color.getRGB() | 0xFF000000;

            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

	public BufferedImage rotateBuffered(BufferedImage before, double a, int xrot, int yrot) {	
    	
    	AffineTransform tx = AffineTransform.getRotateInstance(a, xrot, yrot);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	    BufferedImage after = op.filter(before, null);

		return after;
    }

    public BufferedImage scaleBuffered(BufferedImage before, double s) {
		int w = before.getWidth();
		int h = before.getHeight();
		BufferedImage after = new BufferedImage((int)(w * s), (int)(h * s), BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale(s,s);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		after = scaleOp.filter(before, after);

		return after;
    }
}