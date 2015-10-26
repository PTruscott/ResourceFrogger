package club.bede.brumhack.game;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Road {
	int dir;
	private int MAX_CARS = 10;
	int y;
	private Car[] cars;
	private Random rand;
	private String name;
	private int fileNum;
	private int dateNum;
	ArrayList<String> dates;
	ArrayList<Integer> carsNum;
	
	/**
	 * A road, along this paths the cars all travel in the same direction
	 * @param int dir, the direction the cars go in 0 = left, 1 = right
	 * @param int y, the height of the road
	 */
	public Road(int dir, int y, int fileN) {
		
		dateNum = 0;
		this.fileNum = fileN;
		String file ="src/res/";
		boolean usedFile = false;
		BufferedReader br = null;
		while (true) {
			try {
				file = "src/res/JTMS"+Integer.toString(fileNum)+".xml";
				br = new BufferedReader(new FileReader(file));
				usedFile = true;
				break;
			} catch (FileNotFoundException e) {
				if (fileNum < 75) {  
					fileNum++;
				}
				else break;
			}
		}
		try {
			if (usedFile) {
			    String line = br.readLine();
	
			    while ((line != null) && (Board.data.getName(line).equals("NO MATCH"))) {
			        line = br.readLine();
			    }
			    name = Board.data.getName(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		    try {
		    	if (usedFile) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		dates = new ArrayList<String>();
		dates = getDates(file, br);
		carsNum = new ArrayList<Integer>();
		carsNum = getCars(file, br);
		this.y = y;
		this.dir = dir;
		initRoad();
		
	}
	
	private void initRoad() {
		rand = new Random();
		MAX_CARS = carsNum.get(dateNum);
		if (MAX_CARS > 70) MAX_CARS = 70;
		cars = new Car[MAX_CARS];
		double spaces = Board.B_WIDTH/Board.BLOCK_SIZE;
		double gapsD = Math.round(spaces/MAX_CARS);
		int gaps = (int)gapsD;
		for (int i = 0; i < MAX_CARS; i++) {
			if (MAX_CARS != 70) {
				cars[i] = new Car((gaps-1)*i*Board.BLOCK_SIZE);	
			}
			else cars[i] = new Car((gaps-1)*i*Board.BLOCK_SIZE);	
		}
	}
	
	private ArrayList<String> getDates(String file, BufferedReader br) {
		ArrayList<String> dates = new ArrayList<String>();
		
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.out.println("Shit how");
		}
		
		try {
		    String line = br.readLine();

		    while (line != null) {
		    	if (!Board.data.getDate(line).equals("NO MATCH")) {
		    		dates.add(Board.data.getDate(line));
		    	}
		        line = br.readLine(); 
		    }
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		    try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return dates;
	}
	private ArrayList<Integer> getCars(String file, BufferedReader br) {
		ArrayList<Integer> carNum = new ArrayList<Integer>();
		
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.out.println("Shit how");
		}
		
		try {
		    String line = br.readLine();

		    while (line != null) {
		    	if (Board.data.getCars(line) != 0) {
		    		carNum.add(Board.data.getCars(line));
		    	}
		        line = br.readLine(); 
		    }
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		    try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return carNum;
	}
	
	public int getFileNum() {
		return fileNum;
	}
	
	public void update() {
		if (dir == 0) {
			for (int i = 0; i < MAX_CARS; i++) {
					int newPos = cars[i].getX()-Board.BLOCK_SIZE;
					if (newPos < 0) newPos = Board.B_WIDTH-Board.BLOCK_SIZE;
					cars[i].setX(newPos);
				
			}
		}
		if (dir == 1) {
			for (int i = 0; i < MAX_CARS; i++) {
					int newPos = cars[i].getX()+Board.BLOCK_SIZE;
					if (newPos > Board.B_WIDTH-10) newPos = 0;
					cars[i].setX(newPos);
			}
		}
	}
	
	public int getMaxCars() {
		return MAX_CARS;
	}
	public Car getCar(int i) {
		return cars[i];
	}
	public int getY() {
		return y;
	}
	
	public String getDate(int index) {
		return dates.get(index);
	}
	
	public void setDate(int index) {
		dateNum = index;
		initRoad();
	}
	
	public int getDateNum() {
		return dateNum;
	}	
	
	public int nextDateNum() {
		return dateNum+1;
	}
	
	public String toString() {
		return name;
	}
	
}
