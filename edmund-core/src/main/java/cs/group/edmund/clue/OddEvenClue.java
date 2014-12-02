package cs.group.edmund.clue;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class OddEvenClue {

    private final List<String> keyWordsAny;
    private final List<String> keyWordsOdd;
    private final List<String> keyWordsEven;

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

        for (int i = 0; i<answerLength.length; i++) {

        }

        return null;
    }
}
