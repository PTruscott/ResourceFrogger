package club.bede.brumhack.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

	public static final int B_WIDTH = 1400;
	public static final int B_HEIGHT = 800;
	public static final int BLOCK_SIZE = 20;
	public static int MAX_ROADS;
	private boolean inGame = false;
	private boolean died = false;
	public Frog frog;
	public Road[] roads;
	private static int score;
	private Random rand;
	int roadCounter;
	private int DELAY;
	private Image frogImg;
	private ArrayList<String> dates;
	private int dateNum;
	private Timer timer;
	public static DataOut data;
	private String currentDate;
	private int dateCounter;
	private int fileNum;
	private Bush[] bushes;
	int bushCounter;
    
    public Board() {
        MAX_ROADS = (B_WIDTH/BLOCK_SIZE);
    	rand = new Random();
    	fileNum = 0;
        addKeyListener(new TAdapter());
        setBackground(Color.green);
        setFocusable(true);
        setDoubleBuffered(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        data = new DataOut();
        
        dates = new ArrayList<String>();

        
		BufferedReader br = null;
		try {
			String file = "src/res/date.txt";
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.out.println("damn");
		}
		try {
		    String line = br.readLine();
		    dates.add(line);

		    while (line != null) {
		        line = br.readLine();
		        dates.add(line);
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
		//System.out.println(dates);
        currentDate = dates.get(0);
        
    }

    private void initGame() {
    	
    	bushCounter = 0;
    	bushes = new Bush[8];
    	timer = new Timer(DELAY, this);
        timer.start(); 
        dateNum = 0;
    	roadCounter = 0;
    	dateCounter = 0;
    	roads = new Road[MAX_ROADS];
    	boolean roadPlaced = false;
    	
    	for (int i = B_HEIGHT-2*BLOCK_SIZE; i >= BLOCK_SIZE; i -= BLOCK_SIZE) {
    		int dir = rand.nextInt(2);
    		if (!roadPlaced) {
    			if (fileNum < 72) {
	    			if (roadCounter == 0) {
	    				roads[roadCounter] = new Road(dir, i,fileNum);
	    				roads[roadCounter].setDate(dateCounter);
	    				fileNum++;
	    			}
	    			else {
	    				fileNum = roads[roadCounter-1].getFileNum();
	    				//System.out.println(fileNum);
	    				fileNum++;
	    				roads[roadCounter] = new Road(dir, i,fileNum);
	    				roads[roadCounter].setDate(dateCounter);
	    			}
	    			
	    			roadCounter++;
	    			roadPlaced = true;
	    		}
    		}
    		else { 
    			roadPlaced = false;
    			int temp = rand.nextInt(5);
    			if ((temp < 2)&&bushCounter < 8) {
    				int tempX = (B_WIDTH/BLOCK_SIZE)-2;
    				int x = rand.nextInt(tempX)*BLOCK_SIZE;
    				bushes[bushCounter] = new Bush(x,i);
    				bushCounter++;
    				//System.out.println("Adding bush");
    			}
    		}
    		
    	}
    	inGame = true;
    	frog = new Frog();
    }

   
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
        	paintGame(g);
        }
        else {
        	paintMenu(g);
        }
    }
    
    public void updateDate() {
    	currentDate = dates.get(dateNum+1);
    	for (int i = 0; i < roadCounter; i++) {
    		String nextDate = roads[i].getDate(roads[i].getDateNum()+1); 
    		if (nextDate.equals(currentDate)) {
    			roads[i].setDate(roads[i].getDateNum()+1);
    		}	
    	}
    	dateNum++;
    }
    
    private void paintGame(Graphics g) {
  
    	for (int i = 0; i < bushCounter; i++) {
    		g.drawImage(bushes[i].getImage(), bushes[i].getX(), bushes[i].getY(), BLOCK_SIZE, BLOCK_SIZE, this);
    	}
    	Font small = new Font("Helvetica", Font.BOLD, 14);
    	Font large = new Font("Helvetica", Font.BOLD, 20);
    	FontMetrics metr;
    	if (score == 2) {
    		g.setFont(large);
    		metr = getFontMetrics(large);
    		String win = "Congratulations!";
    		g.drawString(win, (B_WIDTH-metr.stringWidth(win))/2, 0+BLOCK_SIZE);
    	}
    	g.setFont(small);
    	metr = getFontMetrics(small);
    	String date = currentDate;
    	g.setColor(Color.black);
    	g.drawString(date, B_WIDTH-metr.stringWidth(date), 0+BLOCK_SIZE);
    	for (int i = 0; i < roadCounter; i++) {
        	g.setColor(Color.WHITE);
    		g.fillRect(0, roads[i].getY(), B_WIDTH, BLOCK_SIZE);
    		g.setColor(Color.black);
    		g.drawRect(0, roads[i].getY(), B_WIDTH-1, BLOCK_SIZE);
    		g.drawString(roads[i].toString(), 0, roads[i].getY());
    		for (int j = 0; j < roads[i].getMaxCars(); j++) {
    			//System.out.println("Max cars: "+roads[i].getMaxCars());
    			g.drawImage(roads[i].getCar(j).getImage(), roads[i].getCar(j).getX(), roads[i].getY(), BLOCK_SIZE, BLOCK_SIZE, this);
    		}
    		Toolkit.getDefaultToolkit().sync();
    	}
    	
    	
    	String frogString= "frog.png";
    	ImageIcon ii = new ImageIcon(this.getClass().getResource(frogString));
    	frogImg = ii.getImage();
    	g.drawImage(frogImg, frog.getX(), frog.getY(), BLOCK_SIZE, BLOCK_SIZE, this);
    }
    
    private void paintMenu(Graphics g) {
		String msg = "Press space to start a new game";
		String help = "Use the arrow keys to move";
	    
	    Font small = new Font("Helvetica", Font.BOLD, 14);
	    Font med = new Font("Helvetica", Font.BOLD, 18);
	    g.setColor(Color.WHITE);
	    FontMetrics metr;
	    
	    if (died)  {
	        metr = getFontMetrics(small);
	        g.setFont(small);
	        String scoreString = "Your score was: " + score;
	        g.drawString(scoreString, (B_WIDTH - metr.stringWidth(scoreString)) / 2, B_HEIGHT / 2-50);
	    }
	    
	    metr = getFontMetrics(med);
	    g.setFont(med);
	    g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
	    metr = getFontMetrics(small);
	    g.setFont(small);
        g.drawString(help, (B_WIDTH - metr.stringWidth(help)) / 2, B_HEIGHT / 2+50);

    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	
    		dateCounter++;
    		if (dateCounter > 10) {
    			dateCounter = 0;
    			updateDate();
    		}
    	
    		for (int i = 0; i < roadCounter; i++) {
    			roads[i].update();
    		}
    		checkCollisions();
    		if (frog.getNextLevel()) {
    			frog.setNextLevel(false);
    			nextLevel();
    		}
    		
    		repaint();
    		
    }
    	
    public void checkCollisions() {
    	int fx = frog.getX();
    	int fy = frog.getY();
    	for (int i = 0; i < roadCounter; i++) {
    		if (fy == roads[i].getY()) {
	    		for (int j = 0; j < roads[i].getMaxCars(); j++) {	
	    			if (roads[i].getCar(j).getX() == fx) {
	    				died = true;
	    				inGame = false;
	    				timer.stop();
	    			}
	    			
	    		}
    		}
    		Toolkit.getDefaultToolkit().sync();
    	}
    }

    public void nextLevel() {
    	//DELAY -= 20;
    	score++;
    	initGame();
    	
    }
	public boolean isInGame() {
		return inGame;
	}
	
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if (inGame) {
            	if (key == KeyEvent.VK_UP) {
            		frog.move("up");
            	}
            	if (key == KeyEvent.VK_DOWN) {
            		frog.move("down");
            	}
            	if (key == KeyEvent.VK_LEFT) {
            		frog.move("left");
            	}
            	if (key == KeyEvent.VK_RIGHT) {
            		frog.move("right");
            	} 
            	if (key == KeyEvent.VK_ESCAPE) {
            		inGame = false;
            	}
            	
            }
            else {
            	if (key == KeyEvent.VK_SPACE) {
            		DELAY = 600;
            		dateNum = 0;
            		fileNum = 0;
            		score = 0;
            		initGame();
            		inGame = true;
            	}
            }
            checkCollisions();
            repaint();
        }
    }
}