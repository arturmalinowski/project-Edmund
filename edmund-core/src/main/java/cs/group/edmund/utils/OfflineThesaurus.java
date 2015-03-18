package cs.group.edmund.utils;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.apache.commons.io.FileUtils.readLines;

public class OfflineThesaurus {

    private HashMap<String, ArrayList<String>> words = new HashMap<>();
    private File file;

    Logger logger = LoggerFactory.getLogger("cs.group.edmund.utils");

    public OfflineThesaurus() {
        try {
            file = new File("thesaurus.txt");
            InputStream in = OfflineThesaurus.class.getClassLoader().getResourceAsStream("thesaurus.txt");
            FileUtils.copyInputStreamToFile(in, file);
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNewQuery(String queryWord, List<String> synonyms) {
        String line = "";

        if (!synonyms.isEmpty()) {
            for (String word : synonyms) {
                line = line + "," + word;
            }
            line = line.substring(1);
            try {
                logger.info("Writing word: " + queryWord.toLowerCase() + " with synonyms to offline Thesaurus");
                FileUtils.writeStringToFile(file, queryWord.toLowerCase() + ":" + line + "\n", "utf8", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                logger.info("Writing word: " + queryWord.toLowerCase() + " with no synonyms to offline Thesaurus");
                FileUtils.writeStringToFile(file, queryWord.toLowerCase() + ":\n", "utf8", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> results(String word) {
        return words.get(word.toLowerCase());
    }

    public boolean hasWord(String word) {
        return words.containsKey(word.toLowerCase());
    }

    public void readFromFile() {
        List<String> listOfWords = new ArrayList<>();
        try {
            listOfWords = readLines(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String word : listOfWords) {
            String arrayNew = word.substring(word.indexOf(":") + 1, word.length());
            words.put(word.substring(0, word.indexOf(":")).toLowerCase(), new ArrayList<>(Arrays.asList(arrayNew.split(","))));
        }
    }
}
