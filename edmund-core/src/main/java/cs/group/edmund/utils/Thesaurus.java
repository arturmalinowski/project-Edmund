package cs.group.edmund.utils;


import cs.group.edmund.fixtures.HttpClient;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Thesaurus {

    private OfflineThesaurus offlineThesaurus = new OfflineThesaurus();

    public enum SynonymType {
        NOUN {
            public String toString() {
                return "noun";
            }
        },

        ADJECTIVE {
            public String toString() {
                return "adjective";
            }
        },

        VERB {
            public String toString() {
                return "verb";
            }
        }
    }

    public JSONObject getJSON(String word) {
        String url = "http://words.bighugelabs.com/api/2/ecdcfa6e1dd349d1d1f4c0755f8b4d1d/" + word + "/json";
        return new JSONObject(HttpClient.makeRequest(url));
    }

    public ArrayList<String> getAllSynonymsXML(String word) {
        if (offlineThesaurus.hasWord(word)) {
            return offlineThesaurus.results(word);
        }
        ArrayList<String> elementList = new ArrayList<>();
        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = reader.read("http://words.bighugelabs.com/api/2/ecdcfa6e1dd349d1d1f4c0755f8b4d1d/" + word.toLowerCase() + "/xml");
        } catch (Exception e) {
            if (e instanceof DocumentException) {
                offlineThesaurus.addNewQuery(word, elementList);
                return elementList;
            } else {
                System.out.println("Error parsing xml - synonyms");
                return elementList;
            }
        }

        Element root = document.getRootElement();

        for (Iterator i = root.elementIterator("w"); i.hasNext(); ) {
            Element element = (Element) i.next();
            elementList.add(element.getText());
        }

        offlineThesaurus.addNewQuery(word, elementList);

        return elementList;
    }

    public List<String> getSynonyms(SynonymType type, String word) {
        List<String> list = new ArrayList<>();
        try {
            JSONObject obj = getJSON(word);
            if (obj.has(type.toString())) {
                JSONArray arr = obj.getJSONObject(type.toString()).getJSONArray("syn");
                for (int i = 0; i < arr.length(); i++) {
                    list.add(arr.getString(i));
                }
                return list;
            } else {
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> getRelatedWordsJSON(String word) {
        List<String> list = new ArrayList<>();
        JSONObject obj = new JSONObject(HttpClient.makeRequest("http://project-shakespeare.herokuapp.com/shakespeare/api/word/" + word + "?format=json"));

        if (obj.has("synonyms")) {
            JSONArray arr = obj.getJSONArray("synonyms");
            for (int i = 0; i < arr.length(); i++) {
                list.add(arr.getString(i).toLowerCase());
            }
            return list;
        } else {
            return list;
        }
    }

    public List<String> getAllSynonyms(String word) {
        if (offlineThesaurus.hasWord(word)) {
            return offlineThesaurus.results(word);
        } else {
            List<String> list;
            list = getSynonyms(SynonymType.VERB, word);
            list.addAll(getSynonyms(SynonymType.ADJECTIVE, word));
            list.addAll(getSynonyms(SynonymType.NOUN, word));
            if (!list.isEmpty()) {
                offlineThesaurus.addNewQuery(word, list);
            }
            return list;
        }
    }

    public ArrayList<String> getRelatedWordsXML(String word) {
        ArrayList<String> elementList = new ArrayList<>();
        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = reader.read("http://project-shakespeare.herokuapp.com/shakespeare/api/word/" + word.toLowerCase());
        } catch (Exception e) {
            if (e instanceof DocumentException) {
                return elementList;
            } else {
                System.out.println("Error parsing xml - related words");
                return elementList;
            }
        }

        Element root = document.getRootElement();

        for (Iterator i = root.elementIterator("synonym"); i.hasNext(); ) {
            Element element = (Element) i.next();
            elementList.add(element.getText());
        }

        return elementList;
    }

    public ArrayList<String> getSynonymsOfRelatedWordsXML(String word) {
        ArrayList<String> relatedList = getRelatedWordsXML(word);
        ArrayList<String> synonymsList = new ArrayList<>();
        for (String w : relatedList) {
            synonymsList.addAll(getAllSynonymsXML(w));
        }
        return synonymsList;
    }

    public ArrayList<String> getRelatedWordsOfSynonymsXML(String word) {
        ArrayList<String> synonymsList = getAllSynonymsXML(word);
        ArrayList<String> relatedList = new ArrayList<>();
        for (String w : synonymsList) {
            relatedList.addAll(getRelatedWordsXML(w));
        }
        return relatedList;
    }
}
