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
    private boolean mustBeTwoWords = false;
    private Thesaurus thesaurus;
    private String hint;

    public DoubleDefinitionsClue(Thesaurus thesaurus) {
        this.thesaurus = thesaurus;
    }

    @Override
    public String create(String word) {
        return null;
    }

    @Override
    public boolean isRelevant(String phrase) {
        matchingWordFound = false;

        putRelatedWordsInTwoLists(splitPhrase(phrase));

        checkRelevance();

        return matchingWordFound;
    }

    @Override
    public String solve(String phrase, String hint, int... answerLength) {
        for (int value : answerLength) {
            this.answerLength.add(value);
        }

        this.hint = hint;
        if (this.hint == null || this.hint.equals("")) {
            this.hint = ".*";
        }

        return getAnswer(phrase.toLowerCase());
    }


    public String getAnswer(String phrase) {
        findMatchingWords(phrase);
        if (answer == null) {
            answer = "Answer not found";
        }

        return answer.replace("-", " ");
    }

    public Boolean findMatchingWords(String phrase) {
        String[] splitPhrase = splitPhrase(phrase);

        putRelatedWordsInTwoLists(splitPhrase);

        compareTheListsOfWords(splitPhrase);

        return matchingWordFound;
    }

    private String[] splitPhrase(String phrase) {
        String[] splitPhrase = phrase.split(" ");
        if (splitPhrase.length > 2) {
            leftBackupWord = splitPhrase[1];
            rightBackupWord = splitPhrase[splitPhrase.length - 2];
            splitPhrase = new String[]{splitPhrase[0], splitPhrase[splitPhrase.length - 1]};
        }
        return splitPhrase;
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
        return !word.equals("the") && !word.equals("a") && !word.equals("in");
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

        int expectedAnswerLength = checkIfAnswerNeedsToBeTwoWords();

        for (String element : firstElementList) {
            if (secondElementList.contains(element) && expectedAnswerLength == 0 && element.matches(hint) && needsToBeTwoWords(mustBeTwoWords, element)) {
                matchingWordFound = true;
                answer = element;
            } else if (secondElementList.contains(element) && element.length() == expectedAnswerLength && element.matches(hint) && needsToBeTwoWords(mustBeTwoWords, element)) {
                matchingWordFound = true;
                answer = element;
            } else if (!element.equals(rightBackupWord)) {
                if (rightBackupWordList.contains(element) && element.length() == expectedAnswerLength && element.matches(hint) && needsToBeTwoWords(mustBeTwoWords, element)) {
                    matchingWordFound = true;
                    answer = element;
                }
            }
        }
        for (String secondListElement : secondElementList) {
            if (!secondListElement.equals(leftBackupWord)) {
                if (leftBackupWordList.contains(secondListElement) && secondListElement.length() == expectedAnswerLength && secondListElement.matches(hint) && needsToBeTwoWords(mustBeTwoWords, secondListElement)) {
                    matchingWordFound = true;
                    answer = secondListElement;
                }
            }
        }
    }

    private int checkIfAnswerNeedsToBeTwoWords() {
        int expectedAnswerLength = 0;
        if (answerLength.size() != 0) {
            expectedAnswerLength = answerLength.get(0);
            if (answerLength.size() > 1) {
                expectedAnswerLength = expectedAnswerLength + answerLength.get(1) + 1;
                mustBeTwoWords = true;
            }

        }
        return expectedAnswerLength;
    }

    private boolean needsToBeTwoWords(boolean mustBeTwoWords, String element) {
        return !mustBeTwoWords || element.contains("-");
    }

    private void checkRelevance() {
        for (String element : firstElementList) {
            if (secondElementList.contains(element)) {
                matchingWordFound = true;
            } else if (secondElementList.contains(element)) {
                matchingWordFound = true;
            } else if (!element.equals(rightBackupWord)) {
                if (rightBackupWordList.contains(element)) {
                    matchingWordFound = true;
                }
            }
        }
        for (String secondListElement : secondElementList) {
            if (!secondListElement.equals(leftBackupWord)) {
                if (leftBackupWordList.contains(secondListElement)) {
                    matchingWordFound = true;
                }
            }
        }
    }

}