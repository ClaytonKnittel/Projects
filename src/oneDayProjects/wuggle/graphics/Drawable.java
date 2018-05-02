package oneDayProjects.wuggle.graphics;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public interface Drawable {
	
	void draw(Graphics g, Rectangle2D.Float boundingBox);
	
	int id();
	
}
