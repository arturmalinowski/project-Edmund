package cs.group.edmund.typeSelector;


import cs.group.edmund.clue.*;
import cs.group.edmund.utils.Thesaurus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public class Selector {

    private List<List<String>> allPossibleAnswers = new ArrayList<>();

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
                if (answer.isPresent()) {
                    allPossibleAnswers.add(answer.get());
                }
            }
        }
        if (allPossibleAnswers.size() == 1) {
            return allPossibleAnswers.get(0).get(0);
        }
        if (allPossibleAnswers.size() > 1) {
            List<String> combinedAnswers = new ArrayList<>();
            for (List<String> possibleAnswer : allPossibleAnswers) {
                if (possibleAnswer.size() == 1) {
                    return possibleAnswer.get(0);
                } else {
                    for (String answer : possibleAnswer) {
                        combinedAnswers.add(answer);
                    }
                }
            }
            return combinedAnswers.toString();
        }

        throw new IllegalArgumentException("Clue could not be solved");

    }
}
