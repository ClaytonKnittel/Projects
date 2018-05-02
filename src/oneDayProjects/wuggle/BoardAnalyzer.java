package oneDayProjects.wuggle;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

import structures.Trie;

public class BoardAnalyzer {
	
	public static List<String> findAllWords(Trie<String> dictionary, Dice[] dice) {
		AdjacencyStructure<Dice> a = new AdjacencyStructure<>(dice);
		a.link((x, y) -> {
			int c = x.numeral();
			int b = y.numeral();
			if (b <= c)
				return false;
			return Math.abs(c % 4 - b % 4) <= 1 && Math.abs(c / 4 - b / 4) <= 1;
		});
		LinkedList<String> words = new LinkedList<>();
		
		for (AdjacencyStructure.ItemList<Dice> d : a.adjacents)
			recurse(dictionary, d, words, "");
		
		a.clear();
		return words;
	}
	
	private static void recurse(Trie<String> dictionary, AdjacencyStructure.ItemList<Dice> el, LinkedList<String> words, String word) {
		el.visited = true;
		word += el.val.val();
		if (dictionary.contains(word)) {
			if (!words.contains(word))
				words.add(word);
		}
		if (dictionary.isPart(word)) {
			for (AdjacencyStructure.ItemList<Dice> adj : el.list) {
				if (!adj.visited) {
					recurse(dictionary, adj, words, word);
				}
			}
		}
		el.visited = false;
	}
	
	private static class AdjacencyStructure<T> {
		ItemList<T>[] adjacents;
		
		@SuppressWarnings("unchecked")
		private AdjacencyStructure(T[] list) {
			ItemList<T> dummy = new ItemList<>(null);
			adjacents = (ItemList<T>[]) Array.newInstance(dummy.getClass(), list.length);
			for (int i = 0; i < list.length; i++)
				adjacents[i] = new ItemList<>(list[i]);
		}
		
		private void link(Verifier<T> v) {
			for (ItemList<T> i : adjacents) {
				for (ItemList<T> j : adjacents) {
					if (v.link(i.val, j.val)) {
						i.list.add(j);
						j.list.add(i);
					}
				}
			}
		}
		
		private void clear() {
			adjacents = null;
		}
		
		private static class ItemList<T> {
			private T val;
			private LinkedList<ItemList<T>> list;
			private boolean visited;
			
			private ItemList(T val) {
				this.val = val;
				list = new LinkedList<>();
				visited = false;
			}
			
			public String toString() {
				String s = val.toString() + "\t";
				for (ItemList<T> l : list)
					s += l.val.toString() + " ";
				return s;
			}
		}
		
		private static interface Verifier<T> {
			boolean link(T a, T b);
		}
	}
	
}
