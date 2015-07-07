package hw9;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class MapPanel extends JPanel {
	
	// image of the map 
	private Image map;
	
	// list of coordinates for a path to paint (if any) 
	private List<Point2D.Double> coords; 
	
	// factor to divide the size of the image by for scaling purposes
	private static final int SCALE = 2; 
	
	/**
	 * Constructs a MapPanel object to display a map image, which may include
	 * a path--which includes a circle on the start and end coordinates, and a line
	 * between each pair of coordinates along the path in the map data. 
	 * 
	 * @param fileName The name of the map image to be displayed. 
	 */
	public MapPanel(String fileName) {
		map = Toolkit.getDefaultToolkit().getImage("src/hw8/data/"+ fileName); 
		coords = new ArrayList<Point2D.Double>();
	}
	
	/**
	 * Set the path of coordinates to be displayed. 
	 * 
	 * @param coords The list of points along a given path. 
	 * @modifies MapPanel
	 * @effects Updates the path to reflect the current path. 
	 */
	public void setCoords(List<Point2D.Double> coords) {
		this.coords = coords; 
	}
	
	/**
	 * Paints the map image and any path to be represented. 
	 */
	@Override 
	public void paintComponent(Graphics g) {
		int width = map.getWidth(this) / SCALE; 
	    int height = map.getHeight(this) / SCALE;

		g.drawImage(map, 0, 0, width, height, Color.WHITE, this); 
		
		// paints the end-points and the lines in the path
		g.setColor(Color.RED);
		if (!coords.isEmpty()) {
			paintPoint(g, coords.get(0)); 
			paintPoint(g, coords.get(coords.size() -1)); 
			for (int i = 1; i < coords.size(); i++) {
				paintLine(g, coords.get(i-1), coords.get(i)); 
			}
		}
	}
	
	// paints a line in the path
	private void paintLine(Graphics g, Point2D.Double start, Point2D.Double end) {
		int x1 = (int) Math.round(start.getX() / SCALE);
		int y1 = (int) Math.round(start.getY() / SCALE);
		int x2 = (int) Math.round(end.getX() / SCALE);
		int y2 = (int) Math.round(end.getY() /SCALE );
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x1, y1 + 1, x2, y2 + 1);
		g.drawLine(x1, y1 + 2, x2, y2 + 2);
		g.drawLine(x1, y1 + 3, x2, y2 + 3);
	}
	
	// paints an end-point of the path
	private void paintPoint(Graphics g, Point2D.Double point) {
		int x = (int) Math.round(point.getX() / SCALE);
		int y = (int) Math.round(point.getY() / SCALE);
		g.fillOval(x - 7, y - 7, 14, 14);
	}
	
	/**
	 * @returns Returns the preferred size of the MapPanel to the scaled size of the image
	 */
	@Override
	public Dimension getPreferredSize() {
	    return new Dimension(map.getWidth(this) / SCALE, map.getHeight(this) / SCALE);
	}
	
	
	/**
	 * @returns Returns the height of the scaled image.
	 */
	public int getHeight() {
		return map.getHeight(this) / SCALE;
	}
	
	/**
	 * @returns Returns the width of the scaled image.
	 */
	public int getWidth() {
		return map.getWidth(this) / SCALE;
	}
}
