package oneDayProjects.wuggle.input;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import oneDayProjects.wuggle.game.Dice;
import oneDayProjects.wuggle.game.ValidChoice;
import oneDayProjects.wuggle.graphics.Drawable;
import oneDayProjects.wuggle.graphics.Drawer;

public class InputButton implements Drawable, Clickable {
	
	private static final float percentageFilledX = .5f;
	private static final float percentageFilledY = .8f;
	
	private static final Color borderColor = new Color(30, 130, 140);
	private static final Color textColor = new Color(0, 20, 10);
	private static final Color bgColor = new Color(210, 200, 140);
	private static final Color chosenColor = new Color(215, 60, 20);
	
	private static final String name = "Submit:";
	
	private ValidChoice validate;
	private boolean pressed;
	
	private static int idNum = 0;
	
	private int id;
	
	public InputButton() {
		validate = new ValidChoice();
		id = idNum++;
	}
	
	public int id() {
		return id;
	}
	
	public String word() {
		return validate.word();
	}
	
	public boolean choose(Dice d) {
		return validate.choose(d);
	}
	
	public void press() {
		if (!validate.word().equals(""))
			pressed = true;
	}
	
	public void release() {
		validate.reset();
	}
	
	public void clear() {
		pressed = false;
	}

	public void draw(Graphics g, Float boundingBox) {
		if (boundingBox == null)
			return;
		g.setColor(borderColor);
		boundingBox = getBox(boundingBox);
		float w = (float) boundingBox.getWidth();
		float h = (float) boundingBox.getHeight();
		float x = (float) boundingBox.getMinX();
		float y = (float) boundingBox.getMinY();
		g.drawRect((int) x, (int) y, (int) w, (int) h);
		if (pressed)
			g.setColor(chosenColor);
		else
			g.setColor(bgColor);
		g.fillRect((int) x, (int) y, (int) w, (int) h);
		g.setColor(textColor);
		String we = name;
		if (!validate.word().equals(""))
			we += " " + validate.word();
		Drawer.drawText(g, we, x, y, w, h);
	}

	@Override
	public Float boundingBox(Drawer drawer) {
		return getBox(drawer.boundingBox(this));
	}
	
	private Float getBox(Float bb) {
		return new Rectangle2D.Float((float) (bb.getMinX() + bb.getWidth() * (1 - percentageFilledX) / 2),
				(float) (bb.getMinY() + bb.getHeight() * (1 - percentageFilledY) / 2),
				(float) (bb.getWidth() * percentageFilledX), (float) (bb.getHeight() * percentageFilledY));
	}
	
	
	
}
