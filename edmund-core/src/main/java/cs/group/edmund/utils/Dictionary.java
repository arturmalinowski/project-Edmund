package cs.group.edmund.utils;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.io.FileUtils.readLines;

public class Dictionary {

    private Multimap<Integer, String> words = HashMultimap.create();

    public Dictionary() {
        try {
            List<String> newList;
            File dictionary = new File("dictionary");
            InputStream in = OfflineThesaurus.class.getClassLoader().getResourceAsStream("dictionary.txt");
            FileUtils.copyInputStreamToFile(in, dictionary);
            newList = readLines(dictionary);

            for (String word : newList) {
                words.put(word.length(), word);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Collection<String> getWords() {
        return words.values();
    }

    public Collection<String> getWordsSize(int size) {

        return words.get(size);
    }

    public boolean validate(String wordToValidate) {
        for (String word : words.values()) {
            if (word.equals(wordToValidate)) {
                return true;
            }
        }
        return false;
    }
}
