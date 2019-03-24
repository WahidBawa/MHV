import java.awt.image.*;

public class Weapon {

	public BufferedImage image;
	private int tier;
	private String type;
	private final String[] names = new String[] {"Basic", "Handy", "Ultra", "Special", "Mayhem"};
	
	public Weapon() {}

	public BufferedImage getImage() {return image;}
	public int getTier() {return tier;}
	public String getTierName() {return names[tier];}
	public String getType() {return type;}
}
