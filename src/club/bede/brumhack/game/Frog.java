package club.bede.brumhack.game;

public class Frog {
	private int x;
	private int y;
	private boolean nextLevel = false;
	
	public Frog() {
		y = Board.B_HEIGHT-Board.BLOCK_SIZE;
		x = Board.B_WIDTH/2;
	}
	
	public void move(String dir) {
		switch (dir) {
			case "left":
				if ((x-Board.BLOCK_SIZE) < 0) break;
				x -= Board.BLOCK_SIZE;
				break;
			case "right":
				if ((x+Board.BLOCK_SIZE*2) > Board.B_WIDTH) break;
				x += Board.BLOCK_SIZE;
				break;
			case "up":
				if ((y-Board.BLOCK_SIZE) < 0) {
					nextLevel =true;
					break;	
				}
				y -= Board.BLOCK_SIZE;
				break;
			case "down":
				if ((y+Board.BLOCK_SIZE*2) > Board.B_HEIGHT) break;
				y += Board.BLOCK_SIZE;
		}
		
	}
	
	public boolean getNextLevel() {
		return nextLevel;
	}
	
	public void setNextLevel(boolean level) {
		this.nextLevel = level;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}
