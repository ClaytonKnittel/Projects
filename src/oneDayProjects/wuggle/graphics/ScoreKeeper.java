package oneDayProjects.wuggle.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D.Float;

public class ScoreKeeper implements Drawable {
	
	private static final Color color = new Color(10, 20, 10);
	
	private int score;
	
	private static int idNum = 0;
	
	private int id;
	
	public ScoreKeeper() {
		score = 0;
		id = idNum++;
	}
	
	public void play(String s) {
		score += score(s);
	}
	
	public int id() {
		return id;
	}
	
	public static int score(String word) {
		switch (word.length()) {
		case 0:
		case 1:
		case 2:
			return 0;
		case 3:
		case 4:
			return 1;
		case 5:
			return 2;
		case 6:
			return 3;
		case 7:
			return 5;
		default:
			return 11;
		}
	}
	
	public void reset() {
		score = 0;
	}
	
	@Override
	public void draw(Graphics g, Float bb) {
		if (bb == null)
			return;
		g.setColor(color);
		Drawer.drawText(g, "Score: " + score + "", (float) bb.getMinX(), (float) bb.getMinY(), (float) bb.getWidth(), (float) bb.getHeight());
	}
	
}
