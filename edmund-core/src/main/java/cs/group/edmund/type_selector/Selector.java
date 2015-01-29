package cs.group.edmund.type_selector;


import cs.group.edmund.clue.*;
import cs.group.edmund.utils.Thesaurus;

public class Selector {

    public Clue findRelevantClueType(String input) {

        AnagramClue anagramClue = new AnagramClue();
        if (anagramClue.isRelevant(input)) {
            return anagramClue;
        }

        ContainerClue containerClue = new ContainerClue();
        if (containerClue.isRelevant(input)) {
            return containerClue;
        }

        DeletionClue deletionClue = new DeletionClue();
        if (deletionClue.isRelevant(input)) {
            return deletionClue;
        }

        DoubleDefinitionsClue doubleDefinitionsClue = new DoubleDefinitionsClue(new Thesaurus());
        if (doubleDefinitionsClue.isRelevant(input)) {
            return doubleDefinitionsClue;
        }

        OddEvenClue oddEvenClue = new OddEvenClue();
        if (oddEvenClue.isRelevant(input)) {
            return oddEvenClue;
        }

        ReversalClue reversalClue = new ReversalClue();
        if (reversalClue.isRelevant(input)) {
            return reversalClue;
        }

        // throw a suitable exception
        else return null;

    }
}
