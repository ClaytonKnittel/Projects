package oneDayProjects.wuggle.graphics;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.LinkedList;

public class WordList implements Drawable {

	private static final Color borderColor = new Color(13, 15, 10);
	private static final Color bgColor = new Color(210, 200, 140);
	private static final Color wordColor = new Color(10, 30, 20);
	private static final Color[] scoreColor = { new Color(40, 200, 200), new Color(40, 221, 20),
			new Color(100, 100, 200), null, new Color(100, 100, 100), null, null, null, null, null,
			new Color(10, 20, 80) };

	private final int border = 10;
	private final int cushion = 4;
	private final int wordSeparation = 4;

	private LinkedList<WordScore> words;

	private int scrollDist;
	private int maxScrollDist;

	private static int idNum = 0;

	private int id;

	public WordList() {
		this(idNum++);
	}

	public WordList(int id) {
		words = new LinkedList<>();
		scrollDist = 0;
		this.id = id;
	}

	public int id() {
		return id;
	}

	public int size() {
		return words.size();
	}
	
	public int maxScore() {
		int max = 0;
		for (WordScore w : words) {
			if (w.score > max)
				max = w.score;
			if (max == 11)
				break;
		}
		return max;
	}
	
	public int total() {
		int total = 0;
		for (WordScore w : words)
			total += w.score;
		return total;
	}

	public void updateMaxScrollDist(int height, int textHeight) {
		maxScrollDist = Math.max(words.size() * (textHeight + wordSeparation) - wordSeparation - height + 2 * cushion, 0);
	}

	public boolean contains(String word) {
		return words.contains(new WordScore(word, 0));
	}

	public void scroll(int units) {
		scrollDist += units;
		if (scrollDist < 0)
			scrollDist = 0;
		if (scrollDist > maxScrollDist)
			scrollDist = maxScrollDist;
	}

	public void add(String word, int score) {
		words.add(new WordScore(word, score));
	}

	public Rectangle2D.Float boundingBox(Drawer drawer) {
		return getBox(drawer.boundingBox(this));
	}

	private Rectangle2D.Float getBox(Rectangle2D.Float bb) {
		float x = (float) (bb.getMinX()) + border;
		float y = (float) (bb.getMinY()) + border;
		float w = (float) (bb.getWidth()) - border * 2;
		float h = (float) (bb.getHeight()) - border * 2;
		return new Rectangle2D.Float(x, y, w, h);
	}

	@Override
	public void draw(Graphics g, Float bb) {
		if (bb == null)
			return;
		int xx = (int) (bb.getMinX());
		int yy = (int) (bb.getMinY());
		int ww = (int) (bb.getWidth());
		int hh = (int) (bb.getHeight());
		int x = xx + border;
		int y = yy + border;
		int w = ww - border * 2;
		int h = hh - border * 2;
		FontMetrics f = g.getFontMetrics();
		int textHeight = f.getAscent() + f.getDescent() + f.getLeading();
		updateMaxScrollDist(h, textHeight);
		g.setColor(bgColor);
		g.fillRect(x, y, w, h);
		int i = 0;
		for (WordScore word : words) {
			String sc = word.score + "";
			Rectangle2D r = g.getFont().getStringBounds(sc, 0, sc.length(), f.getFontRenderContext());
			int yPos = (int) (y + cushion + textHeight + i * (wordSeparation + textHeight) - scrollDist);
			if (yPos < y + h + textHeight && yPos > y) {
				g.setColor(wordColor);
				g.drawBytes(word.val.getBytes(), 0, word.val.length(), x + cushion, yPos);
				g.setColor(scoreColor[word.score - 1]);
				int off = (int) r.getWidth();
				g.drawBytes(sc.getBytes(), 0, sc.length(), x - cushion + w - off, yPos);
			}
			i++;
		}
		g.setColor(Drawer.bgColor);
		g.fillRect(xx, yy, ww, border);
		g.fillRect(xx, yy, border, hh);
		g.fillRect(xx, yy + hh - border, ww, border);
		g.fillRect(xx + ww - border, yy, border, hh);
		g.setColor(bgColor);
		g.fillRect(x, y, w, cushion);
		g.fillRect(x, y, cushion, h);
		g.fillRect(x, y + h - cushion, w, cushion);
		g.fillRect(x + w - cushion, y, cushion, h);
		g.setColor(borderColor);
		g.drawRect(x, y, w, h);
	}

	private static class WordScore {
		private String val;
		private int score;

		public WordScore(String val, int score) {
			this.val = val;
			this.score = score;
		}

		@Override
		public boolean equals(Object o) {
			if (o == this)
				return true;
			if (!(o instanceof WordScore))
				return false;
			WordScore w = (WordScore) o;
			return w.val.equals(val);
		}
	}

}
