package cs.group.edmund.utils;


import org.dom4j.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ThesaurusTest {

    private static Thesaurus thesaurus;

    @BeforeClass
    public static void setup() {
        thesaurus = new Thesaurus();
    }

    @Test
    public void xmlTest() {
        Document document = thesaurus.getSynonymsAsDocument("fork");
        ArrayList list = thesaurus.addSynonymsToList(document);
        assertThat(list.toString(), containsString("cutlery"));
    }

    @Test
    public void jsonTest() {
        JSONObject answer = thesaurus.getJSON("monkey");
        JSONArray array = answer.getJSONObject("noun").getJSONArray("syn");

        assertThat(array.toString(), containsString("primate"));
    }

    @Test
    public void nounTest() {
        List list = thesaurus.getSynonyms(Thesaurus.SynonymType.NOUN, "future");
        assertThat(list.contains("hereafter"), is(true));
        assertThat(list.contains("time to come"), is(true));
    }

    @Test
    public void adjectiveTest() {
        List list = thesaurus.getSynonyms(Thesaurus.SynonymType.ADJECTIVE, "future");
        assertThat(list.contains("next"), is(true));
        assertThat(list.contains("later"), is(true));
    }

    @Test
    public void verbTest() {
        List list = thesaurus.getSynonyms(Thesaurus.SynonymType.VERB, "time");
        assertThat(list.contains("clock"), is(true));
        assertThat(list.contains("schedule"), is(true));
    }

    @Test
    public void allSynonymsTest() {
        List list = thesaurus.getAllSynonyms("stable");
        assertThat(list.contains("static"), is(true));
        assertThat(list.contains("stalls"), is(true));
        assertThat(list.contains("shelter"), is(true));
    }

    @Test
    public void allSynonymsXmlTest() {
        Document document = thesaurus.getSynonymsAsDocument("Stable");

        ArrayList list = thesaurus.addSynonymsToList(document);
        assertThat(list.contains("static"), is(true));
        assertThat(list.contains("stalls"), is(true));
        assertThat(list.contains("shelter"), is(true));
    }

    @Test
    public void relatedWordsJSONTest() {
        List list = thesaurus.getRelatedWordsJSON("nurture");

        assertThat(list.contains("school"), is(true));
    }
}
