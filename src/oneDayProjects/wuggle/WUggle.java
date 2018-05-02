package oneDayProjects.wuggle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import oneDayProjects.wuggle.graphics.Drawer;
import oneDayProjects.wuggle.graphics.ScoreKeeper;
import oneDayProjects.wuggle.graphics.Screen;
import oneDayProjects.wuggle.graphics.Timer;
import oneDayProjects.wuggle.input.Action;
import oneDayProjects.wuggle.input.ActionMap;
import oneDayProjects.wuggle.input.InputButton;
import structures.Trie;

public class WUggle {

	private Dice[] grid;
	private InputButton button;
	private ScoreKeeper scoreKeeper;
	private WordList wordList;
	private WordList answerList;
	private Timer timer;
	private ActionMap input;
	private Screen screen;
	private Trie<String> dictionary;
	
	public static final boolean bigDictionary = true;

	/**
	 * A WUggle game
	 * 
	 * @param grid
	 *            a 4x4 array of Strings. The string at row r, 0 <= r < 4 and column
	 *            c, 0 <= c < 4 is stored at grid[r][c]
	 */
	public WUggle(Screen screen) {
		this.screen = screen;
		initDictionary();
		this.input = new ActionMap(18);
		screen.addMouseListener(input);
		screen.addMouseWheelListener(input);
		initAll();
		do {
			screen.clear();
			initBoard();
			initAnswers();
			if (answerList.maxScore() != 11 || answerList.size() < 80)
				continue;
			break;
		} while (true);
		initActions();
		initTimer();
	}
	
	private void initTimer() {
		timer = new Timer();
		screen.drawer().add(timer);
	}
	
	private void initDictionary() {
		dictionary = new Trie<>(26, (s) -> {
			String[] r = new String[s.length()];
			int i = 0;
			for (char c : s.toCharArray())
				r[i++] = c + "";
			return r;
		});
		
		FileInputStream s = null;
		try {
			if (bigDictionary)
				s = new FileInputStream(new File("datafiles/dictionary/words.txt"));
			else
				s = new FileInputStream(new File("datafiles/dictionary/shortWords.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			String word = "";
			while (s.available() != 0) {
				char c = (char) s.read();
				if (c == '\n') {
					if (word.length() >= 3)
						dictionary.add(word.toUpperCase());
					word = "";
				}
				else
					word += c;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initAll() {
		button = new InputButton();
		scoreKeeper = new ScoreKeeper();
		wordList = new WordList();
	}
	
	private void initAnswers() {
		answerList = new WordList(1);
        List<String> e = BoardAnalyzer.findAllWords(dictionary, grid);
        e.sort((a, b) -> {
        	return a.compareTo(b);
        });
        for (String r : e)
        	answerList.add(r, ScoreKeeper.score(r));
	}

	private void initActions() {
		Drawer d = screen.drawer();
		input.clear();
		for (Dice b : grid) {
			input.add(b.boundingBox(d), new Action() {
				public void press() {
					b.press();
				}
				public void release() {
					b.release(button);
				}
				public void clear() {
					b.clear();
				}
				public void scroll(int units) {
					return;
				}
			});
		}
		d.add(button);
		input.add(button.boundingBox(d), new Action() {
			public void press() {
				button.press();
			}
			public void release() {
				submitWord(button.word());
				button.release();
				reset();
			}
			public void clear() {
				button.clear();
			}
			public void scroll(int units) {
				return;
			}
		});
		d.add(scoreKeeper);
		d.add(wordList);
		input.add(wordList.boundingBox(d), new Action() {
			public void press() {
				return;
			}
			public void release() {
				return;
			}
			public void clear() {
				return;
			}
			public void scroll(int units) {
				wordList.scroll(units * 2);
			}
		});
	}
	
	private void initEndActions() {
		Drawer d = screen.drawer();
		input.clear();
		d.add(wordList);
		input.add(wordList.boundingBox(d), new Action() {
			public void press() {
				return;
			}
			public void release() {
				return;
			}
			public void clear() {
				return;
			}
			public void scroll(int units) {
				wordList.scroll(units * 2);
			}
		});
		d.add(answerList);
		input.add(answerList.boundingBox(d), new Action() {
			public void press() {
				return;
			}
			public void release() {
				return;
			}
			public void clear() {
				return;
			}
			public void scroll(int units) {
				answerList.scroll(units * 2);
			}
		});
	}
	
	private void reset() {
		for (Dice d : grid)
			d.reset();
	}
	
	private void submitWord(String word) {
		if (dictionary.contains(word)) {
			if (!wordList.contains(word)) {
				scoreKeeper.play(word);
				wordList.add(word, ScoreKeeper.score(word));
			}
		}
	}

	private void initBoard() {
		this.grid = new Dice[16];
		int size = grid.length;
		ArrayList<Integer> nums = new ArrayList<>(size);
		ArrayList<Integer> randos = new ArrayList<>(size);
		for (int i = 0; i < size; i++)
			nums.add(i);
		while (nums.size() > 0)
			randos.add(nums.remove((int) (Math.random() * nums.size())));
		for (int i = 0; i < size; i++) {
			grid[i] = new Dice(randos.get(i), i);
		}
		for (Dice d : grid) {
			screen.add(d);
		}
	}
	
	private void checkEnd() {
		if (timer.over()) {
			timer.finish();
			screen.drawer().initEndScreen();
			initEndActions();
		}
	}

	public void render() {
		checkEnd();
		screen.draw();
	}
}
