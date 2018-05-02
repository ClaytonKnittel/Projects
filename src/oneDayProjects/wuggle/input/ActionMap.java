package oneDayProjects.wuggle.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class ActionMap implements MouseListener, MouseWheelListener {

	private ArrayList<Button> buttons;

	public ActionMap(int numButtons) {
		buttons = new ArrayList<>(numButtons);
	}

	public void add(Rectangle2D.Float hitBox, Action a) {
		buttons.add(new Button(a, hitBox));
	}
	
	public void clear() {
		buttons.clear();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		return;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (Button b : buttons)
			b.press(e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (Button b : buttons)
			b.release(e.getX(), e.getY());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		return;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		return;
	}

	private class Button {

		private Action a;
		private Rectangle2D.Float hitBox;

		public Button(Action a, Rectangle2D.Float hitBox) {
			this.a = a;
			this.hitBox = hitBox;
		}

		public void press(int mouseX, int mouseY) {
			if (contains(mouseX, mouseY))
				a.press();
		}

		public void release(int mouseX, int mouseY) {
			if (contains(mouseX, mouseY))
				a.release();
			a.clear();
		}
		
		public void scroll(int mouseX, int mouseY, int units) {
			if (contains(mouseX, mouseY))
				a.scroll(units);
		}
		
		public boolean contains(int x, int y) {
			return hitBox.contains(new Point2D.Float(x, y));
		}

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int x = e.getX();
		int y = e.getY();
		int s = e.getWheelRotation();
		for (Button b : buttons) {
			b.scroll(x, y, s);
		}
	}

}
