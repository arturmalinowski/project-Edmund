package cs.group.edmund.utils;

import java.util.ArrayList;
import java.util.HashSet;

public class Helper {

    public static ArrayList<String> removeDuplicates(ArrayList<String> list) {
        HashSet<String> hs = new HashSet<>();
        hs.addAll(list);
        list.clear();
        list.addAll(hs);
        return list;
    }

    public static ArrayList<String> combineWords(ArrayList<String> list, int answerLength) {
        ArrayList<String> wordsList = new ArrayList<>();
        ArrayList<String> secondList = new ArrayList<>(list);
        ArrayList<String> thirdList = new ArrayList<>(list);

        for(String word : list) {
            if (word.length() > answerLength) {
                continue;
            }
            if (word.length() == answerLength) {
                wordsList.add(word);
            }
            secondList.remove(word);
            for(String secondWord : secondList) {
                if (word.length() + secondWord.length() == answerLength) {
                    String newWord = secondWord + word;
                    wordsList.add(newWord);
                }
                thirdList.remove(word);
                thirdList.remove(secondWord);
                for(String thirdWord : thirdList) {
                    if (word.length() + secondWord.length() + thirdWord.length() == answerLength) {
                        String newWord = thirdWord + secondWord + word;
                        wordsList.add(newWord);
                    }
                }
            }
        }
        return wordsList;
    }
}
