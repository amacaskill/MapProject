package hw9;

import hw8.Connection;
import hw8.MapPaths;

import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

public class FunctionsPanel extends JPanel {
	
	private EventListenerList listenerList = new EventListenerList(); 
	
	private MapPaths map; 
	
	/**
	 * Constructs a FunctionsPanel object. This includes two drop down menus
	 * to select the start and destination buildings and two buttons, one to find
	 * the path between the selected buildings, and a button to reset the selections
	 * and clear the visual paths. 
	 * 
	 * @param mapData The MapPaths containing the data relevant to all paths for 
	 *        a given map image. 
	 * @effects Constructs a FunctionsPanel object. 
	 */
	public FunctionsPanel(MapPaths mapData) {
		
		map = mapData; 
		
		// set layout
		this.setLayout(new FlowLayout()); 
		
		// array of options for drop down lists  
		String[] options = getOptions(); 
		
		// drop list for start
		final JComboBox<String> start = new JComboBox<String>(options); 
		start.setSelectedIndex(0);
		start.setBorder(BorderFactory.createTitledBorder("Starting Location"));
		
		// drop list for destination
		final JComboBox<String> end = new JComboBox<String>(options); 
		end.setSelectedIndex(0);
		end.setBorder(BorderFactory.createTitledBorder("Destination"));
		
		// button to get path 
		JButton getPath = new JButton("Get Path");
		
		getPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String building1 = ((String) start.getSelectedItem()).split(":")[0]; 
			    String building2 = ((String) end.getSelectedItem()).split(":")[0];
			    List<Connection> path = map.getPath(building1, building2); 
			    
				List<Point2D.Double> coords = new ArrayList<Point2D.Double>(); 
				
				// calculate values for scroll panel to shift to 
				Point2D.Double coord1 = map.getBuildingCoords(building1);
				Point2D.Double coord2 = map.getBuildingCoords(building2);
				int vert = (int) Math.round(Math.max(coord1.getY(), coord2.getY()) / 4);
				int horiz = (int) Math.round(Math.max(coord1.getX(), coord2.getX()) / 4);
				
				if (!path.isEmpty()) {
				    for (Connection c: path) {
				    	coords.add(c.getSource());
				    }
				    coords.add(path.get(path.size() -1).getDest()); 
				} else {
					// if the path is from a building to itself, still
					// records the location of the building
					coords.add(map.getBuildingCoords(building1)); 
					
				}  
				fireFunctionEvent(new FunctionEvent(this, coords, vert, horiz));  
			}
		});
		
		// button to reset map
		JButton reset = new JButton("Reset"); 
		
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start.setSelectedIndex(0);
				end.setSelectedIndex(0); 
				fireFunctionEvent(new FunctionEvent(this, new ArrayList<Point2D.Double>(), 0, 0)); 
			}
		});
		
		// add components to layout
		this.add(start); 
		this.add(end);
		this.add(getPath);
		this.add(reset); 
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
	}
	
	/**
	 * Tells a FunctionListner object that a FunctionEvent has 
	 * occurred. 
	 * 
	 * @param event A FunctionEvent object.
	 * 
	 */
	public void fireFunctionEvent (FunctionEvent event) {
		Object[] listeners = listenerList.getListenerList(); 
		
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == FunctionListener.class) {
				((FunctionListener) listeners[i+1]).functionEventOccurred(event); 
			}
		}
	}
	
	/**
	 * Adds an event listener to this FunctionPanel. 
	 * 
	 * @param listener A FunctionListener to act as an event listener. 
	 * @effects Adds an action listener for this FunctionsPanel. 
	 */
	public void addFunctionListener (FunctionListener listener) {
		listenerList.add(FunctionListener.class, listener); 
	}
	
	// Converts the data from the MapPaths object into a better 
	// visual format for the drop lists
	private String[] getOptions() {
		Map<String, String> buildingNames = map.getBuildings();
		
		List<String> buildings = new ArrayList<String>(); 
		
		for (String s: buildingNames.keySet()) {
			buildings.add(s); 
		}
		
		Collections.sort(buildings); 
		
		List<String> opts = new ArrayList<String>(); 
		
		for (String s: buildings) {
			opts.add(s + ": " + buildingNames.get(s));
		}
		
		String[] options = new String[opts.size()];
		options = opts.toArray(options); 
		
		return options; 
	}
	
	
}
