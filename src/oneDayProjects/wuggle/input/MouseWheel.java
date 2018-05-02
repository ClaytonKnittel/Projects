package oneDayProjects.wuggle.input;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseWheel implements MouseWheelListener {
	
	public MouseWheel() {
		
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		String message;
		int notches = e.getWheelRotation();
		if (notches < 0) {
			message = "Mouse wheel moved UP " + -notches + " notch(es)\n";
		} else {
			message = "Mouse wheel moved DOWN " + notches + " notch(es)\n";
		}
		if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
			message += "    Scroll type: WHEEL_UNIT_SCROLL\n";
			message += "    Scroll amount: " + e.getScrollAmount() + " unit increments per notch\n";
			message += "    Units to scroll: " + e.getUnitsToScroll() + " unit increments\n";
		} else { // scroll type == MouseWheelEvent.WHEEL_BLOCK_SCROLL
			message += "    Scroll type: WHEEL_BLOCK_SCROLL\n";
		}
		System.out.println(message);
	}
}
