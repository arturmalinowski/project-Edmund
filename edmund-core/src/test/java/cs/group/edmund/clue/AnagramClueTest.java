package cs.group.edmund.clue;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AnagramClueTest {

    @Ignore
    @Test
    public void anagramClueTestCanBeSolved() {
        Clue clue = new AnagramClue();
        String solvedWord = clue.solve("Times when things appear obscure?");

        assertThat(solvedWord, is("nights"));
    }

}
