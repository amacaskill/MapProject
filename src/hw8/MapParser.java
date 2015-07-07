package hw8;

/**
 * @author Hope Crandall
 * 
 * Static class that contains methods to parse data needed for MapPaths. 
 * 
 */

import hw5.Edge;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MapParser {
	
	/**
     * A checked exception class for bad data files
    */
    @SuppressWarnings("serial")
    public static class MalformedDataException extends Exception {
        public MalformedDataException() { }

        public MalformedDataException(String message) {
            super(message);
        }

        public MalformedDataException(Throwable cause) {
            super(cause);
        }

        public MalformedDataException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    /**
     * @param filename The name of the file containing the relevant data. 
     * @param names An empty map to be populated by the file data.
     * @param coordinates An empty map to be populated by file data.
     * @throws MalformedDataException Throws exception if the data in the file
     *                                is formatted incorrectly
     * @modifies names, coordinates
     * @effects Populates names with the short and long names from the file and
     *          populates coordinates with short names and their coordinates. 
     */
	public static void parseDataBuildings(String filename, Map<String, String> names,
			Map<String, Point2D.Double> coordinates) throws MalformedDataException{
		
		 BufferedReader reader = null;
		 
		 try {
			 reader = new BufferedReader(new FileReader("src/hw8/data/" + filename));

		     // Constructs the Map from shortNames to longNames and the 
			 // Map from shortNames to coordinates one line of data 
			 // at a time
		     String inputLine;
		     while ((inputLine = reader.readLine()) != null) {
		    	 
		    	 // Ignore comment lines.
		    	 if (inputLine.startsWith("#")) {
		                continue;
		    	 }

		         // Parses the data and throws an exception for malformed lines
		         String[] tokens = inputLine.split("\t");
		         if (tokens.length != 4) {
		        	 throw new MalformedDataException("Line should contain exactly three tabs: "
		                                                 + inputLine);
		         }

		         String shortName = tokens[0];
		         String longName = tokens[1];
		         Point2D.Double coordinate = new Point2D.Double(Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]));
		         
		         // Add the parsed data to the name and coordinates maps
		         names.put(shortName, longName);
		         coordinates.put(shortName, coordinate);
		        }
		 } catch (IOException e) {
			 System.err.println(e.toString());
		     e.printStackTrace(System.err);
		 } finally {
			 if (reader != null) {
				 try {
					 reader.close();
		         } catch (IOException e) {
		             System.err.println(e.toString());
		             e.printStackTrace(System.err);
		         }
		     }
		 }
	}
	
	/**
	 * @param filename The name of the file to be processed. 
	 * @return Returns a Set<Edge<Point2D.Double, Double>> containing all connections between 
	 *         locations given by the data file. 
	 * @throws MalformedDataException Throws exception if the data is formatted incorrectly. 
	 */
	public static Set<Edge<Point2D.Double, Double>> parseDataPaths (String filename) throws MalformedDataException {

		 BufferedReader reader = null;
		 Set<Edge<Point2D.Double, Double>> edges =  new HashSet<Edge<Point2D.Double, Double>>(); 
		 try {
			 reader = new BufferedReader(new FileReader("src/hw8/data/" + filename));
			 
		     // Constructs the set of edges from one coordinate to another
			 // with an edge label of there distance, by constructing 
			 // each edge from a pair of data lines 
			 
		     String inputLine = reader.readLine();
		     
		     // just in case the file is empty, the fence post case at the 
		     // beginning of the while loop would throw an exception
		     if (inputLine == null) {
		    	 return edges; 
		     }
		     
		     String[] tokens = inputLine.split(",");
    		 if (tokens.length != 2) {
    			 throw new MalformedDataException("Line should contain exactly one comma: "
    					                           + inputLine);
    		 }
    		 Point2D.Double from = new Point2D.Double(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]));
		     while (inputLine != null) {
		    	 
		    	 // Ignore comment lines.
		    	 if (inputLine.startsWith("#")) {
		                continue;
		    	 }
		    	 
		    	 if (inputLine.contains("\t")) {
		   
		    		 // Parses the data, stripping out punctuation and throwing
			         // an exception for malformed lines.
		    		 
			         inputLine = inputLine.replace(",", " ");
			         inputLine = inputLine.replace(":", ""); 
			         inputLine = inputLine.replace("\t", ""); 
			         tokens = inputLine.split(" ");
			         if (tokens.length != 3) {
			        	 throw new MalformedDataException("Line should contain one comma and one colon"
			                                                 + inputLine);
			         }
                     
			         Point2D.Double to = new Point2D.Double(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]));
			         double distance = Double.parseDouble(tokens[2]);
			         
			         // create a new edge and add it to the set 
			         edges.add(new Edge<Point2D.Double, Double>(from, to, distance)); 
		    		 
		    	 } else {  // new starting coordinate 
		    		 tokens = inputLine.split(",");
		    		 if (tokens.length != 2) {
		    			 throw new MalformedDataException("Line should contain exactly one comma: "
		    					                           + inputLine);
		    		 }
				     from = new Point2D.Double(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]));
		    	 }
		         
		         inputLine = reader.readLine(); 
		     }
		     
		 } catch (IOException e) {
			 System.err.println(e.toString());
		     e.printStackTrace(System.err);
		 } finally {
			 if (reader != null) {
				 try {
					 reader.close();
		         } catch (IOException e) {
		             System.err.println(e.toString());
		             e.printStackTrace(System.err);
		         }
		     }
		 }
		 return edges; 
	}

}
