import java.awt.image.*;

public class Weapon {

	protected BufferedImage image;
	protected int tier;
	protected String type;
	protected final String[] names = new String[] {"Basic", "Handy", "Ultra", "Special", "Mayhem"};
	
	public Weapon() {}

	public BufferedImage getImage() {return image;}
	public int getTier() {return tier;}
	public String getTierName() {return names[tier];}
	public String getType() {return type;}
}
