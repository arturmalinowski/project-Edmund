package cs.group.edmund.clue;

import java.util.Optional;

public interface Clue {

	String create(String word);
    boolean isRelevant(String phrase);
    Optional<String> solve(String phrase, String hint, int... answerLength);
}