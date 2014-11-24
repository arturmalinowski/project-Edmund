package cs.group.edmund.utils;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class OfflineThesaurusTest {

    private static OfflineThesaurus offlineThesaurus;

    @BeforeClass
    public static void setup() {
        offlineThesaurus = new OfflineThesaurus();
    }

    @Test
    public void addNewQueryTest(){
        offlineThesaurus.addNewQuery("tennis", new ArrayList<String>(){{
            add("lawn tennis");
            add("court game");
        }});
        offlineThesaurus.readFromFile();
        List list = offlineThesaurus.results("tennis");

        assertThat(list.contains("court game"), is(true));
    }

    @Test
    public void displayResultsTest() {
        List list = offlineThesaurus.results("potter");

        assertThat(list.contains("thrower"), is(true));
    }

    @Test
    public void hasWordTest() {
        assertThat(offlineThesaurus.hasWord("potter"), is(true));
    }

}
