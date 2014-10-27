package cs.group.edmund.utils;


import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class ThesaurusTest {

    private static Thesaurus thesaurus = new Thesaurus();

    @Test
    public void XMLTest(){
        String answer = thesaurus.getXML("fork");
        assertThat(answer, containsString("cutlery"));
    }

    @Test
    public void JSONTest(){
        JSONObject answer = thesaurus.getJSON("monkey");
        JSONArray array =  answer.getJSONObject("noun").getJSONArray("syn");

        assertThat(array.toString(), containsString("primate"));
    }
}
