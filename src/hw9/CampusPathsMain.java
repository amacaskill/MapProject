package hw9;

import hw8.MapParser.MalformedDataException;
import hw8.MapPaths;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class CampusPathsMain {
	
	/**
	 * Main method to run a map application with the information from 
	 * campus_buildings.dat and campus_paths.dat, and using the map 
	 * picture file campus_map.jpg. 
	 * 
	 * Includes controls to view the map and select buildings to find the
	 * shortest path between them, which is displayed on the map. 
	 * 
	 */
	public static void main(String[] args) {
	    SwingUtilities.invokeLater(new Runnable() {
	      public void run() {
	        try {
				makeMap();
			} catch (MalformedDataException e) {
				System.out.println(e);
				e.printStackTrace();
			} 
	      }
	    });
	  }
	
	// does the work of building the map
	private static void makeMap() throws MalformedDataException  {
		String imageName = "campus_map.jpg"; 
		
		MapPaths campus = new MapPaths("campus_buildings.dat", "campus_paths.dat");
		
		MapFrame frame = new MapFrame("Campus Paths", imageName, campus); 
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack(); 
		frame.setVisible(true); 
	}

}
