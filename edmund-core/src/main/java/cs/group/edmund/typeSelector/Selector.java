package cs.group.edmund.typeSelector;


import cs.group.edmund.clue.*;
import cs.group.edmund.utils.Thesaurus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cs.group.edmund.utils.Helper.removeDuplicates;
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

        // go through each clue solver with the phrase
        retrievePossibleAnswers(phrase, hint, answerLength, clues);

        if (allPossibleAnswers.size() == 1) {
            // when there is only one answer, return it
            return allPossibleAnswers.get(0).get(0);
        }

        if (allPossibleAnswers.size() > 1) {
            ArrayList<String> combinedAnswers = new ArrayList<>();
            for (List<String> possibleAnswer : allPossibleAnswers) {
                if (possibleAnswer.size() == 1) {
                    // when there is only one clue returning a list of answers, return that list
                    return possibleAnswer.get(0);
                } else {
                    // when more than one clue comes back with more than one answer, merge the lists and return that list
                    combinedAnswers.addAll(possibleAnswer.stream().collect(Collectors.toList()));
                    removeDuplicates(combinedAnswers);
                }
            }
            return combinedAnswers.toString();
        }

        // when no answers are returned, throw
        throw new IllegalArgumentException("Clue could not be solved");

    }

    private void retrievePossibleAnswers(String phrase, String hint, int answerLength, List<Clue> clues) {
        clues.stream().filter(clue -> clue.isRelevant(phrase)).forEach(clue -> {
                    Optional<List<String>> answer = clue.solve(phrase, hint, answerLength);
                    if (answer.isPresent()) allPossibleAnswers.add(answer.get());
                }
        );
    }
}
