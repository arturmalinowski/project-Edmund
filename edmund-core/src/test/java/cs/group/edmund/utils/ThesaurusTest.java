package cs.group.edmund.utils;


import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

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

    @Test
    public void nounTest(){
        List list = thesaurus.getSynonyms(Thesaurus.SynonymType.NOUN, "future");
        assertThat(list.contains("hereafter"), is(true));
        assertThat(list.contains("time to come"), is(true));
    }

    @Test
    public void adjectiveTest(){
        List list = thesaurus.getSynonyms(Thesaurus.SynonymType.ADJECTIVE, "future");
        assertThat(list.contains("next"), is(true));
        assertThat(list.contains("later"), is(true));
    }

    @Test
    public void verbTest(){
        List list = thesaurus.getSynonyms(Thesaurus.SynonymType.VERB, "time");
        assertThat(list.contains("clock"), is(true));
        assertThat(list.contains("schedule"), is(true));
    }
}