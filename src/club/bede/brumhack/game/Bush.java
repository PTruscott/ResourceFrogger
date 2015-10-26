package club.bede.brumhack.game;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public class Bush {
	
	int x;
	int y;
	private Image pic;
	
	public Bush(int x,int y) {
		this.x = x;
		this.y = y;
		Random rand = new Random();
		int colour = rand.nextInt(4)+1;
		String bush = Integer.toString(colour)+"tree.png";
		ImageIcon ii = new ImageIcon(this.getClass().getResource(bush));
	    pic = ii.getImage();		
	} 
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		
		return y;
	}
	public Image getImage() {
		return pic;
	}
}
