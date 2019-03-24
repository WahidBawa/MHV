import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import javax.imageio.*;

import java.awt.image.*;
import util.*;

public class LightHavoc extends JFrame implements ActionListener {
	JPanel cards;
	CardLayout cLayout = new CardLayout();

	javax.swing.Timer myTimer;
	GamePanel game; 


	private int myTick; 

    public LightHavoc() {

		super("Light Havoc");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 800);

		game = new GamePanel(); 

		myTimer = new javax.swing.Timer(10, this);	 // trigger every 10 ms
		myTimer.start();

		cards = new JPanel(cLayout);
		cards.add(game, "game");
				
		add(cards);
		setResizable(false);
		setVisible(true);
    }

	public void actionPerformed(ActionEvent evt){
		Object source = evt.getSource();

		if(game != null){
			game.requestFocus();
			game.refresh(myTick, this.getLocation());
			game.repaint();
			if(source == myTimer){
				++myTick;
			}
		}	
	}

    public static void main(String[] args) {
		LightHavoc frame = new LightHavoc();
    }

}

class GamePanel extends JPanel implements MouseListener, KeyListener{

	private boolean[] keys, mb;
	private int importTick;
	private Point screenPos;

	private ArrayList<String> imageFiles;
	private BufferedImage[] tiles;

	private double playerAng;
	private String gunClass;

	private World world;

	private final double[][] roomStarts = new double[][] {{0, 0.5}, {0, 3.5}, {1, 0}, {1, 1}, {1, 3}, 
														{1, 4}, {1.5, 2}, {2, 0.5}, {2, 3.5}, {2.5, 1.5}, 
														{2.5, 2.5}, {3, 0.5}, {3, 3.5}, {3.5, 2}, {4, 0}, 
														{4, 1}, {4, 3}, {4, 4}, {5, 0.5}, {5, 3.5}};

	public GamePanel(){
		addKeyListener(this);
		addMouseListener(this);
		keys = new boolean[KeyEvent.KEY_LAST+1];
		mb = new boolean[3];
		screenPos = new Point(0, 0);

		world = new World();

		Filefetcher imageGetter = new Filefetcher();
		imageFiles = imageGetter.showFiles(System.getProperty("user.dir") + "/environment");
		tiles = new BufferedImage[imageFiles.size()];
		for (int i = 0; i < imageFiles.size(); i++) {
			try {
			    tiles[i] = ImageIO.read(new File("environment/" + imageFiles.get(i)));

			} catch (IOException e) {System.out.println("Image not found");}
		}

		playerAng = - Math.PI / 2;
		gunClass = "rifle";
	}

    public void refresh(int myTick, Point pos){ 
    	screenPos = pos;
    	importTick = myTick;
    	if (keys[KeyEvent.VK_ESCAPE]) {
    		System.exit(0);
    	}
	}

	@Override
    public void paintComponent(Graphics g){

    	Point m = MouseInfo.getPointerInfo().getLocation();
    	m.move(m.x - screenPos.x, m.y - screenPos.y);

    	g.setColor(Color.WHITE);
    	g.fillRect(0, 0, 800, 600);

    	if (m.x > 500 + 50) {playerAng += Math.PI / 150;}
    	else if (m.x < 500 - 50) {playerAng -= Math.PI / 100;}
    	g.setColor(Color.BLACK);
    	g.drawLine(400, 300, 400 + (int)(Math.cos(playerAng) * 50), 300 + (int)(Math.sin(playerAng) * 50));

    	g.setColor(new Color(60, 0, 60));
    	g.fillRect(800, 0, 200, 800);
    	g.fillRect(0, 600, 1000, 200);

    	g.setColor(Color.RED);
    	g.drawOval(400 - 20, 300 - 20, 40, 40);
    	g.drawRect(500 - 50, 600, 100, 200);
    } 

	public void keyTyped(KeyEvent e) {

	} 
    public void keyPressed(KeyEvent e) {
    	setKey(e.getKeyCode(),true);

    }
    public void keyReleased(KeyEvent e) {
    	setKey(e.getKeyCode(),false);
    }
    public void setKey(int k, boolean v) {
    	keys[k] = v;
    }  

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

    public void mousePressed(MouseEvent e){

	}
}

