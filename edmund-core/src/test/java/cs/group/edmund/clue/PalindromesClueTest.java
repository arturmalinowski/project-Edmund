package cs.group.edmund.clue;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PalindromesClueTest {

    @Ignore
    @Test
    public void palindromesClueTestCanBeSolved() {
        Clue clue = new PalindromesClue();
        String solvedWord = clue.solve("Advance in either direction");

        assertThat(solvedWord, is("put up"));
    }
}
