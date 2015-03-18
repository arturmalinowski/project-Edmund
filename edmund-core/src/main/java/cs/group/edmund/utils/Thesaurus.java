package cs.group.edmund.utils;


import cs.group.edmund.fixtures.HttpClient;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Thesaurus {

    private OfflineThesaurus offlineThesaurus = new OfflineThesaurus();
    public static final String BIG_HUGE_LABS_API_KEY = "81fee77d8f082fbcc725c0684c190b28";

    Logger logger = LoggerFactory.getLogger("cs.group.edmund.utils");


    public ArrayList<String> getAllSynonymsXML(String word) {
        if (word.length() < 2) {
            if ((!(word.equals("a")) || !(word.equals("i"))) || !(word.equals("o")))
                return new ArrayList<>();
        }
        if (offlineThesaurus.hasWord(word)) {
            return offlineThesaurus.results(word);
        }
        ArrayList<String> elementList = new ArrayList<>();
        SAXReader reader = new SAXReader();
        Document document;
        String url = "http://words.bighugelabs.com/api/2/" + BIG_HUGE_LABS_API_KEY + "/" + word.toLowerCase().replace(" ", "%20") + "/xml";
        logger.info("Making http request to: " + url);
        String response = HttpClient.makeRequest(url);

        if (HttpClient.responseCode() == 404) {
            logger.info("Http response code: " + HttpClient.responseCode());

            offlineThesaurus.addNewQuery(word, elementList);
            return elementList;
        }

        if (HttpClient.responseCode() == 200) {
            try {
                logger.info("Http response code: " + HttpClient.responseCode());

                document = reader.read(new StringReader(response));
                Element root = document.getRootElement();

                for (Iterator i = root.elementIterator("w"); i.hasNext(); ) {
                    Element element = (Element) i.next();
                    elementList.add(element.getText());
                }
                offlineThesaurus.addNewQuery(word, elementList);

            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }

        return elementList;
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

    public ArrayList<String> getRelatedWordsXML(String word) {
        if (word.length() < 2) {
            if ((!(word.equals("a")) || !(word.equals("i"))) || !(word.equals("o")))
                return new ArrayList<>();
        }
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
