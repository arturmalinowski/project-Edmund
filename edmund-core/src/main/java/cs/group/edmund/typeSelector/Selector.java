package cs.group.edmund.typeSelector;


import cs.group.edmund.clue.*;
import cs.group.edmund.utils.Dictionary;
import cs.group.edmund.utils.Thesaurus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cs.group.edmund.utils.Helper.removeDuplicates;
import static java.util.Arrays.asList;

public class Selector {


    private final Thesaurus thesaurus;
    private final Dictionary dictionary;

    public Selector(Thesaurus thesaurus, Dictionary dictionary) {

        this.thesaurus = thesaurus;
        this.dictionary = dictionary;
    }

    public List<String> retrieveAnswer(String phrase, String hint, int answerLength) throws Exception {

        List<List<String>> allPossibleAnswers = new ArrayList<>();


        List<Clue> clues = asList(
                new AnagramClue(thesaurus, dictionary),
                new HiddenClue(thesaurus, dictionary),
                new OddEvenClue(dictionary),
                new DeletionClue(thesaurus),
                new ReversalClue(thesaurus),
                new ContainerClue(thesaurus),
                new DoubleDefinitionsClue(thesaurus));

        // go through each clue solver with the phrase
        retrievePossibleAnswers(phrase, hint, answerLength, clues, allPossibleAnswers);

        if (allPossibleAnswers.size() == 1) {
            if (allPossibleAnswers.get(0).size() == 1) {
                // when there is only one answer, return it
                return new ArrayList<>(asList(allPossibleAnswers.get(0).get(0)));
            } else {
                // otherwise return the first answer from the first list
                return allPossibleAnswers.get(0);
            }
        }

        if (allPossibleAnswers.size() > 1) {
            ArrayList<String> combinedAnswers = new ArrayList<>();
            for (List<String> possibleAnswer : allPossibleAnswers) {
                if (possibleAnswer.size() == 1) {
                    // when there is only one clue returning a list of answers, return that list
                    return new ArrayList<>(asList(possibleAnswer.get(0)));
                } else {
                    // when more than one clue comes back with more than one answer, merge the lists and return that list
                    combinedAnswers.addAll(possibleAnswer.stream().collect(Collectors.toList()));
                    removeDuplicates(combinedAnswers);
                }
            }
            return combinedAnswers;
        }

        // when no answers are returned, throw
        throw new IllegalArgumentException("Clue could not be solved");

    }

    private void retrievePossibleAnswers(String phrase, String hint, int answerLength, List<Clue> clues, List<List<String>> allPossibleAnswers) {
        for (Clue clue : clues) {
            if (clue.isRelevant(phrase)) {
                computeAnswers(phrase, hint, answerLength, allPossibleAnswers, clue);
            }
        }
        if (allPossibleAnswers.size() == 0) {
            for (Clue clue : clues) {
                computeAnswers(phrase, hint, answerLength, allPossibleAnswers, clue);
            }
        }
    }

    private void computeAnswers(String phrase, String hint, int answerLength, List<List<String>> allPossibleAnswers, Clue clue) {
        Optional<List<String>> answer = clue.solve(phrase, hint, answerLength);
        if (answer.isPresent()) {
            allPossibleAnswers.add(answer.get());
        }
    }
}
