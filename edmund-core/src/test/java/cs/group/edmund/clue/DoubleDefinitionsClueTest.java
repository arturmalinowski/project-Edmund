package cs.group.edmund.clue;

import cs.group.edmund.fixtures.HttpClient;
import org.junit.Before;
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
    public void basicDoubleDefinitionClueWithDefinitionsAddEitherEndCanBeSolved() {
        String solvedWord = clue.solve("Expensive sweet golden honey", 4);

        assertThat(solvedWord, is("dear"));
    }

    @Test
    public void thesaurusHttpConnectionOK() throws Exception {
        clue.sendGet("Honey");

        assertThat(HttpClient.responseCode(), is(200));
    }

}
