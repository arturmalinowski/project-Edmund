package cs.group.edmund.typeSelector;


import cs.group.edmund.clue.*;
import cs.group.edmund.utils.Thesaurus;

import java.util.List;

import static java.util.Arrays.asList;

public class Selector {

    private final Thesaurus thesaurus = new Thesaurus();
    private final List<Clue> clues = asList(new AnagramClue(thesaurus),
            new DoubleDefinitionsClue(thesaurus),
            new ContainerClue(thesaurus),
            new DeletionClue(thesaurus),
            new OddEvenClue(),
            new ReversalClue(thesaurus));

    public String retrieveAnswer(String phrase, String hint, int answerLength) throws Exception {

        for (Clue clue : clues) {
            if (clue.isRelevant(phrase)) {
                String answer = clue.solve(phrase, hint, answerLength);
                if (!answer.equals("Answer not found")) {
                    return answer;
                }
            }
        }
        throw new IllegalArgumentException("Clue could not be solved");


    }
}
