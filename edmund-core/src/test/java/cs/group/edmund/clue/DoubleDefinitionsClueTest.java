package cs.group.edmund.clue;

import cs.group.edmund.solver.ThesaurusHttpUrlConnection;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

public class DoubleDefinitionsClueTest {

    private Clue clue;

    @Before
    public void setup() {
        clue = new DoubleDefinitionsClue();
    }

    @Ignore
    @Test
    public void detectsTheDoubleDefinitionClue() {
        assertEquals(clue.isRelevant("Expensive honey"), true);
    }

    @Ignore
    @Test
    public void doubleDefinitionsClueTestCanBeSolved() {
        String solvedWord = clue.solve("Yearn for quite a while");

        assertThat(solvedWord, is("long"));
    }

    @Test
    public void thesaurusHttpConnectionOK() throws Exception {
        ThesaurusHttpUrlConnection httpUrlConnection = new ThesaurusHttpUrlConnection();
        httpUrlConnection.sendGet("Honey");

        assertThat(httpUrlConnection.responseCode(), is(200));
    }

}
