package oneDayProjects.wuggle.graphics;

import java.awt.Font;

import javax.swing.JFrame;

public class Screen extends JFrame {
	private static final long serialVersionUID = 299923281162268744L;
	
	private Drawer drawer;
	
	public static final int topBorder = 22;
	
	public static final Font font = new Font("Serif", Font.BOLD, 17);
	
	public Screen(int width, int height) {
		this.setTitle("WUggle");
		this.setSize(width, height);
		this.setResizable(false);
		this.setVisible(true);
		this.setFocusable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.createBufferStrategy(2);
		this.drawer = new Drawer(this.getBufferStrategy(), width, height);
		drawer.initGameScreen();
	}
	
	public Drawer drawer() {
		return drawer;
	}
	
	public void clear() {
		drawer.clear();
	}
	
	public void add(Drawable...drawables) {
		drawer.add(drawables);
	}
	
	public void remove(Drawable...drawables) {
		drawer.remove(drawables);
	}
	
	public void draw() {
		drawer.draw();
	}
	
}
