import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;

import java.awt.image.*;
import util.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import javax.swing.*;

public class LightHavoc extends JFrame implements ActionListener {
	JPanel cards;
	CardLayout cLayout = new CardLayout();

	private ArrayList<JButton>buttonList;

	javax.swing.Timer myTimer;
	GamePanel game;
	JPanel titlePage, instr;

	private int myTick; 

    public LightHavoc() {

		super("Light Havoc");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 800);

		game = new GamePanel(); 

		myTimer = new javax.swing.Timer(10, this);	 // trigger every 10 ms
		myTimer.start();

		buttonList = new ArrayList<JButton>();

		titlePage = new JPanel();
		titlePage.setLayout(null);
		
		addButton("Play", titlePage, buttonList, 300, 360, 400, 100, Color.BLACK, 40, "Cooper Black", Color.WHITE, this); //adding buttons and text to all the non game cards
		addButton("Instructions", titlePage, buttonList, 300, 470, 400, 100, Color.BLACK, 40, "Cooper Black", Color.WHITE, this);
		addButton("Quit", titlePage, buttonList, 300, 580, 400, 100, Color.BLACK, 40, "Cooper Black", Color.WHITE, this);
		addImage("menu_images/title.png", titlePage, 0, -250, 1000, 800);
		addImage("menu_images/titlePic.png", titlePage, 0, 0, 1000, 800);


		instr = new JPanel();
		instr.setLayout(null);

		addImage("menu_images/intr.png", instr, 0, -250, 1000, 800);
		addButton("Back", instr, buttonList, 50, 650, 150, 100, Color.BLACK, 40, "Cooper Black", Color.WHITE, this);
		String[] lines = new String[] {"Hold your hands out flat above the sensor. if hands are not detected, the game will pause", 
										"Rotate your torso and hands to rotate the player",
										"Bank your left hand side to side and front to back to move the player",
										"Hold your right hand in the shape of a gun and bend your pointer finger to shoot",
										"Survive as long as you can!"};
		for (int i = 0; i < lines.length; i++) {
			addLabel(lines[i], instr, 75, 200 + i * (400 / lines.length), 900, 400 / lines.length, 18, "Cooper Black", Color.WHITE);
		}

		addImage("menu_images/purpRect.png", instr, 50, 200, 900, 400);
		addImage("menu_images/titlePic.png", instr, 0, 0, 1000, 800);

		cards = new JPanel(cLayout);
		cards.add(titlePage, "title");
		cards.add(game, "game");
		cards.add(instr, "instructions");
				
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
            if (World.player.health <= 1) {
                cLayout.show(cards, "title");
            }
		}

		if(source == buttonList.get(0)){ 
		    cLayout.show(cards,"game");
		    game.reset();
		    game.reset();
			myTimer.start();
		    game.requestFocus();
		}

		if(source == buttonList.get(1)){ 
		    cLayout.show(cards,"instructions");
		}

		if(source == buttonList.get(2)){ 
		    System.exit(0);
		}

		try {
			if(source == buttonList.get(3)){ 
			    cLayout.show(cards,"title");
			}
		} catch(Exception e) {
			System.out.print("");
		}
	}

    public static void main(String[] args) {
		LightHavoc frame = new LightHavoc();
    }

    private static void addLabel(String text, Container container, int x, int y, int w, int h, int fontSize, String typeFace, Color textCol) { //adds a label
        JLabel label = new JLabel(text);
        label.setFont(new Font(typeFace, Font.PLAIN, fontSize));
        label.setForeground(textCol);
        label.setSize(w,h);
		label.setLocation(x, y);

        container.add(label);
    }

    public static void addImage(String fname, Container container, int x, int y, int w, int h) { 
    	ImageIcon icon = new ImageIcon(fname);
		JLabel label = new JLabel(icon);
		label.setSize(w,h);
		label.setLocation(x,y);
		container.add(label);
    }

    private static void addButton(String text, Container container, ArrayList<JButton> blist, int x, int y, int w, int h, Color butCol,
    								 int fontSize, String typeFace, Color textCol, LightHavoc lh) { 
        JButton button = new JButton(text);
        button.setFont(new Font(typeFace, Font.PLAIN, fontSize));
        button.setForeground(textCol);
        button.setBackground(butCol);
        button.addActionListener(lh);

		button.setSize(w, h);
		button.setLocation(x, y);

        container.add(button);
        blist.add(button);
    }
}

class GamePanel extends JPanel implements MouseListener, KeyListener{

	private boolean[] keys, mb;
	private int importTick;
	private Point screenPos;

	private double playerAng, angle;
    private boolean pewing, ppewing;
	private String gunClass;

	private World world;

    public static double[] playerRotateVals;

	public GamePanel(){
		addKeyListener(this);
		addMouseListener(this);
		keys = new boolean[KeyEvent.KEY_LAST+1];
		mb = new boolean[3];
		screenPos = new Point(0, 0);

		playerAng = - Math.PI / 2;
		gunClass = "rifle";

		world = new World(gunClass);
		world.initTiles();
		
	}

	public void reset(){
		addKeyListener(this);
		addMouseListener(this);
		keys = new boolean[KeyEvent.KEY_LAST+1];
		mb = new boolean[3];
		screenPos = new Point(0, 0);

		playerAng = - Math.PI / 2;
		gunClass = "rifle";

		world = new World(gunClass);
		world.initTiles();

		
	}

    public void refresh(int myTick, Point pos){ 
        angle = 0;
        try {
            double[] a = new ReadFile("tmp.tmp").getArray();
            playerRotateVals = new double[] {a[0], a[1]};
            angle = -a[2] / 30;
            ppewing = pewing;
            pewing = a[3] == 0.0 ? false : true;
            if (ppewing != pewing && pewing) {
                world.useWeapon(playerAng);
            }
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
        world.moveEnemies();

    	g.setColor(Color.BLACK);
    	g.fillRect(0, 0, 800, 600);
        
    	if (m.x > 500 + 50) {playerAng += Math.PI / 110;}
    	else if (m.x < 500 - 50) {playerAng -= Math.PI / 110;}

        playerAng += angle;
    	world.render(g, playerAng);
    	world.drawUI(g);
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
    		world.addSource();
    	}
	}
}

