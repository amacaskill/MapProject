package hw9;

import hw8.MapPaths;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class MapFrame extends JFrame {
	
	// holds the drop down menus and the buttons
	private FunctionsPanel functions; 
	// holds the map data
	private static MapPanel map; 
	
	private static JScrollPane mapPane; 
	
    
	/**
	 * Constructs a MapFrame object to display a map and path information
	 * from the given files. This includes drop boxes to select buildings to find 
	 * a path to be displayed, and a scrollable display of the map. Includes a 
	 * getPath button to display a path between the two chosen buildings, and a 
	 * reset button to reset all fields and clear paths from the screen.
	 * 
	 * @param title The title of the MapFrame to be displayed on the window. 
	 * @param fileName The name of the file holding the image of the map. 
	 * @param mapData The MapPaths object holding data corresponding to the map image.
	 * @effects Constructs a MapFrame object
	 */
	public MapFrame (String title, String fileName, MapPaths mapData)  {
		super(title); 
		
		setLayout(new BorderLayout()); 
	
		functions = new FunctionsPanel(mapData);
		
		functions.addFunctionListener (new FunctionListener() {
			public void functionEventOccurred(FunctionEvent e) {
				final List<Point2D.Double> coords = e.getCoords();
				final int vertical = e.getVert(); 
				final int horizontal = e.getHoriz(); 
				 Thread mapThread = new Thread() {
				      public void run() {
				        updateMap(coords, vertical, horizontal);
				      }
				    };
				    mapThread.start();
			}
		}); 
		
		map = new MapPanel(fileName);
		
		mapPane = new JScrollPane(map); 
		mapPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		mapPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mapPane.setPreferredSize(new Dimension(500, 600));    
		
		
		Container content = getContentPane(); 
	
		content.add(functions, BorderLayout.PAGE_START); 
		content.add(mapPane, BorderLayout.CENTER); 
	}
	
	// updates the map with paths and scroll information after an event
	private static void updateMap(final List<Point2D.Double> coords, final int vert, final int horiz) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	// paints path on map
		    	map.setCoords(coords);
				map.repaint(); 
				
				// calculates vertical scroll 
				JScrollBar vertical = mapPane.getVerticalScrollBar(); 
				double vScale = (vertical.getMaximum() - vertical.getMinimum()) / map.getHeight();
				int vertShift = (int) Math.round(vert * vScale); 
				vertical.setValue(vertShift);
				
				// calculates horizontal scroll
				JScrollBar horizontal = mapPane.getHorizontalScrollBar();
				double hScale = (vertical.getMaximum() - vertical.getMinimum()) / map.getWidth();
				int horizShift = (int) Math.round(vert * hScale);
				horizontal.setValue(horizShift);
		    }
		  });
	}

}
