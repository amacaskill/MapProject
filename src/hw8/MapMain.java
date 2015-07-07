package hw8;

/**
 * @author Hope Crandall
 * 
 * Class that allows users to interact with the campus map via the command line. Providing 
 * functionality for viewing buildings and finding paths. 
 * 
 */

import hw8.Connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MapMain {
	
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in); 
		
		MapPaths map = null;
		
		try {
			map = new MapPaths("campus_buildings.dat", "campus_paths.dat"); 
		} catch (Exception e) {
			System.err.println(e.toString());
		    e.printStackTrace(System.err);
		}
		
		printMenu();
		System.out.println(); 
		
		System.out.print("Enter an option ('m' to see the menu): ");
		String next = console.nextLine();
		while (!next.equals("q")) {
			if (next.equals("b")) {
				printBuildings(map); 
			} else if (next.equals("r")) {
				System.out.print("Abbreviated name of starting building: ");
				String b1 = console.nextLine();
				System.out.print("Abbreviated name of ending building: ");
				String b2 = console.nextLine(); 
				printPath(map, b1, b2); 
			} else if (next.equals("m")) {
				printMenu(); 
			} else if (next.equals("") | next.contains("#")) {
				while (next.equals("") | next.contains("#")) {
					System.out.println(next);
					next = console.nextLine(); 
				}
				continue; 
		    } else {
				System.out.println("Unknown option"); 
			} 
			System.out.println(); 
			System.out.print("Enter an option ('m' to see the menu): ");  
			next = console.nextLine();
		}
		console.close(); 
	}
	
	// print a path between the two given locations in map 
	private static void printPath(MapPaths map, String s1, String s2) {
		if (!map.containsBuilding(s1) | !map.containsBuilding(s2)) {
			if (!map.containsBuilding(s1)) {
				System.out.println("Unknown building: " + s1);
			}
			if (!map.containsBuilding(s2)) {
				System.out.println("Unknown building: " + s2);
			}
		} else {
			List<Connection> path = map.getPath(s1, s2); 
			System.out.println("Path from " + map.getLongName(s1) + " to " + map.getLongName(s2) + ":");
			
			double totalDist = 0; 
			for (Connection e: path) {
				String direction = e.getDir(); 
				double dist = e.getDist();
				totalDist += dist; 
				int x = (int) Math.round(e.getDest().getX());
				int y = (int) Math.round(e.getDest().getY());
				System.out.println("\tWalk " + Math.round(dist) + " feet " + direction + " to " + "(" + x + ", " + y + ")");
			}
			System.out.println("Total distance: " + Math.round(totalDist) + " feet"); 
		}
	}
	

	// prints the menu of command options
	private static void printMenu() {
		System.out.println("Menu:");
		System.out.println("\tr to find a route");
		System.out.println("\tb to see a list of all buildings");
		System.out.println("\tq to quit");
	}
	
	// prints a list of all the buildings in map
	private static void printBuildings(MapPaths map) {
		System.out.println("Buildings:");
		Map<String, String> m = map.getBuildings(); 
		List<String> buildings = new ArrayList<String>(); 
		for (String s: m.keySet()) {
			buildings.add(s);
		}
		Collections.sort(buildings); 
		for (String s: buildings) {
			System.out.println("\t" + s + ": " + m.get(s));
		}
	}

}
