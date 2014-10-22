package cs.group.edmund.clue;

public interface Clue {

	String create(String word);

    boolean isRelevant(String phrase);
    String solve(String phrase, int answerLength);
}