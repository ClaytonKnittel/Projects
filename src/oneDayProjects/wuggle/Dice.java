package oneDayProjects.wuggle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import oneDayProjects.wuggle.graphics.Drawable;
import oneDayProjects.wuggle.graphics.Drawer;
import oneDayProjects.wuggle.input.Clickable;
import oneDayProjects.wuggle.input.InputButton;

/**
 * I'm giving you this class so that you don't have to type in the dice values
 * from the Boggle game. Each die has 6 sides, with the letters shown below. The
 * game uses all 16 dice.
 * 
 * You can reference a die by saying: Dice.die00 for example anywhere in your
 * code, because the String arrays here are all public For example,
 * Dice.die00[2] is "F".
 * 
 * Source:
 * https://boardgames.stackexchange.com/questions/29264/boggle-what-is-the-dice-configuration-for-boggle-in-various-languages
 * 
 * @author roncytron
 *
 */
public class Dice implements Drawable, Clickable {

	public final static String[][] die;

	static {
		String[] die00 = new String[] { "R", "I", "F", "O", "B", "X" };
		String[] die01 = new String[] { "I", "F", "E", "H", "E", "Y" };
		String[] die02 = new String[] { "D", "E", "N", "O", "W", "S" };
		String[] die03 = new String[] { "U", "T", "O", "K", "N", "D" };
		String[] die04 = new String[] { "H", "M", "S", "R", "A", "O" };
		String[] die05 = new String[] { "L", "U", "P", "E", "T", "S" };
		String[] die06 = new String[] { "A", "C", "I", "T", "O", "A" };
		String[] die07 = new String[] { "Y", "L", "G", "K", "U", "E" };
		String[] die08 = new String[] { "QU", "B", "M", "J", "O", "A" };
		String[] die09 = new String[] { "E", "H", "I", "S", "P", "N" };
		String[] die10 = new String[] { "V", "E", "T", "I", "G", "N" };
		String[] die11 = new String[] { "B", "A", "L", "I", "Y", "T" };
		String[] die12 = new String[] { "E", "Z", "A", "V", "N", "D" };
		String[] die13 = new String[] { "R", "A", "L", "E", "S", "C" };
		String[] die14 = new String[] { "U", "W", "I", "L", "R", "G" };
		String[] die15 = new String[] { "P", "A", "C", "E", "M", "D" };

		die = new String[][] { die00, die01, die02, die03, die04, die05, die06, die07, die08, die09, die10, die11,
				die12, die13, die14, die15, };
	}

	private static final float percentFilled = .8f;

	private static final Color borderColor = new Color(30, 130, 140);
	private static final Color textColor = new Color(0, 20, 10);
	private static final Color bgColor = new Color(210, 200, 140);
	private static final Color pressedColor = new Color(211, 159, 141);
	private static final Color chosenColor = new Color(141, 211, 141);
	
	private boolean pressed;
	private boolean chosen;

	private int which, val;

	/**
	 * 0 means top left, 15 means bottom right, goes across rows and then down
	 * columns
	 */
	private int numeral;
	
	private static int idNum = 0;
	
	private int id;

	public Dice(int which, int numeral) {
		this.which = which;
		this.numeral = numeral;
		pressed = false;
		chosen = false;
		randomize();
		id = idNum++;
	}

	public void randomize() {
		val = (int) (Math.random() * 6);
	}
	
	public int id() {
		return id;
	}
	
	public int numeral() {
		return numeral;
	}

	public String val() {
		return die[which][val];
	}
	
	public void press() {
		pressed = true;
	}
	
	public void release(InputButton button) {
		if (pressed) {
			if (button.choose(this)) {
				chosen = !chosen;
			}
		}
	}
	
	public void clear() {
		pressed = false;
	}
	
	public void reset() {
		pressed = false;
		chosen = false;
	}

	private Rectangle2D.Float getBox(Rectangle2D.Float boundingBox) {
		float width = (float) boundingBox.getWidth();
		float height = (float) boundingBox.getHeight();
		float offX = (float) boundingBox.getMinX();
		float offY = (float) boundingBox.getMinY();
		float x = width / 4f * (numeral % 4) + offX;
		float y = height / 4f * (numeral / 4) + offY;
		return new Rectangle2D.Float((int) (x + width / 4f * (1 - percentFilled) / 2f),
				(int) (y + height / 4f * (1 - percentFilled) / 2), (int) (width / 4 * percentFilled),
				(int) (height / 4 * percentFilled));
	}

	@Override
	public Rectangle2D.Float boundingBox(Drawer drawer) {
		return getBox(drawer.boundingBox(this));
	}

	@Override
	public void draw(Graphics g, Rectangle2D.Float boundingBox) {
		if (boundingBox == null)
			return;
		g.setColor(borderColor);
		boundingBox = getBox(boundingBox);
		float width = (float) boundingBox.getWidth();
		float height = (float) boundingBox.getHeight();
		float offX = (float) boundingBox.getMinX();
		float offY = (float) boundingBox.getMinY();
		g.drawRect((int) offX, (int) offY, (int) width, (int) height);
		if (pressed)
			g.setColor(pressedColor);
		else if (chosen)
			g.setColor(chosenColor);
		else
			g.setColor(bgColor);
		g.fillRect((int) offX, (int) offY, (int) width, (int) height);
		g.setColor(textColor);
		Drawer.drawText(g, val(), offX, offY, width, height);
	}
	
	public String toString() {
		return "Dice " + numeral + ": " + val();
	}

}
