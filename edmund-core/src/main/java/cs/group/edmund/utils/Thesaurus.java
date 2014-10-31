package cs.group.edmund.utils;


import cs.group.edmund.fixtures.HttpClient;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.json.*;

import java.util.ArrayList;
import java.util.List;

public class Thesaurus {

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

    public String getXML(String word) {
        String url = "http://words.bighugelabs.com/api/2/ecdcfa6e1dd349d1d1f4c0755f8b4d1d/" + word + "/xml";
        return HttpClient.makeRequest(url);
    }


    public Document getSynonymsAsDocument(String word) throws Exception {
        SAXReader reader = new SAXReader();
        return reader.read("http://words.bighugelabs.com/api/2/ecdcfa6e1dd349d1d1f4c0755f8b4d1d/" + word + "/xml");
    }

    public JSONObject getJSON(String word) {
        String url = "http://words.bighugelabs.com/api/2/ecdcfa6e1dd349d1d1f4c0755f8b4d1d/" + word + "/json";
        return new JSONObject(HttpClient.makeRequest(url));
    }

    public List getSynonyms(SynonymType type, String word) {
        List list = new ArrayList<String>();
        JSONObject obj = getJSON(word);

        if (obj.has(type.toString())) {
            JSONArray arr = obj.getJSONObject(type.toString()).getJSONArray("syn");
            for (int i = 0; i < arr.length(); i++) {
                list.add(arr.getString(i));
            }
            return list;
        }
        else {
            return list;
        }

    }

    public List getAllSynonyms(String word) {
        List list = new ArrayList<String>();
        list = getSynonyms(SynonymType.VERB, word);
        list.addAll(getSynonyms(SynonymType.ADJECTIVE, word));
        list.addAll(getSynonyms(SynonymType.NOUN, word));

        return list;
    }

}
