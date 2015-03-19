package cs.group.edmund.typeSelector;


import cs.group.edmund.clue.*;
import cs.group.edmund.utils.Dictionary;
import cs.group.edmund.utils.Thesaurus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cs.group.edmund.utils.Helper.removeDuplicates;
import static java.util.Arrays.asList;

public class Selector {

    Logger logger = LoggerFactory.getLogger("cs.group.edmund.typeSelector");

    private final Thesaurus thesaurus;
    private final Dictionary dictionary;
    private List<String> algoCheck = new ArrayList<>();

    public Selector(Thesaurus thesaurus, Dictionary dictionary) {
        this.thesaurus = thesaurus;
        this.dictionary = dictionary;
    }

    public List<String> retrieveAnswer(String phrase, String hint, int answerLength) throws Exception {
        logger.info("Retrieving answer for clue: " + phrase);
        logger.info("Hint: " + hint);
        logger.info("Answer length: " + answerLength);

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
            logger.info("Final answers for clue " + "\"" + phrase + "\"" + " : " + allPossibleAnswers);
            return allPossibleAnswers.get(0);
        }

        if (allPossibleAnswers.size() > 1) {
            ArrayList<String> combinedAnswers = new ArrayList<>();
            for (List<String> possibleAnswer : allPossibleAnswers) {
                // when more than one clue comes back with more than one answer, merge the lists and return that list
                combinedAnswers.addAll(possibleAnswer.stream().collect(Collectors.toList()));
                removeDuplicates(combinedAnswers);
            }

            logger.info("Final answers for clue " + "\"" + phrase + "\"" + " : " + combinedAnswers);
            return combinedAnswers;
        }

        // when no answers are returned, throw
        logger.info("No answer found for clue: " + "\"" + phrase + "\"");
        throw new IllegalArgumentException("Clue could not be solved");

    }

    private void retrievePossibleAnswers(String phrase, String hint, int answerLength, List<Clue> clues, List<List<String>> allPossibleAnswers) {
        for (Clue clue : clues) {
            if (clue.isRelevant(phrase)) {
                algoCheck.add(clue.getClass().getSimpleName());
                logger.info("Current relevant clue type: " + clue.getClass().getSimpleName());
                computeAnswers(phrase, hint, answerLength, allPossibleAnswers, clue);
            }
        }
        if (allPossibleAnswers.size() == 0) {
            for (Clue clue : clues) {
                if (!algoCheck.contains(clue.getClass().getSimpleName())) {
                    logger.info("Current non-relevant clue type: " + clue.getClass().getSimpleName());
                    computeAnswers(phrase, hint, answerLength, allPossibleAnswers, clue);
                }
            }
        }
        algoCheck.clear();
    }

    private void computeAnswers(String phrase, String hint, int answerLength, List<List<String>> allPossibleAnswers, Clue clue) {
        Optional<List<String>> answer = clue.solve(phrase, hint, answerLength);
        if (answer.isPresent()) {
            allPossibleAnswers.add(answer.get());
            logger.info("Current answers found for: " + clue.getClass().getSimpleName() + " is :" + answer.get());
        }
    }
}
