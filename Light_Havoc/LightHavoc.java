import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import javax.imageio.*;

import java.awt.image.*;
import util.*;

public class RoomBuilder extends JFrame implements ActionListener {
	JPanel cards;
	CardLayout cLayout = new CardLayout();

	javax.swing.Timer myTimer;
	GamePanel game; 


	private int myTick; 

    public RoomBuilder() {

		super("Room builder");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1064, 700);


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
			game.refresh(myTick);
			game.repaint();
			if(source == myTimer){
				++myTick;
			}
		}	
	}

    public static void main(String[] args) {
		RoomBuilder frame = new RoomBuilder();
    }

}

class GamePanel extends JPanel implements MouseListener, KeyListener{

	private boolean[] keys, mb;
	private int importTick, holding, roomNum;
	private int[][] room;
	private boolean canInc, canDec, canFilePlus, canFileMinus;

	private ArrayList<String> imageFiles;
	private BufferedImage[] tiles;

	private final int rW = 16;
	private final int rH = 10;

	private final double[][] roomStarts = new double[][] {{0, 0.5}, {0, 3.5}, {1, 0}, {1, 1}, {1, 3}, 
														{1, 4}, {1.5, 2}, {2, 0.5}, {2, 3.5}, {2.5, 1.5}, 
														{2.5, 2.5}, {3, 0.5}, {3, 3.5}, {3.5, 2}, {4, 0}, 
														{4, 1}, {4, 3}, {4, 4}, {5, 0.5}, {5, 3.5}};

	public GamePanel(){
		addKeyListener(this);
		addMouseListener(this);
		keys = new boolean[KeyEvent.KEY_LAST+1];
		mb = new boolean[3];

		room = new int[rW][rH];
		canInc = true;
		canDec = true;
		canFilePlus = true;
		canFileMinus = true;
		roomNum = 0;
		holding = 1;

		Filefetcher imageGetter = new Filefetcher();
		imageFiles = imageGetter.showFiles(System.getProperty("user.dir") + "/environment");
		tiles = new BufferedImage[imageFiles.size()];

		for (int i = 0; i < imageFiles.size(); i++) {
			try {
			    tiles[i] = ImageIO.read(new File("environment/" + imageFiles.get(i)));

			} catch (IOException e) {System.out.println("Image not found");}
		}
	}

    public void refresh(int myTick){ 
    	importTick = myTick;
    	if (keys[KeyEvent.VK_ESCAPE]) {
    		System.exit(0);
    	}

		if (keys[KeyEvent.VK_RIGHT]){
			if (canInc) {
				holding = Math.min(tiles.length - 1, holding + 1);
				canInc = false;
			}
		} else {
			canInc = true;
		}
		if (keys[KeyEvent.VK_LEFT]){
			if (canDec) {
				holding = Math.max(1, holding - 1);
				canDec = false;
			}
		} else {
			canDec = true;
		}

		if (keys[KeyEvent.VK_UP]){
			if (canFilePlus) {
				roomNum = Math.min(19, roomNum + 1);
				canFilePlus = false;
			}
		} else {
			canFilePlus = true;
		}
		if (keys[KeyEvent.VK_DOWN]){
			if (canFileMinus) {
				roomNum = Math.max(0, roomNum - 1);
				canFileMinus = false;
			}
		} else {
			canFileMinus = true;
		}	
		if (keys[KeyEvent.VK_S]){
			writeMap();
		}

		if (keys[KeyEvent.VK_O]){
			openRoom();
		}

		if (keys[KeyEvent.VK_Q] && keys[KeyEvent.VK_P]) {
			wipeArena();
		}
		//System.out.println(holding);	
	}

	@Override
    public void paintComponent(Graphics g){
    	
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

