package oneDayProjects.wuggle.game;

import java.awt.event.KeyEvent;
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
import oneDayProjects.wuggle.graphics.WordList;
import oneDayProjects.wuggle.input.Action;
import oneDayProjects.wuggle.input.ActionMap;
import oneDayProjects.wuggle.input.InputButton;
import oneDayProjects.wuggle.input.KeyMap;
import structures.Trie;

public class WUggle {

	private Dice[] grid;
	private InputButton button;
	private ScoreKeeper scoreKeeper;
	private WordList wordList;
	private Timer timer;
	private ActionMap input;
	private KeyMap keys;
	private Screen screen;
	private Trie<String> dictionary;
	
	private WordList answerList;
	private ScoreKeeper totalPossible, total;

	public WUggle(Screen screen) {
		this.screen = screen;
		initDictionary();
		this.input = new ActionMap(18);
		this.keys = new KeyMap();
		screen.addMouseListener(input);
		screen.addMouseWheelListener(input);
		screen.addKeyListener(keys);
		initAll();
		do {
			screen.clear();
			initBoard();
			initAnswers();
			if (answerList.maxScore() != 11 || answerList.size() < 80)
				continue;
			totalPossible.setScore(answerList.total());
			break;
		} while (true);
		initDrawables();
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
			s = new FileInputStream(new File("src/libraries/words.txt"));
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
		total = new ScoreKeeper("Total points: ");
		totalPossible = new ScoreKeeper("Total possible: ");
	}
	
	private void initDrawables() {
		screen.add(button);
		screen.add(scoreKeeper);
		screen.add(wordList);
		screen.add(answerList);
		screen.add(total);
		screen.add(totalPossible);
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
		input.add(button.boundingBox(d), new Action() {
			public void press() {
				button.press();
			}
			public void release() {
				submit();
			}
			public void clear() {
				button.clear();
			}
			public void scroll(int units) {
				return;
			}
		});
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
		keys.put(KeyEvent.VK_ENTER, () -> {
			submit();
		});
	}
	
	private void submit() {
		submitWord(button.word());
		button.release();
		reset();
	}
	
	private void initEndActions() {
		Drawer d = screen.drawer();
		input.clear();
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
		total.setScore(wordList.total());
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
