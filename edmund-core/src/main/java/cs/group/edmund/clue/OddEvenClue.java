package cs.group.edmund.clue;


import cs.group.edmund.utils.Dictionary;
import cs.group.edmund.utils.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class OddEvenClue {

    private final List<String> keyWordsAny, keyWordsOdd, keyWordsEven;
    private String keyWord = null;
    private ArrayList<String> clueWords;
    private Dictionary dict = new Dictionary();

    public OddEvenClue(){
        keyWordsAny = asList("ALTERNATE", "REGULAR", "REGULARLY", "EVERY OTHER");
        keyWordsEven = asList("EVEN", "EVENLY", "EVERY SECOND");
        keyWordsOdd = asList("ODD", "ODDS");
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

    public String solve(String phrase, String hint, int... answerLength){
        keyWord = null;
        String possibleAnswer = "";
        String[] tempWords = phrase.replaceAll("[-+.^:,?!'’/]"," ").toUpperCase().split(" ");
        clueWords = new ArrayList<>(Arrays.asList(tempWords));
        clueWords = Helper.removeDuplicates(clueWords);
        clueWords.removeAll(Arrays.asList("", null));

        for (int i = 0; i<answerLength.length; i++) {

            if(findType(keyWordsAny, phrase)) possibleAnswer = solveAny(answerLength[i]);
            if(findType(keyWordsEven, phrase)) possibleAnswer = solveOddEven(answerLength[i], 1);
            if(findType(keyWordsOdd, phrase)) possibleAnswer = solveOddEven(answerLength[i], 0);

            return possibleAnswer;
        }

        return null;
    }

    private String solveOddEven(int answerLength, int type) {
        ArrayList<String> tempList = new ArrayList(clueWords);
        String answer = "";
        String multipleAnswer = "Possible answers: ";
        int counter = 0;

        for (String word : tempList) {
            if (word.length() < answerLength+1 || word.length() > answerLength+3) {
                clueWords.remove(word);
            }
        }

        if (clueWords.size() > 1) {
            for (String word : clueWords) {
                for (int i = 0; i < word.length(); i = i + 2) {
                    answer = answer + word.charAt(i);
                }
                if (dict.validate(answer)) {
                    multipleAnswer = multipleAnswer + answer.toLowerCase() + ", ";
                    counter++;
                }
            }
        }
        else {
            for (int i = 0; i < clueWords.get(0).length(); i = i + 2) {
                answer = answer + clueWords.get(0).charAt(i);
            }
        }

        if (counter > 1) {
            return multipleAnswer;
        }
        else {
            return answer.toLowerCase();
        }
    }

    private String solveAny(int answerLength) {
        return null;
    }

    private boolean findType(List<String> keyWords, String phrase) {
        for(String key : keyWords) {
            if (phrase.toUpperCase().contains(key)) {
                keyWord = key;
                if (keyWord.contains(" ")) {
                    clueWords.remove(keyWord.substring(0, keyWord.indexOf(" ")));
                    clueWords.remove(keyWord.substring(keyWord.indexOf(" ")+1));
                }
                return true;
            }
        }
        return false;
    }
}
