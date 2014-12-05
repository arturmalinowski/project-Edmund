package cs.group.edmund.clue;


import cs.group.edmund.utils.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class OddEvenClue {

    private final List<String> keyWordsAny, keyWordsOdd, keyWordsEven;

    public OddEvenClue(){
        keyWordsAny = asList("ALTERNATE", "REGULAR", "REGULARLY", "EVERY OTHER");
        keyWordsOdd = asList("EVEN", "EVENLY", "EVERY SECOND");
        keyWordsEven = asList("ODD", "ODDS");
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
        String keyWord = null;
        String possibleAnswer = "";
        String[] tempWords = phrase.replaceAll("[-+.^:,?!'’/]"," ").toUpperCase().split(" ");
        ArrayList<String> clueWords = new ArrayList<>(Arrays.asList(tempWords));
        clueWords = Helper.removeDuplicates(clueWords);
        clueWords.removeAll(Arrays.asList("", null));

        for (int i = 0; i<answerLength.length; i++) {

            for(String key : keyWordsAny) {
                if (phrase.toUpperCase().contains(key)) {
                    keyWord = key;
                    if (keyWord.contains(" ")) {
                        clueWords.remove(keyWord.substring(0, keyWord.indexOf(" ")));
                        clueWords.remove(keyWord.substring(keyWord.indexOf(" ")+1));
                    }
                }
            }

        }

        return null;
    }
}
