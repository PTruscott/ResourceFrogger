package club.bede.brumhack.game;
import java.awt.EventQueue;
import java.util.Scanner;

import javax.swing.JFrame;


public class Application extends JFrame{
	public Board board;
	
    public Application() {
        add(board = new Board());
        
        setResizable(false);
        pack();
        
        setTitle("Resource Frogger");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    

    public static void main(String[] args) {
    	System.out.println("Starting");
        final Application ex = new Application();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	ex.setVisible(true);   
            }
        });
        
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while((input = scanner.nextLine()) != "stop") {
        	ex.board.frog.move(input);
        }
    }
}
