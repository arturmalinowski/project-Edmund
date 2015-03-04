package cs.group.edmund.utils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class OfflineThesaurusTest {

    private static OfflineThesaurus offlineThesaurus;

    @Before
    public void setup() {
        offlineThesaurus = new OfflineThesaurus();
    }

    @Test
    public void addNewQueryTest() {
        offlineThesaurus.addNewQuery("tennis", new ArrayList<String>() {{
            add("lawn tennis");
            add("court game");
        }});
        offlineThesaurus.readFromFile();
        List list = offlineThesaurus.results("tennis");

        assertThat(list.contains("court game"), is(true));
    }

    @Test
    public void displayResultsTest() {
        List list = offlineThesaurus.results("retards");

        assertThat(list.contains("idiot"), is(true));
    }

    @Test
    public void hasWordTest() {
        assertThat(offlineThesaurus.hasWord("retards"), is(true));
    }

    @Ignore
    // new words are added to the offline thesaurus so will only pass once with a certain word
    @Test
    public void checkIfWordWithNoResultsGetsSavedOfflineTest() {
        Boolean check = offlineThesaurus.hasWord("sharif");

        assertThat(check, is(false));
    }

}
