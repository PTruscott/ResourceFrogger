package club.bede.brumhack.game;

import java.util.regex.*;

public class DataOut {
	
	public DataOut() {
		
	    
	    
	}
	public String getName(String name) {
		
		String pattern = "<ShortDescription>(.*)</ShortDescription>";
		
		
		// Create a Pattern object
	    Pattern r = Pattern.compile(pattern);
	
	    // Now create matcher object.
	    Matcher m = r.matcher(name);
	    if (m.find( )) {
	         String tempName = "";
	         tempName = m.group(1);
	         return tempName;
	         
	    } else {
	         return "NO MATCH";
	    }
    	
    }
	
	public String getDate(String dt) {
		
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
	public int getCars(String dt) {
		
		//<Flow Date="2015-09-21 01:38:00">2</Flow>
		String pattern = "<Flow Date=\".*\">(\\d+)+</Flow>";	
		
		// Create a Pattern object
	    Pattern r = Pattern.compile(pattern);
	
	    // Now create matcher object.
	    Matcher m = r.matcher(dt);
	    int tempNum = 0;
	    if (m.find( )) {
	    	tempNum = Integer.valueOf(m.group(1)); 
	        return tempNum;
	         
	    } else {
	         return 0;
	    }
	}
}
