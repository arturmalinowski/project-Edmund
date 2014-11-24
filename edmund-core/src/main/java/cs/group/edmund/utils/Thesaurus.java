package cs.group.edmund.utils;


import cs.group.edmund.fixtures.HttpClient;
import org.dom4j.Document;
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
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read("http://words.bighugelabs.com/api/2/ecdcfa6e1dd349d1d1f4c0755f8b4d1d/" + word + "/xml");
        } catch (Exception e) {
            System.out.println("Error parsing xml");
        }

        Element root = document.getRootElement();
        ArrayList<String> elementList = new ArrayList<>();
        for (Iterator i = root.elementIterator("w"); i.hasNext(); ) {
            Element element = (Element) i.next();
            elementList.add(element.getText());
        }
        return elementList;
    }

    public List getSynonyms(SynonymType type, String word) {
        List list = new ArrayList<String>();
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

    public List getRelatedWords(String word) {
        List list = new ArrayList<String>();
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

    public List getAllSynonyms(String word) {
        if (offlineThesaurus.hasWord(word)) {
            return offlineThesaurus.results(word);
        }
        else {
            List list = new ArrayList<String>();
            list = getSynonyms(SynonymType.VERB, word);
            list.addAll(getSynonyms(SynonymType.ADJECTIVE, word));
            list.addAll(getSynonyms(SynonymType.NOUN, word));
            if (!list.isEmpty()){
                offlineThesaurus.addNewQuery(word, list);
            }
            return list;
        }
    }
}
