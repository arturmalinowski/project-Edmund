package cs.group.edmund.clue;

import cs.group.edmund.utils.Thesaurus;

import java.util.ArrayList;
import java.util.List;

public class DoubleDefinitionsClue implements Clue {

    private List<String> firstElementList = new ArrayList<>();
    private List<String> secondElementList = new ArrayList<>();
    private List<String> leftBackupWordList = new ArrayList<>();
    private List<String> rightBackupWordList = new ArrayList<>();
    private boolean matchingWordFound;
    private String answer;
    private ArrayList<Integer> answerLength = new ArrayList<>();
    private String leftBackupWord;
    private String rightBackupWord;

    @Override
    public String create(String word) {
        return null;
    }

    @Override
    public boolean isRelevant(String phrase) {
        return findMatchingWords(phrase);
    }

    @Override
    public String solve(String phrase, String hint, int... answerLength) {
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
            leftBackupWord = splitPhrase[1];
            rightBackupWord = splitPhrase[splitPhrase.length - 2];
            splitPhrase = new String[]{splitPhrase[0], splitPhrase[splitPhrase.length - 1]};
        }

        putRelatedWordsInTwoLists(splitPhrase);

        compareTheListsOfWords();

        return matchingWordFound;
    }

    private void putRelatedWordsInTwoLists(String[] splitPhrase) {
        Thesaurus thesaurus = new Thesaurus();
        leftBackupWordList = thesaurus.getAllSynonymsXML(leftBackupWord);
        rightBackupWordList = thesaurus.getAllSynonymsXML(rightBackupWord);
            firstElementList = thesaurus.getAllSynonymsXML(splitPhrase[0]);
            secondElementList = thesaurus.getAllSynonymsXML(splitPhrase[1]);
    }

    private void compareTheListsOfWords() {
        matchingWordFound = false;
        for (String element : firstElementList) {
            if (secondElementList.contains(element) && answerLength.size() == 0) {
                matchingWordFound = true;
                answer = element;
            } else if (secondElementList.contains(element) && element.length() == answerLength.get(0)) {
                matchingWordFound = true;
                answer = element;
            } else if (rightBackupWordList.contains(element) && element.length() == answerLength.get(0)) {
                matchingWordFound = true;
                answer = element;
            }
        }
        for (String secondListElement : secondElementList) {
            if (leftBackupWordList.contains(secondListElement) && secondListElement.length() == answerLength.get(0)) {
                matchingWordFound = true;
                answer = secondListElement;
            }
        }
    }
}