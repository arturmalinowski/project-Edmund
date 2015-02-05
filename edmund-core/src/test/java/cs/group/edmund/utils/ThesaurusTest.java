package cs.group.edmund.utils;


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
        ArrayList list = thesaurus.getAllSynonymsXML("face");
        assertThat(list.toString(), containsString("grimace"));
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

        ArrayList list = thesaurus.getAllSynonymsXML("stable");
        assertThat(list.contains("static"), is(true));
        assertThat(list.contains("stalls"), is(true));
        assertThat(list.contains("shelter"), is(true));
    }

    @Test
    public void relatedWordsJSONTest() {
        List list = thesaurus.getRelatedWordsJSON("nurture");

        assertThat(list.contains("school"), is(true));
    }

    @Test
    public void relatedWordsXMLTest() {
        List list = thesaurus.getRelatedWordsXML("nurture");

        assertThat(list.contains("school"), is(true));
    }

    @Test
    public void getSynonymsRelatedWordsXMLTest() {
        ArrayList<String> relatedWordsList = thesaurus.getRelatedWordsXML("bird");
        ArrayList<String> synonymsRelatedWordsList = thesaurus.getSynonymsOfRelatedWordsXML("bird");

        assertThat(true, is(relatedWordsList.size() < synonymsRelatedWordsList.size()));
    }

    @Test
    public void getRelatedWordsOfSynonymsXMLTest() {
        ArrayList<String> synonymsList = thesaurus.getAllSynonymsXML("hard");
        ArrayList<String> relatedWordsSynonymsList = thesaurus.getRelatedWordsOfSynonymsXML("hard");

        System.out.println(synonymsList);
        System.out.println(relatedWordsSynonymsList);


        assertThat(true, is(relatedWordsSynonymsList.size() > synonymsList.size()));
    }
}
