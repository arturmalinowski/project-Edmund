package cs.group.edmund.clue;

import cs.group.edmund.utils.Dictionary;
import cs.group.edmund.utils.Thesaurus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public class HiddenClue implements Clue {

    private final List<String> keyWords;
    private Dictionary dictionary;
    private Thesaurus thesaurus;

    public HiddenClue(Thesaurus thesaurus, Dictionary dictionary) {
        this.thesaurus = thesaurus;
        this.dictionary = dictionary;
        keyWords = asList("buried in", "part of", "concealed", "contains", "from", "held by", "hide", "hiding", "hides", "include", "including", "includes", "mislaid", "part", "partially", "piece", "reveal", "some", "little", "within");
    }

    public boolean isRelevant(String phrase) {
        for (String keyWord : keyWords) {
            if (phrase.toUpperCase().contains(keyWord.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String create(String word) {
        return null;
    }


    public Optional<List<String>> solve(String phrase, String hint, int... answerLength) {
        String keyWord = null;
        Boolean isHidden = false;
        String[] words = phrase.replaceAll("[-+.^:,?!'’/]", " ").toLowerCase().split(" ");
        ArrayList<String> clueWords = new ArrayList<>(Arrays.asList(words));
        List<String> finalAnswers = new ArrayList<>();

        for (String word : clueWords) {
            if (keyWords.contains(word)) {
                isHidden = true;
                keyWord = word;
                break;
            }
        }

        if (!isHidden) {
            finalAnswers = solveKeyless(clueWords, answerLength[0], hint);
            if (finalAnswers.size() > 0) {
                return Optional.of(finalAnswers);
            }
            else {
                return Optional.empty();
            }
        }
        else {
            ArrayList<String> leftWords = new ArrayList<>();
            ArrayList<String> rightWords = new ArrayList<>();
            ArrayList<String> possibleAnswers = new ArrayList<>();

            for (int i = 0; i < clueWords.indexOf(keyWord); i++) {
                leftWords.add(clueWords.get(i));
            }
            for (int i = clueWords.indexOf(keyWord)+1; i < clueWords.size(); i++) {
                rightWords.add(clueWords.get(i));
            }

            joinWords(leftWords, possibleAnswers, answerLength[0]);
            joinWords(rightWords, possibleAnswers, answerLength[0]);

            for (String word : possibleAnswers) {

                    ArrayList<String> newWords = thesaurus.getAllSynonymsXML(word);
                    List<String> clueNonKeywords = new ArrayList<>(leftWords);
                    clueNonKeywords.addAll(rightWords);

                    for (String nonKeywords : clueNonKeywords) {
                        if (newWords.contains(nonKeywords)) {
                            finalAnswers.add(word);
                        }
                    }
            }

            if (finalAnswers.isEmpty()) {
                for (String singleAnswer : possibleAnswers) {
                    if (!hint.equals("")) {
                        if (singleAnswer.matches(hint)) {
                            finalAnswers.add(singleAnswer);
                        }
                    }
                }
                if (!finalAnswers.isEmpty()) {
                    return Optional.of(finalAnswers);
                }
            }

            if (!hint.equals("")) {
                for (String word : possibleAnswers) {
                    if (word.matches(hint)) {
                        finalAnswers.add(word);
                    }
                }
                if (!finalAnswers.isEmpty()) {
                    return Optional.of(finalAnswers);
                }
            }
            else {
                return Optional.of(possibleAnswers);
            }

            return Optional.empty();
        }
    }

    private void joinWords(ArrayList<String> words, ArrayList<String> answerList, int answerLength){
        String joinedWords = "";
        searchAndCombine(joinedWords, words, answerLength, answerList);
    }

    private List<String> solveKeyless(ArrayList<String> clueWords, int answerLength, String hint) {
        String joinedWords = "";
        ArrayList<String> wordList = new ArrayList<>();
        ArrayList<String> answerList = new ArrayList<>();

        searchAndCombine(joinedWords, clueWords, answerLength, wordList);

        if (!hint.equals("")) {
            for (String word : answerList) {
                if (word.matches(hint)) {
                    answerList.add(word);
                }
            }
        }
        else {
            return wordList;
        }

        return answerList;
    }

    private void searchAndCombine(String combine, ArrayList<String> words, int length, ArrayList<String> answerList) {
        for (String word : words) {
            combine = combine + word;
        }

        for (String word : dictionary.getWords()) {
            if (word.length() == length) {
                if (combine.contains(word)) {
                    answerList.add(word);
                }
            }
        }
    }
}
