package club.bede.brumhack.game;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public class Car {
	
	int x;
	private Image pic;
	
	public Car(int x) {
		this.x = x;
		Random rand = new Random();
		int colour = rand.nextInt(4)+1;
		String name = ""+Integer.toString(colour)+".png";
		ImageIcon ii = new ImageIcon(this.getClass().getResource(name));
	    pic = ii.getImage();		
	} 
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public Image getImage() {
		return pic;
	}
}
