package oneDayProjects.wuggle;

import java.util.LinkedList;

public class ValidChoice {
	
	private Dice last;
	private LinkedList<Dice> chain;
	private String word;
	
	public ValidChoice() {
		last = null;
		chain = new LinkedList<>();
		word = "";
	}
	
	public boolean choose(Dice d) {
		if (last == null) {
			add(d);
			return true;
		}
		if (chain.contains(d)) {
			while (chain.getLast() != d) {
				if (chain.getLast().val().equals("QU"))
					word = word.substring(0, word.length() - 2);
				else
					word = word.substring(0, word.length() - 1);
				chain.removeLast().reset();
			}
			last = d;
			return false;
		}
		int last = this.last.numeral();
		int tile = d.numeral();
		if (Math.abs(tile % 4 - last % 4) <= 1 && Math.abs(tile / 4 - last / 4) <= 1) {
			add(d);
			return true;
		}
		return false;
	}
	
	private void add(Dice d) {
		this.last = d;
		chain.add(d);
		word += d.val();
	}
	
	public String word() {
		return word;
	}
	
	public void reset() {
		last = null;
		chain.clear();
		word = "";
	}
	
}
