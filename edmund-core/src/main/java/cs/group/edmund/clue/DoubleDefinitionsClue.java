package cs.group.edmund.clue;

import cs.group.edmund.utils.Thesaurus;
import org.dom4j.Document;

import java.util.ArrayList;

public class DoubleDefinitionsClue implements Clue {

    private ArrayList<String> firstElementList = new ArrayList<>();
    private ArrayList<String> secondElementList = new ArrayList<>();
    private boolean matchingWordFound;
    private String answer;
    private ArrayList<Integer> answerLength = new ArrayList<>();

    @Override
    public String create(String word) {
        return null;
    }

    @Override
    public boolean isRelevant(String phrase) {
        answerLength.add(0);
        return findMatchingWords(phrase);
    }

    @Override
    public String solve(String phrase, int... answerLength) {
        for (int value : answerLength) {
            this.answerLength.add(value);
        }
        return getAnswer(phrase);
    }

    public String getAnswer(String phrase) {
        findMatchingWords(phrase);
        if (answer == null) {
            answer = "Answer not found :(";
        }
        return answer;
    }

    public Boolean findMatchingWords(String phrase) {
        String[] splitPhrase = phrase.split(" ");
        if (splitPhrase.length > 2) {
            splitPhrase = new String[]{splitPhrase[0], splitPhrase[splitPhrase.length - 1]};
        }

        putRelatedWordsInTwoLists(splitPhrase);

        compareTheListsOfWords();

        if (matchingWordFound) {
            System.out.println("Answer found: " + answer);
        }

        return matchingWordFound;
    }

    private void putRelatedWordsInTwoLists(String[] splitPhrase) {
        Thesaurus thesaurus = new Thesaurus();
        for (int i = 0; i < 2; i++) {
            Document document = thesaurus.getSynonymsAsDocument(splitPhrase[i]);
            if (i == 0 && document != null) {
                firstElementList = thesaurus.addSynonymsToList(document);
            } else if (document != null) {
                secondElementList = thesaurus.addSynonymsToList(document);
            }
        }
    }

    private void compareTheListsOfWords() {
        matchingWordFound = false;
        for (String element : firstElementList) {
            if (secondElementList.contains(element) && answerLength.get(0) == 0) {
                matchingWordFound = true;
                answer = element;
            } else if (secondElementList.contains(element) && element.length() == answerLength.get(0)) {
                matchingWordFound = true;
                answer = element;
            }
        }
    }
}