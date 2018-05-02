package oneDayProjects.wuggle.input;

public interface Action {
	
	void press();
	void release();
	void clear();
	void scroll(int units);
}
