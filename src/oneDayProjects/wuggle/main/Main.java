package oneDayProjects.wuggle.main;

import oneDayProjects.wuggle.game.WUggle;
import oneDayProjects.wuggle.graphics.Screen;

public class Main implements Runnable {

	public static WUggle game;

	public static void main(String args[]) {
		game = new WUggle(new Screen(800, 600));
		new Thread(new Main()).start();
	}

	public Main() {
	}

	@Override
	public void run() {
		while (true) {
			Thread.currentThread().setPriority(10);
			game.render();
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
