package oneDayProjects.wuggle.input;

import java.awt.geom.Rectangle2D;

import oneDayProjects.wuggle.graphics.Drawer;

public interface Clickable {
	
	/**
	 * 
	 * @param drawer
	 * @return the hit box for the mouse, not necessarily the same as the graphics bounding box
	 */
	Rectangle2D.Float boundingBox(Drawer drawer);
	
}
