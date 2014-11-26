package cs.group.edmund.clue;

import cs.group.edmund.utils.Thesaurus;

import java.util.ArrayList;
import java.util.List;

public class DoubleDefinitionsClue implements Clue {

    private List<String> firstElementList = new ArrayList<>();
    private List<String> secondElementList = new ArrayList<>();
    private List<String> leftBackupWordList = new ArrayList<>();
    private List<String> rightBackupWordList = new ArrayList<>();
    private List<Integer> answerLength = new ArrayList<>();
    private String answer;
    private String leftBackupWord;
    private String rightBackupWord;
    private boolean matchingWordFound;
    private int hintIndex = 0;
    private char hintLetter = '.';
    private Thesaurus thesaurus;

    public DoubleDefinitionsClue(Thesaurus thes) {
        thesaurus = thes;
    }

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

        extractHint(hint);

        return getAnswer(phrase.toLowerCase());
    }

    private void extractHint(String hint) {
        if (hint != null) {
            // TODO enable this to work with answers of more than one word
            if (hint.length() == answerLength.get(0)) {
                for (int i = 0; i < hint.length(); i++) {
                    if (hint.charAt(i) == '.') {
                        hintIndex++;
                    } else {
                        hintLetter = hint.charAt(i);
                        i = hint.length();
                    }
                }
            }
        }
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

        compareTheListsOfWords(splitPhrase);

        return matchingWordFound;
    }

    private void putRelatedWordsInTwoLists(String[] splitPhrase) {
        firstElementList = (containsSearchableWords(splitPhrase[0]) ? thesaurus.getAllSynonymsXML(splitPhrase[0]) : firstElementList);

        secondElementList = (containsSearchableWords(splitPhrase[1]) ? thesaurus.getAllSynonymsXML(splitPhrase[1]) : secondElementList);

        if (leftBackupWord != null) {
            leftBackupWordList = (containsSearchableWords(leftBackupWord) ? thesaurus.getAllSynonymsXML(leftBackupWord) : leftBackupWordList);
        }
        if (leftBackupWord != null) {
            rightBackupWordList = (containsSearchableWords(rightBackupWord) ? thesaurus.getAllSynonymsXML(rightBackupWord) : rightBackupWordList);
        }
    }

    private boolean containsSearchableWords(String word) {
        return !word.equals("the") && !word.equals("a");
    }

    private void compareTheListsOfWords(String[] splitPhrase) {
        matchingWordFound = false;

        searchLists();

        if (!matchingWordFound) {
            firstElementList.addAll(thesaurus.getRelatedWordsXML(splitPhrase[0]));

            secondElementList.addAll(thesaurus.getRelatedWordsXML(splitPhrase[1]));

            leftBackupWordList.addAll(thesaurus.getRelatedWordsXML(leftBackupWord));

            rightBackupWordList.addAll(thesaurus.getRelatedWordsXML(rightBackupWord));

            searchLists();
        }
    }

    private void searchLists() {
        for (String element : firstElementList) {
            if (secondElementList.contains(element) && answerLength.size() == 0 && matchesHint(element)) {
                matchingWordFound = true;
                answer = element;
            } else if (secondElementList.contains(element) && element.length() == answerLength.get(0) && matchesHint(element)) {
                matchingWordFound = true;
                answer = element;
            } else if (!element.equals(rightBackupWord)) {
                if (rightBackupWordList.contains(element) && element.length() == answerLength.get(0) && matchesHint(element)) {
                    matchingWordFound = true;
                    answer = element;
                }
            }
        }
        for (String secondListElement : secondElementList) {
            if (!secondListElement.equals(leftBackupWord)) {
                if (leftBackupWordList.contains(secondListElement) && secondListElement.length() == answerLength.get(0) && matchesHint(secondListElement)) {
                    matchingWordFound = true;
                    answer = secondListElement;
                }
            }
        }
    }

    public boolean matchesHint(String element) {
        return hintLetter == '.' || element.charAt(hintIndex) == hintLetter;
    }

}