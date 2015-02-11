package cs.group.edmund.typeSelector;


import cs.group.edmund.clue.*;
import cs.group.edmund.utils.Thesaurus;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public class Selector {

    public String retrieveAnswer(String phrase, String hint, int answerLength, Thesaurus thesaurus) throws Exception {

        List<Clue> clues = asList(new ReversalClue(thesaurus),
                new DoubleDefinitionsClue(thesaurus),
                new ContainerClue(thesaurus),
                new DeletionClue(thesaurus),
                new OddEvenClue(),
                new AnagramClue(thesaurus));

        for (Clue clue : clues) {
            if (clue.isRelevant(phrase)) {
                Optional<List<String>> answer = clue.solve(phrase, hint, answerLength);
                if (answer.isPresent() && answer.get().size() == 1) {
                    return answer.get().get(0);
                }
            }
        }
        throw new IllegalArgumentException("Clue could not be solved");


    }
}
