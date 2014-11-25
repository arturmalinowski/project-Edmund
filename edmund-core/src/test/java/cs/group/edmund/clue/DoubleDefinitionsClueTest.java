package cs.group.edmund.clue;

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

    @Test
    public void detectsClueIsNotADoubleDefinitionClue() {
        assertEquals(clue.isRelevant("Bird allowed outside tavern"), false);
    }

    @Test
    public void limitedDoubleDefinitionClueCanBeSolved() {
        String solvedWord = clue.solve("Expensive sweet golden honey", null, 4);

        assertThat(solvedWord, is("dear"));
    }

    @Test
    public void doubleDefinitionClueWithClueWordNotAtTheLeftEndOfTheClueCanBeSolved() {
        String solvedWord = clue.solve("Sweet expensive golden honey", null, 4);

        assertThat(solvedWord, is("dear"));
    }

    @Test
    public void doubleDefinitionClueWithClueWordNotAtTheRightEndOfTheClueCanBeSolved() {
        String solvedWord = clue.solve("Expensive sweet honey golden", null, 4);

        assertThat(solvedWord, is("dear"));
    }

    @Ignore
    @Test
    public void moreComplexDoubleDefinitionClueCanBeSolved() {
        String solvedWord = clue.solve("A fast train, say", null, 7);

        assertThat(solvedWord, is("Express"));
    }

    @Test
    public void handlesWhenNoMatchingWordIsFound() {
        String solvedWord = clue.solve("Bird allowed outside tavern", null, 4);

        assertThat(solvedWord, is("Answer not found :("));
    }

    @Test
    public void correctLengthWordIsReturned() {
        String solvedWord = clue.solve("Expensive expensive", null, 4);
        assertThat(solvedWord, is("dear"));
    }

}
