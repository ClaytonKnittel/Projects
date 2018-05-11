package oneDayProjects.wuggle.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D.Float;

public class Timer implements Drawable {
	
	private static final Color color = new Color(10, 20, 10);
	
	private long time;
	private static final int gameTime = 181;
	
	private static int idNum = 0;
	
	private boolean done;
	
	private int id;
	
	public Timer() {
		reset();
		id = idNum++;
		done = false;
	}
	
	public int id() {
		return id;
	}
	
	public void finish() {
		done = true;
	}
	
	public void reset() {
		time = System.currentTimeMillis();
	}
	
	public boolean over() {
		return System.currentTimeMillis() - time > gameTime * 1000 && !done;
	}

	@Override
	public void draw(Graphics g, Float bb) {
		if (bb == null)
			return;
		g.setColor(color);
		int time = gameTime - (int) ((System.currentTimeMillis() - this.time) / 1000);
		Drawer.drawText(g, "Time: " + time + "", (float) bb.getMinX(), (float) bb.getMinY(), (float) bb.getWidth(), (float) bb.getHeight());
	}
	
}
