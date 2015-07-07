package hw9;

import java.awt.geom.Point2D;
import java.util.EventObject;
import java.util.List;

public class FunctionEvent extends EventObject {
	
	private List<Point2D.Double> coords; 
	private int vertical;
	private int horizontal; 
	
	/**
	 * Constructs a new FunctionEvent object, which is defined by its source
	 * and a list of coordinates that define a path. 
	 * 
	 * @param source The source of this event.
	 * @param coords A list of coordinates for a path. 
	 * @effects Constructs a new FunctionEvent object.
	 */
	public FunctionEvent(Object source, List<Point2D.Double> coords, int vert, int horiz) {
		super(source);
		this.coords = coords; 
		this.vertical = vert;
		this.horizontal = horiz; 
	}
	
	/**
	 * Returns a list of coordinates to change the display of the map. 
	 * 
	 * @return Returns a list of coordinates in a path (designates the lack
	 *         of path required if the list is empty)
	 */
	public List<Point2D.Double> getCoords() {
		return coords; 
	}
	
	/**
	 * @return Returns the vertical shift element for the scroll bar.
	 */
	public int getVert() {
		return vertical;
	}
	
	/**
	 * 
	 * @return Returns the horizontal shift element for the scroll bar.
	 */
	public int getHoriz() {
		return horizontal; 
	}
	

}
