package cs.group.edmund.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.io.FileUtils.readLines;

public class Dictionary {

    private List<String> words = new ArrayList<>();

    public Dictionary() {
        URI url;
        try {
            url = ClassLoader.getSystemResource("dictionary.txt").toURI();
            words = readLines(new File(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getWords() {
        return words;
    }

    public boolean validate(String wordToValidate) {
        for (String word : words) {
            if (word.equals(wordToValidate)) {
                return true;
            }
        }
        return false;
    }
}
