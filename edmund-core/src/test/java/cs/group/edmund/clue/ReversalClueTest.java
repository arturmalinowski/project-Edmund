package cs.group.edmund.clue;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ReversalClueTest {

    @Test
    public void detectsThatClueIsAReversal() {
        Clue clue = new ReversalClue();
        assertThat(clue.isRelevant("Physician brings fish round"), is(true));
    }

    @Test
    public void detectsThatClueIsNotAReversal() {
        Clue clue = new ReversalClue();
        assertThat(clue.isRelevant("Crazy flying mammals"), is(false));
    }

    @Test
    public void simpleReversalClueCanBeSolved() {
        Clue clue = new ReversalClue();
        String solvedWord = clue.solve("Are you an ogre?", null, 4);

        assertThat(solvedWord, is("ergo"));
    }

    @Test
    public void reversalClueCanBeSolved() {
        Clue clue = new ReversalClue();
        String solvedWord = clue.solve("Physician brings fish round", null, 3);

        assertThat(solvedWord, is("doc"));

    }
}
