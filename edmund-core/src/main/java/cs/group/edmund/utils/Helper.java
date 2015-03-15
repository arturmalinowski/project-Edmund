package cs.group.edmund.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

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

    // Filter methods

    public static ArrayList<String> removeDuplicates(ArrayList<String> list) {
        HashSet<String> hs = new HashSet<>();
        hs.addAll(list);
        list.clear();
        list.addAll(hs);
        return list;
    }

    public static ArrayList<String> filterByAnswerLength(ArrayList<String> list, int... answerLength) {
        if (list.size() > 0) {
            if (answerLength.length == 1) {
                for (Iterator<String> iter = list.listIterator(); iter.hasNext(); ) {
                    String word = iter.next();
                    if ((word.length() != answerLength[0]) || (word.contains(" ")))
                        iter.remove();
                }
            }
        }
        return list;
    }

    public static ArrayList<String> filterByHint(ArrayList<String> list, String hint) {
        for (Iterator<String> iter = list.listIterator(); iter.hasNext(); ) {
            String word = iter.next();

            Pattern pattern = Pattern.compile(hint);
            Matcher matcher = pattern.matcher(word);

            String group = "";
            while (matcher.find()) {
                group = matcher.group();
            }
            if (group.equals(""))
                iter.remove();
        }
        return list;
    }

    public static ArrayList<String> filterAll(ArrayList<String> list, String hint, int... answerLength) {

        list = removeDuplicates(list);
        list = filterByAnswerLength(list, answerLength);
        list = filterByHint(list, hint);

        return list;
    }

    public static ArrayList<String> removeSpecialChar(String word){
        return new ArrayList<>(Arrays.asList(word.replaceAll("[-+.^:,?!'’/\\s]", " ").toLowerCase().trim().split(" ")));
    }
}
