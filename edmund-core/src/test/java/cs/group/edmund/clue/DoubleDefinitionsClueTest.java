package cs.group.edmund.clue;

import cs.group.edmund.fixtures.HttpClient;
import cs.group.edmund.utils.Thesaurus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

public class DoubleDefinitionsClueTest {

    private DoubleDefinitionsClue clue;

    @Before
    public void setup() {
        clue = new DoubleDefinitionsClue();
    }

    @Test
    public void detectsTheDoubleDefinitionClue() {
        assertEquals(clue.isRelevant("Expensive honey"), true);
    }

    // enable answer length
    @Test
    public void limitedDoubleDefinitionClueCanBeSolved() {
        String solvedWord = clue.solve("Expensive sweet golden honey", 4);

        assertThat(solvedWord, is("dear"));
    }

    @Test
    public void handlesWhenNoMatchingWordIsFound() {
        String solvedWord = clue.solve("Blah blah bluegh", 4);

        assertThat(solvedWord, is("Answer not found :("));
    }

    @Ignore
    @Test
    public void correctLengthWordIsReturned() {
        String solvedWord = clue.solve("Yearn for quite a while", 4);
        assertThat(solvedWord, is("long"));
    }

    @Test
    public void thesaurusHttpConnectionOK() throws Exception {
        Thesaurus thesaurus = new Thesaurus();
        thesaurus.getXML("Honey");

        assertThat(HttpClient.responseCode(), is(200));
    }

}
