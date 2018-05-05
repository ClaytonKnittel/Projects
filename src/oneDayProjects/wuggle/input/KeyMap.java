package oneDayProjects.wuggle.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author claytonknittel
 * 
 * Added 05/05/2018
 *
 */
public class KeyMap implements KeyListener {
	
	private Map<Integer, Action> map;
	
	public KeyMap() {
		map = new HashMap<>();
	}
	
	public void put(int key, Action action) {
		map.put(key, action);
	}
	
	public static interface Action {
		void act();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		return;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (map.containsKey(e.getKeyCode()))
			map.get(e.getKeyCode()).act();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		return;
	}
	
}
