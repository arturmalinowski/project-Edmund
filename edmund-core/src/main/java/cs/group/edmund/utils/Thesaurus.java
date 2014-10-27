package cs.group.edmund.utils;


import cs.group.edmund.fixtures.HttpClient;
import org.json.*;

public class Thesaurus {

    public String getXML(String word) {
        String url = "http://words.bighugelabs.com/api/2/ecdcfa6e1dd349d1d1f4c0755f8b4d1d/" + word + "/xml";
        return HttpClient.makeRequest(url);
    }

    public JSONObject getJSON(String word) {
        String url = "http://words.bighugelabs.com/api/2/ecdcfa6e1dd349d1d1f4c0755f8b4d1d/" + word + "/json";
        JSONObject obj = new JSONObject(HttpClient.makeRequest(url));
        return obj;
    }
}
