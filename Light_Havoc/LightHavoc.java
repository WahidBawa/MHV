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

	private double playerAng;
	private String gunClass;

	private World world;

    public static double[] playerRotateVals;

	public GamePanel(){
		addKeyListener(this);
		addMouseListener(this);
		keys = new boolean[KeyEvent.KEY_LAST+1];
		mb = new boolean[3];
		screenPos = new Point(0, 0);

		world = new World();
		world.initTiles();

		playerAng = - Math.PI / 2;
		gunClass = "rifle";
	}

    public void refresh(int myTick, Point pos){ 
        try {
            playerRotateVals = new ReadFile("tmp.tmp").getArray();
            // System.out.println(Arrays.toString(playerRotateVals));
        } catch (Exception e) {}

    	screenPos = pos;
    	importTick = myTick;
    	if (keys[KeyEvent.VK_ESCAPE]) {
    		System.exit(0);
    	}

        world.movePlayer(Math.min(6, Math.max(playerRotateVals[0] * 2, -6)), Math.min(6, Math.max(playerRotateVals[1] * 4, -6)));

    	if (keys[KeyEvent.VK_W]) {
    		world.movePlayer(0, -6);
    	}

    	if (keys[KeyEvent.VK_S]) {
    		world.movePlayer(0, 6);
    	}

    	if (keys[KeyEvent.VK_D]) {
    		world.movePlayer(6, 0);
    	}

    	if (keys[KeyEvent.VK_A]) {
    		world.movePlayer(-6, 0);
    	}

	}

	@Override
    public void paintComponent(Graphics g){

    	Point m = MouseInfo.getPointerInfo().getLocation();
    	m.move(m.x - screenPos.x, m.y - screenPos.y);

    	world.moveProjectiles();

    	g.setColor(Color.BLACK);
    	g.fillRect(0, 0, 800, 600);

    	if (m.x > 500 + 50) {playerAng += Math.PI / 110;}
    	else if (m.x < 500 - 50) {playerAng -= Math.PI / 110;}

    	world.render(g, playerAng);
    	drawUI(g);    	
    } 

    public void drawUI(Graphics g) {

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
    	if (e.getButton() == 1) {
    		world.useWeapon(playerAng);
    	}
	}
}

