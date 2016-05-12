package jarvis.dev;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Dev tool for jarvis developement.
 * @author Francois.
 *
 */
public class JarvisDevTools {
	
	/**
	 * Constructor.
	 */
	public JarvisDevTools() {
		
	}
	
	/**
	 * Run a SQL script.
	 * @param scriptPath Script path.
	 * @throws IOException Exception.
	 */
	public void runScript(String scriptPath) throws IOException {
	      Process p = Runtime.getRuntime().exec("psql -d jarvis -U jarvis_master -f " + scriptPath);
	      BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
	      BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	      boolean something = true;
	      while (something) {
	    	  something = false;
	    	  String line = input.readLine();
	    	  if (line != null) {
	    		  System.out.println(line);
	    		  something = true;
	    	  }
	    	  line = error.readLine();
	    	  if (line != null) {
	    		  System.out.println("Error : " + line);
	    		  something = true;
	    	  }
	      }
	      input.close();
	}
	
	/**
	 * Dev tool main.
	 * @param args
	 */
	public static void main(String[] args) {
		JarvisDevTools dev = new JarvisDevTools();
		
		// Execute database initialization script
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		try {
			dev.runScript("script/createtable.sql");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error executing script : " + e.getMessage());
		}
	}
}
