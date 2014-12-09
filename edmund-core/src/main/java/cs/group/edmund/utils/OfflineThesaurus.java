package cs.group.edmund.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.apache.commons.io.FileUtils.readLines;

public class OfflineThesaurus {

    private HashMap<String, ArrayList<String>> words = new HashMap<>();
    private URI url;

    public OfflineThesaurus() {
        try {
            url = ClassLoader.getSystemResource("thesaurus.txt").toURI();
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNewQuery(String queryWord, List<String> synonyms) {
        String line = "";

        if(!synonyms.isEmpty()) {
            for (String word : synonyms) {
                line = line + "," + word;
            }
            line = line.substring(1);
            try {
                FileUtils.writeStringToFile(new File(url), queryWord + ":" + line + "\n", "utf8", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                FileUtils.writeStringToFile(new File(url), queryWord + ":\n", "utf8", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> results(String word){
        return words.get(word);
    }

    public boolean hasWord(String word){
        return words.containsKey(word);
    }

    public void readFromFile(){
        List<String> listOfWords = new ArrayList<>();
        try {
            listOfWords = readLines(new File(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String word : listOfWords) {
            String arrayNew = word.substring(word.indexOf(":")+1, word.length());
            words.put(word.substring(0, word.indexOf(":")), new ArrayList<>(Arrays.asList(arrayNew.split(","))));
        }
    }
}
