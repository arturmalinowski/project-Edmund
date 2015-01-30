package cs.group.edmund.typeSelector;


import cs.group.edmund.clue.*;
import cs.group.edmund.utils.Thesaurus;

public class Selector {

    public String retrieveAnswer(String input, String hint, int answerLength) throws Exception {
        Thesaurus thesaurus = new Thesaurus();
        String answer;

        AnagramClue anagramClue = new AnagramClue(thesaurus);
        answer = anagramClue.solve(input, hint, answerLength);

        if (answer.equals("Answer not found")) {
            ContainerClue containerClue = new ContainerClue(thesaurus);
            containerClue.solve(input, hint, answerLength);
        }

        if (answer.equals("Answer not found")) {
            DeletionClue deletionClue = new DeletionClue(thesaurus);
            deletionClue.solve(input, hint, answerLength);
        }

        if (answer.equals("Answer not found")) {
            DoubleDefinitionsClue doubleDefinitionsClue = new DoubleDefinitionsClue(thesaurus);
            doubleDefinitionsClue.solve(input, hint, answerLength);
        }

        if (answer.equals("Answer not found")) {
            OddEvenClue oddEvenClue = new OddEvenClue();
            oddEvenClue.solve(input, hint, answerLength);
        }

        if (answer.equals("Answer not found")) {
            ReversalClue reversalClue = new ReversalClue(thesaurus);
            reversalClue.solve(input, hint, answerLength);
        }

        if (answer.equals("Answer not found")) {
            throw new Exception("No answer found for clue");
        } else {
            return answer;
        }
    }
}
