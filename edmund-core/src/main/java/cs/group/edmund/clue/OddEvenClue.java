package cs.group.edmund.clue;


import cs.group.edmund.utils.Dictionary;
import cs.group.edmund.utils.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public class OddEvenClue implements Clue {

    private final List<String> keyWordsAny, keyWordsOdd, keyWordsEven;
    private String keyWord = null;
    private ArrayList<String> clueWords;
    private Dictionary dict = new Dictionary();

    public OddEvenClue() {
        keyWordsAny = asList("ALTERNATE", "REGULAR", "REGULARLY", "EVERY OTHER");
        keyWordsEven = asList("EVEN", "EVENLY", "EVERY SECOND");
        keyWordsOdd = asList("ODD", "ODDS", "UNEVEN");
    }

    @Override
    public String create(String word) {
        return null;
    }

    public boolean isRelevant(String phrase) {
        List<String> keyWords = new ArrayList<>(keyWordsAny);
        keyWords.addAll(keyWordsOdd);
        keyWords.addAll(keyWordsEven);

        for (String keyWord : keyWords) {
            if (phrase.toUpperCase().contains(keyWord)) {
                return true;
            }
        }
        return false;
    }

    public Optional<List<String>> solve(String phrase, String hint, int... answerLength) {
        keyWord = null;
        String possibleAnswer = "";
        String[] tempWords = phrase.replaceAll("[-+.^:,?!'’/]", " ").toUpperCase().split(" ");
        clueWords = new ArrayList<>(Arrays.asList(tempWords));
        clueWords = Helper.removeDuplicates(clueWords);
        clueWords.removeAll(Arrays.asList("", null));

        for (int i : answerLength) {
            if (findType(keyWordsAny, phrase)) possibleAnswer = solveAny(i, hint);
            if (findType(keyWordsEven, phrase)) possibleAnswer = solveOddEven(i, 1, hint);
            if (findType(keyWordsOdd, phrase)) possibleAnswer = solveOddEven(i, 0, hint);
        }

        List<String> finalAnswers = new ArrayList<>();

        if (!possibleAnswer.equals("")) finalAnswers.add(possibleAnswer);

        return Optional.of(finalAnswers);
    }

    private String solveOddEven(int answerLength, int type, String hint) {
        ArrayList<String> tempList;
        tempList = new ArrayList<>(clueWords);
        String answer;
        answer = "";
        List<String> possibleAnswers = new ArrayList<>();
        int counter = 0;

        for (String word : tempList) {
            if (word.length() < answerLength * 2 - 1 || word.length() > answerLength * 2 + 1) {
                clueWords.remove(word);
            }
        }

        if (clueWords.size() > 1) {
            for (String word : clueWords) {
                for (int i = type; i < word.length(); i = i + 2) {
                    answer = answer + word.toLowerCase().charAt(i);
                }
                if (dict.validate(answer) && answer.length() == answerLength) {
                    possibleAnswers.add(answer.toLowerCase());
                    counter++;
                }
                answer = "";
            }
        } else {
            for (int i = type; i < clueWords.get(0).length(); i = i + 2) {
                answer = answer + clueWords.get(0).charAt(i);
            }
            possibleAnswers.add(answer.toLowerCase());
        }

        if (counter > 1) {
            for (String singleAnswer : possibleAnswers) {
                if (hint != null) {
                    if (singleAnswer.matches(hint)) {
                        answer = singleAnswer;
                    }
                }
            }
        } else {
            answer = possibleAnswers.get(0);
        }
        return answer.toLowerCase();
    }

    private String solveAny(int answerLength, String hint) {
        List<String> possibleAnswers = new ArrayList<>();
        String possibleAnswer = "";
        String answer = "";
        for (String word : clueWords) {
            if (word.length() == answerLength * 2) {
                for (int i = 0; i < word.length(); i = i + 2) {
                    possibleAnswer = possibleAnswer + word.toLowerCase().charAt(i);
                }
                if (dict.validate(possibleAnswer)) {
                    possibleAnswers.add(possibleAnswer);
                } else {
                    possibleAnswer = "";
                    for (int i = 1; i < word.length(); i = i + 2) {
                        possibleAnswer = possibleAnswer + word.toLowerCase().charAt(i);
                    }
                    if (dict.validate(possibleAnswer)) {
                        possibleAnswers.add(possibleAnswer);
                    }
                }
            }
        }
        for (String singleAnswer : possibleAnswers) {
            if (hint != null && possibleAnswer.length() > 1) {
                if (singleAnswer.matches(hint)) {
                    answer = possibleAnswer;
                    break;
                }
            } else {
                answer = possibleAnswers.get(0);
            }
        }
        return answer;
    }

    private boolean findType(List<String> keyWords, String phrase) {
        for (String key : keyWords) {
            if (phrase.toUpperCase().startsWith(key)) {
                if (phrase.toUpperCase().matches(".*" + key + ".*")) {
                    keyWord = key;
                    if (keyWord.contains(" ")) {
                        clueWords.remove(keyWord.substring(0, keyWord.indexOf(" ")));
                        clueWords.remove(keyWord.substring(keyWord.indexOf(" ") + 1));
                    }
                    return true;
                }
            } else {
                if (phrase.toUpperCase().matches(".*" + " " + key + ".*")) {
                    keyWord = key;
                    if (keyWord.contains(" ")) {
                        clueWords.remove(keyWord.substring(0, keyWord.indexOf(" ")));
                        clueWords.remove(keyWord.substring(keyWord.indexOf(" ") + 1));
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
