package cs.group.edmund;

public abstract class Clue {

	// Fields
	String answer;
	String clue;

	int answerLength;
	int answerXPosition;
	int answerYPosition;
	boolean answerVertical;


	// Abstract Methods
	abstract void create();
	abstract void solve();
}