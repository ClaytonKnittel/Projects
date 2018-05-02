package oneDayProjects.wuggle.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import java.util.List;

import oneDayProjects.wuggle.Dice;
import oneDayProjects.wuggle.WordList;
import oneDayProjects.wuggle.input.InputButton;

public class Drawer {
	
	private BufferStrategy b;
	private List<Drawable> objects;
	private Layout layouts;
	
	public static final Color bgColor = new Color(240, 240, 220);
	
	private int w, h;
	
	public Drawer(BufferStrategy b, int width, int height) {
		this.b = b;
		this.w = width;
		this.h = height - Screen.topBorder;
		this.objects = new LinkedList<Drawable>();
		this.layouts = new Layout();
	}
	
	public void clear() {
		this.objects.clear();
	}
	
	public void initGameScreen() {
		layouts.clear();
		layouts.add(Dice.class, new Rectangle2D.Float(0, Screen.topBorder + h / 7, w * 3 / 4, h * 6 / 7));
		layouts.add(InputButton.class, new Rectangle2D.Float(0, Screen.topBorder, w * 3 / 4, h / 7));
		layouts.add(ScoreKeeper.class, new Rectangle2D.Float(0, Screen.topBorder, w * 3 / 16, h / 7));
		layouts.add(WordList.class, new Rectangle2D.Float(w * 3 / 4, Screen.topBorder, w / 4, h), 0);
		layouts.add(Timer.class, new Rectangle2D.Float(w * 9 / 16, Screen.topBorder, w * 3 / 16, h / 7));
	}
	
	public void initEndScreen() {
		layouts.clear();
		layouts.add(WordList.class, new Rectangle2D.Float(w * 1 / 2, Screen.topBorder, w / 2, h), 0);
		layouts.add(WordList.class, new Rectangle2D.Float(0, Screen.topBorder, w / 2, h), 1);
	}
	
	public Rectangle2D.Float boundingBox(Drawable d) {
		return layouts.get(d);
	}
	
	public void add(Drawable... d) {
		for (Drawable b : d)
			objects.add(b);
	}
	
	public void remove(Drawable... d) {
		for (Drawable b : d)
			objects.remove(b);
	}
	
	public void draw() {
		Graphics g = b.getDrawGraphics();
		
		g.setColor(bgColor);
		g.fillRect(0, 0, w, h);
		g.setFont(Screen.font);
		
		for (Drawable d : objects)
			d.draw(g, layouts.get(d));
		
		g.dispose();
		b.show();
	}
	
	public static void drawText(Graphics g, String s, float offX, float offY, float width, float height) {
		Rectangle2D r = g.getFont().getStringBounds(s, 0, s.length(), g.getFontMetrics().getFontRenderContext());
		g.drawBytes(s.getBytes(), 0, s.length(), (int) (offX + width / 2f - r.getWidth() / 2f),
				(int) (offY + height / 2f));
	}
}
