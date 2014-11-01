package cs.group.edmund.clue;

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

    @Test
    public void correctLengthWordIsReturned() {
        String solvedWord = clue.solve("Expensive expensive", 4);
        assertThat(solvedWord, is("dear"));
    }

}
