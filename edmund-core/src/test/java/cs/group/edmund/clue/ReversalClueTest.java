package cs.group.edmund.clue;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ReversalClueTest {

    @Test
    public void detectsThatClueIsAReversal() {
        Clue clue = new ContainerClue();
        assertThat(clue.isRelevant("Physician brings fish round"), is(true));
    }

    @Test
    public void detectsThatClueIsNotAReversal() {
        Clue clue = new ContainerClue();
        assertThat(clue.isRelevant("Crazy flying mammals"), is(false));
    }

    @Ignore
    @Test
    public void reversalClueTestCanBeSolved() {
        Clue clue = new ReversalClue();
        String solvedWord = clue.solve("Are you an ogre?");

        assertThat(solvedWord, is("ergo"));
    }

}
