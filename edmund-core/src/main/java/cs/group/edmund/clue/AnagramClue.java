package cs.group.edmund.clue;

import java.util.List;

import static java.util.Arrays.asList;

public class AnagramClue implements Clue {

    private final List<String> keyWords;

    public AnagramClue() {
        keyWords = asList("obscure", "destroy");
    }

    @Override
    public String create(String word) {
        return null;
    }

    @Override
    public boolean isRelevant(String phrase) {
        for (String keyWord : keyWords) {
            if (phrase.contains(keyWord)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String solve(String phrase) {
        return null;
    }

}
