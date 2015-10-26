package club.bede.brumhack.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public static void main(String[] args) throws FileNotFoundException {
		int fileNum = 1; 
		String file ="";
		ArrayList<String> main = new ArrayList<String>();
		BufferedReader br = null;
		while (fileNum < 100) {
			while (true) {
				try {
					file = "JTMS"+Integer.toString(fileNum)+".xml";
					br = new BufferedReader(new FileReader(file));
					break;
				} catch (FileNotFoundException e) {
					if (fileNum < 100) fileNum++;
					else break;
				}
			}
			
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			ArrayList<String> temp = new ArrayList<String>();
			temp = getDates(file, br);
			main.addAll(temp);
			fileNum++;
		}
		
		Collections.sort(main, new Comparator<String>() {
	        @Override
	        public int compare(String s1, String s2) {
	            return s1.compareToIgnoreCase(s2);
	        }
	    });
		
		Set<String> toRetain = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		toRetain.addAll(main);
		Set<String> set = new LinkedHashSet<String>(main);
		set.retainAll(new LinkedHashSet<String>(toRetain));
		main = new ArrayList<String>(set);
		System.out.println(main);
		
		PrintWriter out = new PrintWriter("date.txt");
		for (String p : main) {
			out.println(p);
		}
		out.close();
        
		
		
        
    }
	
	private static ArrayList<String> getDates(String file, BufferedReader br) {
		ArrayList<String> dates = new ArrayList<String>();
		
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.out.println("Shit how");
		}
		
		try {
		    String line = br.readLine();

		    while (line != null) {
		    	if (!getDate(line).equals("NO MATCH")) {
		    		dates.add(getDate(line));
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
	
	public static String getDate(String dt) {
		
		//<Flow Date="2015-09-21 01:38:00">2</Flow>
		String pattern = "<Flow Date=\"(.*)\">\\d+</Flow>";	
		
		// Create a Pattern object
	    Pattern r = Pattern.compile(pattern);
	
	    // Now create matcher object.
	    Matcher m = r.matcher(dt);
	    String tempName = "";
	    if (m.find( )) {
	    	tempName = m.group(1); 
	        return tempName;
	         
	    } else {
	         return "NO MATCH";
	    }
	}
}
